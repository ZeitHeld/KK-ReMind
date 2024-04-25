package online.magicksaddon.magicsaddonmod.reactioncommands;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.reactioncommands.ReactionCommand;
import online.magicksaddon.magicsaddonmod.KingdomKeysReMind;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.driveform.ModDriveFormsRM;

@Mod.EventBusSubscriber(modid = KingdomKeysReMind.MODID)
public class TestReaction extends ReactionCommand {

	public TestReaction(ResourceLocation registryName, boolean constantCheck) {
		super(registryName, constantCheck);
	}

	@Override
	public void onUse(Player player, LivingEntity livingEntity, LivingEntity livingEntity1) {
		if (conditionsToAppear(player, player)) {
			IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);

		}
	}

	@Override
	public boolean conditionsToAppear(Player player, LivingEntity livingEntity) {
		IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
		IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);
		if (playerData != null) {
			if (playerData.getActiveDriveForm().equals(ModDriveFormsRM.LIGHT.get().getRegistryName().toString())) {
				return true;
			}
		}
		return false;
	}
}