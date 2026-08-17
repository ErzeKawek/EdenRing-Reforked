package paulevs.edenring.blocks;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.betterx.bclib.blocks.BaseRotatedPillarBlock;
import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.client.models.PatternsHelper;
import org.betterx.bclib.interfaces.RuntimeBlockModelProvider;

import java.util.Map;
import java.util.Optional;

public class GraviliteBlock extends BaseRotatedPillarBlock implements RuntimeBlockModelProvider {
	public GraviliteBlock() {
		super(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK).luminance(15));
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public UnbakedModel getModelVariant(ModelResourceLocation stateId, BlockState blockState, Map<ResourceLocation, UnbakedModel> modelCache) {
		ResourceLocation pillarUp = ResourceLocation.fromNamespaceAndPath(stateId.id().getNamespace(), "block/" + stateId.id().getPath());

		if (!modelCache.containsKey(pillarUp)) {
			Map<String, String> textures = Maps.newHashMap();
			String modId = stateId.id().getNamespace();
			String name = stateId.id().getPath();
			textures.put("%side%", modId + ":block/" + name + "_side");
			textures.put("%end%", modId + ":block/" + name + "_top");
			Optional<String> pattern = PatternsHelper.createJson(EdenPatterns.BLOCK_PILLAR_NO_SHADE, textures);
			modelCache.put(pillarUp, ModelsHelper.fromPattern(pattern));
		}
		
		return ModelsHelper.createRotatedModel(pillarUp, blockState.getValue(AXIS));
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public BlockModel getItemModel(ResourceLocation itemID) {
		Map<String, String> textures = Maps.newHashMap();
		String modId = itemID.getNamespace();
		String name = itemID.getPath();
		textures.put("%side%", modId + ":block/" + name + "_side");
		textures.put("%end%", modId + ":block/" + name + "_top");
		Optional<String> pattern = PatternsHelper.createJson(EdenPatterns.BLOCK_PILLAR_NO_SHADE, textures);
		return ModelsHelper.fromPattern(pattern);
	}
}
