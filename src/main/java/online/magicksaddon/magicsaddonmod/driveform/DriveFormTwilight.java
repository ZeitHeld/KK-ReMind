package online.magicksaddon.magicsaddonmod.driveform;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import online.magicksaddon.magicsaddonmod.KingdomKeysReMind;
import online.magicksaddon.magicsaddonmod.mixin.DriveFormMixin;

@Mod.EventBusSubscriber(modid = KingdomKeysReMind.MODID)
public class DriveFormTwilight extends DriveForm {
    ResourceLocation skinRL2;
    public DriveFormTwilight(String registeryName, int order, ResourceLocation skinRL, boolean hasKeychain, boolean baseGrowthAbilities) {
        super(registeryName, order, hasKeychain, baseGrowthAbilities);
        ((DriveFormMixin) this).setColor(new float[] {0.25F,0.25F,0.25F});
        skinRL2 = skinRL;
    }

    // Twilight Form EXP Gain

    @Override
    public ResourceLocation getTextureLocation() {
        return skinRL2;
    }
}
