package paulevs.edenring.blocks;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;
import org.betterx.bclib.behaviours.BehaviourBuilders;
import org.betterx.bclib.blocks.BaseVineBlock;
import org.betterx.wover.block.api.BlockProperties.TripleShape;
import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.client.models.ModelsHelper.MultiPartBuilder;
import org.betterx.bclib.client.models.PatternsHelper;
import org.betterx.bclib.interfaces.CustomColorProvider;
import org.betterx.bclib.interfaces.RuntimeBlockModelProvider;
import org.betterx.ui.ColorUtil;

import java.util.Map;
import java.util.Optional;

public class OverlayVineBlock extends BaseVineBlock implements CustomColorProvider, RuntimeBlockModelProvider {
	public OverlayVineBlock() {
		super();
	}

	@Environment(EnvType.CLIENT)
	public @Nullable BlockModel getBlockModel(ResourceLocation blockId, BlockState blockState) {
		String modId = blockId.getNamespace();
		String name = blockId.getPath();
		if (blockState.getValue(BaseVineBlock.SHAPE) == TripleShape.BOTTOM) {
			name += "_bottom";
		}
		Map<String, String> textures = Maps.newHashMap();
		textures.put("%texture%", modId + ":block/" + name + "_color");
		textures.put("%overlay%", modId + ":block/" + name + "_overlay");
		Optional<String> pattern = PatternsHelper.createJson(EdenPatterns.BLOCK_TINTED_CROSS_OVERLAY, textures);
		return ModelsHelper.fromPattern(pattern);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public UnbakedModel getModelVariant(ModelResourceLocation stateId, BlockState blockState, Map<ResourceLocation, UnbakedModel> modelCache) {
		String modId = stateId.id().getNamespace();
		String name = stateId.id().getPath();
		MultiPartBuilder model = MultiPartBuilder.create(stateDefinition);
		for (boolean bottom : new boolean[]{true, false}) {
			ModelResourceLocation modelId = RuntimeBlockModelProvider.remapModelResourceLocation(stateId, blockState, bottom ? "_bottom" : "_mid");
			String textureName = name + (bottom ? "_bottom" : "");
			Map<String, String> textures = Maps.newHashMap();
			textures.put("%texture%", modId + ":block/" + textureName + "_color");
			textures.put("%overlay%", modId + ":block/" + textureName + "_overlay");
			modelCache.put(modelId.id(), ModelsHelper.fromPattern(PatternsHelper.createJson(EdenPatterns.BLOCK_TINTED_CROSS_OVERLAY, textures)));
			model.part(modelId.id()).setCondition(state -> (state.getValue(BaseVineBlock.SHAPE) == TripleShape.BOTTOM) == bottom).add();
		}
		return model.build();
	}

	@Environment(EnvType.CLIENT)
	public BlockModel getItemModel(ResourceLocation itemID) {
		String modId = itemID.getNamespace();
		String name = itemID.getPath();
		Map<String, String> textures = Maps.newHashMap();
		textures.put("%texture%", modId + ":block/" + name + "_bottom_color");
		textures.put("%overlay%", modId + ":block/" + name + "_bottom_overlay");
		Optional<String> pattern = PatternsHelper.createJson(EdenPatterns.ITEM_TINTED_OVERLAY, textures);
		return ModelsHelper.fromPattern(pattern);
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public BlockColor getProvider() {
		return (blockState, blockAndTintGetter, blockPos, i) -> {
			return blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageGrassColor(blockAndTintGetter, blockPos) : GrassColor.get(0.5D, 1.0D);
		};
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public ItemColor getItemProvider() {
		return (itemStack, i) -> i == 1 ? GrassColor.get(0.5D, 1.0D) : ColorUtil.color(255, 255, 255);
	}
}
