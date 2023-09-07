package online.magicksaddon.magicsaddonmod;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.magicksaddon.capabilities.GlobalCapabilitiesMA;
import online.magicksaddon.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.capabilities.ModCapabilitiesMA;

public class MagicksEntityEvents {

    private GlobalCapabilitiesMA globalData;

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
                    //Haste Tree
                if (globalData.getHasteTicks() > 0) {
                    globalData.remHasteTicks(1);
                    // Haste
                    if (globalData.getHasteLevel() == 1) {
                        if(event.getEntity().tickCount % 20 == 0){
                            System.out.println("Haste!");
                            player.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(new AttributeModifier("Attack Speed", 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL));
                            player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("Movement Speed", 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL));
                        }
                    } else if (globalData.getHasteLevel() == 2) {
                            //Hastera
                        if(event.getEntity().tickCount % 20 == 0) {
                            System.out.println("Hastera!");
                            player.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(new AttributeModifier("Hastera", 0.75, AttributeModifier.Operation.MULTIPLY_BASE));
                            player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("Hastera", 0.75, AttributeModifier.Operation.MULTIPLY_BASE));
                        }
                    } else if (globalData.getHasteLevel() == 3) {
                        //Hastega
                        if (event.getEntity().tickCount % 20 == 0) {
                            System.out.println("Hastega!");
                            player.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(new AttributeModifier("Hastega", 1, AttributeModifier.Operation.MULTIPLY_BASE));
                            player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("Hastega", 1, AttributeModifier.Operation.MULTIPLY_BASE));
                        }
                    }

                }
                //Slow

            }
        }
    }
}
