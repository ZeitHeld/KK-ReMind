package online.magicksaddon.magicsaddonmod.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.client.model.RuinModel;
import online.magicksaddon.magicsaddonmod.entity.magic.RuinEntity;

import javax.annotation.Nullable;

public class RuinEntityRenderer extends EntityRenderer<ThrowableProjectile> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(MagicksAddonMod.MODID,"textures/entity/models/ruin.png");
    RuinModel ruinModel;

    public RuinEntityRenderer(EntityRendererProvider.Context context){
        super(context);
        this.shadowRadius = 0.25F;
        ruinModel = new RuinModel<>(context.bakeLayer(ruinModel.LAYER_LOCATION));
    }

    float ticks = 0F;
    float prevRotationTicks = 0F;

    @Override
    public void render(ThrowableProjectile entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        {

            float rotation = prevRotationTicks + (ticks - prevRotationTicks) * partialTicks;

            float speed = 2;
            float scale = 0;
            int maxTicks = 0;



            //float r = 1, g = 0, b = 0;
            VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
            matrixStackIn.translate(0, 1, 0);

            matrixStackIn.scale(scale, scale, scale);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(rotation));

            this.ruinModel.renderToBuffer(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.NO_OVERLAY, 1,1,1,1);

            prevRotationTicks = ticks;
            ticks += speed;


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
