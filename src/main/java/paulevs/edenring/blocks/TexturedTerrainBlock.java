package paulevs.edenring.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.edenring.registries.EdenBiomes;
import paulevs.edenring.registries.EdenBlocks;

public class TexturedTerrainBlock extends GrassBlock {
	public TexturedTerrainBlock(BlockBehaviour.Properties properties) {
		super(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK));
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
					if (serverLevel.getBiome(nearby).is(EdenBiomes.MYCOTIC_FOREST) || serverLevel.getBiome(nearby).is(EdenBiomes.OLD_MYCOTIC_FOREST)) {
						serverLevel.setBlock(nearby.above(), EdenBlocks.MYCOTIC_GRASS.defaultBlockState(), 2);
					}
					break;
				}
			}
		}
    }
}
