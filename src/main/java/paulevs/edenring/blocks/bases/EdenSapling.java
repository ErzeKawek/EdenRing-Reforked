package paulevs.edenring.blocks.bases;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.PushReaction;
import paulevs.edenring.EdenRing;
import paulevs.edenring.registries.EdenBlocks;
import paulevs.edenring.registries.EdenTags;

public class EdenSapling extends SaplingBlock {

    public final Supplier<Feature<NoneFeatureConfiguration>> treeConstructor;
    public final int growChance;

    public EdenSapling(
            Supplier<Feature<NoneFeatureConfiguration>> treeConstructor,
            Properties settings
    ) {
        this(treeConstructor, settings, 15);
    }

    public EdenSapling(
            Supplier<Feature<NoneFeatureConfiguration>> treeConstructor,
            Properties settings,
            int growChance
    ) {
        super(SAPLING_GENERATOR,
                settings
                        .noCollission()
                        .instabreak()
                        .sound(SoundType.CROP)
                        .pushReaction(PushReaction.DESTROY)
                        .ignitedByLava()
                        .randomTicks()
        );
        this.treeConstructor = treeConstructor;
        this.growChance = growChance;
    }

    @Override
    public void advanceTree(ServerLevel world, BlockPos pos, BlockState state, RandomSource random) {
        if (!isAllowedToGrow(world, pos)) {
            return;
        }
        if (state.getValue(STAGE) == 0) {
            world.setBlock(pos, state.cycle(STAGE),
                    Block.UPDATE_NONE);
        } else {
            FeaturePlaceContext<NoneFeatureConfiguration> context = new FeaturePlaceContext<>(null, world,
                    world.getChunkSource().getGenerator(), random, pos, new NoneFeatureConfiguration());
            treeConstructor.get().place(context);
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(EdenBlocks.EDEN_GRASS_BLOCK);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextInt(this.growChance) == 0) {
            this.advanceTree(world, pos, state, random);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return isAllowedToGrow(world, pos);
    }

    protected static boolean isAllowedToGrow(LevelReader world, BlockPos pos) {
        return !EdenRing.CONFIG.edenPlantsOnlyGrowInEdenRing()
                || world.getBiome(pos).is(EdenTags.EDEN);
    }

    private static final TreeGrower SAPLING_GENERATOR = new TreeGrower(
            EdenRing.MOD_ID + ":sapling",
            Optional.empty(),
            Optional.empty(),
            Optional.empty()
    );
}
