package online.magicksaddon.magicsaddonmod.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesX;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesX;
import online.magicksaddon.magicsaddonmod.client.model.AutoLifeModel;

@OnlyIn(Dist.CLIENT)
public class AutoLifeLayerRenderer<T extends LivingEntity> extends RenderLayer<T, PlayerModel<T>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(MagicksAddonMod.MODID, "textures/entity/models/auto-life.png");
    public static final String BOX = "box";
    private final ModelPart box;

    public AutoLifeLayerRenderer(RenderLayerParent<T, PlayerModel<T>> p_174540_, EntityModelSet p_174541_) {
        super(p_174540_);
        ModelPart modelpart = p_174541_.bakeLayer(AutoLifeModel.LAYER_LOCATION);
        this.box = modelpart.getChild("Base");
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }

    public void renderEntity(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (ModCapabilitiesX.getGlobal(entitylivingbaseIn) != null) {
            IGlobalCapabilitiesX globalData = ModCapabilitiesX.getGlobal(entitylivingbaseIn);
            if (globalData.getAutoLifeActive() == 1) {
                VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.entityTranslucent(TEXTURE));

                matrixStackIn.pushPose();
                float scale = 1;
                if (entitylivingbaseIn instanceof Player) {
                    matrixStackIn.scale(scale, scale * 1.0F, scale);
                    matrixStackIn.translate(0.0D, 1.0D, 0.0D);
                }
            }
        }
    }
}
