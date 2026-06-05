package paulevs.edenring.world.features.basic;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import paulevs.edenring.misc.AllPurposeUtility;

public class ScatterFeature extends Feature<NoneFeatureConfiguration> {
	private Block block;

	public ScatterFeature(Block block) {
		super(NoneFeatureConfiguration.CODEC);
		this.block = block;
	}

	@SuppressWarnings("deprecation")
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		RandomSource random = featurePlaceContext.random();
		BlockPos center = featurePlaceContext.origin();
		WorldGenLevel level = featurePlaceContext.level();
		
		BlockState state = block.defaultBlockState();
		MutableBlockPos pos = new MutableBlockPos();
		int count = getCount(random);
		for (int i = 0; i < count; i++) {
			int px = center.getX() + Mth.floor(Mth.clamp(random.nextGaussian() * 2 + 0.5F, -8, 8));
			int pz = center.getZ() + Mth.floor(Mth.clamp(random.nextGaussian() * 2 + 0.5F, -8, 8));
			pos.setX(px);
			pos.setZ(pz);
			for (int y = 5; y > -5; y--) {
				pos.setY(center.getY() + y);
				if (level.getBlockState(pos).isFaceSturdy(level, pos, Direction.UP)) {
					pos.setY(pos.getY() + 1);
					if (level.getBlockState(pos).isAir() && state.canSurvive(level, pos)) {
						placeBlock(level, pos, state);
						break;
					}
				}
			}
		}
		
		return true;
	}
	
	protected int getCount(RandomSource random) {
		return AllPurposeUtility.randRange(10, 20, random);
	}
	protected void placeBlock(WorldGenLevel level, BlockPos pos, BlockState state) {
		level.setBlock(pos, state, AllPurposeUtility.Flags.SILENT);
	}
}
