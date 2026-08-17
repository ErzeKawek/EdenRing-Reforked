package paulevs.edenring.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WetSpongeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.edenring.registries.EdenBlocks;

public class SoakedMetalSpongeBlock extends WetSpongeBlock {

    public SoakedMetalSpongeBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (level.getBiome(blockPos) == Biomes.SNOWY_PLAINS) {
            level.setBlock(blockPos, EdenBlocks.METAL_SPONGE.defaultBlockState(), 3);
            level.levelEvent(2009, blockPos, 0);
            level.playSound((Player)null, blockPos, SoundEvents.WET_SPONGE_DRIES, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
        }

    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        Direction direction = Direction.getRandom(randomSource);
        if (direction != Direction.UP) {
            BlockPos blockPos2 = blockPos.relative(direction);
            BlockState blockState2 = level.getBlockState(blockPos2);
            if (!blockState.canOcclude() || !blockState2.isFaceSturdy(level, blockPos2, direction.getOpposite())) {
                double d = (double)blockPos.getX();
                double e = (double)blockPos.getY();
                double f = (double)blockPos.getZ();
                if (direction == Direction.DOWN) {
                    e -= 0.05;
                    d += randomSource.nextDouble();
                    f += randomSource.nextDouble();
                } else {
                    e += randomSource.nextDouble() * 0.8;
                    if (direction.getAxis() == Direction.Axis.X) {
                        f += randomSource.nextDouble();
                        if (direction == Direction.EAST) {
                            ++d;
                        } else {
                            d += 0.05;
                        }
                    } else {
                        d += randomSource.nextDouble();
                        if (direction == Direction.SOUTH) {
                            ++f;
                        } else {
                            f += 0.05;
                        }
                    }
                }

                level.addParticle(ParticleTypes.DRIPPING_LAVA, d, e, f, (double)0.0F, (double)0.0F, (double)0.0F);
            }
        }
    }
}
