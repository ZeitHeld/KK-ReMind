package online.magicksaddon.magicsaddonmod.client;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import online.magicksaddon.magicsaddonmod.client.render.AutoLifeLayerRenderer;
import online.magicksaddon.magicsaddonmod.client.render.BerserkLayerRenderer;
import online.magicksaddon.magicsaddonmod.entity.ModEntitiesRM;
import online.magicksaddon.magicsaddonmod.handler.ClientEventsRM;
import online.magicksaddon.magicsaddonmod.handler.InputHandlerRM;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupRM {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ModEntitiesRM.registerRenderers(event);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        ModEntitiesRM.registerLayers(event);
    }

    /*@SubscribeEvent
    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
        for (MAInputHandler.Keybinds key : MAInputHandler.Keybinds.values())
            event.register(key.getKeybind());
    }*/

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        /*for (Map.Entry<EntityType<?>, EntityRenderer<?>> entry : Minecraft.getInstance().getEntityRenderDispatcher().renderers.entrySet()) {
            if (entry.getValue() instanceof LivingEntityRenderer renderer && !(entry.getValue() instanceof PlayerRenderer)) {
                renderer.addLayer(new BerserkLayerRenderer<LivingEntity>(renderer, event.getEntityModels()));
            }
        }*/
        LivingEntityRenderer<Player, PlayerModel<Player>> renderer = event.getSkin("default");
        renderer.addLayer(new BerserkLayerRenderer<>(renderer, event.getEntityModels()));
        renderer.addLayer(new AutoLifeLayerRenderer<>(renderer, event.getEntityModels()));

        renderer = event.getSkin("slim");
        renderer.addLayer(new BerserkLayerRenderer<>(renderer, event.getEntityModels()));
        renderer.addLayer(new AutoLifeLayerRenderer<>(renderer, event.getEntityModels()));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new ClientEventsRM());
    }


}
