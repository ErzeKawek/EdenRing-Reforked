package paulevs.edenring.client.environment.renderers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry.CloudRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import org.joml.Matrix4fStack;
import paulevs.edenring.EdenRing;
import paulevs.edenring.client.EdenRingClient;
import paulevs.edenring.client.environment.TransformHelper;
import paulevs.edenring.client.environment.animation.SpriteGrid;
import paulevs.edenring.client.environment.weather.CloudAnimation;

@Environment(value= EnvType.CLIENT)
public class EdenCloudRenderer implements CloudRenderer {
	private static final ResourceLocation CLOUDS = EdenRing.makeID("textures/environment/clouds.png");
	private SpriteGrid grid = new SpriteGrid(CloudAnimation::new, (biome, random) -> {
		if (biome == null) {
			// Handle null biome case (e.g., return a default value)
			return 0; // Or another appropriate value based on your logic
		}
		float fog = biome.fogDensity;
		if (fog > 1 && random.nextInt(5) > 0) {
			return (int) (random.nextFloat() * fog * 2);
		}
		return random.nextInt(16) == 0 ? 1 : 0;
	});


	
	@Override
	@SuppressWarnings("resource")
	public void render(WorldRenderContext context) {
		PoseStack poseStack = context.matrixStack();
		ClientLevel level = context.world();
		Camera camera = context.camera();
		
		if (poseStack == null || camera == null || level == null) return;
		
		// Init
		
		RenderSystem.enableBlend();
		RenderSystem.enableDepthTest();
		RenderSystem.depthMask(true);
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, CLOUDS);
		RenderSystem.defaultBlendFunc();
		
		// Start
		
		Minecraft minecraft = context.gameRenderer().getMinecraft();
		Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
		modelViewStack.pushMatrix();
		modelViewStack.rotateY((float) Math.PI);
		RenderSystem.applyModelViewMatrix();
		try {
			poseStack.pushPose();
			if (minecraft.options.bobView().get() && EdenRingClient.hasIris()) {
				TransformHelper.fixBobbing(poseStack, minecraft.player, context.tickCounter().getGameTimeDeltaPartialTick(false));
			}

			ChunkPos pos = camera.getEntity().chunkPosition();
			int distance = context.gameRenderer().getMinecraft().options.renderDistance().get();
			grid.render(level, pos, distance << 1 | 1, poseStack, camera, context.tickCounter().getGameTimeDeltaPartialTick(false), context.frustum());
		}
		finally {
			poseStack.popPose();
			modelViewStack.popMatrix();
			RenderSystem.applyModelViewMatrix();
		}
		
		// Finalise
		
		RenderSystem.enableCull();
		RenderSystem.disableBlend();
	}
}
