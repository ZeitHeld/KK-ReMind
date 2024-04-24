package online.magicksaddon.magicsaddonmod.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import online.kingdomkeys.kingdomkeys.api.client.KKInputEvent;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.IWorldCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.client.gui.menu.NoChoiceMenuPopup;
import online.kingdomkeys.kingdomkeys.client.gui.overlay.CommandMenuGui;
import online.kingdomkeys.kingdomkeys.client.sound.ModSounds;
import online.kingdomkeys.kingdomkeys.handler.InputHandler;
import online.kingdomkeys.kingdomkeys.lib.SoAState;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.cts.CSSyncAllClientDataPacket;
import online.kingdomkeys.kingdomkeys.util.Utils;
import online.kingdomkeys.kingdomkeys.world.dimension.ModDimensions;
import online.magicksaddon.magicsaddonmod.KingdomKeysReMind;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.client.gui.GUIHelperRM;
import online.magicksaddon.magicsaddonmod.client.sound.ModSoundsRM;
import online.magicksaddon.magicsaddonmod.lib.StringsRM;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerRM;
import online.magicksaddon.magicsaddonmod.network.cts.CSSetStepTicksPacket;
import online.magicksaddon.magicsaddonmod.network.cts.CSSyncAllClientDataPacketRM;

public class InputHandlerRM {

