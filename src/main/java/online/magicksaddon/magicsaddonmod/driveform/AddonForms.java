package online.magicksaddon.magicsaddonmod.driveform;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.lib.Strings;

public class AddonForms {
    public static DeferredRegister<DriveForm> DRIVE_FORMS = DeferredRegister.create(new ResourceLocation(KingdomKeys.MODID, "driveforms"), MagicksAddonMod.MODID);

    static int order = 10;

     public static final RegistryObject<DriveForm>

        // Forms list
            RAGE = DRIVE_FORMS.register(Strings.DFMA_Prefix + "rage", () -> new DriveFormRage(MagicksAddonMod.MODID + ":"+ Strings.DFMA_Prefix + "rage", order++, new ResourceLocation(MagicksAddonMod.MODID, "textures/models/armor/rage.png"), false)),
            DARK = DRIVE_FORMS.register(Strings.DFMA_Prefix + "dark", () -> new DriveFormDark(MagicksAddonMod.MODID + ":"+ Strings.DFMA_Prefix + "dark", order++, new ResourceLocation(MagicksAddonMod.MODID, "textures/models/armor/dark_mode.png"), false));
}
