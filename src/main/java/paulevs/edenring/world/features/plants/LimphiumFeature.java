package paulevs.edenring.world.features.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import paulevs.edenring.blocks.LimphiumSapling;
import paulevs.edenring.misc.AllPurposeUtility;
import paulevs.edenring.registries.EdenBlocks;
import paulevs.edenring.world.features.basic.ScatterFeature;

public class LimphiumFeature extends ScatterFeature {
	public LimphiumFeature() {
		super(EdenBlocks.LIMPHIUM_SAPLING);
	}
	
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> fpc) {
		BlockPos origin = fpc.origin();
		WorldGenLevel level = fpc.level();
		origin = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, origin);
		return super.place(new FeaturePlaceContext<>(fpc.topFeature(), level, fpc.chunkGenerator(), fpc.random(), origin, fpc.config()));
	}
	
	@Override
	protected int getCount(RandomSource random) {
		return AllPurposeUtility.randRange(5, 10, random);
	}
	
	@Override
	protected void placeBlock(WorldGenLevel level, BlockPos pos, BlockState state) {
		LimphiumSapling.grow(level, level.getRandom(), pos, state);
	}
}
