package paulevs.edenring.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.betterx.bclib.util.BlocksHelper;
import paulevs.edenring.registries.EdenBlocks;

public class MycoticGrass extends SimplePlantBlock {
	public MycoticGrass() {
		super(true);
	}
	
	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		BlockPos above = pos.above();
		if (level.getBlockState(above).isAir()) {
			BlockState tall = EdenBlocks.TALL_MYCOTIC_GRASS.defaultBlockState();
			BlocksHelper.setWithoutUpdate(level, above, tall.setValue(DoublePlantBlock., true));
			BlocksHelper.setWithUpdate(level, pos, tall.setValue(BaseDoublePlantBlock.TOP, false));
		}
	}
}
