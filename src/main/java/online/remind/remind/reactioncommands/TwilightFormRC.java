package online.remind.remind.reactioncommands;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import online.kingdomkeys.kingdomkeys.driveform.ModDriveForms;
import online.kingdomkeys.kingdomkeys.item.ModItems;
import online.kingdomkeys.kingdomkeys.reactioncommands.ReactionCommand;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.capabilities.IGlobalCapabilitiesRM;
import online.remind.remind.capabilities.ModCapabilitiesRM;
import online.remind.remind.driveform.ModDriveFormsRM;
import online.remind.remind.lib.StringsRM;

@Mod.EventBusSubscriber(modid = KingdomKeysReMind.MODID)
public class TwilightFormRC extends ReactionCommand {

	public TwilightFormRC(ResourceLocation registryName, boolean constantCheck) {
		super(registryName, constantCheck);
	}

	@Override
	public void onUse(Player player, LivingEntity livingEntity, LivingEntity livingEntity1) {
		if (conditionsToAppear(player, player)) {
			IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
			IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);
			/*if (playerData.getEquippedKeychain(DriveForm.NONE).getItem() == ModItems.oblivionChain.get()) {				
				playerData.setNewKeychain(new ResourceLocation(KingdomKeysReMind.MODID + ":" + StringsRM.twilight), oathkeeper);
			}
			if (playerData.getEquippedKeychain(DriveForm.NONE).getItem() == ModItems.oathkeeperChain.get()) {
				playerData.setNewKeychain(new ResourceLocation(KingdomKeysReMind.MODID + ":" + StringsRM.twilight), new ItemStack(ModItems.oblivionChain.get()));
			}*/
			//PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
			
			DriveForm twilightForm = ModDriveForms.registry.get().getValue(new ResourceLocation(KingdomKeysReMind.MODID + ":" + StringsRM.twilight));
			twilightForm.initDrive(player);
			//playerData.setActiveDriveForm(KingdomKeysReMind.MODID + ":" + StringsRM.twilight);

		}
	}

	@Override
	public boolean conditionsToAppear(Player player, LivingEntity livingEntity) {
		IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
		if (playerData != null) {
			if (playerData.getDriveFormLevel(KingdomKeysReMind.MODID + ":" + StringsRM.darkMode) == 7 && playerData.getDriveFormLevel(KingdomKeysReMind.MODID + ":" + StringsRM.light) == 7) {
				if (playerData.getActiveDriveForm().equals(KingdomKeysReMind.MODID + ":" + StringsRM.darkMode))
					return playerData.getEquippedKeychain(DriveForm.NONE).getItem() == ModItems.oblivionChain.get() && playerData.getEquippedKeychain(ModDriveFormsRM.TWILIGHT.get().getRegistryName()) != null && playerData.getEquippedKeychain(ModDriveFormsRM.TWILIGHT.get().getRegistryName()).getItem() == ModItems.oathkeeperChain.get();
				
				if (playerData.getActiveDriveForm().equals(KingdomKeysReMind.MODID + ":" + StringsRM.light))
					return playerData.getEquippedKeychain(DriveForm.NONE).getItem() == ModItems.oathkeeperChain.get() && playerData.getEquippedKeychain(ModDriveFormsRM.TWILIGHT.get().getRegistryName()) != null && playerData.getEquippedKeychain(ModDriveFormsRM.TWILIGHT.get().getRegistryName()).getItem() == ModItems.oblivionChain.get();
			}
		}
		return false;
	}
}
