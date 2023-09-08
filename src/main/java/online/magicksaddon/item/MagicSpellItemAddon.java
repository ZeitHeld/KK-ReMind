package online.magicksaddon.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import online.kingdomkeys.kingdomkeys.item.MagicSpellItem;
import online.magicksaddon.capabilities.IPlayerCapabilitiesMA;
import online.magicksaddon.capabilities.ModCapabilitiesMA;
import online.magicksaddon.magic.ModMagicks;

public class MagicSpellItemAddon extends MagicSpellItem{
        String magicAddon;
    public MagicSpellItemAddon(Properties properties, String name) {
        super(properties, name);
        this.magicAddon = name;

    }






}
