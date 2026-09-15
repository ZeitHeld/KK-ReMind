package online.remind.remind.handler;

import net.minecraft.client.KeyMapping;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import online.kingdomkeys.kingdomkeys.ability.ModAbilities;
import online.kingdomkeys.kingdomkeys.api.event.client.KKInputEvent;
import online.kingdomkeys.kingdomkeys.data.PlayerData;
import online.kingdomkeys.kingdomkeys.handler.InputHandler;
import online.kingdomkeys.kingdomkeys.util.Utils;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.ability.ModAbilitiesRM;
import online.remind.remind.capabilities.GlobalDataRM;
import online.remind.remind.capabilities.ModDataRM;
import online.remind.remind.client.sound.ModSoundsRM;
import online.remind.remind.config.ModConfigs;
import online.remind.remind.driveform.ModDriveFormsRM;
import online.remind.remind.lib.StringsRM;
import online.remind.remind.network.PacketHandlerRM;
import online.remind.remind.network.cts.CSSetStepTicksPacket;
import online.remind.remind.network.cts.CSSummonSpiritPacket;
import org.lwjgl.glfw.GLFW;

public class InputHandlerRM {

	@SubscribeEvent
	public void kkInputEvent(KKInputEvent.Pre event) {
		if (event.getKeybind() != InputHandler.Keybinds.ACTION) {
			return;
		}

		Player player = event.getHandler().player;
		PlayerData playerData = event.getHandler().playerData;
		GlobalDataRM globalData = ModDataRM.getGlobal(player);

		if (player == null || playerData == null || globalData == null) {
			return;
		}

		/*
		 * Step movement
		 *
		 * Light Form:
		 * - Light Step works automatically.
		 * - Quick Run is NOT required.
		 * - Movement is a forward/upward arc.
		 *
		 * Dark Form:
		 * - Dark Step works automatically.
		 * - Quick Run is NOT required.
		 * - Movement is a server-authoritative teleport.
		 *
		 * Outside the respective Forms:
		 * - Light Step / Dark Step still require Quick Run.
		 */

		boolean moving =
				player.getDeltaMovement().x != 0.0D
						|| player.getDeltaMovement().z != 0.0D;

		if (InputHandler.qrCooldown > 0 || !moving || !player.isSprinting()) {
			return;
		}

		int lightLevel =
				playerData.getDriveFormLevel(
						ModDriveFormsRM.LIGHT.location()
				);

		int darkLevel =
				playerData.getDriveFormLevel(
						ModDriveFormsRM.DARK.location()
				);

		boolean lightForm =
				playerData.isFormActive(
						ModDriveFormsRM.LIGHT
				);

		boolean darkForm =
				playerData.isFormActive(
						ModDriveFormsRM.DARK
				);

		boolean hasQuickRun =
				playerData.isAbilityEquipped(
						ModAbilities.QUICK_RUN
				);

		boolean hasLightStep =
				playerData.isAbilityEquipped(
						ModAbilitiesRM.LIGHT_STEP
				);

		boolean hasDarkStep =
				playerData.isAbilityEquipped(
						ModAbilitiesRM.DARK_STEP
				);

		/*
		 * Form usage does not require Quick Run.
		 *
		 * Outside a Form, preserve the old behavior:
		 * the Step ability itself + Quick Run are required.
		 */
		boolean canLightStep =
				lightForm
						|| (hasLightStep
						&& hasQuickRun
						&& !darkForm
						&& !hasDarkStep);

		boolean canDarkStep =
				darkForm
						|| (hasDarkStep
						&& hasQuickRun
						&& !lightForm);

		/*
		 * Keep movement types mutually exclusive so one ACTION press
		 * cannot accidentally stack multiple step movements.
		 */

		// Organization Quick Step
		if (playerData.getAlignment() != Utils.OrgMember.NONE) {
			performOrgQuickStep(player);
			event.setCanceled(true);
		}

		// Twilight Step
		else if (playerData.isFormActive(ModDriveFormsRM.TWILIGHT)
				&& hasQuickRun) {

			performTwilightStep(player);
			event.setCanceled(true);
		}

		// Rage Run
		else if (playerData.isFormActive(ModDriveFormsRM.RAGE)
				&& hasQuickRun) {

			performRageRun(player, globalData);
			event.setCanceled(true);
		}

		// Light Step
		else if (canLightStep) {
			if (performLightStep(
					player,
					lightLevel,
					lightForm
			)) {
				event.setCanceled(true);
			}
		}

		// Dark Step
		else if (canDarkStep) {
			if (performDarkStep(
					player,
					darkLevel,
					darkForm
			)) {
				event.setCanceled(true);
			}
		}

		PacketHandlerRM.syncGlobalToAllAround(
				player,
				globalData
		);
	}

