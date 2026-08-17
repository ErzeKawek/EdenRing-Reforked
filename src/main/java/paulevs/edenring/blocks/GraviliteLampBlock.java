package paulevs.edenring.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.betterx.bclib.blocks.BaseBlock;

public class GraviliteLampBlock extends BaseBlock {
	public GraviliteLampBlock() {
		super(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).lightLevel(state -> 15));
	}
}
