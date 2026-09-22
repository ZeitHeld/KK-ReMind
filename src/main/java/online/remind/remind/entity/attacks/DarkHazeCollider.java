package online.remind.remind.entity.attacks;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import online.kingdomkeys.kingdomkeys.data.WorldData;
import online.kingdomkeys.kingdomkeys.lib.Party;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.effect.ModMobEffectsRM;
import online.remind.remind.entity.ModEntitiesRM;
import org.joml.Vector3f;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.registry.entries.EpicFightParticles;
import yesman.epicfight.registry.entries.EpicFightSounds;

public class DarkHazeCollider extends ThrowableProjectile {

    private LivingEntity caster;

    private float damage;
    private float doomChance;

    private int maxTicks = 10;

    private double lastCasterX;
    private double lastCasterY;
    private double lastCasterZ;

    private boolean hasLastCasterPos = false;

    public DarkHazeCollider(
            EntityType<? extends ThrowableProjectile> type,
            Level level
    ) {
        super(type, level);

        this.noPhysics = true;
    }

    public DarkHazeCollider(
            Level level,
            LivingEntity caster,
            float damage,
            float doomChance
    ) {
        this(
                ModEntitiesRM.TYPE_DARK_HAZE.get(),
                level
        );

        this.caster = caster;
        this.damage = damage;
        this.doomChance = doomChance;

        this.setOwner(caster);

        this.setPos(
                caster.getX(),
                caster.getY() + 0.5D,
                caster.getZ()
        );

        this.lastCasterX = caster.getX();
        this.lastCasterY = caster.getY();
        this.lastCasterZ = caster.getZ();

        this.hasLastCasterPos = true;
    }

    @Override
    protected void defineSynchedData(
            SynchedEntityData.Builder builder
    ) {

    }

    @Override
    public void tick() {

        if (caster == null || !caster.isAlive()) {
            remove(RemovalReason.KILLED);
            return;
        }

        if (this.tickCount > maxTicks) {
            remove(RemovalReason.KILLED);
            return;
        }

        this.setOwner(caster);

        this.setPos(
                caster.getX(),
                caster.getY() + 0.5D,
                caster.getZ()
        );

        spawnTrailParticles();

        /*
         * Collision/damage only needs to happen server-side.
         */
        if (level().isClientSide) {
            super.tick();
            return;
        }

        double currentX = caster.getX();
        double currentY = caster.getY();
        double currentZ = caster.getZ();

        if (!hasLastCasterPos) {
            lastCasterX = currentX;
            lastCasterY = currentY;
            lastCasterZ = currentZ;

            hasLastCasterPos = true;
        }

        double moveX =
                currentX - lastCasterX;

        double moveY =
                currentY - lastCasterY;

        double moveZ =
                currentZ - lastCasterZ;

        /*
         * Swept hitbox.
         *
         * Dark Haze is fast enough that checking only the
         * caster's current bounding box could skip smaller mobs.
         */
        AABB hitBox =
                caster.getBoundingBox()
                        .expandTowards(
                                -moveX,
                                -moveY,
                                -moveZ
                        )
                        .inflate(
                                0.35D,
                                0.20D,
                                0.35D
                        );

        for (Entity entity : level().getEntities(
                caster,
                hitBox,
                e -> e instanceof LivingEntity
                        && e != caster
        )) {

            if (!(entity instanceof LivingEntity target)) {
                continue;
            }

            if (!canHitTarget(target)) {
                continue;
            }

            hitTarget(target);
            return;
        }

        lastCasterX = currentX;
        lastCasterY = currentY;
        lastCasterZ = currentZ;

        super.tick();
    }

    private boolean canHitTarget(
            LivingEntity target
    ) {

        Party party = null;

        if (getOwner() != null
                && getOwner().getServer() != null) {

            party = WorldData
                    .get(getOwner().getServer())
                    .getPartyFromMember(
                            getOwner().getUUID()
                    );
        }

        return party == null
                || party.getMember(target.getUUID()) == null
                || party.getFriendlyFire();
    }

