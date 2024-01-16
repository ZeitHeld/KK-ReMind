package online.magicksaddon.magicsaddonmod.driveform;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import online.kingdomkeys.kingdomkeys.lib.Strings;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.mixin.DriveFormMixin;

@Mod.EventBusSubscriber(modid = MagicksAddonMod.MODID)
public class DriveFormRage extends DriveForm {
	ResourceLocation skinRL2;

    public DriveFormRage(String registeryName, int order, ResourceLocation skinRL, boolean hasKeychain, boolean baseGrowthAbilities) {
        super(registeryName, order, hasKeychain, baseGrowthAbilities);
        ((DriveFormMixin) this).setColor(new float[] { 0.5F, 0F, 0F });
        skinRL2 = skinRL;        
    }
    
    @Override
    public ResourceLocation getTextureLocation() {
    	return skinRL2;
    }
   
}
