package online.remind.remind.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Brightness;

import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.entity.attacks.CrossSlashEffectEntity;

public class CrossSlashEffectRenderer
        extends EntityRenderer<CrossSlashEffectEntity> {

    private static final ResourceLocation SLASH_1 =
            ResourceLocation.fromNamespaceAndPath(
                    KingdomKeysReMind.MODID,
                    "textures/entity/cross_slash/cross_slash_1.png"
            );

    private static final ResourceLocation SLASH_2 =
            ResourceLocation.fromNamespaceAndPath(
                    KingdomKeysReMind.MODID,
                    "textures/entity/cross_slash/cross_slash_2.png"
            );

    private static final ResourceLocation SLASH_3 =
            ResourceLocation.fromNamespaceAndPath(
                    KingdomKeysReMind.MODID,
                    "textures/entity/cross_slash/cross_slash_3.png"
            );

    public CrossSlashEffectRenderer(
            EntityRendererProvider.Context context
    ) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(
            CrossSlashEffectEntity entity,
            float entityYaw,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight
    ) {
        poseStack.pushPose();

        // Always face the camera
        poseStack.mulPose(
                this.entityRenderDispatcher.cameraOrientation()
        );

        // Rotate right / clockwise
        poseStack.mulPose(
                Axis.ZP.rotationDegrees(180.0F)
        );

        // Correct billboard orientation
        poseStack.scale(
                -1.0F,
                -1.0F,
                1.0F
        );

        int stage = entity.getStage();

        if (stage >= 1) {
            renderSlash(
                    poseStack,
                    bufferSource,
                    SLASH_1,
                    1,
                    -0.26F,
                    0.14F,
                    0.000F
            );
        }

        if (stage >= 2) {
            renderSlash(
                    poseStack,
                    bufferSource,
                    SLASH_2,
                    2,
                    0.24F,
                    0.12F,
                    0.002F
            );
        }

        if (stage >= 3) {
            renderSlash(
                    poseStack,
                    bufferSource,
                    SLASH_3,
                    3,
                    0.09F,
                    0.30F,
                    0.004F
            );
        }

        poseStack.popPose();

        super.render(
                entity,
                entityYaw,
                partialTick,
                poseStack,
                bufferSource,
                packedLight
        );
    }

    private void renderSlash(
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            ResourceLocation texture,
            int slashIndex,
            float xOffset,
            float yOffset,
            float zOffset
    ) {
        float halfWidth;
        float halfHeight;

        switch (slashIndex) {
            case 1 -> {
                halfWidth = 0.28F;
                halfHeight = 0.88F;
            }

            case 2 -> {
                halfWidth = 0.72F;
                halfHeight = 0.72F;
            }

            case 3 -> {
                halfWidth = 0.72F;
                halfHeight = 0.72F;
            }

            default -> {
                halfWidth = 0.45F;
                halfHeight = 0.45F;
            }
        }

        VertexConsumer vertexConsumer =
                bufferSource.getBuffer(
                        RenderType.entityTranslucentEmissive(texture)
                );

        PoseStack.Pose pose = poseStack.last();

        int fullBright =
                Brightness.FULL_BRIGHT.pack();

        // Bottom-left
        vertexConsumer
                .addVertex(
                        pose,
                        xOffset - halfWidth,
                        yOffset - halfHeight,
                        zOffset
                )
                .setColor(255, 255, 255, 255)
                .setUv(0.0F, 1.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(fullBright)
                .setNormal(pose, 0.0F, 0.0F, 1.0F);

        // Bottom-right
        vertexConsumer
                .addVertex(
                        pose,
                        xOffset + halfWidth,
                        yOffset - halfHeight,
                        zOffset
                )
                .setColor(255, 255, 255, 255)
                .setUv(1.0F, 1.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(fullBright)
                .setNormal(pose, 0.0F, 0.0F, 1.0F);

        // Top-right
        vertexConsumer
                .addVertex(
                        pose,
                        xOffset + halfWidth,
                        yOffset + halfHeight,
                        zOffset
                )
                .setColor(255, 255, 255, 255)
                .setUv(1.0F, 0.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(fullBright)
                .setNormal(pose, 0.0F, 0.0F, 1.0F);

        // Top-left
        vertexConsumer
                .addVertex(
                        pose,
                        xOffset - halfWidth,
                        yOffset + halfHeight,
                        zOffset
                )
                .setColor(255, 255, 255, 255)
                .setUv(0.0F, 0.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(fullBright)
                .setNormal(pose, 0.0F, 0.0F, 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(
            CrossSlashEffectEntity entity
    ) {
        // Required by EntityRenderer, but rendering chooses
        // the individual textures manually above.
        return SLASH_1;
    }
}