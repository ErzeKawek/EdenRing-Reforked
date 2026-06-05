package paulevs.edenring.world.features.plants;

import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.edenring.blocks.EdenBlockProperties;
import paulevs.edenring.misc.AllPurposeUtility;
import paulevs.edenring.registries.EdenBlocks;
import paulevs.edenring.world.features.basic.CeilScatterFeature;

public class VineFeature extends CeilScatterFeature {
	@Override
	protected void generate(WorldGenLevel level, MutableBlockPos pos, RandomSource random) {
		BlockState bottom = EdenBlocks.EDEN_VINE.defaultBlockState().setValue(EdenBlockProperties.TRIPLE_SHAPE, EdenBlockProperties.TripleShape.BOTTOM);
		BlockState middle = EdenBlocks.EDEN_VINE.defaultBlockState().setValue(EdenBlockProperties.TRIPLE_SHAPE, EdenBlockProperties.TripleShape.MIDDLE);
		BlockState top = EdenBlocks.EDEN_VINE.defaultBlockState().setValue(EdenBlockProperties.TRIPLE_SHAPE, EdenBlockProperties.TripleShape.TOP);
		int length = AllPurposeUtility.randRange(5, 10, random);
		int max = length - 1;
		for (int i = 0; i < length; i++) {
			if (level.getBlockState(pos).isAir()) {
				level.setBlock(pos, i == 0 ? top : i == max ? bottom : middle, AllPurposeUtility.Flags.SILENT);
			}
			else {
				pos.setY(pos.getY() + 1);
				level.setBlock(pos, bottom, AllPurposeUtility.Flags.SILENT);
				return;
			}
			pos.setY(pos.getY() - 1);
		}
	}
}
