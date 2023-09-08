package online.magicksaddon.magicsaddonmod;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.magicksaddon.capabilities.GlobalCapabilitiesMA;
import online.magicksaddon.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.capabilities.IPlayerCapabilitiesMA;
import online.magicksaddon.capabilities.ModCapabilitiesMA;

public class MagicksEntityEvents {

    private GlobalCapabilitiesMA globalData;
    private boolean setHasteActive;

    //Haste
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        IGlobalCapabilitiesMA globalData = ModCapabilitiesMA.getGlobal(event.getEntity());
        IPlayerCapabilities playerData = null;
        Player player = null;
        if (event.getEntity() instanceof Player) {
            player = (Player) event.getEntity();
            playerData = (IPlayerCapabilities) ModCapabilitiesMA.getPlayer(player);

            if (globalData != null) {
                //Haste
                if (globalData.getHasteTicks() > 0) {
                    globalData.remHasteTicks(1);
                    System.out.println("Haste Level: " + globalData.getHasteLevel() + " " + "Haste Ticks Remaining: " + globalData.getHasteTicks());
                    if (globalData.getHasteTicks() <= 0){
                        player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("Haste", -(0.25+(0.25* globalData.getHasteLevel())), AttributeModifier.Operation.MULTIPLY_BASE));
                        player.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(new AttributeModifier("Haste", -(0.25+(0.25* globalData.getHasteLevel())), AttributeModifier.Operation.MULTIPLY_BASE));
                    }
                }
                // Slow
                if (globalData.getSlowTicks() > 0){
                    globalData.remSlowTicks(1);
                    System.out.println("Slow Level: "+ globalData.getSlowLevel() + " " + "Slow Ticks Remaining: " + globalData.getSlowTicks());

                    if (event.getEntity() instanceof Mob){
                        //event.getEntity().getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("Slow", -(0.25 + (0.25 * globalData.getSlowLevel())), AttributeModifier.Operation.MULTIPLY_BASE));

                        if (globalData.getSlowTicks() <= 0){
                            event.getEntity().getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("Slow", 0.5 + (0.25 * globalData.getSlowLevel()), AttributeModifier.Operation.MULTIPLY_BASE));
                            event.getEntity().getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(new AttributeModifier("Slow", 0.5 + (0.25 * globalData.getSlowLevel()), AttributeModifier.Operation.MULTIPLY_BASE));

                        }
                    }


                }
            }
        }
    }
}