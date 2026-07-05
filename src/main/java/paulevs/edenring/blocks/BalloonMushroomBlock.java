package paulevs.edenring.blocks;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import paulevs.edenring.items.BalloonMushroomBlockItem;

public class BalloonMushroomBlock extends Block {
	public static final BooleanProperty NATURAL = EdenBlockProperties.NATURAL;
	
	public BalloonMushroomBlock(BlockBehaviour.Properties properties) {
		super(BlockBehaviour.Properties.ofFullCopy(Blocks.MUSHROOM_STEM).mapColor(MapColor.COLOR_PURPLE));
		registerDefaultState(defaultBlockState().setValue(NATURAL, false));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> stateManager) {
		super.createBlockStateDefinition(stateManager);
		stateManager.add(NATURAL);
	}

	public BlockItem getCustomItem(Identifier identifier, Item.Properties itemProperties) {
		return new BalloonMushroomBlockItem(this, itemProperties);
	}
}
