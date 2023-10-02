package online.magicksaddon.magicsaddonmod.driveform;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;

import java.util.function.Supplier;

public class AddonForms {
    public static DeferredRegister<DriveForm> DRIVE_FORMS = DeferredRegister.create(new ResourceLocation(KingdomKeys.MODID, "driveforms"), "magicksaddon");

    public static Supplier<IForgeRegistry<DriveForm>> registry = DRIVE_FORMS.makeRegistry(RegistryBuilder::new);

    static int order = 6;

    // public static final RegistryObject<DriveForm>

        // Forms list
           // RAGE = DRIVE_FORMS.register(DriveForm.RAGE.getPath(), () -> new DriveFormRage(DriveForm.RAGE.toString(), order++, true)),
           // DARK = DRIVE_FORMS.register(DriveForm.DARK.getPath(), () -> new DriveFormDark(DriveForm.DARK.toString(), order++, true));
}
