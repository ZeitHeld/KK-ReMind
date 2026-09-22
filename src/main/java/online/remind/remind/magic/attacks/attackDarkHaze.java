package online.remind.remind.magic.attacks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.entity.attacks.DarkHazeCollider;

public class attackDarkHaze extends Magic {

    public attackDarkHaze(
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
         * Damage scaling for Re:Mind.
         *
         * We're keeping this in line with your other Attack Commands
         * rather than literally using BBS's internal 3.0-3.3 power value.
         */
        float dmg = switch (getTier()) {
            case 0 -> casterStrengthStat(caster) * 1.40F;
            case 1 -> casterStrengthStat(caster) * 1.55F;
            case 2 -> casterStrengthStat(caster) * 1.70F;
            case 3 -> casterStrengthStat(caster) * 1.85F;
            default -> casterStrengthStat(caster) * 1.40F;
        };

        dmg *= fullMPBlastMult;

        /*
         * Original BBS Doom chances:
         *
         * LV1 = 25%
         * LV2 = 40%
         * LV3 = 55%
         * LV4 = 60%
         */
        float doomChance = switch (getTier()) {
            case 0 -> 0.25F;
            case 1 -> 0.40F;
            case 2 -> 0.55F;
            case 3 -> 0.60F;
            default -> 0.25F;
        };

        /*
         * If locked on, face the target before charging.
         */
        if (lockOnEntity != null && lockOnEntity.isAlive()) {
            faceTarget(
                    caster,
                    lockOnEntity
            );
        }

        launchDarkHazeDash(caster);

        DarkHazeCollider collider =
                new DarkHazeCollider(
                        caster.level(),
                        caster,
                        dmg,
                        doomChance
                );

        caster.level().addFreshEntity(
                collider
        );
    }

    public static void launchDarkHazeDash(
            LivingEntity caster
    ) {

        /*
         * Dark Haze should feel like one heavy, fast charge.
         */
        double speed = 2.05D;

        double yawRad =
                Math.toRadians(
                        caster.getYRot()
                );

        double dx =
                -Math.sin(yawRad) * speed;

        double dz =
                Math.cos(yawRad) * speed;

        caster.hurtMarked = true;
        caster.fallDistance = 0.0F;

        /*
         * Same Epic Fight movement compensation we've been
         * using for your other dash Attack Commands.
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

        /*
         * Temporary vanilla sound.
         * We can replace it with something darker later.
         */
        player.level().playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ENDERMAN_TELEPORT,
                SoundSource.PLAYERS,
                0.8F,
                0.65F
        );
    }
}