    private void hitTarget(
            LivingEntity target
    ) {

        target.invulnerableTime = 0;

        boolean damaged;

        /*
         * Same pattern as Sliding Dash / Sonic Blade:
         *
         * Epic Fight handles visuals.
         * The collider owns the actual damage.
         */
        if (KingdomKeysReMind.efmLoaded) {

            damaged = target.hurt(
                    caster.damageSources().magic(),
                    damage
            );

        } else {

            damaged = target.hurt(
                    caster.damageSources().indirectMagic(
                            this,
                            caster
                    ),
                    damage
            );
        }

        target.invulnerableTime = 0;

        if (!damaged) {
            return;
        }

        /*
         * Roll Doom only after a successful hit.
         */
        tryApplyDoom(target);

        applyHitEffects(target);

        caster.setDeltaMovement(
                0.0D,
                0.0D,
                0.0D
        );

        caster.swing(
                InteractionHand.MAIN_HAND
        );

        /*
         * Dark Haze is one-and-done.
         */
        remove(RemovalReason.KILLED);
    }

    private void tryApplyDoom(
            LivingEntity target
    ) {

        if (level().random.nextFloat() >= doomChance) {
            return;
        }

        /*
         * 5 seconds = 100 ticks.
         *
         * The actual kill behavior will live inside
         * the DOOM effect implementation.
         */
        target.addEffect(
                new MobEffectInstance(
                        ModMobEffectsRM.DOOM,
                        100,
                        0,
                        false,
                        true,
                        true
                )
        );
    }

    private void spawnTrailParticles() {

        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }

        /*
         * Dark Haze:
         * black shadow cloud + sharp white energy.
         */
        DustParticleOptions blackDust =
                new DustParticleOptions(
                        new Vector3f(
                                0.02F,
                                0.02F,
                                0.02F
                        ),
                        1.35F
                );

        DustParticleOptions whiteDust =
                new DustParticleOptions(
                        new Vector3f(
                                1.0F,
                                1.0F,
                                1.0F
                        ),
                        1.0F
                );

        /*
         * Thick black body of the charge.
         */
        serverLevel.sendParticles(
                blackDust,
                caster.getX(),
                caster.getY() + 1.0D,
                caster.getZ(),
                10,
                0.35D,
                0.40D,
                0.35D,
                0.015D
        );

        /*
         * White streaks mixed through the darkness.
         */
        serverLevel.sendParticles(
                whiteDust,
                caster.getX(),
                caster.getY() + 1.0D,
                caster.getZ(),
                6,
                0.25D,
                0.30D,
                0.25D,
                0.025D
        );

        /*
         * Some smoke gives the black section more volume.
         */
        serverLevel.sendParticles(
                ParticleTypes.LARGE_SMOKE,
                caster.getX(),
                caster.getY() + 1.0D,
                caster.getZ(),
                3,
                0.30D,
                0.35D,
                0.30D,
                0.01D
        );
    }

    private void applyHitEffects(
            LivingEntity target
    ) {

        Level targetLevel =
                target.level();

        targetLevel.playSound(
                null,
                target.blockPosition(),
                SoundEvents.PLAYER_ATTACK_STRONG,
                SoundSource.PLAYERS,
                1.1F,
                0.70F
        );

        if (!(targetLevel instanceof ServerLevel serverLevel)) {
            return;
        }

        serverLevel.sendParticles(
                ParticleTypes.LARGE_SMOKE,
                target.getX(),
                target.getY()
                        + target.getBbHeight() * 0.5D,
                target.getZ(),
                10,
                0.35D,
                0.35D,
                0.35D,
                0.05D
        );

        if (KingdomKeysReMind.efmLoaded) {

            EpicFightParticles.HIT_BLADE
                    .get()
                    .spawnParticleWithArgument(
                            serverLevel,
                            HitParticleType.RANDOM_WITHIN_BOUNDING_BOX,
                            HitParticleType.ZERO,
                            target,
                            target
                    );

            targetLevel.playSound(
                    null,
                    target.blockPosition(),
                    EpicFightSounds.BLADE_HIT.get(),
                    SoundSource.PLAYERS,
                    1.0F,
                    0.75F
            );
        }
    }

    @Override
    protected void readAdditionalSaveData(
            CompoundTag tag
    ) {

        this.damage =
                tag.getFloat("Damage");

        this.doomChance =
                tag.getFloat("DoomChance");
    }

    @Override
    protected void addAdditionalSaveData(
            CompoundTag tag
    ) {

        tag.putFloat(
                "Damage",
                this.damage
        );

        tag.putFloat(
                "DoomChance",
                this.doomChance
        );
    }
}