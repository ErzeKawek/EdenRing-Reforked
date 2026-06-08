package paulevs.edenring.blocks;

import com.google.common.collect.Maps;
import com.mojang.math.Transformation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.betterx.bclib.blocks.BaseAttachedBlock;
import org.betterx.bclib.client.models.BasePatterns;
import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.client.models.PatternsHelper;
import org.betterx.bclib.client.render.BCLRenderLayer;
import org.betterx.bclib.interfaces.RenderLayerProvider;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import static net.minecraft.world.level.block.Block.box;

public class GraviliteShardsBlock extends BaseAttachedBlock implements RenderLayerProvider {
	private static final EnumMap<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(Direction.class);
	public static final EnumProperty FACING = BlockStateProperties.FACING;
	
	public GraviliteShardsBlock() {
		super(FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER).luminance(15).noCollision().noOcclusion());
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return BOUNDING_SHAPES.get(state.getValue(FACING));
	}
	
	static {
		BOUNDING_SHAPES.put(Direction.UP, box(2, 0, 2, 14, 15, 14));
		BOUNDING_SHAPES.put(Direction.DOWN, box(2, 1, 2, 14, 16, 14));
		BOUNDING_SHAPES.put(Direction.NORTH, box(2, 2, 1, 14, 14, 16));
		BOUNDING_SHAPES.put(Direction.SOUTH, box(2, 2, 0, 14, 14, 15));
		BOUNDING_SHAPES.put(Direction.WEST, box(1, 2, 2, 16, 14, 14));
		BOUNDING_SHAPES.put(Direction.EAST, box(0, 2, 2, 15, 14, 14));
	}
}
