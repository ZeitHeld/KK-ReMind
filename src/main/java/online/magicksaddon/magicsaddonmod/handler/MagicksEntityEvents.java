package online.magicksaddon.magicsaddonmod.handler;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesMA;

public class MagicksEntityEvents {






    //Haste
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {

        IGlobalCapabilitiesMA globalData = ModCapabilitiesMA.getGlobal(event.getEntity());
        if(globalData != null) {
        IPlayerCapabilities playerData;
        Player player;
        // Spells go Down Below

        // Slow
            if(globalData != null) {
        if (globalData.getSlowTicks() > 0) {
            globalData.remSlowTicks(1);
            //System.out.println("Slow Level: " + globalData.getSlowLevel() + " " + "Slow Ticks Remaining: " + globalData.getSlowTicks());
            if (globalData.getSlowTicks() <= 0) {
                event.getEntity().getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("Slow", 0.1 + (0.1 * globalData.getSlowLevel()), AttributeModifier.Operation.MULTIPLY_BASE));
                event.getEntity().getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(new AttributeModifier("Slow", 0.1 + (0.1 * globalData.getSlowLevel()), AttributeModifier.Operation.MULTIPLY_BASE));
                }
            }
        }
        // Haste
        if (event.getEntity() instanceof Player) {
            player = (Player) event.getEntity();
            playerData = (IPlayerCapabilities) ModCapabilitiesMA.getPlayer(player);
            if (globalData != null) {
                if (globalData.getHasteTicks() > 0) {
                    globalData.remHasteTicks(1);
                    //System.out.println("Haste Level: " + globalData.getHasteLevel() + " " + "Haste Ticks Remaining: " + globalData.getHasteTicks());
                    if (globalData.getHasteTicks() <= 0) {
                        player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("Haste", -(0.25 + (0.25 * globalData.getHasteLevel())), AttributeModifier.Operation.MULTIPLY_BASE));
                        player.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(new AttributeModifier("Haste", -(0.25 + (0.25 * globalData.getHasteLevel())), AttributeModifier.Operation.MULTIPLY_BASE));
                    }
                }
            }
        }
        // Next Spell
        }
    }
}