	@SubscribeEvent
	public void kkInputEvent(KKInputEvent.Pre event) {
		if (event.getKeybind() == InputHandler.Keybinds.ACTION) {
			Player player = event.getHandler().player;
			IPlayerCapabilities playerData = event.getHandler().playerData;
			IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);

			// 0.13000001 - Sprint speed (Vanilla)

			// Light/Dark Step Abilities
			if (InputHandler.qrCooldown <= 0 && (player.getDeltaMovement().x != 0 && player.getDeltaMovement().z != 0)) {
				if (player.isSprinting()) {

					// System.out.println(playerData.getActiveDriveForm());
					int lightLevel = playerData.getDriveFormLevel("magicksaddon:form_light");
					int darkLevel = playerData.getDriveFormLevel("magicksaddon:form_dark");

					// System.out.println(globalData.getStepTicks());
					// Twilight Step
					 if (playerData.getActiveDriveForm().equals(KingdomKeysReMind.MODID + ":" + StringsRM.twilight)){
						float yaw = player.getYRot();
						float motionX = -Mth.sin(yaw / 180.0f * (float) Math.PI);
						float motionZ = Mth.cos(yaw / 180.0f * (float) Math.PI);
						double power = 7;
						PacketHandlerRM.sendToServer(new CSSetStepTicksPacket(10, StringsRM.twilightStepType));
						//
						player.push(motionX * power / 1.5, 0, motionZ * power / 1.5);
						InputHandler.qrCooldown = 20;
						//Insert Sound Here
						 player.level().playSound(player, player.blockPosition(), ModSoundsRM.TWILIGHT_STEP.get(), SoundSource.PLAYERS, 1F, 1F);
						event.setCanceled(true);
					}
					// Light Step
					else if (playerData.getActiveDriveForm().equals(KingdomKeysReMind.MODID + ":" + StringsRM.light) || playerData.isAbilityEquipped(StringsRM.lightStep)  && !playerData.getActiveDriveForm().equals(KingdomKeysReMind.MODID + ":" + StringsRM.darkMode) && !playerData.isAbilityEquipped(StringsRM.darkStep)) {
						float yaw = player.getYRot();
						float motionX = -Mth.sin(yaw / 180.0f * (float) Math.PI);
						float motionZ = Mth.cos(yaw / 180.0f * (float) Math.PI);
						double power = lightLevel;
						PacketHandlerRM.sendToServer(new CSSetStepTicksPacket(10, StringsRM.lightStepType));
						// Light Form
						if (playerData.getActiveDriveForm().equals(KingdomKeysReMind.MODID + ":" + StringsRM.light)) {
							player.level().playSound(player, player.blockPosition(), ModSoundsRM.LIGHTSTEP1.get(), SoundSource.PLAYERS, 1F, 1F);
							player.push(motionX * power / 1.5, 0, motionZ * power / 1.5);
							InputHandler.qrCooldown = 20;
						} else if (playerData.isAbilityEquipped(StringsRM.lightStep)) {
							if (lightLevel > 2) {
								player.level().playSound(player, player.blockPosition(), ModSoundsRM.LIGHTSTEP1.get(), SoundSource.PLAYERS, 1F, 1F);
								power = lightLevel - 2;
								player.push(motionX * power, 0, motionZ * power);
								InputHandler.qrCooldown = 20;
							}
						}
						event.setCanceled(true);
					} else if (playerData.isAbilityEquipped(StringsRM.darkStep) || playerData.getActiveDriveForm().equals(KingdomKeysReMind.MODID+":form_dark")) {
						float yaw = player.getYRot();
						float motionX = -Mth.sin(yaw / 180.0f * (float) Math.PI);
						float motionZ = Mth.cos(yaw / 180.0f * (float) Math.PI);
						double power = darkLevel;

						PacketHandlerRM.sendToServer(new CSSetStepTicksPacket(10, StringsRM.darkStepType));
						// Dark Mode
						if (playerData.getActiveDriveForm().equals(KingdomKeysReMind.MODID + ":" + StringsRM.darkMode)) {
							player.level().playSound(player, player.blockPosition(), ModSoundsRM.DARKSTEP1.get(), SoundSource.PLAYERS, 1F, 1F);
							player.push(motionX * power / 1.5, 0, motionZ * power / 1.5);
							InputHandler.qrCooldown = 20;
						} else if (playerData.isAbilityEquipped(StringsRM.darkStep)) {
							if (darkLevel > 2) {
								player.level().playSound(player, player.blockPosition(), ModSoundsRM.DARKSTEP1.get(), SoundSource.PLAYERS, 1F, 1F);
								power = darkLevel - 2;
								player.push(motionX * power, 0, motionZ * power);
								InputHandler.qrCooldown = 20;
							}
						}
						event.setCanceled(true);
					}
					PacketHandlerRM.syncGlobalToAllAround(player, globalData);
					// PacketHandlerRM.sendToServer(new CSSetStepTicksPacket());
				}
			}
		} else if(event.getKeybind() == InputHandler.Keybinds.OPENMENU) {
            PacketHandler.sendToServer(new CSSyncAllClientDataPacket());
            PacketHandlerRM.sendToServer(new CSSyncAllClientDataPacketRM());
            LocalPlayer player = Minecraft.getInstance().player;
            if (ModCapabilities.getPlayer(player).getSoAState() != SoAState.COMPLETE) {
                if (player.level().dimension() != ModDimensions.DIVE_TO_THE_HEART) {
                    Minecraft.getInstance().setScreen(new NoChoiceMenuPopup());
                }
            } else {
                GUIHelperRM.openAddonMenu();
                //return;
            }
			event.setCanceled(true);
		} else if (event.getKeybind() == InputHandler.Keybinds.ENTER){
			Minecraft mc = Minecraft.getInstance();
			Level world = mc.level;
			Player player = mc.player;



			//ExtendedWorldData worldData = ExtendedWorldData.get(world);
			IWorldCapabilities worldData = ModCapabilities.getWorld(world);
			IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
			event.getHandler().portalCommands = worldData.getAllPortalsFromOwnerID(mc.player.getUUID());

			switch (CommandMenuGui.selected) {
				case CommandMenuGui.ATTACK:
					if (playerData.getAlignment() != Utils.OrgMember.NONE || playerData.isAbilityEquipped(StringsRM.darkPassage)) {
						// Submenu of the portals
						if (CommandMenuGui.submenu == CommandMenuGui.SUB_MAIN) {
							if (!event.getHandler().portalCommands.isEmpty() && !playerData.getRecharge()) {
								CommandMenuGui.submenu = CommandMenuGui.SUB_PORTALS;
								CommandMenuGui.portalSelected = 0;
								world.playSound(player, player.blockPosition(), ModSounds.menu_in.get(), SoundSource.MASTER, 1.0f, 1.0f);
							} else {
								CommandMenuGui.selected = CommandMenuGui.ATTACK;
								world.playSound(player, player.blockPosition(), ModSounds.error.get(), SoundSource.MASTER, 1.0f, 1.0f);
							}
							event.setCanceled(true);
						}
					}

					break;
			}
		}



	}



}
