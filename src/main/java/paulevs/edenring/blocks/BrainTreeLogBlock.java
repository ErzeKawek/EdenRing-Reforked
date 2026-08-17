package paulevs.edenring.blocks;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.betterx.bclib.blocks.BaseRotatedPillarBlock;

public class BrainTreeLogBlock extends BaseRotatedPillarBlock {
	public BrainTreeLogBlock() {
		super(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.NETHERITE_BLOCK));
	}
}
