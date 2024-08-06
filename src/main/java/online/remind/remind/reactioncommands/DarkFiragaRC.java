package online.remind.remind.reactioncommands;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraftforge.fml.common.Mod;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.client.sound.ModSounds;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import online.kingdomkeys.kingdomkeys.item.ModItems;
import online.kingdomkeys.kingdomkeys.lib.Strings;
import online.kingdomkeys.kingdomkeys.reactioncommands.ReactionCommand;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.capabilities.IGlobalCapabilitiesRM;
import online.remind.remind.capabilities.ModCapabilitiesRM;
import online.remind.remind.entity.reactioncommand.DarkFiragaEntity;
import online.remind.remind.lib.StringsRM;
import online.remind.remind.network.PacketHandlerRM;

@Mod.EventBusSubscriber(modid = KingdomKeysReMind.MODID)
public class DarkFiragaRC extends ReactionCommand {

	public DarkFiragaRC(ResourceLocation registryName, boolean constantCheck) {
		super(registryName, constantCheck);
	}

	@Override
	public void onUse(Player player, LivingEntity target, LivingEntity lockedOnEntity) {
		IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
		IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);
		float dmgMult = (ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(StringsRM.darknessBoost) * 0.3F) + (ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(Strings.fireBoost) * 0.3F);
		globalData.setRCCooldownTicks(60);
		//System.out.println(globalData.getRCCooldownTicks());
		playerData.remFocus(15);
		PacketHandlerRM.syncGlobalToAllAround(player, globalData);

		player.level().playSound(null, player.position().x(), player.position().y(), player.position().z(), ModSounds.firaga.get(), SoundSource.PLAYERS, 1F, 0.7F);
		ThrowableProjectile darkFiraga = new DarkFiragaEntity(player.level(), player, dmgMult);
		player.level().addFreshEntity(darkFiraga);
		darkFiraga.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 2F, 0);

	}

	@Override
	public boolean conditionsToAppear(Player player, LivingEntity livingEntity) {
		IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
		IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);
		if (playerData != null && playerData.getEquippedKeychain(DriveForm.NONE) != null) {
			if (playerData.getActiveDriveForm().equals(DriveForm.NONE) && playerData.getEquippedKeychain(DriveForm.NONE).getItem() == ModItems.soulEaterChain.get() && globalData.getRCCooldownTicks() == 0 || playerData.getEquippedKeychain(DriveForm.NONE).getItem() == ModItems.keybladeOfPeoplesHeartsChain.get() && globalData.getRCCooldownTicks() == 0) {
				if (playerData.getFocus() >= 15) {
					return true;
				}
			}
		}
		return false;
	}
}
