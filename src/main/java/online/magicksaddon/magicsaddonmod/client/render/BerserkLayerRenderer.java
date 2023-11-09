package online.magicksaddon.magicsaddonmod.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import online.kingdomkeys.kingdomkeys.util.IDisabledAnimations;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesMA;

@OnlyIn(Dist.CLIENT)
public class BerserkLayerRenderer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
        private PlayerRenderer renderPlayer;

        public BerserkLayerRenderer(RenderLayerParent<T, M> entityRendererIn){
            super (entityRendererIn);
            this.renderPlayer = (PlayerRenderer) entityRendererIn;
        }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int pPackedLight, T entitylivingbaseIn, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (ModCapabilitiesMA.getGlobal(entitylivingbaseIn) != null) {
            IGlobalCapabilitiesMA globalData = ModCapabilitiesMA.getGlobal(entitylivingbaseIn);
            if (globalData.getBerserkTicks() > 0){
                //
                VertexConsumer ivertexbuilder = ItemRenderer.getFoilBuffer(bufferIn, RenderType.entityTranslucent(getTextureLocation(entitylivingbaseIn)), false, false);
                renderPlayer.getModel().renderToBuffer(matrixStackIn, ivertexbuilder, pPackedLight, OverlayTexture.NO_OVERLAY, 1,0,0,0.25F);
            }

        }
    }
}