	private static void performOrgQuickStep(Player player) {
		float yaw = player.getYRot();

		float motionX =
				-Mth.sin(
						yaw / 180.0F * (float) Math.PI
				);

		float motionZ =
				Mth.cos(
						yaw / 180.0F * (float) Math.PI
				);

		double power = 8.0D;

		PacketHandlerRM.sendToServer(
				new CSSetStepTicksPacket(
						15,
						StringsRM.orgStepType
				)
		);

		player.push(
				motionX * power / 1.5D,
				0.0D,
				motionZ * power / 1.5D
		);

		InputHandler.qrCooldown = 15;
	}

	private static void performTwilightStep(Player player) {
		float yaw = player.getYRot();

		float motionX =
				-Mth.sin(
						yaw / 180.0F * (float) Math.PI
				);

		float motionZ =
				Mth.cos(
						yaw / 180.0F * (float) Math.PI
				);

		double power = 3.0D;

		PacketHandlerRM.sendToServer(
				new CSSetStepTicksPacket(
						10,
						StringsRM.twilightStepType
				)
		);

		player.push(
				motionX * power / 1.5D,
				0.0D,
				motionZ * power / 1.5D
		);

		InputHandler.qrCooldown = 10;

		player.level().playSound(
				player,
				player.blockPosition(),
				ModSoundsRM.TWILIGHT_STEP.get(),
				SoundSource.PLAYERS,
				1F,
				1F
		);
	}

	private static void performRageRun(
			Player player,
			GlobalDataRM globalData
	) {
		float yaw = player.getYRot();

		float motionX =
				-Mth.sin(
						yaw / 180.0F * (float) Math.PI
				);

		float motionZ =
				Mth.cos(
						yaw / 180.0F * (float) Math.PI
				);

		double power =
				0.5D
						+ globalData.getRiskchargeCount();

		PacketHandlerRM.sendToServer(
				new CSSetStepTicksPacket(
						10,
						StringsRM.rageStepType
				)
		);

		player.push(
				motionX * power / 1.5D,
				0.0D,
				motionZ * power / 1.5D
		);

		InputHandler.qrCooldown =
				Math.max(
						1,
						15 - globalData.getRiskchargeCount()
				);
	}

	/*
	 * Light Step
	 *
	 * Instead of another flat Quick Run-style push,
	 * Light Step launches the player forward and upward.
	 *
	 * Gravity naturally pulls the player back down,
	 * creating the arc.
	 */
	private static boolean performLightStep(
			Player player,
			int lightLevel,
			boolean lightForm
	) {
		/*
		 * Preserve the old standalone ability requirement:
		 * Light Step did nothing before Form Level 3.
		 */
		if (!lightForm && lightLevel <= 2) {
			return false;
		}

		float yaw = player.getYRot();

		float motionX =
				-Mth.sin(
						yaw / 180.0F * (float) Math.PI
				);

		float motionZ =
				Mth.cos(
						yaw / 180.0F * (float) Math.PI
				);

		/*
		 * Keep roughly the old horizontal scaling.
		 */
		double horizontalPower =
				lightForm
						? Math.max(1.0D, lightLevel / 2.0D)
						: Math.max(1.0D, lightLevel - 2.0D);

		/*
		 * Arc height.
		 *
		 * Increase these for a more dramatic leap.
		 * Decrease them for a flatter dash.
		 */
		double verticalPower =
				lightForm
						? 0.48D
						: 0.38D;

		PacketHandlerRM.sendToServer(
				new CSSetStepTicksPacket(
						10,
						StringsRM.lightStepType
				)
		);

		player.level().playSound(
				player,
				player.blockPosition(),
				ModSoundsRM.LIGHTSTEP1.get(),
				SoundSource.PLAYERS,
				1F,
				1F
		);

		player.setDeltaMovement(
				motionX * horizontalPower,
				verticalPower,
				motionZ * horizontalPower
		);

		player.hurtMarked = true;
		InputHandler.qrCooldown = 20;

		return true;
	}


