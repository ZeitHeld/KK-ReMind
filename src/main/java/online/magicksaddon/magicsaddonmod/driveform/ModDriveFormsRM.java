package online.magicksaddon.magicsaddonmod.driveform;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.lib.StringsRM;

public class ModDriveFormsRM {
    public static DeferredRegister<DriveForm> DRIVE_FORMS = DeferredRegister.create(new ResourceLocation(KingdomKeys.MODID, "driveforms"), MagicksAddonMod.MODID);

    static int order = 10;



     public static final RegistryObject<DriveForm>

        // Forms list
            RAGE = DRIVE_FORMS.register(StringsRM.DFMA_Prefix + "rage", () -> new DriveFormRage(MagicksAddonMod.MODID + ":"+ StringsRM.DFMA_Prefix + "rage", order++, new ResourceLocation(MagicksAddonMod.MODID, "textures/models/armor/rage.png"), false, false)),
            DARK = DRIVE_FORMS.register(StringsRM.DFMA_Prefix + "dark", () -> new DriveFormDark(MagicksAddonMod.MODID + ":"+ StringsRM.DFMA_Prefix + "dark", order++, new ResourceLocation(MagicksAddonMod.MODID, "textures/models/armor/dark_mode.png"), false, false)),
            LIGHT = DRIVE_FORMS.register(StringsRM.DFMA_Prefix + "light", () -> new DriveFormLight(MagicksAddonMod.MODID + ":"+ StringsRM.DFMA_Prefix + "light", order++, new ResourceLocation(MagicksAddonMod.MODID, "textures/models/armor/light.png"), false, false));
}
