package paulevs.edenring.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import paulevs.edenring.registries.EdenBiomes;
import paulevs.edenring.registries.EdenBlocks;

import java.util.Collections;
import java.util.List;

public class EdenGrassBlock extends GrassBlock {
	public EdenGrassBlock(Properties settings) {
		super(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK));
	}

	@Environment(EnvType.CLIENT)
	public BlockColors getProvider() {
		return (blockState, blockAndTintGetter, blockPos, i) -> {
			return blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageGrassColor(blockAndTintGetter, blockPos) : GrassColor.get(0.5D, 1.0D);
		};
	}

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        super.performBonemeal(serverLevel, randomSource, blockPos, blockState);
		if (isValidBonemealTarget(serverLevel, blockPos, blockState)) {
			for (Direction direction : Direction.values()) {
				Boolean spread = false;
				BlockPos nearby = blockPos.relative(direction);
				spread = serverLevel.getBlockState(nearby).is(EdenBlocks.EDEN_GRASS_BLOCK) && serverLevel.getBlockState(nearby.above()).isAir();
				if (spread) {
					if (serverLevel.getBiome(nearby).is(EdenBiomes.GOLDEN_FOREST)) {
						serverLevel.setBlock(nearby.above(), EdenBlocks.GOLDEN_GRASS.defaultBlockState(), 2);
					}
					break;
				}
			}
		}
    }
}
