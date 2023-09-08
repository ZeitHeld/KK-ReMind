package online.magicksaddon.item;

import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import online.kingdomkeys.kingdomkeys.item.MagicSpellItem;


public class ModItemsMA {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MagicksAddonMod.MODID);

        public static final RegistryObject<Item>
                hasteSpell = ITEMS.register("SpellHaste", () -> new Item(new Item.Properties())),
                slowSpell = ITEMS.register("SpellSlow", () -> new Item(new Item.Properties()));


    public static void register(IEventBus modEventBus) {
    }
}
