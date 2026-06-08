package paulevs.edenring.blocks;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import paulevs.edenring.registries.EdenBiomes;
import paulevs.edenring.registries.EdenBlocks;

import org.betterx.bclib.client.models.BasePatterns;
import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.client.models.PatternsHelper;
import org.betterx.bclib.interfaces.BlockModelProvider;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TexturedTerrainBlock extends GrassBlock {
	public TexturedTerrainBlock() {
		super(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK));
	}
	
	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		ItemStack tool = (ItemStack) builder.getParameter(LootContextParams.TOOL);
		if (tool == null || EnchantmentHelper.getItemEnchantmentLevel((Holder<Enchantment>) Enchantments.SILK_TOUCH, tool) == 0) {
			return Collections.singletonList(new ItemStack(Blocks.DIRT));
		}
		return Collections.singletonList(new ItemStack(this));
	}

	@Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        super.performBonemeal(serverLevel, randomSource, blockPos, blockState);
		if (isValidBonemealTarget(serverLevel, blockPos, blockState, serverLevel.isClientSide)) {
			for (Direction direction : Direction.values()) {
				Boolean spread = false;
				BlockPos nearby = blockPos.relative(direction);
				spread = serverLevel.getBlockState(nearby).is(EdenBlocks.EDEN_GRASS_BLOCK) && serverLevel.getBlockState(nearby.above()).isAir();
				if (spread) {
					if (serverLevel.getBiome(nearby).is(EdenBiomes.MYCOTIC_FOREST) || serverLevel.getBiome(nearby).is(EdenBiomes.OLD_MYCOTIC_FOREST)) {
						serverLevel.setBlock(nearby.above(), EdenBlocks.MYCOTIC_GRASS.defaultBlockState(), 2);
					}
					break;
				}
			}
		}
    }
}
