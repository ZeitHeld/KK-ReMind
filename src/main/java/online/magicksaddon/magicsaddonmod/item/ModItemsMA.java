package online.magicksaddon.magicsaddonmod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import online.kingdomkeys.kingdomkeys.item.KeybladeItem;
import online.kingdomkeys.kingdomkeys.item.KeychainItem;
import online.kingdomkeys.kingdomkeys.item.MagicSpellItem;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.lib.Strings;


public class ModItemsMA{
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MagicksAddonMod.MODID);

    public static final RegistryObject<Item>
            // Spell Orbs
        hasteSpell = ITEMS.register("haste_spell", () -> new MagicSpellItem(new Item.Properties(), MagicksAddonMod.MODID+":magic_haste")),
        slowSpell = ITEMS.register("slow_spell", () -> new MagicSpellItem(new Item.Properties(), MagicksAddonMod.MODID+":magic_slow")),
        holySpell = ITEMS.register("holy_spell", () -> new MagicSpellItem(new Item.Properties(), MagicksAddonMod.MODID+":magic_holy")),
        ruinSpell = ITEMS.register("ruin_spell", () -> new MagicSpellItem(new Item.Properties(), MagicksAddonMod.MODID+":magic_ruin")),
        balloonSpell = ITEMS.register("balloon_spell", () -> new MagicSpellItem(new Item.Properties(), MagicksAddonMod.MODID+":magic_balloon")),
        ultimaSpell = ITEMS.register("ultima_spell", () -> new MagicSpellItem(new Item.Properties(), MagicksAddonMod.MODID+":magic_ultima")),
        cometSpell = ITEMS.register("comet_spell", () -> new MagicSpellItem(new Item.Properties(), MagicksAddonMod.MODID+":magic_comet")),
        berserkSpell = ITEMS.register("berserk_spell",() -> new MagicSpellItem(new Item.Properties(),MagicksAddonMod.MODID+":magic_berserk")),

            // Keyblades
        xephiroKeyblade = ITEMS.register("xephiro_keyblade", () -> new KeybladeItem(new Item.Properties())),
            // Keychains
        xephiroKeybladeChain = ITEMS.register("xephiro_keyblade_chain", () -> new KeychainItem());
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
