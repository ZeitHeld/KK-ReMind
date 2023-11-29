package online.magicksaddon.magicsaddonmod.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;

public class BerserkAuraModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MagicksAddonMod.MODID, "berserk"), "main");
    private final ModelPart Base;

    public BerserkAuraModel(ModelPart root) {
        this.Base = root.getChild("Base");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Base = partdefinition.addOrReplaceChild("Base", CubeListBuilder.create(), PartPose.offset(0.0002F, 9.4231F, 0.3803F));

        PartDefinition right2_r1 = Base.addOrReplaceChild("right2_r1", CubeListBuilder.create().texOffs(21, 0).addBox(-9.2356F, -22.9101F, 11.6266F, 21.0F, 40.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1498F, 5.1623F, -0.4447F, 2.9234F, -0.7854F, -3.1416F));

        PartDefinition back2_r1 = Base.addOrReplaceChild("back2_r1", CubeListBuilder.create().texOffs(21, 0).addBox(-9.75F, -22.989F, 11.9824F, 21.0F, 40.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1498F, 5.1623F, -0.4447F, -0.2182F, -0.7854F, 0.0F));

        PartDefinition front2_r1 = Base.addOrReplaceChild("front2_r1", CubeListBuilder.create().texOffs(21, 0).addBox(-10.6F, -22.355F, -12.1229F, 21.0F, 40.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1498F, 5.1623F, -0.4447F, 0.2182F, -0.7854F, 0.0F));

        PartDefinition left2_r1 = Base.addOrReplaceChild("left2_r1", CubeListBuilder.create().texOffs(21, 0).addBox(-10.0356F, -22.9101F, -11.6266F, 21.0F, 40.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1498F, 5.1623F, -0.4447F, -2.9234F, -0.7854F, 3.1416F));

        PartDefinition left_r1 = Base.addOrReplaceChild("left_r1", CubeListBuilder.create().texOffs(21, 0).addBox(-10.0F, -31.0F, 0.0F, 21.0F, 40.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.9998F, 5.5769F, -0.3803F, 0.0F, -1.5708F, 0.2182F));

        PartDefinition right_r1 = Base.addOrReplaceChild("right_r1", CubeListBuilder.create().texOffs(21, 0).addBox(-10.0F, -31.0F, 0.0F, 21.0F, 40.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.8002F, 5.5769F, -0.3803F, 0.0F, -1.5708F, -0.2182F));

        PartDefinition back_r1 = Base.addOrReplaceChild("back_r1", CubeListBuilder.create().texOffs(21, 0).addBox(-10.75F, -31.0F, 0.0F, 21.0F, 40.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0002F, 5.5769F, 9.1197F, -0.2182F, 0.0F, 0.0F));

        PartDefinition front_r1 = Base.addOrReplaceChild("front_r1", CubeListBuilder.create().texOffs(21, 0).addBox(-10.5F, -31.0F, -3.0F, 21.0F, 40.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0002F, 5.5769F, -6.3803F, 0.2182F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}

