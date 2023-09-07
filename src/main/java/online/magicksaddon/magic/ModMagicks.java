package online.magicksaddon.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;

public class ModMagicks {

    static int order = 11;

    //The Command
    public static DeferredRegister<Magic> MAGIC = DeferredRegister.create(new ResourceLocation(KingdomKeys.MODID, "magics"), "magicksaddon");

    //Normal Spells
    public static final RegistryObject<Magic>
            HASTE = MAGIC.register("magic_haste", () -> new magicHaste(new ResourceLocation(MagicksAddonMod.MODID, "magic_haste"), true, 4, order++)),
            SLOW = MAGIC.register("magic_slow", () -> new magicSlow(new ResourceLocation(MagicksAddonMod.MODID, "magic_slow"), true, 4, order++));
            //RUIN = MAGIC.register(new ResourceLocation(Strings.Magic.magicRuin).getPath(), () -> new magicRuin(Strings.magicRuin, true, 4, order++));
            //PEARL = MAGIC.register(new ResourceLocation(String.Magic.magicPearl).getPath(), () -> new magicPearl(Strings.Magic.magicPearl, true, 4, order++));

    // Add more magic later...



    }






