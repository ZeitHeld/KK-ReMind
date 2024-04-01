package online.magicksaddon.magicsaddonmod.handler;

import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import online.kingdomkeys.kingdomkeys.handler.InputHandler;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.client.sound.ModSoundsRM;
import online.magicksaddon.magicsaddonmod.lib.StringsRM;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerRM;
import online.magicksaddon.magicsaddonmod.network.cts.CSSetStepTicksPacket;

public class InputHandlerRM extends InputHandler {

	@Override
	@SubscribeEvent
	public void handleKeyInputEvent(InputEvent.Key event) {
		super.handleKeyInputEvent(event);
	}

	@Override
    public void commandAction() {
		IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);

        // 0.13000001 - Sprint speed (Vanilla)

		// Light/Dark Step Abilities
		if (qrCooldown <= 0 && (player.getDeltaMovement().x != 0 && player.getDeltaMovement().z != 0)) {
			if (player.isSprinting()) {

				// System.out.println(playerData.getActiveDriveForm());
				int lightLevel = playerData.getDriveFormLevel("magicksaddon:form_light");
				int darkLevel = playerData.getDriveFormLevel("magicksaddon:form_dark");

				// System.out.println(globalData.getStepTicks());

				// Light Step
				if (playerData.isAbilityEquipped(StringsRM.lightStep) || playerData.getActiveDriveForm().equals("magicksaddon:form_light")) {
					float yaw = player.getYRot();
					float motionX = -Mth.sin(yaw / 180.0f * (float) Math.PI);
					float motionZ = Mth.cos(yaw / 180.0f * (float) Math.PI);
					double power = lightLevel;
					PacketHandlerRM.sendToServer(new CSSetStepTicksPacket(10, StringsRM.lightStepType));

					// Light Form
					if (playerData.getActiveDriveForm().equals("magicksaddon:form_light")) {
						player.level().playSound(player, player.blockPosition(), ModSoundsRM.LIGHTSTEP1.get(), SoundSource.PLAYERS, 1F, 1F);
						player.push(motionX * power / 1.5, 0, motionZ * power / 1.5);
						qrCooldown = 20;
					} else if (playerData.isAbilityEquipped(StringsRM.lightStep)) {
						if (lightLevel > 2) {
							player.level().playSound(player, player.blockPosition(), ModSoundsRM.LIGHTSTEP1.get(), SoundSource.PLAYERS, 1F, 1F);
							power = lightLevel - 2;
							player.push(motionX * power, 0, motionZ * power);
							qrCooldown = 20;
						}
					}
				} else if (playerData.isAbilityEquipped(StringsRM.darkStep) || playerData.getActiveDriveForm().equals("magicksaddon:form_dark")) {
					float yaw = player.getYRot();
					float motionX = -Mth.sin(yaw / 180.0f * (float) Math.PI);
					float motionZ = Mth.cos(yaw / 180.0f * (float) Math.PI);
					double power = darkLevel;
					PacketHandlerRM.sendToServer(new CSSetStepTicksPacket(10, StringsRM.darkStepType));
					// Dark Mode
					if (playerData.getActiveDriveForm().equals("magicksaddon:form_dark")) {
						player.level().playSound(player, player.blockPosition(), ModSoundsRM.DARKSTEP1.get(), SoundSource.PLAYERS, 1F, 1F);
						player.push(motionX * power / 1.5, 0, motionZ * power / 1.5);
						qrCooldown = 20;
					} else if (playerData.isAbilityEquipped(StringsRM.darkStep)) {
						if (darkLevel > 2) {
							player.level().playSound(player, player.blockPosition(), ModSoundsRM.DARKSTEP1.get(), SoundSource.PLAYERS, 1F, 1F);
							power = darkLevel - 2;
							player.push(motionX * power, 0, motionZ * power);
							qrCooldown = 20;
						}
					}

				} else { //If not doing adrk or light step run base KK code
					super.commandAction();
				}
				PacketHandlerRM.syncGlobalToAllAround(player, globalData);
				// PacketHandlerX.sendToServer(new CSSetStepTicksPacket());
			}
		}
    }
}
