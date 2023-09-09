package online.magicksaddon.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.lib.Strings;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;

public class ModMagicks {

    static int order = 11;

    //The Command
    public static DeferredRegister<Magic> MAGIC = DeferredRegister.create(new ResourceLocation(KingdomKeys.MODID, "magics"), "magicksaddon");

    //Normal Spells
    public static final RegistryObject<Magic>
            HASTE = MAGIC.register("magic_haste", () -> new magicHaste(new ResourceLocation(MagicksAddonMod.MODID, "magic_haste"), true, 4, order++)),
            SLOW = MAGIC.register("magic_slow", () -> new magicSlow(new ResourceLocation(MagicksAddonMod.MODID, "magic_slow"), true, 4, order++)),
            HOLY = MAGIC.register("magic_holy",() -> new magicHoly(new ResourceLocation(MagicksAddonMod.MODID, "magic_holy"), true, 4, null, order++)),
            RUIN = MAGIC.register("magic_ruin", () -> new magicRuin(new ResourceLocation(MagicksAddonMod.MODID, "magic_ruin"), true, 4, null, order++));



    // Add more magic later...



    }






