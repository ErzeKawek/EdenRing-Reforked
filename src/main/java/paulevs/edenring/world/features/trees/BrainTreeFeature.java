package paulevs.edenring.world.features.trees;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import paulevs.edenring.blocks.EdenBlockProperties;
import paulevs.edenring.misc.AllPurposeUtility;
import paulevs.edenring.registries.EdenBlocks;

public class BrainTreeFeature {
	private static final BlockState[] TYPES = new BlockState[3];

	@SuppressWarnings("deprecation")
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		WorldGenLevel level = featurePlaceContext.level();
		BlockPos center = featurePlaceContext.origin();
		RandomSource random = featurePlaceContext.random();

		if (!EdenBlocks.PULSE_TREE_SAPLING.defaultBlockState().canSurvive(level, center)) {
			return false;
		}
		
		if (TYPES[0] == null) {
			TYPES[0] = EdenBlocks.BRAIN_TREE_BLOCK_IRON.defaultBlockState();
			TYPES[1] = EdenBlocks.BRAIN_TREE_BLOCK_COPPER.defaultBlockState();
			TYPES[2] = EdenBlocks.BRAIN_TREE_BLOCK_GOLD.defaultBlockState();
		}
		
		BlockState brain = TYPES[random.nextInt(3)];
		BlockState brainActive = brain.setValue(EdenBlockProperties.ACTIVE, true);
		//BlockState stem = EdenBlocks.BRAIN_TREE_LOG.defaultBlockState();
		BlockState stem = EdenBlocks.BRAIN_TREE_MATERIAL.log.defaultBlockState();
		
		MutableBlockPos pos = center.mutable();
		int h = AllPurposeUtility.randRange(2, 4, random);
		
		level.setBlock(pos, stem, AllPurposeUtility.Flags.SILENT);
		for (int i = 1; i < h; i++) {
			pos.setY(center.getY() + i);
			setBlock(level, pos, stem);
		}
		
		pos.setY(center.getY() + h);
		setBlock(level, pos, stem);
		pos.setY(pos.getY() + 1);
		setBlock(level, pos, stem);
		setBlock(level, pos.north(), stem);
		setBlock(level, pos.south(), stem);
		setBlock(level, pos.east(), stem);
		setBlock(level, pos.west(), stem);
		
		pos.setY(pos.getY() + 1);
		setBlock(level, pos.north(), stem);
		setBlock(level, pos.south(), stem);
		setBlock(level, pos.east(), stem);
		setBlock(level, pos.west(), stem);
		
		for (int y = 0; y < 4; y++) {
			pos.setY(center.getY() + h + y);
			for (int x = -1; x < 2; x++) {
				pos.setX(center.getX() + x);
				for (int z = -1; z < 2; z++) {
					pos.setZ(center.getZ() + z);
					setBlock(level, pos, random.nextInt(16) == 0 ? brainActive : brain);
				}
			}
		}
		
		for (int y = 0; y < 2; y++) {
			pos.setY(center.getY() + h + y + 1);
			for (int x = -2; x < 3; x++) {
				pos.setX(center.getX() + x);
				int ax = Mth.abs(x);
				for (int z = -2; z < 3; z++) {
					pos.setZ(center.getZ() + z);
					int az = Mth.abs(z);
					if (ax != 2 || az != 2 || ax != az) {
						setBlock(level, pos, random.nextInt(16) == 0 ? brainActive : brain);
					}
				}
			}
		}
		
		return true;
	}
	
	private void setBlock(WorldGenLevel level, BlockPos pos, BlockState state) {
		if (canReplace(level.getBlockState(pos))) {
			level.setBlock(pos, state, AllPurposeUtility.Flags.SILENT);
		}
	}
	
	private boolean canReplace(BlockState state) {
		return state.isAir() || state.canBeReplaced();
	}
}
