package online.remind.remind.entity.attacks;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import online.kingdomkeys.kingdomkeys.data.WorldData;
import online.kingdomkeys.kingdomkeys.lib.Party;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.entity.ModEntitiesRM;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.registry.entries.EpicFightParticles;
import yesman.epicfight.registry.entries.EpicFightSounds;

public class slidingDashCollider extends ThrowableProjectile {

    private LivingEntity caster;
    private float damage;

    private int maxTicks = 10;
    private int hits = 0;
    private int maxHits = 2;

    private double lastCasterX;
    private double lastCasterY;
    private double lastCasterZ;
    private boolean hasLastCasterPos = false;

    public slidingDashCollider(
            EntityType<? extends ThrowableProjectile> type,
            Level level
    ) {
        super(type, level);

        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData(
            SynchedEntityData.Builder builder
    ) {

    }

    public slidingDashCollider(
            Level level,
            LivingEntity caster,
            float damage
    ) {
        this(
                ModEntitiesRM.TYPE_QUICK_BLITZ.get(),
                level
        );

        this.caster = caster;
        this.damage = damage;

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

        /*
         * Trail particles.
         */
        if (tickCount > 1
                && caster.level() instanceof ServerLevel serverLevel) {

            serverLevel.sendParticles(
                    ParticleTypes.CRIT,
                    caster.getX(),
                    caster.getY() + 1.0D,
                    caster.getZ(),
                    1,
                    0.0D,
                    0.0D,
                    0.0D,
                    0.0D
            );
        }

        /*
         * Damage/collision only needs to happen server-side.
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

        double moveX = currentX - lastCasterX;
        double moveY = currentY - lastCasterY;
        double moveZ = currentZ - lastCasterZ;

        /*
         * Swept hitbox:
         *
         * Covers the space the player traveled through between
         * the previous tick and this tick.
         *
         * Much more reliable than simply inflating the hitbox
         * several blocks in every direction.
         */
        AABB hitBox = caster.getBoundingBox()
                .expandTowards(
                        -moveX,
                        -moveY,
                        -moveZ
                )
                .inflate(
                        0.30D,
                        0.20D,
                        0.30D
                );

        for (Entity entity : level().getEntities(
                caster,
                hitBox,
                e -> e instanceof LivingEntity && e != caster
        )) {

            if (!(entity instanceof LivingEntity target)) {
                continue;
            }

            if (!canHitTarget(target)) {
                continue;
            }

            hitTarget(target);

            if (this.isRemoved()) {
                return;
            }
        }

        lastCasterX = currentX;
        lastCasterY = currentY;
        lastCasterZ = currentZ;

        super.tick();
    }

    private boolean canHitTarget(LivingEntity target) {

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

    private void hitTarget(LivingEntity target) {

        target.invulnerableTime = 0;

        boolean damaged;

        /*
         * Epic Fight workaround:
         *
         * SWORD_DASH is allowed to handle the visuals,
         * but the Re:Mind collider remains responsible
         * for actual damage.
         *
         * Don't associate this hit with the player's
         * current Epic Fight attack while EFM is loaded.
         */
        if (KingdomKeysReMind.efmLoaded) {

            damaged = target.hurt(
                    caster.damageSources().magic(),
                    damage
            );

        } else {

            damaged = target.hurt(
                    caster.damageSources().mobAttack(caster),
                    damage
            );
        }

        target.invulnerableTime = 0;

        if (!damaged) {
            return;
        }

        hits++;

        caster.setDeltaMovement(
                0.0D,
                0.0D,
                0.0D
        );

        caster.swing(
                InteractionHand.MAIN_HAND
        );

        applyHitEffects(target);

        if (hits >= maxHits) {
            remove(RemovalReason.KILLED);
        }
    }

    private void applyHitEffects(LivingEntity target) {

        Level targetLevel = target.level();

        targetLevel.playSound(
                null,
                target.blockPosition(),
                SoundEvents.PLAYER_ATTACK_STRONG,
                SoundSource.PLAYERS,
                1.0F,
                1.0F
        );

        if (!(targetLevel instanceof ServerLevel serverLevel)) {
            return;
        }

        if (KingdomKeysReMind.efmLoaded) {

            EpicFightParticles.HIT_BLADE.get()
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
                    1.0F
            );

        } else {

            serverLevel.sendParticles(
                    ParticleTypes.CRIT,
                    target.getX(),
                    target.getY()
                            + target.getBbHeight() * 0.5D,
                    target.getZ(),
                    5,
                    0.25D,
                    0.25D,
                    0.25D,
                    0.05D
            );
        }
    }

    @Override
    protected void readAdditionalSaveData(
            CompoundTag tag
    ) {
        this.damage = tag.getFloat("Damage");
        this.hits = tag.getInt("Hits");
    }

    @Override
    protected void addAdditionalSaveData(
            CompoundTag tag
    ) {
        tag.putFloat("Damage", this.damage);
        tag.putInt("Hits", this.hits);
    }
}