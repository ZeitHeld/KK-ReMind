package online.magicksaddon.magicsaddonmod.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.client.model.BalloonModel;

import javax.annotation.Nullable;

public class BalloonEntityRenderer extends EntityRenderer<ThrowableProjectile> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(MagicksAddonMod.MODID,"textures/entity/models/balloon.png");
    BalloonModel balloonModel;

    public BalloonEntityRenderer(EntityRendererProvider.Context context){
        super(context);
        this.shadowRadius = 0.25F;
        balloonModel = new BalloonModel<>(context.bakeLayer(BalloonModel.LAYER_LOCATION));
    }

    float ticks = 0F;
    float prevRotationTicks = 0F;

    @Override
    public void render(ThrowableProjectile entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        {
            VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
            matrixStackIn.scale(2, 2, 2);
            this.balloonModel.renderToBuffer(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.NO_OVERLAY, 1,1,1,1);
        }

        matrixStackIn.popPose();
        super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Nullable
    @Override
    public ResourceLocation getTextureLocation(ThrowableProjectile entity) {
        return TEXTURE;
    }
}
