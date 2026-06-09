package paulevs.edenring.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CeilPlantBlock extends VegetationBlock {

	public static final MapCodec<CeilPlantBlock> CODEC = simpleCodec(CeilPlantBlock::new);
	public CeilPlantBlock(Properties settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends VegetationBlock> codec() {
		return CODEC;
	}

	protected boolean isTerrain(BlockState state) {
		return true;
	}
	
	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		BlockState up = world.getBlockState(pos.above());
		return up.isFaceSturdy(world, pos, Direction.DOWN);
	}
}
