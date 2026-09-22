package online.remind.remind.entity.attacks;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
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
import online.remind.remind.integration.epicfight.EpicFightEvents;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.registry.entries.EpicFightParticles;
import yesman.epicfight.registry.entries.EpicFightSounds;

public class SonicBladeCollider extends ThrowableProjectile {

    private LivingEntity caster;
    private float damage;
    private int chainStep;
    private int maxTicks = 8;

    public SonicBladeCollider(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setBoundingBox(new AABB(
                -0.5D,
                0.0D,
                -0.5D,
                0.5D,
                1.5D,
                0.5D
        ));
    }

    public SonicBladeCollider(Level level, LivingEntity caster, float damage, int chainStep) {
        this(ModEntitiesRM.TYPE_SONIC_BLADE.get(), level);

        this.caster = caster;
        this.damage = damage;
        this.chainStep = chainStep;

        this.setOwner(caster);

        this.setPos(
                caster.getX(),
                caster.getY() + 0.5D,
                caster.getZ()
        );
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

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

        /*
         * Follow the player during the Sonic Blade dash.
         *
         * The actual dash movement is handled by the attack command,
         * just like Blitz.
         */
        this.setPos(
                caster.getX(),
                caster.getY() + 0.5D,
                caster.getZ()
        );

        spawnTrailParticles();

        AABB hitBox = caster.getBoundingBox()
                .expandTowards(caster.getLookAngle().scale(1.25D))
                .inflate(0.35D, 0.25D, 0.35D);

        for (Entity entity : level().getEntities(
                this,
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
            return;
        }

        super.tick();
    }

    private boolean canHitTarget(LivingEntity target) {
        Party party = null;

        if (getOwner() != null && getOwner().getServer() != null) {
            party = WorldData
                    .get(getOwner().getServer())
                    .getPartyFromMember(getOwner().getUUID());
        }

        return party == null
                || party.getMember(target.getUUID()) == null
                || party.getFriendlyFire();
    }

    private void hitTarget(LivingEntity target) {

        if (level().isClientSide) {
            return;
        }

        target.invulnerableTime = 0;

        /*
         * IMPORTANT:
         *
         * When Epic Fight is installed, don't attach the player
         * as the causing entity for this damage.
         *
         * Otherwise Epic Fight can replace/intercept the damage
         * using the player's current attack animation state.
         *
         * The COLLIDER owns the damage for these commands.
         */
        boolean damaged;

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

        applyHitEffects(target);
        openChainWindow();

        caster.setDeltaMovement(
                0.0D,
                0.0D,
                0.0D
        );

        caster.swing(InteractionHand.MAIN_HAND);

        remove(RemovalReason.KILLED);
    }

    private void openChainWindow() {
        if (!(caster instanceof Player player)) {
            return;
        }

        /*
         * chainStep:
         *
         * 0 = initial Sonic Blade
         * 1 = follow-up 1
         * 2 = follow-up 2
         * 3 = follow-up 3
         * 4 = follow-up 4
         * 5 = follow-up 5
         * 6 = follow-up 6 / final hit
         *
         * Initial + six follow-ups = seven total charges.
         */
        int maxChainStep = 6;

        if (chainStep >= maxChainStep) {

            player.removeEffect(
                    ModMobEffectsRM.SONIC_BLADE_CHAIN
            );

            return;
        }

        /*
         * Give the player a short reaction-command window.
         *
         * Amplifier stores which follow-up comes next.
         */
        player.addEffect(new MobEffectInstance(
                ModMobEffectsRM.SONIC_BLADE_CHAIN,
                18, // 0.9 second timing window
                chainStep + 1,
                false,
                false,
                false
        ));

    }

    private void spawnTrailParticles() {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }

        /*
         * Normal rush trail.
         */
        serverLevel.sendParticles(
                ParticleTypes.CRIT,
                caster.getX(),
                caster.getY() + 1.0D,
                caster.getZ(),
                4,
                0.35D,
                0.25D,
                0.35D,
                0.05D
        );

        /*
         * Give later Sonic Blade charges a little more visual weight.
         */
        if (chainStep >= 3) {
            serverLevel.sendParticles(
                    ParticleTypes.SONIC_BOOM,
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
    }

    private void applyHitEffects(LivingEntity target) {
        Level level = target.level();

        boolean finisher = chainStep >= 6;

        level.playSound(
                null,
                target.blockPosition(),
                finisher
                        ? SoundEvents.PLAYER_ATTACK_CRIT
                        : SoundEvents.PLAYER_ATTACK_STRONG,
                SoundSource.PLAYERS,
                finisher ? 1.2F : 1.0F,
                finisher ? 1.25F : 1.0F
        );

        if (level instanceof ServerLevel serverLevel) {

            serverLevel.sendParticles(
                    ParticleTypes.SWEEP_ATTACK,
                    target.getX(),
                    target.getY() + target.getBbHeight() * 0.5D,
                    target.getZ(),
                    finisher ? 3 : 1,
                    0.25D,
                    0.25D,
                    0.25D,
                    0.0D
            );

            serverLevel.sendParticles(
                    ParticleTypes.CRIT,
                    target.getX(),
                    target.getY() + target.getBbHeight() * 0.5D,
                    target.getZ(),
                    20 + chainStep * 5,
                    0.45D,
                    0.45D,
                    0.45D,
                    0.15D
            );

            if (KingdomKeysReMind.efmLoaded) {

                EpicFightParticles.HIT_BLADE.get()
                        .spawnParticleWithArgument(
                                serverLevel,
                                HitParticleType.RANDOM_WITHIN_BOUNDING_BOX,
                                HitParticleType.ZERO,
                                target,
                                target
                        );

                target.level().playSound(
                        null,
                        target.blockPosition(),
                        EpicFightSounds.BLADE_HIT.get(),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                );
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.damage = tag.getFloat("Damage");
        this.chainStep = tag.getInt("ChainStep");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putFloat("Damage", this.damage);
        tag.putInt("ChainStep", this.chainStep);
    }
}