package online.magicksaddon.magicsaddonmod.driveform;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import online.kingdomkeys.kingdomkeys.item.ModItems;
import online.magicksaddon.magicsaddonmod.KingdomKeysReMind;
import online.magicksaddon.magicsaddonmod.lib.StringsRM;

@Mod.EventBusSubscriber(modid = KingdomKeysReMind.MODID)
public class DriveFormTwilight extends DriveForm {
    public DriveFormTwilight(String registeryName, int order, ResourceLocation skinRL, boolean hasKeychain, boolean baseGrowthAbilities) {
        super(registeryName, order, hasKeychain, baseGrowthAbilities);
        this.color = new float[] {0.25F,0.25F,0.25F};
        this.skinRL = skinRL;
    }

    // Twilight Form EXP Gain

    @Override
    public boolean isSlotVisible(Player player) {
    	IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
    	if (playerData != null) {
			if (playerData.getDriveFormLevel(KingdomKeysReMind.MODID + ":" + StringsRM.darkMode) == 7 && playerData.getDriveFormLevel(KingdomKeysReMind.MODID + ":" + StringsRM.light) == 7) {
				if (playerData.getActiveDriveForm().equals(KingdomKeysReMind.MODID + ":" + StringsRM.darkMode))
					return playerData.getEquippedKeychain(DriveForm.NONE).getItem() == ModItems.oblivionChain.get();
				
				if (playerData.getActiveDriveForm().equals(KingdomKeysReMind.MODID + ":" + StringsRM.light))
					return playerData.getEquippedKeychain(DriveForm.NONE).getItem() == ModItems.oathkeeperChain.get();
				
				return playerData.getActiveDriveForm().equals(KingdomKeysReMind.MODID + ":" + StringsRM.twilight);
			}
		}
    	return false;
    }
}
