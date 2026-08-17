package paulevs.edenring.blocks.entities.renderers;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import paulevs.edenring.EdenRing;
import paulevs.edenring.blocks.entities.EdenPortalBlockEntity;

public class EdenPortalBlockEntityRenderer <T extends EdenPortalBlockEntity> implements BlockEntityRenderer<T> {
	private static final ResourceLocation PORTAL_RAY_DEEP = EdenRing.makeID("textures/block/portal_ray_deep.png");
	private static final ResourceLocation PORTAL_RAY = EdenRing.makeID("textures/block/portal_ray.png");
	
	public EdenPortalBlockEntityRenderer(Context context) {
		super();
	}
	
	@Override
	public void render(T entity, float tickDelta, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
		poseStack.pushPose();
		Matrix4f matrix = poseStack.last().pose();

		RenderSystem.disableCull();

		float time1 = (float) ((((double) entity.getTicks() + tickDelta) * 0.025) % 1.0);
		float time2 = (float) ((((double) entity.getTicks() + tickDelta) * 0.04) % 1.0);

		float v1 = 1.0F + time1;
		float v2 = time1;

		float v3 = 1.0F + time2 + 0.3F;
		float v4 = time2 + 0.3F;

		VertexConsumer consumer = multiBufferSource.getBuffer(RenderType.beaconBeam(
			PORTAL_RAY_DEEP,
			true
		));
		RenderSystem.setShaderTexture(0, PORTAL_RAY_DEEP);
		renderFaces(consumer, matrix, v3, v4, 0.25F);

		consumer = multiBufferSource.getBuffer(RenderType.beaconBeam(
			PORTAL_RAY,
			true
		));
		RenderSystem.setShaderTexture(0, PORTAL_RAY);
		renderFaces(consumer, matrix, v1, v2, 0.375F);

		poseStack.popPose();

		RenderSystem.enableCull();
	}

	private void renderFaces(VertexConsumer consumer, Matrix4f matrix, float v1, float v2, float offset) {
		float xz1 = -offset;
		float xz2 = 1.0F + offset;
		
		consumer.addVertex(matrix, xz2,  0.0F, xz1).setColor(255, 255, 255, 255).setUv(0.0F, v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(0.0f, 1.0f, 0.0f);
		consumer.addVertex(matrix, xz2,  3.0F, xz1).setColor(255, 255, 255,   0).setUv(0.0F, v2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(0.0f, 1.0f, 0.0f);
		consumer.addVertex(matrix, xz2,  3.0F, xz2).setColor(255, 255, 255,   0).setUv(1.0F, v2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(0.0f, 1.0f, 0.0f);
		consumer.addVertex(matrix, xz2,  0.0F, xz2).setColor(255, 255, 255, 255).setUv(1.0F, v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(0.0f, 1.0f, 0.0f);
		
		consumer.addVertex(matrix, xz1,  0.0F, xz2).setColor(255, 255, 255, 255).setUv(0.0F, v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(0.0f, 1.0f, 0.0f);
		consumer.addVertex(matrix, xz1,  3.0F, xz2).setColor(255, 255, 255,   0).setUv(0.0F, v2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(0.0f, 1.0f, 0.0f);
		consumer.addVertex(matrix, xz1,  3.0F, xz1).setColor(255, 255, 255,   0).setUv(1.0F, v2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(0.0f, 1.0f, 0.0f);
		consumer.addVertex(matrix, xz1,  0.0F, xz1).setColor(255, 255, 255, 255).setUv(1.0F, v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(0.0f, 1.0f, 0.0f);
		
		consumer.addVertex(matrix, xz1,  0.0F,  xz2).setColor(255, 255, 255, 255).setUv(0.0F, v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(0.0f, 1.0f, 0.0f);
		consumer.addVertex(matrix, xz2,  0.0F,  xz2).setColor(255, 255, 255, 255).setUv(1.0F, v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(0.0f, 1.0f, 0.0f);
		consumer.addVertex(matrix, xz2,  3.0F,  xz2).setColor(255, 255, 255,   0).setUv(1.0F, v2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(0.0f, 1.0f, 0.0f);
		consumer.addVertex(matrix, xz1,  3.0F,  xz2).setColor(255, 255, 255,   0).setUv(0.0F, v2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(0.0f, 1.0f, 0.0f);
		
		consumer.addVertex(matrix, xz1,  3.0F, xz1).setColor(255, 255, 255,   0).setUv(0.0F, v2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(0.0f, 1.0f, 0.0f);
		consumer.addVertex(matrix, xz2,  3.0F, xz1).setColor(255, 255, 255,   0).setUv(1.0F, v2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(0.0f, 1.0f, 0.0f);
		consumer.addVertex(matrix, xz2,  0.0F, xz1).setColor(255, 255, 255, 255).setUv(1.0F, v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(0.0f, 1.0f, 0.0f);
		consumer.addVertex(matrix, xz1,  0.0F, xz1).setColor(255, 255, 255, 255).setUv(0.0F, v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(0.0f, 1.0f, 0.0f);
	}
}