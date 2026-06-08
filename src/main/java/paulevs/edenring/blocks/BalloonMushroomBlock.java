package paulevs.edenring.blocks;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;

import org.betterx.bclib.client.models.BasePatterns;
import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.client.models.ModelsHelper.MultiPartBuilder;
import org.betterx.bclib.client.models.PatternsHelper;
import org.betterx.bclib.interfaces.CustomItemProvider;
import paulevs.edenring.BaseCTBlock;
import paulevs.edenring.items.BalloonMushroomBlockItem;

import java.util.Map;
import java.util.Optional;

public class BalloonMushroomBlock extends BaseCTBlock {
	public static final BooleanProperty NATURAL = EdenBlockProperties.NATURAL;
	
	public BalloonMushroomBlock() {
		super(FabricBlockSettings.copyOf(Blocks.MUSHROOM_STEM).mapColor(MapColor.COLOR_PURPLE));
		registerDefaultState(defaultBlockState().setValue(NATURAL, false));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> stateManager) {
		super.createBlockStateDefinition(stateManager);
		stateManager.add(NATURAL);
	}
	
	@Override
	public BlockItem getCustomItem(Identifier identifier, Item.Properties itemProperties) {
		return new BalloonMushroomBlockItem(this, itemProperties);
	}
}
