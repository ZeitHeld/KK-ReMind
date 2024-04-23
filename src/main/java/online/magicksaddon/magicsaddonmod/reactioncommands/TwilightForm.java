package online.magicksaddon.magicsaddonmod.reactioncommands;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import online.kingdomkeys.kingdomkeys.item.ModItems;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncCapabilityPacket;
import online.kingdomkeys.kingdomkeys.reactioncommands.ReactionCommand;
import online.magicksaddon.magicsaddonmod.KingdomKeysReMind;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.lib.StringsRM;

@Mod.EventBusSubscriber(modid = KingdomKeysReMind.MODID)
public class TwilightForm extends ReactionCommand {

	public TwilightForm(ResourceLocation registryName, boolean constantCheck) {
		super(registryName, constantCheck);
	}

	@Override
	public void onUse(Player player, LivingEntity livingEntity, LivingEntity livingEntity1) {
		if (conditionsToAppear(player, player)) {
			IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
			IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);
			playerData.setActiveDriveForm(KingdomKeysReMind.MODID + ":" + StringsRM.twilight);
			if (playerData.getEquippedKeychain(DriveForm.NONE).getItem() == ModItems.oblivionChain.get()) {
				playerData.setNewKeychain(new ResourceLocation(KingdomKeysReMind.MODID + ":" + StringsRM.twilight), new ItemStack(ModItems.oathkeeperChain.get()));
				PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
			}
			if (playerData.getEquippedKeychain(DriveForm.NONE).getItem() == ModItems.oathkeeperChain.get()) {
				playerData.setNewKeychain(new ResourceLocation(KingdomKeysReMind.MODID + ":" + StringsRM.twilight), new ItemStack(ModItems.oblivionChain.get()));
				PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
			}
		}
	}

	@Override
	public boolean conditionsToAppear(Player player, LivingEntity livingEntity) {
		IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
		IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);
		if (playerData != null) {
			if (playerData.getDriveFormLevel(KingdomKeysReMind.MODID + ":" + StringsRM.darkMode) == 7 && playerData.getDriveFormLevel(KingdomKeysReMind.MODID + ":" + StringsRM.light) == 7) {
				if (playerData.getActiveDriveForm().equals(KingdomKeysReMind.MODID + ":" + StringsRM.darkMode))
					return playerData.getEquippedKeychain(DriveForm.NONE).getItem() == ModItems.oblivionChain.get() && playerData.getEquippedKeychain(DriveForm.SYNCH_BLADE).getItem() == ModItems.oathkeeperChain.get();
				
				if (playerData.getActiveDriveForm().equals(KingdomKeysReMind.MODID + ":" + StringsRM.light))
					return playerData.getEquippedKeychain(DriveForm.NONE).getItem() == ModItems.oathkeeperChain.get() && playerData.getEquippedKeychain(DriveForm.SYNCH_BLADE).getItem() == ModItems.oblivionChain.get();
			}
		}
		return false;
	}
}
