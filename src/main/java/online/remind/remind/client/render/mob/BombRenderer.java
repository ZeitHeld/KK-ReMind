package online.remind.remind.client.render.mob;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import online.remind.remind.client.model.mob.BombModel;
import online.remind.remind.entity.enemies.BombEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BombRenderer extends GeoEntityRenderer<BombEntity> {

    private static final float MODEL_SCALE = 2.25F;

    public BombRenderer(
            EntityRendererProvider.Context context
    ) {
        super(
                context,
                new BombModel()
        );

        this.shadowRadius = 0.55F;

        this.scaleWidth = MODEL_SCALE;
        this.scaleHeight = MODEL_SCALE;
    }
}