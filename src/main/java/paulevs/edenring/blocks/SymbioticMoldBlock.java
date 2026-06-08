package paulevs.edenring.blocks;


import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;


public class SymbioticMoldBlock extends CeilPlantBlock {
	public SymbioticMoldBlock(int emission) {
		super(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_ROOTS).lightLevel((blockState) -> emission).offsetType(BlockBehaviour.OffsetType.NONE));
	}
}
