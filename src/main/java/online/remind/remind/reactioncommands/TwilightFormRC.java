package online.remind.remind.reactioncommands;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import online.kingdomkeys.kingdomkeys.data.PlayerData;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import online.kingdomkeys.kingdomkeys.driveform.ModDriveForms;
import online.kingdomkeys.kingdomkeys.item.ModItems;
import online.kingdomkeys.kingdomkeys.reactioncommands.ReactionCommand;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.driveform.ModDriveFormsRM;
import online.remind.remind.lib.StringsRM;

public class TwilightFormRC extends ReactionCommand {

	public TwilightFormRC(ResourceLocation registryName, boolean constantCheck) {
		super(registryName, constantCheck, -1, 0xebebeb);
	}

	@Override
	public void onUse(Player player, LivingEntity livingEntity, LivingEntity livingEntity1) {
		if (conditionsToAppear(player, player)) {
			DriveForm twilightForm = ModDriveFormsRM.TWILIGHT.get();
			twilightForm.initDrive(player);
		}
	}

	@Override
	public boolean conditionsToAppear(Player player, LivingEntity livingEntity) {
		PlayerData playerData = PlayerData.get(player);
		if (playerData == null) {
			return false;
		}

		// Keyblade Check
		ItemStack baseKeychain =
				playerData.getEquippedKeychain(DriveForm.NONE);

		ItemStack twilightKeychain =
				playerData.getEquippedKeychain(ModDriveFormsRM.TWILIGHT.location());

		if (baseKeychain == null || twilightKeychain == null) {
			return false;
		}

		// Dark/Light Form Level Check
		if (playerData.getDriveFormLevel(ModDriveFormsRM.DARK.location()) < 7 ||
				playerData.getDriveFormLevel(ModDriveFormsRM.LIGHT.location()) < 7) {
			return false;
		}

		// Active Form Check

		boolean darkActive =
				playerData.isFormActive(ModDriveFormsRM.DARK);

		boolean lightActive =
				playerData.isFormActive(ModDriveFormsRM.LIGHT);

		boolean darkSetup =
				playerData.isFormActive(ModDriveFormsRM.DARK)
						&& baseKeychain.is(ModItems.oblivionChain.get())
						&& twilightKeychain.is(ModItems.oathkeeperChain.get());

		boolean lightSetup =
				playerData.isFormActive(ModDriveFormsRM.LIGHT)
						&& baseKeychain.is(ModItems.oathkeeperChain.get())
						&& twilightKeychain.is(ModItems.oblivionChain.get());

		// DEBUG
		//		System.out.println(
		//				"Twilight slot check:"
		//						+ " client=" + player.level().isClientSide()
		//						+ " darkLv=" + playerData.getDriveFormLevel(ModDriveFormsRM.DARK.location())
		//						+ " lightLv=" + playerData.getDriveFormLevel(ModDriveFormsRM.LIGHT.location())
		//						+ " darkActive=" + darkActive
		//						+ " lightActive=" + lightActive
		//						+ " base=" + baseKeychain
		//						+ " twilight=" + twilightKeychain
		//		);

		return darkSetup || lightSetup;
	}
}
