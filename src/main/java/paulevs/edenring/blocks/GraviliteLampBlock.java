package paulevs.edenring.blocks;


import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class GraviliteLampBlock {
	public GraviliteLampBlock() {
		BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).lightLevel(value -> 15);
	}
}
