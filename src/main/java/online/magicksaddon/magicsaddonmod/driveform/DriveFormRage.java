package online.magicksaddon.magicsaddonmod.driveform;

import net.minecraft.resources.ResourceLocation;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;


public class DriveFormRage extends DriveForm {
    public DriveFormRage(String registeryName, int order, ResourceLocation skinRL, boolean hasKeychain) {
        super(registeryName, order, hasKeychain);
        
    }
    @Override
    public String getBaseAbilityForLevel(int driveFormLevel) {
        return null;
    }

    @Override
    public String getDFAbilityForLevel(int driveFormLevel) {
        return null;
    }
}
