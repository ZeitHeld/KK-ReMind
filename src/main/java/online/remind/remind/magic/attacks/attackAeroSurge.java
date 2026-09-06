package online.remind.remind.magic.attacks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import online.kingdomkeys.kingdomkeys.ability.ModAbilities;
import online.kingdomkeys.kingdomkeys.lib.Strings;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.remind.remind.entity.attacks.aeroSurgeCollider;

public class attackAeroSurge extends Magic {

    public attackAeroSurge(ResourceLocation registryName, boolean hasToSelect, int tier, ResourceLocation gmAbility) {
        super(registryName, hasToSelect, gmAbility);
setTier(tier);
    }

    public void magicUse(LivingEntity player, LivingEntity caster, float fullMPBlastMult, LivingEntity lockOnEntity) {
        float dmg = switch (getTier()) {
	        case 0 -> casterStrengthStat(caster) * (abilityStacks(caster, ModAbilities.THUNDER_BOOST) * 0.1f);
	        case 1 -> (casterStrengthStat(caster) * 1.1f) * (abilityStacks(caster, ModAbilities.THUNDER_BOOST) * 0.1f);
	        case 2 -> (casterStrengthStat(caster) * 1.25f) * (abilityStacks(caster, ModAbilities.THUNDER_BOOST) * 0.1f);
	        default -> 0;
        };

	    float radius = 1.5f + (0.5f * getTier());

        double speed = 0.75;

        double yawRad = Math.toRadians(player.getYRot());
        double dx = -Math.sin(yawRad) * speed;
        double jump = 0.175;
        double dz = Math.cos(yawRad) * speed;
        float yaw = player.getYRot();
        float motionX = -Mth.sin(yaw / 180.0f * (float) Math.PI);
        float motionZ = Mth.cos(yaw / 180.0f * (float) Math.PI);
        caster.setDeltaMovement(dx, jump, dz);
        caster.push(motionX, 0, motionZ);
        caster.hurtMarked = true;
        caster.fallDistance = 0;

        aeroSurgeCollider surge = new aeroSurgeCollider(caster.level(), caster, dmg);
        caster.level().addFreshEntity(surge);
    }

    @Override
    public void playMagicCastSound(LivingEntity player, LivingEntity caster) {
            player.level().playSound(null, player.blockPosition(), SoundEvents.VEX_CHARGE, SoundSource.PLAYERS, 1F, 1F);
    }
}
