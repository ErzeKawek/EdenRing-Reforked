package paulevs.edenring.world.features.terrain;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import paulevs.edenring.misc.AllPurposeUtility;
import paulevs.edenring.misc.noise.OpenSimplexNoise;

public class LayeredBulbFeature {
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise("ore".hashCode());
	private Block[] layers;
	private Block[] scatterIn;
	private int minCount;
	private int maxCount;
	private int minRadius;
	private int maxRadius;
	
	public LayeredBulbFeature(Block[] layers, int count, int radius, Block... scatterIn) {
		this.layers = layers;
		this.scatterIn = scatterIn;
		this.minCount = count / 3;
		this.maxCount = count;
		this.minRadius = radius / 2;
		this.maxRadius = radius;
	}

	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		RandomSource random = featurePlaceContext.random();
		BlockPos center = featurePlaceContext.origin();
		WorldGenLevel level = featurePlaceContext.level();
		
		int posX = center.getX() & 0xFFFFFFF0;
		int posZ = center.getZ() & 0xFFFFFFF0;
		
		MutableBlockPos pos = new MutableBlockPos();
		int iterations = AllPurposeUtility.randRange(minCount, maxCount, random);
		
		for (int n = 0; n < iterations; n++) {
			int wx = posX | random.nextInt(16);
			int wz = posZ | random.nextInt(16);
			int wy = random.nextInt(256);
			
			int radius = AllPurposeUtility.randRange(minRadius, maxRadius, random);
			
			int x1 = wx - radius;
			int x2 = wx + radius;
			int z1 = wz - radius;
			int z2 = wz + radius;
			int y1 = wy - radius;
			int y2 = wy + radius;
			
			for (int x = x1; x <= x2; x++) {
				pos.setX(x);
				for (int y = y1; y <= y2; y++) {
					pos.setY(y);
					for (int z = z1; z <= z2; z++) {
						pos.setZ(z);
						float dist = AllPurposeUtility.length(x - wx, y - wy, z - wz);
						float localRadius = radius - (float) NOISE.eval(x * 0.3, y * 0.3, z * 0.3) * radius * 0.3F;
						if (dist < localRadius - random.nextInt(3)) {
							int index = (int) (dist / localRadius * layers.length);
							BlockState state = layers[index].defaultBlockState();
							BlockState worldState = level.getBlockState(pos);
							for (Block block: scatterIn) {
								if (worldState.is(block)) {
									level.setBlock(pos, state, AllPurposeUtility.Flags.SILENT);
								}
							}
						}
					}
				}
			}
		}
		
		return true;
	}
}
