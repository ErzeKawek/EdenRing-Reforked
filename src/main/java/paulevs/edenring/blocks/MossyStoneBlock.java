package paulevs.edenring.blocks;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import org.betterx.bclib.blocks.BaseBlock;
import org.betterx.bclib.client.models.BasePatterns;
import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.client.models.PatternsHelper;
import org.betterx.bclib.interfaces.RuntimeBlockModelProvider;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MossyStoneBlock extends BaseBlock implements BonemealableBlock, RuntimeBlockModelProvider {
	public MossyStoneBlock() {
		super(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public BlockModel getBlockModel(ResourceLocation blockId, BlockState blockState) {
		String modId = blockId.getNamespace();
		Map<String, String> textures = Maps.newHashMap();
		textures.put("%top%", modId + ":block/mossy_stone_top");
		textures.put("%side%", modId + ":block/mossy_stone_side");
		textures.put("%bottom%", "minecraft:block/stone");
		Optional<String> pattern = PatternsHelper.createJson(BasePatterns.BLOCK_TOP_SIDE_BOTTOM, textures);
		return ModelsHelper.fromPattern(pattern);
	}
	
	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		ItemStack tool = builder.getParameter(LootContextParams.TOOL);
		if (tool == null || EnchantmentHelper.getEnchantmentsForCrafting(tool).keySet().stream().noneMatch(enchantment -> enchantment.is(Enchantments.SILK_TOUCH))) {
			return Collections.singletonList(new ItemStack(Blocks.STONE));
		}
		return Collections.singletonList(new ItemStack(this));
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		BlockState up = level.getBlockState(pos.above());
		return up.isAir() || up.is(Blocks.STONE);
	}

	@Override
	public boolean isBonemealSuccess(Level var1, RandomSource var2, BlockPos var3, BlockState var4) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		for (int i = 1; i < 4; i++) {
			for (int j = 1; j < 2; j++) {
				for (Direction.Axis axis : Direction.Axis.values()) {
					Direction direction =
					switch (axis) {
						case X -> random.nextFloat() < .5F ? Direction.EAST : Direction.WEST;
						case Z -> random.nextFloat() < .5F ? Direction.NORTH : Direction.SOUTH;
						default -> Direction.NORTH;
					};
					Direction yPos = random.nextFloat() < .2F ? Direction.DOWN : Direction.UP;
					BlockPos pos2 = pos.relative(direction, i).relative(yPos, j);
					BlockState state2 = level.getBlockState(pos2);
					if (state2.is(Blocks.STONE)) {
						level.setBlock(pos2, state2, 2);
					}
				}
			}
		}
	}
}
