package paulevs.edenring.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AquatusOuterLeaves extends Block {
    private static final VoxelShape TOP_SHAPE = box(1, 8, 1, 15, 16, 15);

    public AquatusOuterLeaves(BlockBehaviour.Properties settings) {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION).randomTicks());
    }

    public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        BlockPos above = pos.above();
            if (!world.getBlockState(above).isFaceSturdy(world, above, Direction.DOWN)) {
                return Blocks.AIR.defaultBlockState();
            }
            return state;
    }


}
