package online.magicksaddon.magicsaddonmod.driveform;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import online.magicksaddon.magicsaddonmod.DawnCrossDuskMod;
import online.magicksaddon.magicsaddonmod.lib.StringsX;

public class AddonForms {
    public static DeferredRegister<DriveForm> DRIVE_FORMS = DeferredRegister.create(new ResourceLocation(KingdomKeys.MODID, "driveforms"), DawnCrossDuskMod.MODID);

    static int order = 10;

     public static final RegistryObject<DriveForm>

        // Forms list
            RAGE = DRIVE_FORMS.register(StringsX.DFMA_Prefix + "rage", () -> new DriveFormRage(DawnCrossDuskMod.MODID + ":"+ StringsX.DFMA_Prefix + "rage", order++, new ResourceLocation(DawnCrossDuskMod.MODID, "textures/models/armor/rage.png"), false, true)),
            DARK = DRIVE_FORMS.register(StringsX.DFMA_Prefix + "dark", () -> new DriveFormDark(DawnCrossDuskMod.MODID + ":"+ StringsX.DFMA_Prefix + "dark", order++, new ResourceLocation(DawnCrossDuskMod.MODID, "textures/models/armor/dark_mode.png"), false, true)),
            LIGHT = DRIVE_FORMS.register(StringsX.DFMA_Prefix + "light", () -> new DriveFormDark(DawnCrossDuskMod.MODID + ":"+ StringsX.DFMA_Prefix + "light", order++, new ResourceLocation(DawnCrossDuskMod.MODID, "textures/models/armor/light.png"), false, true));
}
