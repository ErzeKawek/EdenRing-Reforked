package paulevs.edenring.world.features.trees;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import paulevs.edenring.blocks.EdenBlockProperties;
import paulevs.edenring.blocks.EdenBlockProperties.BalloonMushroomStemState;
import paulevs.edenring.blocks.EdenBlockProperties.QuadShape;
import paulevs.edenring.misc.AllPurposeUtility;
import paulevs.edenring.registries.EdenBlocks;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.level.block.Blocks.AIR;

public class BalloonMushroomTreeFeature {
	@SuppressWarnings("deprecation")
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		WorldGenLevel level = featurePlaceContext.level();
		BlockPos center = featurePlaceContext.origin();
		RandomSource random = featurePlaceContext.random();
		
		Block below = level.getBlockState(center.below()).getBlock();
		if (!(below instanceof GrassBlock) && below != Blocks.DIRT) {
			return false;
		}
		
		MutableBlockPos pos = center.mutable();
		int h = AllPurposeUtility.randRange(5, 9, random);
		for (int i = 1; i <= h; i++) {
			pos.setY(center.getY() + i);
			if (!level.getBlockState(pos).isAir()) {
				h = i - 1;
			}
		}
		if (h < 5) {
			h = random.nextInt(4);
			pos.setY(center.getY());
			BlockState tall = EdenBlocks.TALL_BALLOON_MUSHROOM.defaultBlockState();
			for (int i = 0; i <= h; i++) {
				BlockState state = tall.setValue(EdenBlockProperties.TEXTURE_4, (int) i);
				if (i == h) state = state.setValue(EdenBlockProperties.TEXTURE_4, 3);
				if (level.getBlockState(pos.above()).isAir()) {
					level.setBlock(pos, state, AllPurposeUtility.Flags.SILENT);
					pos.setY(pos.getY() + 1);
				}
				else {
					state = state.setValue(EdenBlockProperties.TEXTURE_4, 3);
					level.setBlock(pos, state, AllPurposeUtility.Flags.SILENT);
					return true;
				}
			}
			return true;
		}
		
		BlockState block = EdenBlocks.BALLOON_MUSHROOM_BLOCK.defaultBlockState();
		BlockState stem = EdenBlocks.BALLOON_MUSHROOM_STEM.defaultBlockState();
		if (h > 5 && random.nextInt(6) == 0) {
			for (int i = 0; i < h; i++) {
				pos.setY(center.getY() + i);
				level.setBlock(pos, stem, AllPurposeUtility.Flags.SILENT);
			}
			
			List<BlockPos> updateBlocks = new ArrayList<BlockPos>(27);
			BlockState head = block.setValue(EdenBlockProperties.NATURAL, true);
			
			BlockState hymenophore = EdenBlocks.BALLOON_MUSHROOM_HYMENOPHORE.defaultBlockState();
			BlockState hymenophoreBottom = hymenophore.setValue(EdenBlockProperties.QUAD_SHAPE, QuadShape.BOTTOM);
			BlockState hymenophoreSmall = hymenophore.setValue(EdenBlockProperties.QUAD_SHAPE, QuadShape.SMALL);
			BlockState hymenophoreTop = hymenophore.setValue(EdenBlockProperties.QUAD_SHAPE, QuadShape.TOP);
			
			for (int x = -1; x < 2; x++) {
				pos.setX(center.getX() + x);
				for (int z = -1; z < 2; z++) {
					pos.setZ(center.getZ() + z);
					pos.setY(center.getY() + h - 1);
					if (level.getBlockState(pos).isAir()) {
						if (x == 0 || z == 0) {
							BlockPos bpos = pos.below();
							if (level.getBlockState(bpos).isAir()) {
								level.setBlock(pos, hymenophoreTop, AllPurposeUtility.Flags.SILENT);
								level.setBlock(bpos, hymenophoreBottom, AllPurposeUtility.Flags.SILENT);
							}
							else {
								level.setBlock(pos, hymenophoreSmall, AllPurposeUtility.Flags.SILENT);
							}
						}
						else {
							level.setBlock(pos, hymenophoreSmall, AllPurposeUtility.Flags.SILENT);
						}
					}
					for (int y = 0; y < 3; y++) {
						pos.setY(center.getY() + h + y);
						if (level.getBlockState(pos).isAir()) {
							level.setBlock(pos, y == 0 ? head : block, AllPurposeUtility.Flags.SILENT);
							updateBlocks.add(pos.immutable());
						}
					}
				}
			}
			updateBlocks.forEach(p -> {
				BlockState s = level.getBlockState(p);
				s = s.getBlock().updateShape(s, Direction.UP, AIR, level, p, p);
				level.setBlock(p, s, AllPurposeUtility.Flags.SILENT);
			});
		}
		else {
			BlockState thin = stem.setValue(EdenBlockProperties.BALLOON_MUSHROOM_STEM, BalloonMushroomStemState.THIN);
			BlockState thin_up = stem.setValue(EdenBlockProperties.BALLOON_MUSHROOM_STEM, BalloonMushroomStemState.THIN_TOP);
			int hMax = h - 1;
			for (int i = 0; i < h; i++) {
				pos.setY(center.getY() + i);
				level.setBlock(pos, i == hMax ? thin_up : thin, AllPurposeUtility.Flags.SILENT);
			}
			pos.setY(center.getY() + h);
			level.setBlock(pos, block.setValue(EdenBlockProperties.NATURAL, true), AllPurposeUtility.Flags.SILENT);
		}
		
		return true;
	}
}
