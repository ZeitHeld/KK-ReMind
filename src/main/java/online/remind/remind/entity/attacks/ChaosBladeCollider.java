package online.remind.remind.entity.attacks;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import online.kingdomkeys.kingdomkeys.data.WorldData;
import online.kingdomkeys.kingdomkeys.lib.Party;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.effect.ModMobEffectsRM;
import online.remind.remind.entity.ModEntitiesRM;
import online.remind.remind.magic.attacks.attackChaosBlade;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.registry.entries.EpicFightParticles;
import yesman.epicfight.registry.entries.EpicFightSounds;

public class ChaosBladeCollider extends ThrowableProjectile {

    private LivingEntity caster;

    private float damage;
    private int chainStep;

    private int maxTicks = 8;

    /*
     * Initial hit = step 0
     * Follow-ups = steps 1 through 9
     *
     * Total possible hits = 10.
     */
    private static final int MAX_CHAIN_STEP = 9;

    /*
     * BBS Chaos Blade:
     * 50% chance of Blind/Bind.
     */
    private static final float STATUS_CHANCE = 0.50F;

    /*
     * Roughly 3.3 seconds.
     *
     * 20 ticks/sec * 3.3 = 66 ticks.
     */
    private static final int STATUS_DURATION = 66;

    private double lastCasterX;
    private double lastCasterY;
    private double lastCasterZ;

    private boolean hasLastCasterPos = false;

    public ChaosBladeCollider(
            EntityType<? extends ThrowableProjectile> type,
            Level level
    ) {
        super(type, level);

        this.noPhysics = true;
    }

    public ChaosBladeCollider(
            Level level,
            LivingEntity caster,
            float damage,
            int chainStep
    ) {
        this(
                ModEntitiesRM.TYPE_CHAOS_BLADE.get(),
                level
        );

        this.caster = caster;
        this.damage = damage;
        this.chainStep = chainStep;

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

        /*
         * Stored Chaos Blade target disappeared/died.
         * Kill the chain.
         */
        LivingEntity storedTarget =
                attackChaosBlade.getStoredTarget(caster);

        if (storedTarget == null) {
            endChaosBlade();
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
         * Actual damage checks happen server-side.
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
         * Chaos Blade moves very quickly, so checking only the
         * player's current position could skip straight over
         * smaller mobs.
         */
        AABB hitBox =
                caster.getBoundingBox()
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
                e -> e instanceof LivingEntity
                        && e != caster
        )) {

            if (!(entity instanceof LivingEntity target)) {
                continue;
            }

            /*
             * Chaos Blade preserves its original target.
             *
             * Don't let the dash accidentally hit some random
             * mob standing beside the intended enemy.
             */
            if (target != storedTarget) {
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
         * Same collider-owned Epic Fight workaround
         * we're using for the dash commands.
         *
         * Epic Fight = animation
         * Collider   = damage
         */
        if (KingdomKeysReMind.efmLoaded) {

            damaged = target.hurt(
                    caster.damageSources().magic(),
                    getDamageForThisHit()
            );

        } else {

            damaged = target.hurt(
                    caster.damageSources().indirectMagic(
                            this,
                            caster
                    ),
                    getDamageForThisHit()
            );
        }

        target.invulnerableTime = 0;

        if (!damaged) {
            return;
        }

        /*
         * Chaos Blade status proc.
         */
        tryApplyStatus(target);

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
         * Either reopen the RC or finish the chain.
         */
        openChainWindow();

        remove(RemovalReason.KILLED);
    }

    private float getDamageForThisHit() {

        /*
         * The final Chaos Blade thrust is stronger.
         *
         * BBS also gives the last hit increased power.
         */
        if (chainStep >= MAX_CHAIN_STEP) {
            return damage * 1.55F;
        }

        return damage;
    }

    private void tryApplyStatus(
            LivingEntity target
    ) {

        /*
         * 50% total chance to inflict a status.
         */
        if (level().random.nextFloat() >= STATUS_CHANCE) {
            return;
        }

        /*
         * Once the proc succeeds:
         *
         * 50/50 between Blind and Bind.
         */
        boolean blind =
                level().random.nextBoolean();

        if (blind) {

            target.addEffect(
                    new MobEffectInstance(
                            MobEffects.BLINDNESS,
                            STATUS_DURATION,
                            0,
                            false,
                            true,
                            true
                    )
            );

        } else {

            /*
             * Temporary Bind implementation:
             *
             * Maximum Slowness effectively roots the target.
             *
             * If/when we use your own proper BIND effect,
             * this is the one section we'll replace.
             */
            target.addEffect(
                    new MobEffectInstance(
                            MobEffects.MOVEMENT_SLOWDOWN,
                            STATUS_DURATION,
                            255,
                            false,
                            true,
                            true
                    )
            );
        }
    }

    private void openChainWindow() {

        if (!(caster instanceof Player player)) {
            return;
        }

        /*
         * Step 9 is the final follow-up.
         */
        if (chainStep >= MAX_CHAIN_STEP) {

            player.removeEffect(
                    ModMobEffectsRM.CHAOS_BLADE_CHAIN
            );

            attackChaosBlade.clearChaosBladeData(
                    player
            );

            player.displayClientMessage(
                    Component.literal(
                                    "Chaos Blade Finish!"
                            )
                            .withColor(0x6F35A5),
                    true
            );

            return;
        }

        /*
         * Store the NEXT chain step in the amplifier.
         */
        player.addEffect(
                new MobEffectInstance(
                        ModMobEffectsRM.CHAOS_BLADE_CHAIN,
                        18,
                        chainStep + 1,
                        false,
                        false,
                        false
                )
        );

        player.displayClientMessage(
                Component.literal(
                                "Chaos Blade!"
                        )
                        .withColor(0x6F35A5),
                true
        );
    }

    private void endChaosBlade() {

        if (caster instanceof Player player) {

            player.removeEffect(
                    ModMobEffectsRM.CHAOS_BLADE_CHAIN
            );

            attackChaosBlade.clearChaosBladeData(
                    player
            );
        }

        remove(RemovalReason.KILLED);
    }

    private void spawnTrailParticles() {

        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }

        /*
         * Dark / teleport-like trail.
         */
        serverLevel.sendParticles(
                ParticleTypes.REVERSE_PORTAL,
                caster.getX(),
                caster.getY() + 1.0D,
                caster.getZ(),
                5,
                0.30D,
                0.35D,
                0.30D,
                0.02D
        );

        serverLevel.sendParticles(
                ParticleTypes.WITCH,
                caster.getX(),
                caster.getY() + 1.0D,
                caster.getZ(),
                2,
                0.20D,
                0.25D,
                0.20D,
                0.01D
        );
    }

