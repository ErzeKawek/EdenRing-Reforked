package paulevs.edenring.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import paulevs.edenring.blocks.complex.EdenSaplings;
import paulevs.edenring.registries.EdenFeatures;
import paulevs.edenring.world.features.plants.AquatusFeature;

@SuppressWarnings("all")
public class AquatusSapling extends EdenSaplings {
	public AquatusSapling(EdenSaplings treeConstructor, BlockBehaviour.Properties settings) {
		super(AquatusFeature::new, settings.mapColor(MapColor.GOLD));
	}
	
	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter world, BlockPos pos) {
		return state.is(Blocks.SAND) || state.is(Blocks.GRAVEL) || state.is(Blocks.SUSPICIOUS_SAND) || state.is(Blocks.SUSPICIOUS_GRAVEL);
	}
}
