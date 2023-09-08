package online.magicksaddon.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModTabMA {
    public static final CreativeModeTab MAGICKS_ADDON_TAB = new CreativeModeTab("magicksaddontab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItemsMA.hasteSpell.get());

        }
    };
}
