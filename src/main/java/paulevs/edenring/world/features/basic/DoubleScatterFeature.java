package paulevs.edenring.world.features.basic;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.edenring.misc.AllPurposeUtility;
import paulevs.edenring.misc.AllPurposeUtility.*;

public class DoubleScatterFeature extends ScatterFeature {
	private int count;
	
	public DoubleScatterFeature(Block block, int count) {
		super(block);
		this.count = count;
	}
	
	public DoubleScatterFeature(Block block) {
		this(block, 20);
	}
	
	@Override
	protected int getCount(RandomSource random) {
		return AllPurposeUtility.randRange(count >> 1, count, random);
	}
	
	@Override
	protected void placeBlock(WorldGenLevel level, BlockPos pos, BlockState state) {
		BlockPos above = pos.above();
		if (level.getBlockState(above).isAir()) {
			level.setBlock(pos, state, Flags.SILENT);
			level.setBlock(above, state.setValue(BaseDoublePlantBlock.TOP, true), Flags.SILENT);
		}
	}
}
