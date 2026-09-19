package online.remind.remind.magic.attacks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.entity.attacks.ChaosBladeCollider;

public class attackChaosBlade extends Magic {

    public static final String CHAOS_BLADE_DAMAGE =
            "kkremind_chaos_blade_damage";

    public static final String CHAOS_BLADE_TARGET =
            "kkremind_chaos_blade_target";

    public attackChaosBlade(
            ResourceLocation registryName,
            boolean hasToSelect,
            int tier,
            ResourceLocation gmAbility
    ) {
        super(registryName, hasToSelect, gmAbility);
        setTier(tier);
    }

    @Override
    public void magicUse(
            LivingEntity player,
            LivingEntity caster,
            float fullMPBlastMult,
            LivingEntity lockOnEntity
    ) {

        /*
         * Chaos Blade needs a target because the teleport
         * is positioned relative to that target.
         */
        if (lockOnEntity == null || !lockOnEntity.isAlive()) {
            return;
        }

        float dmg = switch (getTier()) {
            case 0 -> casterStrengthStat(caster) * 1.15F;
            case 1 -> casterStrengthStat(caster) * 1.35F;
            case 2 -> casterStrengthStat(caster) * 1.55F;
            default -> casterStrengthStat(caster);
        };

        dmg *= fullMPBlastMult;

        /*
         * Preserve the original calculated damage for every
         * Reaction Command follow-up.
         */
        caster.getPersistentData().putFloat(
                CHAOS_BLADE_DAMAGE,
                dmg
        );

        /*
         * Preserve the target for the entire chain.
         */
        caster.getPersistentData().putUUID(
                CHAOS_BLADE_TARGET,
                lockOnEntity.getUUID()
        );

        /*
         * Initial Chaos Blade.
         *
         * Teleport first, THEN dash.
         */
        startChaosBladeStep(
                caster,
                lockOnEntity,
                0
        );

        ChaosBladeCollider collider =
                new ChaosBladeCollider(
                        caster.level(),
                        caster,
                        dmg,
                        0
                );

        caster.level().addFreshEntity(collider);
    }

    /**
     * Performs one Chaos Blade movement sequence.
     *
     * Teleport -> face target -> dash through target.
     */
    public static void startChaosBladeStep(
            LivingEntity caster,
            LivingEntity target,
            int chainStep
    ) {

        teleportForChaosBlade(
                caster,
                target,
                chainStep
        );

        launchChaosBladeDash(
                caster,
                target,
                chainStep
        );
    }

    /**
     * Teleports the caster to alternating sides of the target.
     *
     * Even steps = behind target
     * Odd steps  = in front of target
     */
    public static void teleportForChaosBlade(
            LivingEntity caster,
            LivingEntity target,
            int chainStep
    ) {

        double teleportDistance = 3.0D;

        /*
         * Each Chaos Blade step teleports to a different
         * angle around the target.
         *
         * Using 137.5 degrees keeps the positions spread out
         * instead of simply bouncing between two points.
         */
        double angleDegrees =
                (target.getYRot() + 180.0D + chainStep * 137.5D) % 360.0D;

        double angleRadians =
                Math.toRadians(angleDegrees);

        double x =
                target.getX()
                        + Math.sin(angleRadians)
                        * teleportDistance;

        double z =
                target.getZ()
                        - Math.cos(angleRadians)
                        * teleportDistance;

        caster.teleportTo(
                x,
                target.getY(),
                z
        );

        caster.setDeltaMovement(
                Vec3.ZERO
        );

        caster.fallDistance = 0.0F;

        /*
         * Immediately turn toward the target so the following
         * dash goes directly through them.
         */
        faceTarget(
                caster,
                target
        );
    }

    /**
     * Launches the caster directly toward the preserved target.
     */
    public static void launchChaosBladeDash(
            LivingEntity caster,
            LivingEntity target,
            int chainStep
    ) {

        double speed = switch (chainStep) {
            case 0 -> 1.70D;
            case 1 -> 1.80D;
            case 2 -> 1.90D;
            case 3 -> 2.00D;
            case 4 -> 2.10D;
            case 5 -> 2.20D;
            default -> 1.70D;
        };

        Vec3 casterPos = caster.position();

        Vec3 targetPos = new Vec3(
                target.getX(),
                caster.getY(),
                target.getZ()
        );

        Vec3 direction =
                targetPos.subtract(casterPos);

        if (direction.lengthSqr() < 0.001D) {
            direction = caster.getLookAngle();
        }

        direction = new Vec3(
                direction.x,
                0.0D,
                direction.z
        ).normalize();

        double dx = direction.x * speed;
        double dz = direction.z * speed;

        caster.hurtMarked = true;
        caster.fallDistance = 0.0F;

        /*
         * Keep the same Epic Fight movement compensation
         * we've been using for the other dash commands.
         */
        if (KingdomKeysReMind.efmLoaded) {

            caster.setDeltaMovement(
                    dx / 2.25D,
                    0.0D,
                    dz / 2.25D
            );

        } else {

            caster.setDeltaMovement(
                    dx,
                    0.0D,
                    dz
            );
        }
    }

    /**
     * Gets the target that was saved when Chaos Blade began.
     */
    public static LivingEntity getStoredTarget(
            LivingEntity caster
    ) {

        if (!(caster.level() instanceof ServerLevel serverLevel)) {
            return null;
        }

        if (!caster.getPersistentData()
                .hasUUID(CHAOS_BLADE_TARGET)) {
            return null;
        }

        Entity entity = serverLevel.getEntity(
                caster.getPersistentData()
                        .getUUID(CHAOS_BLADE_TARGET)
        );

        if (!(entity instanceof LivingEntity target)) {
            return null;
        }

        if (!target.isAlive() || target.isRemoved()) {
            return null;
        }

        return target;
    }

    /**
     * Clears everything associated with the current Chaos Blade chain.
     */
    public static void clearChaosBladeData(
            LivingEntity caster
    ) {

        caster.getPersistentData().remove(
                CHAOS_BLADE_DAMAGE
        );

        caster.getPersistentData().remove(
                CHAOS_BLADE_TARGET
        );
    }

    private static void faceTarget(
            LivingEntity caster,
            LivingEntity target
    ) {

        double dx =
                target.getX() - caster.getX();

        double dy =
                target.getEyeY() - caster.getEyeY();

        double dz =
                target.getZ() - caster.getZ();

        double horizontalDistance =
                Math.sqrt(
                        dx * dx +
                                dz * dz
                );

        float yaw = (float) (
                Mth.atan2(dz, dx)
                        * (180.0D / Math.PI)
        ) - 90.0F;

        float pitch = (float) (
                -(Mth.atan2(
                        dy,
                        horizontalDistance
                ) * (180.0D / Math.PI))
        );

        caster.setYRot(yaw);
        caster.setXRot(pitch);

        caster.setYHeadRot(yaw);
        caster.setYBodyRot(yaw);
    }

    @Override
    public void playMagicCastSound(
            LivingEntity player,
            LivingEntity caster
    ) {

        player.level().playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ENDERMAN_TELEPORT,
                SoundSource.PLAYERS,
                0.8F,
                0.8F
        );
    }
}