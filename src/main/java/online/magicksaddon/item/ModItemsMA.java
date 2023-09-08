package online.magicksaddon.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
        hasteSpell = ITEMS.register("haste_spell", () -> new MagicSpellItem(new Item.Properties().tab(ModCreativeModTabMA.MAGICKS_ADDON_TAB), MagicksAddonMod.MODID+":magic_haste")),
        slowSpell = ITEMS.register("slow_spell", () -> new MagicSpellItem(new Item.Properties().tab(ModCreativeModTabMA.MAGICKS_ADDON_TAB), MagicksAddonMod.MODID+":magic_slow"));
        //holySpell = ITEMS.register("holy_spell", () -> new MagicSpellItem(new Item.Properties().tab(ModCreativeModTabMA.MAGICKS_ADDON_TAB), MagicksAddonMod.MODID+":magic_holy"));
        //ruinSpell = ITEMS.register("ruin_spell", () -> new MagicSpellItem(new Item.Properties().tab(ModCreativeModTabMA.MAGICKS_ADDON_TAB), MagicksAddonMod.MODID+":magic_ruin"));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
