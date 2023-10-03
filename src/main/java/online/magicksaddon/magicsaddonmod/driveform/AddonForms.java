package online.magicksaddon.magicsaddonmod.driveform;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.lib.Strings;

import java.util.function.Supplier;

public class AddonForms {
    public static DeferredRegister<DriveForm> DRIVE_FORMS = DeferredRegister.create(new ResourceLocation(KingdomKeys.MODID, "driveforms"), "magicksaddon");

    //public static Supplier<IForgeRegistry<DriveForm>> registry = DRIVE_FORMS.makeRegistry(RegistryBuilder::new);

    static int order = 6;

     public static final RegistryObject<DriveForm>

        // Forms list
            RAGE = DRIVE_FORMS.register(Strings.DFMA_Prefix + "rage", () -> new DriveFormRage(MagicksAddonMod.MODID + ":"+ Strings.DFMA_Prefix + "rage", order++, new ResourceLocation(MagicksAddonMod.MODID, "textures/models/armor/rage.png"), true)),
            DARK = DRIVE_FORMS.register(Strings.DFMA_Prefix + "dark", () -> new DriveFormDark(MagicksAddonMod.MODID + ":"+ Strings.DFMA_Prefix + "dark", order++, new ResourceLocation(MagicksAddonMod.MODID, "textures/models/armor/dark.png"), false));

    ResourceLocation skinRL;

     public ResourceLocation getTextureLocation() {
        return skinRL;
    }
}
