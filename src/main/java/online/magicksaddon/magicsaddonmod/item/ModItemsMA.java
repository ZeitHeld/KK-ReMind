package online.magicksaddon.magicsaddonmod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import online.kingdomkeys.kingdomkeys.item.MagicSpellItem;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;


public class ModItemsMA{
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MagicksAddonMod.MODID);

    public static final RegistryObject<Item>
        hasteSpell = ITEMS.register("haste_spell", () -> new MagicSpellItem(new Item.Properties(), MagicksAddonMod.MODID+":magic_haste")),
        slowSpell = ITEMS.register("slow_spell", () -> new MagicSpellItem(new Item.Properties(), MagicksAddonMod.MODID+":magic_slow")),
        holySpell = ITEMS.register("holy_spell", () -> new MagicSpellItem(new Item.Properties(), MagicksAddonMod.MODID+":magic_holy")),
        ruinSpell = ITEMS.register("ruin_spell", () -> new MagicSpellItem(new Item.Properties(), MagicksAddonMod.MODID+":magic_ruin")),
        balloonSpell = ITEMS.register("balloon_spell", () -> new MagicSpellItem(new Item.Properties(), MagicksAddonMod.MODID+":magic_balloon")),
        ultimaSpell = ITEMS.register("ultima_spell", () -> new MagicSpellItem(new Item.Properties(), MagicksAddonMod.MODID+":magic_ultima")),
        cometSpell = ITEMS.register("comet_spell", () -> new MagicSpellItem(new Item.Properties(), MagicksAddonMod.MODID+":magic_comet"));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
