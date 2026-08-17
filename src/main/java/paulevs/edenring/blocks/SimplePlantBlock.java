package paulevs.edenring.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.betterx.bclib.behaviours.BehaviourBuilders;
import org.betterx.bclib.blocks.BasePlantBlock;

public class SimplePlantBlock extends BasePlantBlock {
	private static final VoxelShape GRASS_SHAPE = Block.box(2, 0, 2, 14, 13, 14);
	private boolean isGrass;

	public SimplePlantBlock(boolean replaceable) {
		super(BehaviourBuilders.createPlant().replaceable());
		isGrass = replaceable;
	}
	
	public SimplePlantBlock(Properties settings) {
		super(settings);
		isGrass = false;
	}
	
	@Override
	protected boolean isTerrain(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof GrassBlock || block instanceof MossyStoneBlock || blockState.is(BlockTags.DIRT) || blockState.is(Blocks.FARMLAND);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return isGrass ? GRASS_SHAPE : super.getShape(state, view, pos, ePos);
	}
}
