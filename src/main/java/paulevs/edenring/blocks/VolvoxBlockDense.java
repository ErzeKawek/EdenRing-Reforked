package paulevs.edenring.blocks;

import com.google.common.collect.Lists;
import com.mojang.math.Transformation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.MultiVariant;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

import org.betterx.bclib.behaviours.BehaviourBuilders;
import org.betterx.bclib.blocks.BaseBlock;
import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.client.models.PatternsHelper;
import org.betterx.bclib.interfaces.RuntimeBlockModelProvider;

import java.util.Map;
import java.util.Optional;

public class VolvoxBlockDense extends BaseBlock implements RuntimeBlockModelProvider {
	public VolvoxBlockDense() {
		super(BehaviourBuilders.createWood().strength(1F).sound(SoundType.SLIME_BLOCK));
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public BlockModel getItemModel(ResourceLocation itemID) {
		Optional<String> pattern = PatternsHelper.createBlockSimple(itemID);
		return ModelsHelper.fromPattern(pattern);
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public UnbakedModel getModelVariant(ModelResourceLocation stateId, BlockState blockState, Map<ResourceLocation, UnbakedModel> modelCache) {
		String namespace = stateId.id().getNamespace();
		ResourceLocation model1 = ResourceLocation.fromNamespaceAndPath(namespace, "block/volvox_block_dense");
		ResourceLocation model2 = ResourceLocation.fromNamespaceAndPath(namespace, "block/volvox_block_dense_mossy");
		modelCache.put(model1, ModelsHelper.fromPattern(PatternsHelper.createBlockSimple(ResourceLocation.fromNamespaceAndPath(namespace, "volvox_block_dense"))));
		modelCache.put(model2, ModelsHelper.fromPattern(PatternsHelper.createBlockSimple(ResourceLocation.fromNamespaceAndPath(namespace, "volvox_block_dense_mossy"))));

		return new MultiVariant(Lists.newArrayList(
			new Variant(model1, Transformation.identity(), false, 2),
			new Variant(model2, Transformation.identity(), false, 1)
		));
	}
}
