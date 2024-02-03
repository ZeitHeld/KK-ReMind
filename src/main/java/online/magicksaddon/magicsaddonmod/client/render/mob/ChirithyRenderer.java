package online.magicksaddon.magicsaddonmod.client.render.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import online.kingdomkeys.kingdomkeys.entity.EntityHelper;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.client.model.mob.chirithyModel;
import online.magicksaddon.magicsaddonmod.entity.mob.ChirithyEntity;

public class ChirithyRenderer extends MobRenderer<ChirithyEntity, chirithyModel<ChirithyEntity>> {

    public ChirithyRenderer(EntityRendererProvider.Context context){
        super(context, new chirithyModel<>(context.bakeLayer(chirithyModel.LAYER_LOCATION)),0.35F);
    }

    @Override
    public void render(ChirithyEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn){
        matrixStackIn.pushPose();
        {
            if (EntityHelper.getState(entityIn) == 1) {
                matrixStackIn.translate(0, -1.5F, 0);
            } else if(EntityHelper.getState(entityIn) == 2){
                matrixStackIn.translate(0, 0.3F, 0);
                if(entityIn.tickCount % 10 == 0) {
                    matrixStackIn.scale(1.2F, 1.2F, 1.2F);
                }
            }
            super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
        matrixStackIn.popPose();
    }

    private static final ResourceLocation TEXTURE = new ResourceLocation(MagicksAddonMod.MODID, "textures/entity/mob/chirithy.png");

    @Override
    public ResourceLocation getTextureLocation(ChirithyEntity entity){
        return TEXTURE;
    }

    @Override
    protected void scale(ChirithyEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(2,2,2);
        super.scale(entitylivingbaseIn, matrixStackIn, partialTickTime);
    }
}
