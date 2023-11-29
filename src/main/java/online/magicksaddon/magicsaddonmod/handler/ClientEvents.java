package online.magicksaddon.magicsaddonmod.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import online.magicksaddon.magicsaddonmod.sound.BerserkAuraSoundInstance;

public class ClientEvents {

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinLevelEvent e) {
        if (e.getEntity() instanceof LivingEntity ent) {
            if (e.getLevel().isClientSide) {
                Minecraft minecraft = Minecraft.getInstance();
                minecraft.getSoundManager().play(new BerserkAuraSoundInstance(ent));
            }
        }
    }
}
