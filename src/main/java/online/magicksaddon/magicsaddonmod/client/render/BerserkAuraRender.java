package online.magicksaddon.magicsaddonmod.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import online.kingdomkeys.kingdomkeys.capability.IGlobalCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.client.model.BerserkAuraModel;

@OnlyIn(Dist.CLIENT)
public class BerserkAuraRender<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(MagicksAddonMod.MODID,"textures/entity/models/berserk1.png");

    ModelPart bb_main;
    BerserkAuraModel<?> berserkAuraModel;

    public BerserkAuraRender(RenderLayerParent<T,M> entityRendererIn, EntityModelSet modelSet){
        super(entityRendererIn);
        ModelPart modelpart = modelSet.bakeLayer(BerserkAuraModel.LAYER_LOCATION);
        berserkAuraModel = new BerserkAuraModel<>(modelSet.bakeLayer(BerserkAuraModel.LAYER_LOCATION));

        this.bb_main = modelpart.getChild("bb_main");
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (ModCapabilitiesMA.getGlobal(entitylivingbaseIn) != null) {
            IGlobalCapabilitiesMA globalData = ModCapabilitiesMA.getGlobal(entitylivingbaseIn);
            if (globalData.getBerserkModelTicks() > 0) {
                VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
                matrixStackIn.pushPose();
                matrixStackIn.translate(0, -1, 0);
                float scale = (10F - globalData.getBerserkModelTicks()) / 5F;
                System.out.println(scale);
                matrixStackIn.scale(scale * 1.2F, scale, scale * 1.2F);
                this.berserkAuraModel.renderToBuffer(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                matrixStackIn.popPose();
            }
        }
    }
}