	private static boolean performDarkStep(
			Player player,
			int darkLevel,
			boolean darkForm
	) {
		/*
		 * Preserve the old standalone ability requirement:
		 * Dark Step did nothing before Form Level 3.
		 */
		if (!darkForm && darkLevel <= 2) {
			return false;
		}

		PacketHandlerRM.sendToServer(
				new CSSetStepTicksPacket(
						10,
						StringsRM.darkStepType
				)
		);

		ClientEventsRM.startDarkStepAfterImage(player);

		PacketHandlerRM.sendToServer(
				new CSDarkStepPacket()
		);

		player.level().playSound(
				player,
				player.blockPosition(),
				ModSoundsRM.DARKSTEP1.get(),
				SoundSource.PLAYERS,
				1F,
				1F
		);

		InputHandler.qrCooldown = 20;

		return true;
	}

	@SubscribeEvent
	public void handleKeyInputEvent(InputEvent.Key event) {
		InputHandlerRM.Keybinds key =
				getPressedKey();

		if (key != null) {
			switch (key) {
				case SUMMONSPIRIT -> {
					if (ModConfigs.spiritsEnabled) {
						summonSpirit();
					}
				}
			}
		}
	}

	public void summonSpirit() {
		PacketHandlerRM.sendToServer(
				new CSSummonSpiritPacket()
		);
	}

	public enum Keybinds {
		SUMMONSPIRIT(
				"key.remind.summonspirit",
				GLFW.GLFW_KEY_Y
		);

		public final KeyMapping keybinding;

		Keybinds(
				String name,
				int defaultKey
		) {
			keybinding =
					new KeyMapping(
							name,
							defaultKey,
							"key.categories.remind"
					);
		}

		public KeyMapping getKeybind() {
			return keybinding;
		}

		private boolean isPressed() {
			return keybinding.consumeClick();
		}
	}

	private Keybinds getPressedKey() {
		for (Keybinds key : Keybinds.values()) {
			if (key.isPressed()) {
				return key;
			}
		}

		return null;
	}

	/*
	 * ============================================================
	 * DARK STEP NETWORK PACKET
	 * ============================================================
	 *
	 * Nested here so all Dark Step logic lives in InputHandlerRM.
	 *
	 * This still MUST be registered in PacketHandlerRM because the
	 * actual teleport needs to happen server-side.
	 */
	public static class CSDarkStepPacket implements CustomPacketPayload {

		public static final Type<CSDarkStepPacket> TYPE =
				new Type<>(
						ResourceLocation.fromNamespaceAndPath(
								KingdomKeysReMind.MODID,
								"cs_dark_step"
						)
				);

		public static final StreamCodec<
				FriendlyByteBuf,
				CSDarkStepPacket
				> STREAM_CODEC =
				StreamCodec.of(
						CSDarkStepPacket::encode,
						CSDarkStepPacket::decode
				);

		public CSDarkStepPacket() {
		}

		private static void encode(
				FriendlyByteBuf buffer,
				CSDarkStepPacket packet
		) {
			/*
			 * Nothing to encode.
			 *
			 * The client does NOT send a distance.
			 * The server calculates the teleport distance itself.
			 */
		}

