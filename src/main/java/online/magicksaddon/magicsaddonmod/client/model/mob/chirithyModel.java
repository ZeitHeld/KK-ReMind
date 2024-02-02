package online.magicksaddon.magicsaddonmod.client.model.mob;// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


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

public class chirithyModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MagicksAddonMod.MODID, "chirithy"), "main");
	private final ModelPart Main;

	public chirithyModel(ModelPart root) {
		this.Main = root.getChild("Main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Main = partdefinition.addOrReplaceChild("Main", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition head = Main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(1, 112).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(37, 116).addBox(-4.25F, -8.3F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(37, 116).addBox(2.35F, -8.3F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition mouth_r1 = head.addOrReplaceChild("mouth_r1", CubeListBuilder.create().texOffs(35, 122).addBox(-3.0F, -2.0F, -1.0F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 4.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition body = Main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(103, 93).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition cloak = body.addOrReplaceChild("cloak", CubeListBuilder.create().texOffs(97, 108).addBox(-4.0F, -1.0F, -3.2F, 8.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(118, 86).addBox(-0.5F, -0.4F, -3.6F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -3.0F));

		PartDefinition legs = Main.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition RLeg = legs.addOrReplaceChild("RLeg", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -6.0F, 0.0F));

		PartDefinition LLeg = legs.addOrReplaceChild("LLeg", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, -6.0F, 0.0F));

		PartDefinition arms = Main.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition LArm = arms.addOrReplaceChild("LArm", CubeListBuilder.create(), PartPose.offset(-3.0F, -12.0F, 0.0F));

		PartDefinition cube_r1 = LArm.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 10).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

		PartDefinition RArm = arms.addOrReplaceChild("RArm", CubeListBuilder.create(), PartPose.offset(3.0F, -12.0F, 0.0F));

		PartDefinition cube_r2 = RArm.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 10).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}