package paulevs.edenring.world.features.plants;

import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import paulevs.edenring.misc.AllPurposeUtility.*;
import paulevs.edenring.world.features.basic.CeilScatterFeature;

public class RootsFeature extends CeilScatterFeature {
	@Override
	protected void generate(WorldGenLevel level, MutableBlockPos pos, RandomSource random) {
		level.setBlock(pos, Blocks.HANGING_ROOTS.defaultBlockState(), Flags.SILENT);
	}
}
