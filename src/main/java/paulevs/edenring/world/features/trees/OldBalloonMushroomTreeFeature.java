package paulevs.edenring.world.features.trees;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.betterx.bclib.api.v2.levelgen.features.features.DefaultFeature;
import org.betterx.bclib.complexmaterials.WoodenComplexMaterial;
import org.betterx.bclib.util.BlocksHelper;
import org.betterx.bclib.util.MHelper;

import paulevs.edenring.blocks.EdenBlockProperties;
import paulevs.edenring.blocks.EdenBlockProperties.BalloonMushroomStemState;
import paulevs.edenring.blocks.EdenBlockProperties.QuadShape;
import paulevs.edenring.registries.EdenBlocks;

import java.util.ArrayList;
import java.util.List;

public class OldBalloonMushroomTreeFeature extends DefaultFeature {
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		WorldGenLevel level = featurePlaceContext.level();
		BlockPos center = featurePlaceContext.origin();
		RandomSource random = featurePlaceContext.random();
		
		Block below = level.getBlockState(center.below()).getBlock();
		if (!(below instanceof GrassBlock) && below != Blocks.DIRT) {
			return false;
		}
		
		BlockState log = EdenBlocks.BALLOON_MUSHROOM_MATERIAL.getBlock(WoodenComplexMaterial.BLOCK_LOG).defaultBlockState();
		
		MutableBlockPos p = center.mutable().setY(center.getY() + 4);
		if (!level.getBlockState(center).is(EdenBlocks.BALLOON_MUSHROOM_SMALL)) {
			for (int x = -14; x <= 14; x++) {
				p.setX(center.getX() + x);
				for (int z = -14; z <= 14; z++) {
					if (Math.abs(x) + Math.abs(z) < 20) {
						p.setZ(center.getZ() + z);
						if (level.getBlockState(p).is(log.getBlock())) {
							return false;
						}
					}
				}
			}
		}
		
		BlockState bark = EdenBlocks.BALLOON_MUSHROOM_MATERIAL.getBlock(WoodenComplexMaterial.BLOCK_BARK).defaultBlockState();
		BlockState block = EdenBlocks.BALLOON_MUSHROOM_BLOCK.defaultBlockState();
		BlockState stem = EdenBlocks.BALLOON_MUSHROOM_STEM.defaultBlockState();
		
		int height = MHelper.randRange(8, 12, random);
		makeTrunk(level, center, p.set(center), log, height);
		makeRoots(level, center, p, log, bark, stem, random);
		makeBranches(level, center, p, stem, height, random);
		makeCap(level, p.set(center).setY(p.getY() + height), block, height, random);
		
