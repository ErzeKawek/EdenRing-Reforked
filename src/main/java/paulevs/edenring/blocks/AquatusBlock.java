package paulevs.edenring.blocks;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.betterx.bclib.blocks.BaseRotatedPillarBlock;

public class AquatusBlock extends BaseRotatedPillarBlock {
	public AquatusBlock() {
		super(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).sound(SoundType.WART_BLOCK));
	}
}
