package paulevs.edenring.blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.edenring.misc.AllPurposeUtility;

public class AquatusRoots extends Block {

    public AquatusRoots(BlockBehaviour.Properties properties) {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION).randomTicks());
    }

    public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        BlockPos above = pos.above();
        BlockPos below = pos.below();
        if (!world.getBlockState(above).isFaceSturdy(world, above, Direction.DOWN) || !world.getBlockState(below).isFaceSturdy(world, below, Direction.UP)) {
            return Blocks.AIR.defaultBlockState();
        }
        return state;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        int px = pos.getX() + AllPurposeUtility.randRange(-4, 4, randomSource);
        int pz = pos.getZ() + AllPurposeUtility.randRange(-4, 4, randomSource);
        BlockPos repPos = new BlockPos(px, pos.getY() - 1, pz);
        if (canReplace(level.getBlockState(repPos))) {
            level.setBlockAndUpdate(repPos, randomSource.nextInt(4) == 0 ? Blocks.GRAVEL.defaultBlockState() : Blocks.SAND.defaultBlockState());
        }
    }

    private boolean canReplace(BlockState state) {
        return state.is(Blocks.DIRT) || state.getBlock() instanceof GrassBlock;
    }

}
