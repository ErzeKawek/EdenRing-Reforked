package paulevs.edenring.world.generator;

import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import paulevs.edenring.misc.AllPurposeUtility;
import paulevs.edenring.misc.noise.OpenSimplexNoise;
import paulevs.edenring.misc.sdf.SDF;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IslandLayer {
	private final Map<BlockPos, SDF> islandCache = Maps.newHashMap();
	private final List<BlockPos> positions = new ArrayList<BlockPos>(9);
	private final List<SDF> nearIslands = new ArrayList<SDF>(9);
	private final RandomSource random = new XoroshiroRandomSource(0);
	private final OpenSimplexNoise density;
	private final LayerOptions options;
	private final int seed;
	private int lastX;
	private int lastZ;
	
	public IslandLayer(int seed, LayerOptions options) {
		this.density = new OpenSimplexNoise(seed);
		this.options = options;
		this.seed = seed;
	}
	
	public void updatePositions(double x, double z) {
		int ix = Mth.floor(x / options.distance);
		int iz = Mth.floor(z / options.distance);
		
		if (lastX != ix || lastZ != iz || positions.isEmpty()) {
			lastX = ix;
			lastZ = iz;
			nearIslands.clear();
			positions.clear();
			for (int pox = -1; pox < 2; pox++) {
				int px = pox + ix;
				for (int poz = -1; poz < 2; poz++) {
					int pz = poz + iz;
					random.setSeed(AllPurposeUtility.getSeed(seed, px, pz));
					double posX = (px + random.nextFloat()) * options.distance;
					double posY = AllPurposeUtility.randRange(options.minY, options.maxY, random);
					double posZ = (pz + random.nextFloat()) * options.distance;
					if (density.eval(posX * 0.01, posZ * 0.01) > options.coverage) {
						BlockPos pos = new BlockPos((int)posX, (int)posY, (int)posZ);
						nearIslands.add(getIsland(pos));
						positions.add(pos);
					}
				}
			}
		}
	}
	
	private SDF getIsland(BlockPos pos) {
		return islandCache.computeIfAbsent(pos, i -> {
			random.setSeed(AllPurposeUtility.getSeed(seed, pos.getX(), pos.getZ()));
			return IslandTypes.getIsland(options, random);
		});
	}
	
	private float getRelativeDistance(SDF sdf, BlockPos center, double px, double py, double pz) {
		float x = (float) (px - center.getX()) / options.scale;
		float y = (float) (py - center.getY()) / options.scale;
		float z = (float) (pz - center.getZ()) / options.scale;
		return sdf.getDistance(x, y, z);
	}
	
	private float calculateSDF(double x, double y, double z) {
		float distance = 10;
		final int size = positions.size();
		for (int i = 0; i < size; i++) {
			SDF island = nearIslands.get(i);
			BlockPos pos = positions.get(i);
			float dist = getRelativeDistance(island, pos, x, y, z);
			if (dist < distance) {
				distance = dist;
			}
			if (distance < 0) {
				break;
			}
		}
		return distance;
	}
	
	public float getDensity(double x, double y, double z) {
		return -calculateSDF(x, y, z);
	}
	
	public void clearCache() {
		if (islandCache.size() > 128) {
			islandCache.clear();
		}
	}
}
