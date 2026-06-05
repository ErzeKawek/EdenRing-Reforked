package paulevs.edenring.world.features.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import paulevs.edenring.blocks.EdenBlockProperties;
import paulevs.edenring.blocks.EdenBlockProperties.*;
import paulevs.edenring.blocks.SixSidePlant;
import paulevs.edenring.misc.AllPurposeUtility;
import paulevs.edenring.registries.EdenBlocks;

import java.util.ArrayList;
import java.util.List;

public class VolvoxFeature {
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		WorldGenLevel level = featurePlaceContext.level();
		BlockPos center = featurePlaceContext.origin();
		RandomSource random = featurePlaceContext.random();
		
		int type = random.nextInt(8);
		if (type < 2) {
			generateSmall(level, center, random);
		}
		else if (type < 6) {
			generateMedium(level, center, random);
		}
		else {
			generateLarge(level, center, random);
		}
		
		return true;
	}
	
	private BlockPos getRandom(BlockPos pos, RandomSource random) {
		int x = (pos.getX() & 0xFFFFFFF0) | random.nextInt(16);
		int z = (pos.getZ() & 0xFFFFFFF0) | random.nextInt(16);
		return new BlockPos(x, AllPurposeUtility.randRange(64, 192, random), z);
	}
	
	private BlockPos getCentered(BlockPos pos, RandomSource random) {
		int x = (pos.getX() & 0xFFFFFFF0) | 8;
		int z = (pos.getZ() & 0xFFFFFFF0) | 8;
		return new BlockPos(x, AllPurposeUtility.randRange(64, 192, random), z);
	}
	
	private void generateSmall(WorldGenLevel level, BlockPos pos, RandomSource random) {
		BlockState volvox = EdenBlocks.VOLVOX_BLOCK.defaultBlockState();
		int count = AllPurposeUtility.randRange(3, 7, random);
		pos = getCentered(pos, random);
		float distance = AllPurposeUtility.randRange(4F, 6F, random);
		List<Vector3f> points = makeFibonacciPoints(count);
		for (int i = 0; i < count; i++) {
			Vector3f p = points.get(i);
			float radius = AllPurposeUtility.randRange(1.5F, 3F, random);
			float px = p.x() * distance + 0.5F;
			float py = p.y() * distance + 0.5F;
			float pz = p.z() * distance + 0.5F;
			makeSphere(level, pos.offset((int)px, (int)py, (int)pz), radius, -1, volvox, null, null);
		}
	}
	
	private void generateMedium(WorldGenLevel level, BlockPos pos, RandomSource random) {
		BlockState volvox = EdenBlocks.VOLVOX_BLOCK.defaultBlockState();
		BlockState water = Blocks.WATER.defaultBlockState();
		float radius = AllPurposeUtility.randRange(5F, 10F, random);
		pos = getRandom(pos, random);
		List<BlockPos> sphere = new ArrayList<BlockPos>();
		makeSphere(level, pos, radius, random.nextFloat(), volvox, water, sphere);
		addSmallPlants(level, pos, sphere, random);
	}

	private void generateLarge(WorldGenLevel level, BlockPos pos, RandomSource random) {
		BlockState volvoxDense = EdenBlocks.VOLVOX_BLOCK_DENSE.defaultBlockState();
		BlockState volvox = EdenBlocks.VOLVOX_BLOCK.defaultBlockState();
		BlockState water = Blocks.WATER.defaultBlockState();
		float radius = AllPurposeUtility.randRange(10F, 15F, random);
		pos = getCentered(pos, random);
		List<BlockPos> sphere = new ArrayList<BlockPos>();
		makeSphere(level, pos, radius, AllPurposeUtility.randRange(0.5F, 0.75F, random), volvox, water, sphere);
		
		Vector3f offset = new Vector3f(0.5F, 0.5F, 0.5F);
		Vector3f axis = new Vector3f(
			AllPurposeUtility.randRange(-1F, 1F, random),
			AllPurposeUtility.randRange(-1F, 1F, random),
			AllPurposeUtility.randRange(-1F, 1F, random)
		);
		axis.normalize();
		float angle = random.nextFloat() * (float) Math.PI * 2;
		Quaternionf rotation = new Quaternionf().setAngleAxis(angle, axis.x(), axis.y(), axis.z());
		for (int i = 0; i < 3; i++) {
			List<Vector3f> spline = makeCircleSpline(radius, i);
			spline.forEach(point -> point.rotate(rotation));
			AllPurposeUtility.offset(spline, offset);
			spline.add(spline.get(0));
			AllPurposeUtility.SplineMath.fillSplineForce(spline, level, volvoxDense, pos, state -> state.canBeReplaced() || state.equals(volvox));
		}
		addSmallPlants(level, pos, sphere, random);
	}
	private void addSmallPlants(WorldGenLevel level, BlockPos center, List<BlockPos> blocks, RandomSource random) {
		MutableBlockPos pos = new MutableBlockPos();
		BlockState mold1 = EdenBlocks.SYMBIOTIC_MOLD.defaultBlockState();
		BlockState mold2 = EdenBlocks.SYMBIOTIC_MOLD_EMISSIVE.defaultBlockState();
		BlockState vine1 = EdenBlocks.EDEN_VINE.defaultBlockState().setValue(EdenBlockProperties.TRIPLE_SHAPE, TripleShape.MIDDLE);
		BlockState vine2 = EdenBlocks.EDEN_VINE.defaultBlockState().setValue(EdenBlockProperties.TRIPLE_SHAPE, TripleShape.BOTTOM);
		blocks.forEach(p -> {
			if (random.nextInt(8) == 0) {
				for (Direction offset: AllPurposeUtility.DirectionalUtility.DIRECTIONS) {
					BlockPos side = pos.set(p).move(offset);
					if (level.getBlockState(side).isAir()) {
						BlockState state = SixSidePlant.class.cast(EdenBlocks.PARIGNUM).getAttachedState(level, side);
						if (state != null) {
							level.setBlock(side, state, AllPurposeUtility.Flags.SILENT);
						}
					}
				}
			}
			pos.set(p).move(Direction.DOWN);
			BlockState state = level.getBlockState(pos);
			if (state.getFluidState().isEmpty() && state.canBeReplaced()) {
				int plant = random.nextInt(8);
				if (plant == 0) {
					if (pos.getY() < center.getY()) {
						return;
					}
					level.setBlock(pos, mold2, AllPurposeUtility.Flags.SILENT);
				}
				else if (plant < 4) {
					level.setBlock(pos, mold1, AllPurposeUtility.Flags.SILENT);
				}
				else if (plant < 6) {
					if (pos.getY() < center.getY() && random.nextInt(3) > 0) {
						return;
					}
					int length = AllPurposeUtility.randRange(3, 6, random);
					for (int i = 0; i <= length; i++) {
						pos.move(Direction.DOWN);
						state = level.getBlockState(pos);
						pos.move(Direction.UP);
						if (!state.getFluidState().isEmpty() || !state.canBeReplaced()) {
							level.setBlock(pos, vine2, AllPurposeUtility.Flags.SILENT);
							break;
						}
						level.setBlock(pos, i == length ? vine2 : vine1, AllPurposeUtility.Flags.SILENT);
						pos.move(Direction.DOWN);
					}
				}
			}
		});
	}
	
	private void makeSphere(WorldGenLevel level, BlockPos center, float radius, float fill, BlockState wall, BlockState water, List<BlockPos> positions) {
		double r21 = (double) radius * (double) radius;
		MutableBlockPos pos = new MutableBlockPos();
		double r22 = (double) (radius - 1) * (double) (radius - 1);
		int min = Mth.floor(-radius);
		int max = Mth.floor(radius + 1);
		int waterY = (int) Mth.lerp(fill, min, max);
		for (int x = min; x <= max; x++) {
			int sqrX = x * x;
			pos.setX(center.getX() + x);
			for (int y = min; y <= max; y++) {
				int sqrY = y * y;
				pos.setY(center.getY() + y);
				for (int z = min; z <= max; z++) {
					int sqrZ = z * z;
					int d = sqrX + sqrY + sqrZ;
					if (d <= r21) {
						pos.setZ(center.getZ() + z);
						if (d >= r22) {
							if (level.getBlockState(pos).canBeReplaced()) {
								level.setBlock(pos, wall, AllPurposeUtility.Flags.SILENT);
								if (positions != null) {
									positions.add(pos.immutable());
								}
							}
						}
						else if (y < waterY && level.getBlockState(pos).canBeReplaced()) {
							level.setBlock(pos, water, AllPurposeUtility.Flags.SILENT);
						}
					}
				}
			}
		}
	}
	
	private List<Vector3f> makeCircleSpline(float radius, int offset) {
		short count = (short) (3 * radius);
		List<Vector3f> spline = new ArrayList<Vector3f>(count/* + 1*/);
		float PI2C = (float) Math.PI * 2 / count;
		float[] p = new float[3];
		for (short i = 0; i < count; i++) {
			p[0] = (float) Math.sin(i * PI2C);
			p[1] = (float) Math.sin(i * PI2C * 3) * 0.3F;
			p[2] = (float) Math.cos(i * PI2C);
			
			float l = AllPurposeUtility.length(p[0], p[1], p[2]);
			l = radius / l;
			
			Vector3f pos = new Vector3f(p[offset] * l, p[(1 + offset) % 3] * l, p[(2 + offset) % 3] * l);
			spline.add(pos);
		}
		return spline;
	}
	
	private List<Vector3f> makeFibonacciPoints(int count) {
		float max = count - 1;
		List<Vector3f> result = new ArrayList<Vector3f>(count + 1);
		for (int i = 0; i < count; i++) {
			float y = 1F - (i / max) * 2F;
			float radius = (float) Math.sqrt(1F - y * y);
			float theta = (float) (Math.PI * (3 - Math.sqrt(5))) * i;
			float x = (float) Math.cos(theta) * radius;
			float z = (float) Math.sin(theta) * radius;
			result.add(new Vector3f(x, y, z));
		}
		return result;
	}
}
