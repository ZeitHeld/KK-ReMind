package online.magicksaddon.magicsaddonmod.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.entity.magic.UltimaEntity;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class UltimaEntityRenderer extends EntityRenderer<UltimaEntity> {

    public UltimaEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.25F;
    }

    @Override
    public void render(UltimaEntity entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn){
        VertexConsumer buffer = bufferIn.getBuffer(Sheets.translucentCullBlockSheet());
        BakedModel model = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(MagicksAddonMod.MODID, "entity/ultima"));

            {
                float ticks = entity.tickCount;
                if(ticks < 25){ // Stationary spin


                }else if (ticks > 25){ //Grow alongside hitbox, it no grow
                    matrixStackIn.scale(ticks*0.2f, ticks*0.2f, ticks*0.2f);

                }


                }
                matrixStackIn.popPose();
                super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
            }

    @Nullable
    @Override
    public ResourceLocation getTextureLocation(UltimaEntity entity) {
        return new ResourceLocation(MagicksAddonMod.MODID, "textures/entity/models/ultima.png");
    }
}