		private static CSDarkStepPacket decode(
				FriendlyByteBuf buffer
		) {
			return new CSDarkStepPacket();
		}

		public static void handle(
				CSDarkStepPacket packet,
				IPayloadContext ctx
		) {
			ctx.enqueueWork(() -> {
				if (!(ctx.player() instanceof ServerPlayer player)) {
					return;
				}

				PlayerData playerData =
						PlayerData.get(player);

				if (playerData == null) {
					return;
				}

				boolean darkForm =
						playerData.isFormActive(
								ModDriveFormsRM.DARK
						);

				boolean lightForm =
						playerData.isFormActive(
								ModDriveFormsRM.LIGHT
						);

				boolean hasDarkStep =
						playerData.isAbilityEquipped(
								ModAbilitiesRM.DARK_STEP
						);

				boolean hasQuickRun =
						playerData.isAbilityEquipped(
								ModAbilities.QUICK_RUN
						);

				/*
				 * Dark Form gets Dark Step intrinsically.
				 *
				 * Outside Dark Form:
				 * Dark Step + Quick Run are still required.
				 */
				boolean canDarkStep =
						darkForm
								|| (hasDarkStep
								&& hasQuickRun
								&& !lightForm);

				if (!canDarkStep) {
					return;
				}

				if (!player.isSprinting()) {
					return;
				}

				int darkLevel =
						playerData.getDriveFormLevel(
								ModDriveFormsRM.DARK.location()
						);

				/*
				 * Preserve the old standalone level requirement.
				 */
				if (!darkForm && darkLevel <= 2) {
					return;
				}

				/*
				 * Dark Form scales roughly with its Form level.
				 *
				 * Standalone Dark Step scales from the old:
				 * darkLevel - 2
				 */
				double maxDistance =
						darkForm
								? Math.max(3.0D, darkLevel)
								: Math.max(2.0D, darkLevel - 2.0D);

				maxDistance =
						Mth.clamp(
								maxDistance,
								1.0D,
								8.0D
						);

				teleportDarkStep(
						player,
						maxDistance
				);
			});
		}

		private static void teleportDarkStep(
				ServerPlayer player,
				double maxDistance
		) {
			float yaw =
					player.getYRot();

			double directionX =
					-Mth.sin(
							yaw / 180.0F * (float) Math.PI
					);

			double directionZ =
					Mth.cos(
							yaw / 180.0F * (float) Math.PI
					);

			double startX = player.getX();
			double startY = player.getY();
			double startZ = player.getZ();

			double bestX = startX;
			double bestZ = startZ;

			/*
			 * Scan forward in small increments.
			 * This prevents Dark Step from passing through walls.
			 */
			for (
					double distance = 0.25D;
					distance <= maxDistance;
					distance += 0.25D
			) {
				double targetX =
						startX
								+ directionX * distance;

				double targetZ =
						startZ
								+ directionZ * distance;

				if (!player.level()
						.getWorldBorder()
						.isWithinBounds(
								BlockPos.containing(
										targetX,
										startY,
										targetZ
								)
						)) {

					break;
				}

				AABB targetBox =
						player.getBoundingBox().move(
								targetX - startX,
								0.0D,
								targetZ - startZ
						);

				if (!player.level().noCollision(
						player,
						targetBox
				)) {

					break;
				}

				bestX = targetX;
				bestZ = targetZ;
			}

			/*
			 * No safe forward position found.
			 */
			if (bestX == startX
					&& bestZ == startZ) {
				return;
			}

			/*
			 * True teleport:
			 * move instantly and kill horizontal momentum.
			 */
			player.teleportTo(
					bestX,
					startY,
					bestZ
			);

			player.setDeltaMovement(
					0.0D,
					player.getDeltaMovement().y,
					0.0D
			);

			player.hurtMarked = true;
		}

		@Override
		public Type<? extends CustomPacketPayload> type() {
			return TYPE;
		}
	}
}
