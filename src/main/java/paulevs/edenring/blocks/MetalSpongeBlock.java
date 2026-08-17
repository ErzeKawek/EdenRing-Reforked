package paulevs.edenring.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import paulevs.edenring.registries.EdenBlocks;
import paulevs.edenring.registries.EdenSounds;

import static net.minecraft.world.level.block.state.BlockBehaviour.simpleCodec;

public class MetalSpongeBlock extends SpongeBlock {
    private static final Direction[] ALL_DIRECTIONS = Direction.values();

    public MetalSpongeBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        this.absorbLava(level, blockPos);
        super.neighborChanged(blockState, level, blockPos, block, blockPos2, bl);
    }

    protected void absorbLava(Level level, BlockPos blockPos) {
        if (this.removeLavaBFS(level, blockPos)) {
            level.setBlock(blockPos, EdenBlocks.SOAKED_METAL_SPONGE.defaultBlockState(), 2);
            level.playSound((Player) null, blockPos, EdenSounds.METAL_SPONGE_SOAK, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

    }

    private boolean removeLavaBFS(Level level, BlockPos blockPos) {
        return BlockPos.breadthFirstTraversal(blockPos, 6, 65, (blockPosx, consumer) -> {
            for(Direction direction : ALL_DIRECTIONS) {
                consumer.accept(blockPosx.relative(direction));
            }

        }, (blockPos2) -> {
            if (blockPos2.equals(blockPos)) {
                return true;
            } else {
                BlockState blockState = level.getBlockState(blockPos2);
                FluidState fluidState = level.getFluidState(blockPos2);
                if (!fluidState.is(FluidTags.LAVA)) {
                    return false;
                } else {
                    Block block = blockState.getBlock();
                    if (block instanceof BucketPickup) {
                        BucketPickup bucketPickup = (BucketPickup)block;
                        if (!bucketPickup.pickupBlock((Player) null, level, blockPos2, blockState).isEmpty()) {
                            return true;
                        }
                    }

                    if (blockState.getBlock() instanceof LiquidBlock) {
                        level.setBlock(blockPos2, Blocks.AIR.defaultBlockState(), 3);
                    }
                    else {

                        BlockEntity blockEntity = blockState.hasBlockEntity() ? level.getBlockEntity(blockPos2) : null;
                        dropResources(blockState, level, blockPos2, blockEntity);
                        level.setBlock(blockPos2, Blocks.AIR.defaultBlockState(), 3);
                    }

                    return true;
                }
            }
        }) > 1;
    }
}
