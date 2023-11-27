package online.magicksaddon.magicsaddonmod.client;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import online.magicksaddon.magicsaddonmod.client.render.BerserkLayerRenderer;
import online.magicksaddon.magicsaddonmod.entity.ModEntitiesMA;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupMA {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ModEntitiesMA.registerRenderers(event);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        ModEntitiesMA.registerLayers(event);
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        for (Map.Entry<EntityType<?>, EntityRenderer<?>> entry : Minecraft.getInstance().getEntityRenderDispatcher().renderers.entrySet()) {
            if (entry.getValue() instanceof LivingEntityRenderer renderer && !(entry.getValue() instanceof PlayerRenderer)) {
                renderer.addLayer(new BerserkLayerRenderer<LivingEntity>(renderer, event.getEntityModels()));
            }
        }
        /*LivingEntityRenderer<Player, PlayerModel<Player>> renderer = event.getSkin("default");
        renderer.addLayer(new BerserkLayerRenderer<>(renderer, event.getEntityModels()));

        renderer = event.getSkin("slim");
        renderer.addLayer(new BerserkLayerRenderer<>(renderer, event.getEntityModels()));*/
    }


}
