package paulevs.edenring.blocks;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.betterx.bclib.blocks.BaseBlockWithEntity;
import org.betterx.bclib.client.models.BasePatterns;
import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.client.models.PatternsHelper;
import org.betterx.bclib.interfaces.RuntimeBlockModelProvider;
import paulevs.edenring.blocks.entities.EdenPortalBlockEntity;

import java.util.Optional;

public class EdenPortalCenterBlock extends BaseBlockWithEntity implements RuntimeBlockModelProvider {
	public EdenPortalCenterBlock() {
		super(BlockBehaviour.Properties.ofFullCopy(Blocks.BARRIER).mapColor(MapColor.NONE).lightLevel(state -> 15).noCollission().noOcclusion());
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return Block.simpleCodec(settings -> new EdenPortalCenterBlock());
	}

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return Shapes.empty();
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new EdenPortalBlockEntity(blockPos, blockState);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		return level.isClientSide() ? EdenPortalBlockEntity::clientTick : EdenPortalBlockEntity::serverTick;
	}
	
	@Override
	public boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return true;
	}
	
	@Override
	public RenderShape getRenderShape(BlockState blockState) {
		return RenderShape.INVISIBLE;
	}
	
	@Override
	public float getShadeBrightness(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return 1.0F;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public BlockModel getBlockModel(ResourceLocation blockId, BlockState blockState) {
		Optional<String> pattern = PatternsHelper.createJson(BasePatterns.BLOCK_EMPTY, ResourceLocation.withDefaultNamespace("stone"));
		return ModelsHelper.fromPattern(pattern);
	}
}
