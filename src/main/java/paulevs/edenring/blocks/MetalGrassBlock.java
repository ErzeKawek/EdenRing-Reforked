package paulevs.edenring.blocks;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import org.betterx.bclib.client.models.BasePatterns;
import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.client.models.PatternsHelper;
import org.betterx.bclib.interfaces.RuntimeBlockModelProvider;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MetalGrassBlock extends GrassBlock implements RuntimeBlockModelProvider {
	public MetalGrassBlock() {
		super(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).sound(SoundType.COPPER));
	}
	
	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		return Collections.singletonList(new ItemStack(this));
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public BlockModel getItemModel(ResourceLocation blockId) {
		return this.getBlockModel(blockId, this.defaultBlockState());
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public BlockModel getBlockModel(ResourceLocation blockId, BlockState blockState) {
		Map<String, String> textures = Maps.newHashMap();
		String modId = blockId.getNamespace();
		String name = blockId.getPath();
		textures.put("%top%", modId + ":block/" + name + "_top");
		textures.put("%side%", modId + ":block/" + name + "_side");
		textures.put("%bottom%", "minecraft:block/dirt");
		Optional<String> pattern = PatternsHelper.createJson(BasePatterns.BLOCK_TOP_SIDE_BOTTOM, textures);
		return ModelsHelper.fromPattern(pattern);
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public UnbakedModel getModelVariant(ModelResourceLocation stateId, BlockState blockState, Map<ResourceLocation, UnbakedModel> modelCache) {
		ModelResourceLocation modelId = new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(stateId.id().getNamespace(), "block/" + stateId.id().getPath()), "");
		this.registerBlockModel(stateId, modelId, blockState, modelCache);
		return ModelsHelper.createRandomTopModel(modelId.id());
	}
}