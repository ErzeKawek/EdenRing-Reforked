package paulevs.edenring.world.features.terrain;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import paulevs.edenring.blocks.EdenBlockProperties;
import paulevs.edenring.blocks.EdenGrassBlock;
import paulevs.edenring.misc.AllPurposeUtility;
import paulevs.edenring.misc.noise.OpenSimplexNoise;
import paulevs.edenring.misc.sdf.SDF;
import paulevs.edenring.misc.sdf.operators.SDFBlink;
import paulevs.edenring.misc.sdf.operators.SDFScale3D;
import paulevs.edenring.misc.sdf.operators.SDFSmoothUnion;
import paulevs.edenring.misc.sdf.operators.SDFTranslate;
import paulevs.edenring.misc.sdf.primitives.SDFCappedCone;
import paulevs.edenring.misc.sdf.primitives.SDFSphere;
import paulevs.edenring.registries.EdenBlocks;

public class StonePillar extends Feature<NoneFeatureConfiguration> {

	public StonePillar() {
		super(NoneFeatureConfiguration.CODEC);
	}

	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		WorldGenLevel level = featurePlaceContext.level();
		BlockPos center = featurePlaceContext.origin();
		RandomSource random = featurePlaceContext.random();
		center = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, center);
		
		if (center.getY() < 5) {
			return false;
		}
		
		float height = AllPurposeUtility.randRange(1.5F, 3.5F, random);
		center = center.above(Mth.floor(height));
		
		float r1 = AllPurposeUtility.randRange(2.5F, 3.5F, random);
		float r2 = AllPurposeUtility.randRange(1.5F, 2.5F, random);
		SDF sdf = new SDFCappedCone().setHeight(height * 2).setRadius1(r1).setRadius2(r2).setBlock(Blocks.STONE);
		int count = random.nextInt(3);
		float radius = r2 * AllPurposeUtility.randRange(1.5F, 2F, random);
		for (int i = 0; i < count; i++) {
			height += radius * 0.7F;
			SDF sphere = new SDFSphere().setRadius(AllPurposeUtility.randRange(2F, 4F, random)).setBlock(Blocks.STONE);
			sphere = new SDFScale3D().setScale(AllPurposeUtility.randRange(2F, 3F, random), 1, AllPurposeUtility.randRange(2F, 3F, random)).setSource(sphere);
			sphere = new SDFTranslate().setTranslate(0, height, 0).setSource(sphere);
			sdf = new SDFSmoothUnion().setRadius(4f).setSourceA(sdf).setSourceB(sphere);
			height += radius;
			radius += AllPurposeUtility.randRange(0.75F, 1.5F, random);
		}
		
		OpenSimplexNoise noise = new OpenSimplexNoise(random.nextInt());
		sdf = new SDFBlink().setFunction(pos -> {
			float disp = (float) noise.eval(pos.x() * 0.06F, pos.y() * 0.06F, pos.z() * 0.06F) * 2.0F;
			disp += (float) noise.eval(pos.x() * 0.1F, pos.y() * 0.1F, pos.z() * 0.1F) * 0.35F;
			return disp;
		}).setSource(sdf);
		
		BlockState bottom = EdenBlocks.EDEN_VINE.defaultBlockState().setValue(EdenBlockProperties.TRIPLE_SHAPE, EdenBlockProperties.TripleShape.BOTTOM);
		BlockState middle = EdenBlocks.EDEN_VINE.defaultBlockState().setValue(EdenBlockProperties.TRIPLE_SHAPE, EdenBlockProperties.TripleShape.MIDDLE);
		BlockState top = EdenBlocks.EDEN_VINE.defaultBlockState().setValue(EdenBlockProperties.TRIPLE_SHAPE, EdenBlockProperties.TripleShape.TOP);
		BlockState roots = Blocks.HANGING_ROOTS.defaultBlockState();
		sdf.addPostProcess(info -> {
			if (info.getState().is(EdenBlocks.EDEN_VINE) || info.getState().is(Blocks.HANGING_ROOTS)) {
				return info.getState();
			}
			if (info.getPos().getY() > 3 && info.getStateDown().isAir() && random.nextInt(8) == 0) {
				if (random.nextBoolean()) {
					info.setBlockPos(info.getPos().below(), roots);
				}
				else {
					int h = AllPurposeUtility.randRange(1, 3, random);
					for (int i = 0; i < h; i++) {
						int p = i + 1;
						info.setBlockPos(info.getPos().below(p), i == 0 ? top : i == p ? bottom : middle);
					}
				}
			}
			if (info.getStateUp().isAir() && noise.eval(info.getPos().getX() * 0.4F, info.getPos().getY() * 0.4F, info.getPos().getZ() * 0.4F) > 0.1) {
				if (random.nextInt(5) == 0) {
					info.setBlockPos(info.getPos().above(), Blocks.GRASS_BLOCK.defaultBlockState());
				}
				return EdenBlocks.MOSSY_STONE.defaultBlockState();
			}
			else if (noise.eval(info.getPos().getX() * 0.4F, info.getPos().getY() * 0.4F + 50, info.getPos().getZ() * 0.4F) > 0.3) {
				return Blocks.MOSSY_COBBLESTONE.defaultBlockState();
			}
			else if (noise.eval(info.getPos().getX() * 0.4F, info.getPos().getY() * 0.4F + 100, info.getPos().getZ() * 0.4F) > 0.3) {
				return Blocks.COBBLESTONE.defaultBlockState();
			}
			else if (noise.eval(info.getPos().getX() * 0.4F, info.getPos().getY() * 0.4F + 150, info.getPos().getZ() * 0.4F) > 0.3) {
				if (info.getStateUp().isAir() && random.nextInt(5) == 0) {
					info.setBlockPos(info.getPos().above(), Blocks.GRASS_BLOCK.defaultBlockState());
				}
				return Blocks.MOSS_BLOCK.defaultBlockState();
			}
			
			return info.getState();
		});
		
		sdf.setReplaceFunction(state -> {
			return state.isAir() || state.getBlock() instanceof EdenGrassBlock || state.is(Blocks.DIRT);
		}).fillRecursive(level, center);
		
		return true;
	}
}