		return true;
	}
	
	private void makeTrunk(WorldGenLevel level, BlockPos center, MutableBlockPos p, BlockState log, int height) {
		p.setY(p.getY() - 1);
		for (int i = 0; i <= height; i++) {
			setBlock(level, p, log);
			setBlock(level, p.setX(center.getX() + 1), log);
			setBlock(level, p.setZ(center.getZ() + 1), log);
			setBlock(level, p.setX(center.getX()), log);
			p.setZ(center.getZ()).setY(p.getY() + 1);
		}
	}
	
	@SuppressWarnings("deprecation")
	private void makeCap(WorldGenLevel level, MutableBlockPos p, BlockState block, int height, RandomSource random) {
		BlockState bottom = block.setValue(EdenBlockProperties.NATURAL, true);
		
		BlockState state = EdenBlocks.BALLOON_MUSHROOM_HYMENOPHORE.defaultBlockState();
		BlockState hymenophoreBottom = state.setValue(EdenBlockProperties.QUAD_SHAPE, QuadShape.BOTTOM);
		BlockState hymenophoreMiddle = state.setValue(EdenBlockProperties.QUAD_SHAPE, QuadShape.MIDDLE);
		BlockState hymenophoreSmall = state.setValue(EdenBlockProperties.QUAD_SHAPE, QuadShape.SMALL);
		BlockState hymenophoreTop = state.setValue(EdenBlockProperties.QUAD_SHAPE, QuadShape.TOP);
		
		state = EdenBlocks.BALLOON_MUSHROOM_STEM.defaultBlockState();
		BlockState stemMiddle = state.setValue(EdenBlockProperties.BALLOON_MUSHROOM_STEM, BalloonMushroomStemState.THIN);
		BlockState stemTop = state.setValue(EdenBlockProperties.BALLOON_MUSHROOM_STEM, BalloonMushroomStemState.THIN_TOP);
		DyeColor dye = random.nextBoolean() ? DyeColor.WHITE : DyeColor.PINK;
		BlockState lantern = EdenBlocks.MYCOTIC_LANTERN_COLORED.get(dye).defaultBlockState();
		
		BlockState sporocap = EdenBlocks.BALLOON_MUSHROOM_SPOROCARP_COLORED.get(DyeColor.WHITE).defaultBlockState();
		
		List<BlockPos> updateBlocks = new ArrayList<BlockPos>(128);
		BlockPos center = p.immutable();
		int radius = height >> 1;
		float startY = -radius * 0.5F;
		int r2 = radius * radius;
		int r3 = (radius - 1) * (radius - 1);
		
		for (int y = Mth.floor(startY); y <= radius; y++) {
			state = y == startY ? bottom : block;
			int y2 = y * y;
			for (int x = -radius; x <= radius; x++) {
				float x2 = (x - 0.5F) * (x - 0.5F);
				for (int z = -radius; z <= radius; z++) {
					float z2 = (z - 0.5F) * (z - 0.5F);
					float xz = x2 + z2;
					float xyz = xz + y2;
					if (xyz <= r2) {
						BlockState cap = y > startY && xyz < r3 ? sporocap : state;
						BlocksHelper.setWithoutUpdate(level, p.set(center).move(x, Mth.floor(y - startY), z), cap);
						updateBlocks.add(p.immutable());
						if (y == startY) {
							if (random.nextInt(16) == 0 && xz > 3) {
								int length = MHelper.randRange(4, 7, random);
								makeLantern(level, p.mutable().setY(p.getY() - 1), length, lantern, stemMiddle, stemTop);
							}
							else if (random.nextInt(4) > 0) {
								int length = MHelper.randRange(1, 3, random);
								makeVine(level, p.mutable().setY(p.getY() - 1), length, hymenophoreSmall, hymenophoreBottom, hymenophoreMiddle, hymenophoreTop);
							}
						}
					}
				}
			}
		}
		
		updateBlocks.forEach(pos -> {
			BlockState s = level.getBlockState(pos);
			s = s.updateShape(Direction.UP, AIR, level, pos, pos);
			BlocksHelper.setWithoutUpdate(level, pos, s);
		});
	}
	
	private void makeVine(WorldGenLevel level, MutableBlockPos pos, int length, BlockState small, BlockState bottom, BlockState middle, BlockState top) {
		if (!level.getBlockState(pos).isAir()) return;
		if (length == 1) {
			BlocksHelper.setWithoutUpdate(level, pos, small);
			return;
		}
		int maxLength = length - 1;
		for (int i = 0; i < length; i++) {
			BlockState state = i == maxLength ? bottom : i == 0 ? top : middle;
			BlocksHelper.setWithoutUpdate(level, pos, state);
			pos.setY(pos.getY() - 1);
			if (!level.getBlockState(pos).isAir()) {
				state = i == 0 ? small : bottom;
				BlocksHelper.setWithoutUpdate(level, pos.setY(pos.getY() + 1), state);
				return;
			}
		}
	}
	
	private void makeLantern(WorldGenLevel level, MutableBlockPos pos, int length, BlockState lantern, BlockState middle, BlockState top) {
		if (!level.getBlockState(pos).isAir()) return;
		int maxLength = length - 1;
		for (int i = 0; i < length; i++) {
			BlockState state = i == maxLength ? lantern : i == 0 ? top : middle;
			BlocksHelper.setWithoutUpdate(level, pos, state);
			pos.setY(pos.getY() - 1);
			if (!level.getBlockState(pos).isAir()) {
				BlocksHelper.setWithoutUpdate(level, pos.setY(pos.getY() + 1), lantern);
				return;
			}
		}
	}
	
	private void makeRoots(WorldGenLevel level, BlockPos center, MutableBlockPos p, BlockState log, BlockState bark, BlockState stem, RandomSource random) {
		int[][] mask = new int[4][4];
		mask[1][1] = 4;
		mask[1][2] = 4;
		mask[2][1] = 4;
		mask[2][2] = 4;
		
		for (int i = 0; i < 2; i++) {
			if (random.nextBoolean()) {
				int h = random.nextInt(3);
				int h2 = -(h + MHelper.randRange(2, 3, random));
				makeLine(level, p.set(center).move(i, h, -1), bark, log, h2);
				mask[i + 1][0] = h + 1;
			}
			if (random.nextBoolean()) {
				int h = random.nextInt(3);
				int h2 = -(h + MHelper.randRange(2, 3, random));
				makeLine(level, p.set(center).move(i, h, 2), bark, log, h2);
				mask[i + 1][3] = h + 1;
			}
			if (random.nextBoolean()) {
				int h = random.nextInt(3);
				int h2 = -(h + MHelper.randRange(2, 3, random));
				makeLine(level, p.set(center).move(-1, h, i), bark, log, h2);
				mask[0][i + 1] = h + 1;
			}
			if (random.nextBoolean()) {
				int h = random.nextInt(3);
				int h2 = -(h + MHelper.randRange(2, 3, random));
				makeLine(level, p.set(center).move(2, h, i), bark, log, h2);
				mask[3][i + 1] = h + 1;
			}
		}
		
		if (random.nextBoolean()) {
			int h = MHelper.randRange(1, 2, random);
			int h2 = (-h - 2);
			makeLine(level, p.set(center).move(-1, h - 1, -1), bark, log, h2);
			mask[0][0] = h;
		}
		if (random.nextBoolean()) {
			int h = MHelper.randRange(1, 2, random);
			int h2 = (-h - 2);
			makeLine(level, p.set(center).move(2, h - 1, -1), bark, log, h2);
			mask[3][0] = h;
		}
		if (random.nextBoolean()) {
			int h = MHelper.randRange(1, 2, random);
			int h2 = (-h - 2);
			makeLine(level, p.set(center).move(2, h - 1, 2), bark, log, h2);
			mask[3][3] = h;
		}
		if (random.nextBoolean()) {
			int h = MHelper.randRange(1, 2, random);
			int h2 = (-h - 2);
			makeLine(level, p.set(center).move(-1, h - 1, 2), bark, log, h2);
			mask[0][3] = h;
		}
		
		BlockState branch = EdenBlocks.BALLOON_MUSHROOM_BRANCH.defaultBlockState().setValue(BlockStateProperties.DOWN, true);
		Direction[] dirs = BlocksHelper.makeHorizontal();
		for (int x = -2; x < 4; x++) {
			for (int z = -2; z < 4; z++) {
				if (random.nextInt(4) == 0) continue;
				MHelper.shuffle(dirs, random);
				for (Direction dir: dirs) {
					int h = getConnection(x, z, dir, mask);
					if (h > 0) {
						if (h > 1) h = MHelper.randRange(1, h, random);
						int h2 = -h - 3;
						BlockState start = branch.setValue(EdenBlockProperties.DIRECTIONS[dir.get3DDataValue()], true);
						makeLine(level, p.set(center).move(x, h - 1, z), start, stem, h2);
						break;
					}
				}
			}
		}
	}
	
	private void makeBranches(WorldGenLevel level, BlockPos center, MutableBlockPos p, BlockState stem, int height, RandomSource random) {
		BlockState branch = EdenBlocks.BALLOON_MUSHROOM_BRANCH.defaultBlockState().setValue(BlockStateProperties.UP, true);
		for (int i = 0; i < 2; i++) {
			if (random.nextInt(4) > 0) {
				int dy = MHelper.randRange(2, 4, random);
				int dx = random.nextInt(2);
				singleBranch(level, p.set(center).move(i, height - dy, -1), Direction.NORTH, dx, dy, stem, branch);
			}
			if (random.nextInt(4) > 0) {
				int dy = MHelper.randRange(2, 4, random);
				int dx = random.nextInt(2);
				singleBranch(level, p.set(center).move(i, height - dy, 2), Direction.SOUTH, dx, dy, stem, branch);
			}
			if (random.nextInt(4) > 0) {
				int dy = MHelper.randRange(2, 4, random);
				int dx = random.nextInt(2);
				singleBranch(level, p.set(center).move(-1, height - dy, i), Direction.WEST, dx, dy, stem, branch);
			}
			if (random.nextInt(4) > 0) {
				int dy = MHelper.randRange(2, 4, random);
				int dx = random.nextInt(2);
				singleBranch(level, p.set(center).move(2, height - dy, i), Direction.EAST, dx, dy, stem, branch);
			}
		}
	}
	
	private void singleBranch(WorldGenLevel level, MutableBlockPos start, Direction dir, int dxz, int dy, BlockState stem, BlockState branch) {
		branch = branch.setValue(EdenBlockProperties.DIRECTIONS[dir.getOpposite().get3DDataValue()], true);
		if (dxz == 0) {
			makeLine(level, start, branch, stem, dy);
		}
		else {
			BalloonMushroomStemState state = dir.getAxis() == Axis.Z ? BalloonMushroomStemState.NORTH_SOUTH : BalloonMushroomStemState.EAST_WEST;
			BlockState side = stem.setValue(EdenBlockProperties.BALLOON_MUSHROOM_STEM, state);
			for (int i = 0; i < dxz; i++) {
				setBlock(level, start, side);
				start.move(dir);
			}
			makeLine(level, start, branch, stem, dy);
		}
	}
	
	private int getConnection(int x, int z, Direction dir, int[][] mask) {
		x += dir.getStepX() + 1;
		z += dir.getStepZ() + 1;
		if (x >= 0 && x < 4 && z >= 0 && z < 4) return mask[x][z];
		return 0;
	}
	
	private void makeLine(WorldGenLevel level, MutableBlockPos p, BlockState start, BlockState line, int height) {
		int delta = height > 0 ? 1 : -1;
		setBlock(level, p, start);
		for (int i = delta; i != height; i += delta) {
			setBlock(level, p.setY(p.getY() + delta), line);
		}
	}
	
	private void setBlock(WorldGenLevel level, BlockPos pos, BlockState state) {
		BlockState place = level.getBlockState(pos);
		if (BlocksHelper.replaceableOrPlant(state) || place.is(EdenBlocks.BALLOON_MUSHROOM_SMALL) || place.isAir()) {
			BlocksHelper.setWithoutUpdate(level, pos, state);
		}
	}
}
