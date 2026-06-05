package paulevs.edenring.world.features.basic;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import paulevs.edenring.misc.AllPurposeUtility;


public class FloorScatterFeature extends Feature<NoneFeatureConfiguration> {
	private Block block;
	private Block[] floors;
	private boolean checkAir;

	public FloorScatterFeature(Block block, Block... floors) {
		this(block, false, floors);
	}
	
	public FloorScatterFeature(Block block, boolean checkAir, Block... floors) {
		super(NoneFeatureConfiguration.CODEC);
		this.block = block;
		this.floors = floors;
		this.checkAir = checkAir;
	}
	
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		RandomSource random = featurePlaceContext.random();
		BlockPos center = featurePlaceContext.origin();
		WorldGenLevel level = featurePlaceContext.level();
		
		MutableBlockPos pos = new MutableBlockPos();
		int count = AllPurposeUtility.randRange(100, 200, random);
		for (int i = 0; i < count; i++) {
			int px = center.getX() + Mth.floor(Mth.clamp(random.nextGaussian() * 2, -8, 8));
			int py = center.getY() + Mth.floor(Mth.clamp(random.nextGaussian() * 2, -8, 8));
			int pz = center.getZ() + Mth.floor(Mth.clamp(random.nextGaussian() * 2, -8, 8));
			pos.set(px, py, pz);
			for (Block floor: floors) {
				if (level.getBlockState(pos).is(floor) && (!checkAir || level.getBlockState(pos.above()).isAir())) {
					level.setBlock(pos, block.defaultBlockState(), AllPurposeUtility.Flags.SILENT);
					break;
				}
			}
		}
		
		return true;
	}
}
