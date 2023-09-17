package online.magicksaddon.magicsaddonmod.entity;

import static net.minecraftforge.registries.ForgeRegistries.ENTITY_TYPES;

import java.util.function.BiFunction;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.client.model.BalloonModel;
import online.magicksaddon.magicsaddonmod.client.model.BalloongaModel;
import online.magicksaddon.magicsaddonmod.client.model.HolyModel;
import online.magicksaddon.magicsaddonmod.client.model.RuinModel;
import online.magicksaddon.magicsaddonmod.client.render.BalloonEntityRenderer;
import online.magicksaddon.magicsaddonmod.client.render.BalloongaEntityRenderer;
import online.magicksaddon.magicsaddonmod.client.render.HolyEntityRenderer;
import online.magicksaddon.magicsaddonmod.client.render.RuinEntityRenderer;
import online.magicksaddon.magicsaddonmod.entity.magic.BalloonEntity;
import online.magicksaddon.magicsaddonmod.entity.magic.BalloongaEntity;
import online.magicksaddon.magicsaddonmod.entity.magic.HolyEntity;
import online.magicksaddon.magicsaddonmod.entity.magic.RuinEntity;

public class ModEntitiesMA {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ENTITY_TYPES, MagicksAddonMod.MODID);

    public static final RegistryObject<EntityType<HolyEntity>> TYPE_HOLY = createEntityType(HolyEntity::new, HolyEntity::new, MobCategory.MISC,"entity_holy", 0.5F, 0.5F);
    public static final RegistryObject<EntityType<RuinEntity>> TYPE_RUIN = createEntityType(RuinEntity::new, RuinEntity::new, MobCategory.MISC,"entity_ruin", 0.5F, 0.5F);
    public static final RegistryObject<EntityType<BalloonEntity>> TYPE_BALLOON = createEntityType(BalloonEntity::new, BalloonEntity::new, MobCategory.MISC, "entity_balloon", 0.5F, 0.5F);
    public static final RegistryObject<EntityType<BalloongaEntity>> TYPE_BALLOONGA = createEntityType(BalloongaEntity::new, BalloongaEntity::new, MobCategory.MISC, "entity_balloonga", 1F, 1F);

    public static <T extends Entity, M extends EntityType<T>>RegistryObject<EntityType<T>> createEntityType(EntityType.EntityFactory<T> factory, BiFunction<PlayMessages.SpawnEntity, Level, T> clientFactory, MobCategory classification, String name, float sizeX, float sizeY) {
        return ENTITIES.register(name, () -> EntityType.Builder.of(factory, classification)
                .setCustomClientFactory(clientFactory)
                .setShouldReceiveVelocityUpdates(false)
                .setUpdateInterval(1)
                .setTrackingRange(8)
                .sized(sizeX, sizeY)
                .build(name));
    }
    @OnlyIn(Dist.CLIENT)
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HolyModel.LAYER_LOCATION, HolyModel::createBodyLayer);
        event.registerLayerDefinition(RuinModel.LAYER_LOCATION, RuinModel::createBodyLayer);
        event.registerLayerDefinition(BalloonModel.LAYER_LOCATION, BalloonModel::createBodyLayer);
        event.registerLayerDefinition(BalloongaModel.LAYER_LOCATION, BalloongaModel::createBodyLayer);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {

        event.registerEntityRenderer(TYPE_HOLY.get(), HolyEntityRenderer::new);
        event.registerEntityRenderer(TYPE_RUIN.get(), RuinEntityRenderer::new);
        event.registerEntityRenderer(TYPE_BALLOON.get(), BalloonEntityRenderer::new);
        event.registerEntityRenderer(TYPE_BALLOONGA.get(), BalloongaEntityRenderer::new);

    }


}
