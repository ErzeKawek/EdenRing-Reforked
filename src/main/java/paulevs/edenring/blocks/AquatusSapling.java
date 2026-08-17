package paulevs.edenring.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.betterx.bclib.blocks.FeatureSaplingBlock;
import paulevs.edenring.registries.EdenFeatures;

@SuppressWarnings("all")
public class AquatusSapling extends FeatureSaplingBlock {
	public AquatusSapling() {
		super((level, pos, state, rnd) -> EdenFeatures.placeInWorld(EdenFeatures.AQUATUS, level, pos, rnd));
	}
	
	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter world, BlockPos pos) {
		return state.is(Blocks.SAND) || state.is(Blocks.GRAVEL) || state.is(Blocks.SUSPICIOUS_SAND) || state.is(Blocks.SUSPICIOUS_GRAVEL) || super.mayPlaceOn(state, world, pos);
	}
}
