package online.remind.remind.client.model.mob;

import net.minecraft.resources.ResourceLocation;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.entity.enemies.BombEntity;
import software.bernie.geckolib.model.GeoModel;

public class BombModel extends GeoModel<BombEntity> {



    private static final ResourceLocation MODEL =
            ResourceLocation.fromNamespaceAndPath(
                    KingdomKeysReMind.MODID,
                    "geo/entity/bomb.geo.json"
            );

    private static final ResourceLocation ANIMATION =
            ResourceLocation.fromNamespaceAndPath(
                    KingdomKeysReMind.MODID,
                    "animations/entity/bomb.animation.json"
            );

    private static final ResourceLocation BOMB_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(
                    KingdomKeysReMind.MODID,
                    "textures/entity/bomb.png"
            );

    private static final ResourceLocation GRENADE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(
                    KingdomKeysReMind.MODID,
                    "textures/entity/grenade.png"
            );

    private static final ResourceLocation VOLCANO_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(
                    KingdomKeysReMind.MODID,
                    "textures/entity/volcano.png"
            );

    @Override
    public ResourceLocation getModelResource(BombEntity entity) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(BombEntity entity) {
        return switch (entity.getVariant()) {
            case BombEntity.VARIANT_GRENADE -> GRENADE_TEXTURE;
            case BombEntity.VARIANT_VOLCANO -> VOLCANO_TEXTURE;
            default -> BOMB_TEXTURE;
        };
    }

    @Override
    public ResourceLocation getAnimationResource(BombEntity entity) {
        return ANIMATION;
    }
}