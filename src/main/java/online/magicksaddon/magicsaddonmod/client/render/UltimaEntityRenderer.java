package online.magicksaddon.magicsaddonmod.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.client.model.RuinModel;
import online.magicksaddon.magicsaddonmod.client.model.UltimaModel;
import online.magicksaddon.magicsaddonmod.entity.magic.UltimaEntity;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class UltimaEntityRenderer extends EntityRenderer<UltimaEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(MagicksAddonMod.MODID,"textures/entity/models/ultima.png");

	UltimaModel<Entity> model;

	public UltimaEntityRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.shadowRadius = 0.25F;
        model = new UltimaModel<>(context.bakeLayer(UltimaModel.LAYER_LOCATION));

	}

	@Override
	public void render(UltimaEntity entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
		matrixStackIn.pushPose();
		{
            //matrixStackIn.translate(0, -0.7, 0);
			float ticks = entity.tickCount;
			matrixStackIn.scale(3,3,3);
			
			//float radius = (ticks-entity.getStartingTicks()) * 0.2f;
			float radius = 2f;
			 if(entity.getStartingTicks() > -1) { // Grow alongside hitbox, it no grow
				//System.out.println("Render: "+entity.getStartingTicks());
				matrixStackIn.scale(radius, radius, radius);
				//matrixStackIn.scale((ticks-25) * 0.2f, 0, (ticks-25) * 0.2f);
	            model.renderToBuffer(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.NO_OVERLAY, 1,1,1,1);
			} else if (ticks > 25) {
	            model.renderToBuffer(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.NO_OVERLAY, 1,1,1,1);

			}

		}
		matrixStackIn.popPose();
		super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Nullable
	@Override
	public ResourceLocation getTextureLocation(UltimaEntity entity) {
		return TEXTURE;
	}
}
