package online.magicksaddon.magicsaddonmod.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.InputEvent.MouseScrollingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import online.kingdomkeys.kingdomkeys.capability.IGlobalCapabilities;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.IWorldCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.client.gui.menu.NoChoiceMenuPopup;
import online.kingdomkeys.kingdomkeys.client.gui.overlay.CommandMenuGui;
import online.kingdomkeys.kingdomkeys.client.sound.ModSounds;
import online.kingdomkeys.kingdomkeys.config.ModConfigs;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import online.kingdomkeys.kingdomkeys.driveform.ModDriveForms;
import online.kingdomkeys.kingdomkeys.entity.mob.SpawningOrbEntity;
import online.kingdomkeys.kingdomkeys.handler.EntityEvents;
import online.kingdomkeys.kingdomkeys.handler.InputHandler;
import online.kingdomkeys.kingdomkeys.handler.KeyboardHelper;
import online.kingdomkeys.kingdomkeys.item.KKPotionItem;
import online.kingdomkeys.kingdomkeys.lib.Constants;
import online.kingdomkeys.kingdomkeys.lib.Party;
import online.kingdomkeys.kingdomkeys.lib.Party.Member;
import online.kingdomkeys.kingdomkeys.lib.PortalData;
import online.kingdomkeys.kingdomkeys.lib.SoAState;
import online.kingdomkeys.kingdomkeys.lib.Strings;
import online.kingdomkeys.kingdomkeys.limit.Limit;
import online.kingdomkeys.kingdomkeys.magic.ModMagic;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.cts.CSExtendedReach;
import online.kingdomkeys.kingdomkeys.network.cts.CSSpawnOrgPortalPacket;
import online.kingdomkeys.kingdomkeys.network.cts.CSSummonArmor;
import online.kingdomkeys.kingdomkeys.network.cts.CSSummonKeyblade;
import online.kingdomkeys.kingdomkeys.network.cts.CSSyncAllClientDataPacket;
import online.kingdomkeys.kingdomkeys.network.cts.CSUseDriveFormPacket;
import online.kingdomkeys.kingdomkeys.network.cts.CSUseItemPacket;
import online.kingdomkeys.kingdomkeys.network.cts.CSUseLimitPacket;
import online.kingdomkeys.kingdomkeys.network.cts.CSUseMagicPacket;
import online.kingdomkeys.kingdomkeys.network.cts.CSUseReactionCommandPacket;
import online.kingdomkeys.kingdomkeys.network.cts.CSUseShortcutPacket;
import online.kingdomkeys.kingdomkeys.util.IExtendedReach;
import online.kingdomkeys.kingdomkeys.util.Utils;
import online.kingdomkeys.kingdomkeys.util.Utils.OrgMember;
import online.kingdomkeys.kingdomkeys.world.dimension.ModDimensions;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesX;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesX;
import online.magicksaddon.magicsaddonmod.client.gui.GUIHelperX;
import online.magicksaddon.magicsaddonmod.client.sound.MagicSounds;
import online.magicksaddon.magicsaddonmod.lib.StringsX;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerX;
import online.magicksaddon.magicsaddonmod.network.cts.CSSetStepTicksPacket;
import online.magicksaddon.magicsaddonmod.network.cts.CSSyncAllClientDataXPacket;

public class MAInputHandler extends InputHandler {

	@Override
	@SubscribeEvent
	public void handleKeyInputEvent(InputEvent.Key event) {
		super.handleKeyInputEvent(event);
	}

	@Override
    public void commandAction() {
		IGlobalCapabilitiesX globalData = ModCapabilitiesX.getGlobal(player);

        // 0.13000001 - Sprint speed (Vanilla)

		// Light/Dark Step Abilities
		if (qrCooldown <= 0 && (player.getDeltaMovement().x != 0 && player.getDeltaMovement().z != 0)) {
			if (player.isSprinting()) {

				// System.out.println(playerData.getActiveDriveForm());
				int lightLevel = playerData.getDriveFormLevel("magicksaddon:form_light");
				int darkLevel = playerData.getDriveFormLevel("magicksaddon:form_dark");

				// System.out.println(globalData.getStepTicks());

				// Light Step
				if (playerData.isAbilityEquipped(StringsX.lightStep) || playerData.getActiveDriveForm().equals("magicksaddon:form_light")) {
					float yaw = player.getYRot();
					float motionX = -Mth.sin(yaw / 180.0f * (float) Math.PI);
					float motionZ = Mth.cos(yaw / 180.0f * (float) Math.PI);
					double power = lightLevel;
					PacketHandlerX.sendToServer(new CSSetStepTicksPacket(10, StringsX.lightStepType));

					// Light Form
					if (playerData.getActiveDriveForm().equals("magicksaddon:form_light")) {
						player.level().playSound(player, player.blockPosition(), MagicSounds.LIGHTSTEP1.get(), SoundSource.PLAYERS, 1F, 1F);
						player.push(motionX * power / 1.5, 0, motionZ * power / 1.5);
						qrCooldown = 20;
					} else if (playerData.isAbilityEquipped(StringsX.lightStep)) {
						if (lightLevel > 2) {
							player.level().playSound(player, player.blockPosition(), MagicSounds.LIGHTSTEP1.get(), SoundSource.PLAYERS, 1F, 1F);
							power = lightLevel - 2;
							player.push(motionX * power, 0, motionZ * power);
							qrCooldown = 20;
						}
					}
				} else if (playerData.isAbilityEquipped(StringsX.darkStep) || playerData.getActiveDriveForm().equals("magicksaddon:form_dark")) {
					float yaw = player.getYRot();
					float motionX = -Mth.sin(yaw / 180.0f * (float) Math.PI);
					float motionZ = Mth.cos(yaw / 180.0f * (float) Math.PI);
					double power = darkLevel;
					PacketHandlerX.sendToServer(new CSSetStepTicksPacket(10, StringsX.darkStepType));
					// Dark Mode
					if (playerData.getActiveDriveForm().equals("magicksaddon:form_dark")) {
						player.level().playSound(player, player.blockPosition(), MagicSounds.DARKSTEP1.get(), SoundSource.PLAYERS, 1F, 1F);
						player.push(motionX * power / 1.5, 0, motionZ * power / 1.5);
						qrCooldown = 20;
					} else if (playerData.isAbilityEquipped(StringsX.darkStep)) {
						if (darkLevel > 2) {
							player.level().playSound(player, player.blockPosition(), MagicSounds.DARKSTEP1.get(), SoundSource.PLAYERS, 1F, 1F);
							power = darkLevel - 2;
							player.push(motionX * power, 0, motionZ * power);
							qrCooldown = 20;
						}
					}

				} else { //If not doing adrk or light step run base KK code
					super.commandAction();
				}
				PacketHandlerX.syncGlobalToAllAround(player, globalData);
				// PacketHandlerX.sendToServer(new CSSetStepTicksPacket());
			}
		}
    }
}
