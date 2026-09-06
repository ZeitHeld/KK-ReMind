package online.remind.remind.magic;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.remind.remind.lib.StringsRM;

public class magicPorted extends Magic {
	public magicPorted(ResourceLocation registryName, boolean hasToSelect, int tier, ResourceLocation gmAbility) {
		super(registryName, hasToSelect, gmAbility);
		setTier(tier);
	}

	@Override
	public void magicUse(LivingEntity player, LivingEntity caster, float fullMPBlastMult, LivingEntity lockOnTarget) {
		caster.sendSystemMessage(Component.translatable(StringsRM.Magic_Ported_Notice));
	}

	@Override
	public void playMagicCastSound(LivingEntity player, LivingEntity caster) {
	}
}
