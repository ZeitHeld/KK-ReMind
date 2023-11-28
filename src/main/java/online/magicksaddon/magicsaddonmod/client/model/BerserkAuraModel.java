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
import online.magicksaddon.magicsaddonmod.DawnCrossDuskMod;

public class BerserkAuraModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DawnCrossDuskMod.MODID, "berserk"), "main");
    private final ModelPart Base;
    private final ModelPart Base2;

    public BerserkAuraModel(ModelPart root) {
        this.Base = root.getChild("Base");
        this.Base2 = root.getChild("Base2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Base = partdefinition.addOrReplaceChild("Base", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, -6.0F));

        PartDefinition left_r1 = Base.addOrReplaceChild("left_r1", CubeListBuilder.create().texOffs(16, 0).addBox(-4.0F, -9.0F, 0.0F, 8.0F, 18.0F, 0.0F, new CubeDeformation(1.5F)), PartPose.offsetAndRotation(6.0F, 0.0F, 6.0F, 0.0F, -1.5708F, 0.2182F));

        PartDefinition right_r1 = Base.addOrReplaceChild("right_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, 0.0F, 8.0F, 18.0F, 0.0F, new CubeDeformation(1.5F)), PartPose.offsetAndRotation(-6.0F, 0.0F, 6.0F, 0.0F, -1.5708F, -0.2182F));

        PartDefinition back_r1 = Base.addOrReplaceChild("back_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-4.0F, -9.0F, 0.0F, 8.0F, 18.0F, 0.0F, new CubeDeformation(1.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 12.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition front_r1 = Base.addOrReplaceChild("front_r1", CubeListBuilder.create().texOffs(16, 18).addBox(-4.0F, -9.0F, 0.0F, 8.0F, 18.0F, 0.0F, new CubeDeformation(1.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition Base2 = partdefinition.addOrReplaceChild("Base2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, 0.0F, -0.6981F, 0.0F));

        PartDefinition left_r2 = Base2.addOrReplaceChild("left_r2", CubeListBuilder.create().texOffs(16, 0).addBox(-4.0F, -9.0F, 0.0F, 8.0F, 18.0F, 0.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(6.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.2182F));

        PartDefinition right_r2 = Base2.addOrReplaceChild("right_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, 0.0F, 8.0F, 18.0F, 0.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(-6.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.2182F));

        PartDefinition back_r2 = Base2.addOrReplaceChild("back_r2", CubeListBuilder.create().texOffs(0, 18).addBox(-4.0F, -9.0F, 0.0F, 8.0F, 18.0F, 0.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 6.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition front_r2 = Base2.addOrReplaceChild("front_r2", CubeListBuilder.create().texOffs(16, 18).addBox(-4.0F, -9.0F, 0.0F, 8.0F, 18.0F, 0.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -6.0F, 0.2182F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Base2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}

