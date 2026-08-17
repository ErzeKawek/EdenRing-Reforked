package paulevs.edenring.blocks;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import org.betterx.bclib.behaviours.BehaviourBuilders;
import org.betterx.bclib.blocks.BasePlantBlock;
import org.betterx.bclib.client.models.BasePatterns;
import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.client.models.ModelsHelper.MultiPartBuilder;
import org.betterx.bclib.client.models.PatternsHelper;
import org.betterx.bclib.interfaces.RuntimeBlockModelProvider;
import org.betterx.bclib.items.tool.BaseShearsItem;
import org.betterx.bclib.util.MHelper;
import paulevs.edenring.EdenRing;
import paulevs.edenring.registries.EdenBlocks;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TallBalloonMushroom extends BasePlantBlock implements RuntimeBlockModelProvider {
	public static final IntegerProperty TEXTURE = EdenBlockProperties.TEXTURE_4;
	
	public TallBalloonMushroom() {
		super(BehaviourBuilders.createPlant().sound(SoundType.FUNGUS));
		registerDefaultState(getStateDefinition().any().setValue(TEXTURE, 3));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> stateManager) {
		stateManager.add(TEXTURE);
	}
	
	@Override
	protected boolean isTerrain(BlockState state) {
		return state.is(EdenBlocks.EDEN_MYCELIUM) || state.is(this);
	}
	
	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		BlockState side = world.getBlockState(pos.below());
		if (!isTerrain(side)) return false;
		if (state.getValue(TEXTURE) == 3) return true;
		return world.getBlockState(pos.above()).is(this);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		BlockPos pos = ctx.getClickedPos();
		Level level = ctx.getLevel();
		int below = getBlocksBelow(level, pos);
		if (below > 3) return null;
		return defaultBlockState();
	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		int below = getBlocksBelow(level, pos);
		if (below > 3) return Blocks.AIR.defaultBlockState();
		boolean isEmptyAbove = !level.getBlockState(pos.above()).is(this);
		if (isEmptyAbove) state = state.setValue(TEXTURE, 3);
		BlockState result = super.updateShape(state, facing, neighborState, level, pos, neighborPos);
		if (result.is(this) && !isEmptyAbove) {
			result = result.setValue(TEXTURE, below);
		}
		return result;
	}
	
	@Environment(EnvType.CLIENT)
	public UnbakedModel getModelVariant(ModelResourceLocation stateId, BlockState blockState, Map<ResourceLocation, UnbakedModel> modelCache) {
		String modId = stateId.id().getNamespace();
		String name = stateId.id().getPath();
		MultiPartBuilder model = MultiPartBuilder.create(stateDefinition);
		for (int texture = 0; texture <= 3; texture++) {
			ModelResourceLocation modelId = RuntimeBlockModelProvider.remapModelResourceLocation(stateId, blockState, "_" + texture);
			Map<String, String> textures = Maps.newHashMap();
			textures.put("%texture%", name + "_" + texture);
			textures.put("%modid%", modId);
			modelCache.put(modelId.id(), ModelsHelper.fromPattern(PatternsHelper.createJson(BasePatterns.BLOCK_CROSS, textures)));
			final int t = texture;
			model.part(modelId.id()).setCondition(state -> state.getValue(TEXTURE) == t).add();
		}
		return model.build();
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public BlockModel getItemModel(ResourceLocation itemID) {
		return ModelsHelper.createBlockItem(EdenRing.makeID(itemID.getPath() + "_3"));
	}
	
	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		ItemStack tool = builder.getParameter(LootContextParams.TOOL);
		if (tool != null && (BaseShearsItem.isShear(tool) || EnchantmentHelper.getEnchantmentsForCrafting(tool).keySet().stream().anyMatch(enchantment -> enchantment.is(Enchantments.SILK_TOUCH)))) {
			return Lists.newArrayList(new ItemStack(this));
		}
		else if (state.getValue(TEXTURE) == 3) {
			return Lists.newArrayList(new ItemStack(EdenBlocks.BALLOON_MUSHROOM_SMALL, MHelper.randRange(1, 3, MHelper.RANDOM_SOURCE)));
		}
		else if (MHelper.RANDOM.nextBoolean()) {
			return Lists.newArrayList(new ItemStack(Items.STICK, MHelper.randRange(1, 2, MHelper.RANDOM_SOURCE)));
		}
		else {
			return Collections.emptyList();
		}
	}
	
	@Override
	public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
		return false;
	}
	
	private int getBlocksBelow(LevelAccessor level, BlockPos pos) {
		MutableBlockPos p = pos.mutable();
		p.setY(p.getY() - 1);
		int count;
		for (count = 0; count < 5 && level.getBlockState(p).is(this); count++) {
			p.setY(p.getY() - 1);
		}
		return count;
	}
}
