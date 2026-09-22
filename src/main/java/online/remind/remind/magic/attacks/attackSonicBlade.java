package online.remind.remind.magic.attacks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.entity.attacks.SonicBladeCollider;

public class attackSonicBlade extends Magic {

    public static final String SONIC_BLADE_DAMAGE =
            "kkremind_sonic_blade_damage";

    public attackSonicBlade(
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

        float dmg = switch (getTier()) {
            case 0 -> casterStrengthStat(caster);
            case 1 -> casterStrengthStat(caster) * 1.05F;
            case 2 -> casterStrengthStat(caster) * 1.1F;
            case 3 -> casterStrengthStat(caster) * 1.15F;
            case 4 -> casterStrengthStat(caster) * 1.2F;
            default -> casterStrengthStat(caster);
        };

        dmg *= fullMPBlastMult;

        caster.getPersistentData().putFloat(
                SONIC_BLADE_DAMAGE,
                dmg
        );

        /*
         * Initial Sonic Blade charge.
         *
         * chainStep 0 = opening attack.
         */
        launchSonicBladeDash(caster, 0);

        SonicBladeCollider sonicBlade = new SonicBladeCollider(
                caster.level(),
                caster,
                dmg,
                0
        );

        caster.level().addFreshEntity(sonicBlade);
    }

    /**
     * Launches one Sonic Blade charge.
     *
     * This is public/static intentionally so SonicBladeRC can use
     * the exact same movement code for all six follow-up attacks.
     */
    public static void launchSonicBladeDash(
            LivingEntity caster,
            int chainStep
    ) {

        double speed = switch (chainStep) {
            case 0 -> 1.65D;
            case 1 -> 1.75D;
            case 2 -> 1.85D;
            case 3 -> 1.90D;
            case 4 -> 1.95D;
            case 5 -> 2.00D;
            case 6 -> 2.05D;
            default -> 1.65D;
        };

        double yawRad = Math.toRadians(caster.getYRot());

        double dx = -Math.sin(yawRad) * speed;
        double yMotion = caster.getDeltaMovement().y;
        double dz = Math.cos(yawRad) * speed;

        caster.hurtMarked = true;
        caster.fallDistance = 0.0F;

        /*
         * Same Epic Fight compensation you're already using for Blitz.
         */
        if (KingdomKeysReMind.efmLoaded) {
            caster.setDeltaMovement(
                    dx / 2.25D,
                    yMotion,
                    dz / 2.25D
            );
        } else {
            caster.setDeltaMovement(
                    dx,
                    yMotion,
                    dz
            );
        }
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
                SoundEvents.PLAYER_ATTACK_SWEEP,
                SoundSource.PLAYERS,
                1.0F,
                1.0F
        );
    }
}