    private void applyHitEffects(
            LivingEntity target
    ) {

        Level targetLevel =
                target.level();

        boolean finisher =
                chainStep >= MAX_CHAIN_STEP;

        targetLevel.playSound(
                null,
                target.blockPosition(),
                finisher
                        ? SoundEvents.PLAYER_ATTACK_CRIT
                        : SoundEvents.PLAYER_ATTACK_STRONG,
                SoundSource.PLAYERS,
                finisher ? 1.2F : 1.0F,
                finisher ? 0.75F : 0.9F
        );

        if (!(targetLevel instanceof ServerLevel serverLevel)) {
            return;
        }

        serverLevel.sendParticles(
                ParticleTypes.REVERSE_PORTAL,
                target.getX(),
                target.getY()
                        + target.getBbHeight() * 0.5D,
                target.getZ(),
                finisher ? 30 : 15,
                0.40D,
                0.50D,
                0.40D,
                0.10D
        );

        serverLevel.sendParticles(
                ParticleTypes.CRIT,
                target.getX(),
                target.getY()
                        + target.getBbHeight() * 0.5D,
                target.getZ(),
                finisher ? 25 : 12,
                0.35D,
                0.35D,
                0.35D,
                0.12D
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
                    0.85F
            );
        }
    }

    @Override
    protected void readAdditionalSaveData(
            CompoundTag tag
    ) {

        this.damage =
                tag.getFloat("Damage");

        this.chainStep =
                tag.getInt("ChainStep");
    }

    @Override
    protected void addAdditionalSaveData(
            CompoundTag tag
    ) {

        tag.putFloat(
                "Damage",
                this.damage
        );

        tag.putInt(
                "ChainStep",
                this.chainStep
        );
    }
}