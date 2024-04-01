package online.magicksaddon.magicsaddonmod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.shotlock.ModShotlocks;
import online.kingdomkeys.kingdomkeys.shotlock.Shotlock;
import online.kingdomkeys.kingdomkeys.util.Utils;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.lib.StringsRM;
import online.magicksaddon.magicsaddonmod.shotlock.ModShotlocksRM;

import static net.minecraft.world.ContainerHelper.takeItem;

public class ShotlockOrbItem extends Item {
    String shotlocks;

    public ShotlockOrbItem(Properties properties, String name){
        super(properties);
        this.shotlocks = name;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand){
        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
        Shotlock shotlockInstance = ModShotlocks.registry.get().getValue(new ResourceLocation(shotlocks));

        if(!world.isClientSide){
            if (!playerData.getShotlockList().contains(shotlocks)){
                playerData.getShotlockList().add(shotlocks);
                takeItem(player);
				player.displayClientMessage(Component.translatable("Unlocked " + Utils.translateToLocal(shotlockInstance.getTranslationKey())), true);
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    private void takeItem(Player player) {
        if (!ItemStack.matches(player.getMainHandItem(), ItemStack.EMPTY) && player.getMainHandItem().getItem() == this) {
            player.getMainHandItem().shrink(1);
        } else if (!ItemStack.matches(player.getOffhandItem(), ItemStack.EMPTY) && player.getOffhandItem().getItem() == this) {
            player.getOffhandItem().shrink(1);
        }
    }
}
