package paulevs.edenring.blocks;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.betterx.bclib.blocks.BaseBlock;
import org.betterx.bclib.client.models.BasePatterns;
import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.client.models.PatternsHelper;
import org.betterx.bclib.interfaces.RuntimeBlockModelProvider;

import java.util.Map;
import java.util.Optional;

public class GravityCompressorBlock extends BaseBlock implements RuntimeBlockModelProvider {
	public static final IntegerProperty POWER = BlockStateProperties.POWER;
	
	public GravityCompressorBlock() {
		super(FabricBlockSettings.copyOf(Blocks.STONE).strength(1.5F).luminance(state -> state.getValue(POWER)));
		registerDefaultState(getStateDefinition().any().setValue(POWER, 0));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> stateManager) {
		stateManager.add(POWER);
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public UnbakedModel getModelVariant(ModelResourceLocation stateId, BlockState blockState, Map<ResourceLocation, UnbakedModel> modelCache) {
		boolean active = blockState.getValue(POWER) > 0;
		String modId = stateId.id().getNamespace();
		String path = stateId.id().getPath();
		String side = active ? path + "_side_on" : path + "_side_off";
		Map<String, String> textures = Maps.newHashMap();
		textures.put("%top%", "minecraft:block/piston_top");
		textures.put("%side%", modId + ":block/" + side);
		textures.put("%bottom%", modId + ":block/" + path + "_bottom");
		Optional<String> pattern = PatternsHelper.createJson(BasePatterns.BLOCK_TOP_SIDE_BOTTOM, textures);
		return ModelsHelper.fromPattern(pattern);
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public BlockModel getItemModel(ResourceLocation blockId) {
		String modId = blockId.getNamespace();
		String name = blockId.getPath();
		Map<String, String> textures = Maps.newHashMap();
		textures.put("%top%", "minecraft:block/piston_top");
		textures.put("%side%", modId + ":block/" + name + "_side_off");
		textures.put("%bottom%", modId + ":block/" + name + "_bottom");
		Optional<String> pattern = PatternsHelper.createJson(BasePatterns.BLOCK_TOP_SIDE_BOTTOM, textures);
		return ModelsHelper.fromPattern(pattern);
	}
	
	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos pos2, boolean bl) {
		BlockState newState = updateSignal(state, level, pos);
		if (!newState.equals(state)) {
			level.setBlockAndUpdate(pos, newState);
		}
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		BlockPos pos = ctx.getClickedPos();
		Level level = ctx.getLevel();
		return updateSignal(defaultBlockState(), level, pos);
	}
	
	private BlockState updateSignal(BlockState state, Level level, BlockPos pos) {
		int signal = level.getBestNeighborSignal(pos);
		return state.setValue(POWER, signal);
	}
}
