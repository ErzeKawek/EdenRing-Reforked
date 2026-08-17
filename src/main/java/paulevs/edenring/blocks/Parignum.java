package paulevs.edenring.blocks;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.math.Transformation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.MultiVariant;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.client.models.ModelsHelper.MultiPartBuilder;
import org.betterx.bclib.client.models.PatternsHelper;
import org.betterx.bclib.interfaces.RuntimeBlockModelProvider;
import org.betterx.bclib.util.BlocksHelper;

import java.util.Map;
import java.util.Optional;

public class Parignum extends SixSidePlant {
	public Parignum() {
		super(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE));
	}

	@Override
	@Environment(EnvType.CLIENT)
	public UnbakedModel getModelVariant(ModelResourceLocation stateId, BlockState blockState, Map<ResourceLocation, UnbakedModel> modelCache) {
		MultiPartBuilder model = MultiPartBuilder.create(stateDefinition);
		for (Direction dir: BlocksHelper.DIRECTIONS) {
			ModelResourceLocation noFlowers = RuntimeBlockModelProvider.remapModelResourceLocation(stateId, blockState, "_no_flowers_" + dir.getName());
			ModelResourceLocation flowers1 = RuntimeBlockModelProvider.remapModelResourceLocation(stateId, blockState, "_flowers_1_" + dir.getName());
			ModelResourceLocation flowers2 = RuntimeBlockModelProvider.remapModelResourceLocation(stateId, blockState, "_flowers_2_" + dir.getName());
			ModelResourceLocation flowers3 = RuntimeBlockModelProvider.remapModelResourceLocation(stateId, blockState, "_flowers_3_" + dir.getName());
			ModelResourceLocation flowers4 = RuntimeBlockModelProvider.remapModelResourceLocation(stateId, blockState, "_flowers_4_" + dir.getName());

			modelCache.put(noFlowers.id(), makeModel(stateId.id(), null));
			modelCache.put(flowers1.id(), makeModel(stateId.id(), "_flowers_1"));
			modelCache.put(flowers2.id(), makeModel(stateId.id(), "_flowers_2"));
			modelCache.put(flowers3.id(), makeModel(stateId.id(), "_flowers_3"));
			modelCache.put(flowers4.id(), makeModel(stateId.id(), "_flowers_4"));

			Transformation transformation = new Transformation(null, dir.getOpposite().getRotation(), null, null);

			ResourceLocation stateModel = RuntimeBlockModelProvider.remapModelResourceLocation(stateId, blockState, "_" + dir.getName()).id();
			modelCache.put(stateModel, new MultiVariant(Lists.newArrayList(
				new Variant(noFlowers.id(), transformation, false, 2),
				new Variant(flowers1.id(), transformation, false, 1),
				new Variant(flowers2.id(), transformation, false, 1),
				new Variant(flowers3.id(), transformation, false, 1),
				new Variant(flowers4.id(), transformation, false, 1)
			)));

			int index = dir.get3DDataValue();
			model.part(stateModel).setCondition(state -> state.getValue(DIRECTIONS[index])).add();
		}

		return model.build();
	}

	@Override
	@Environment(EnvType.CLIENT)
	public BlockModel getItemModel(ResourceLocation itemID) {
		String modId = itemID.getNamespace();
		String name = itemID.getPath();
		Map<String, String> textures = Maps.newHashMap();
		textures.put("%texture%", modId + ":block/" + name);
		textures.put("%overlay%", modId + ":block/" + name + "_flowers_1");
		Optional<String> pattern = PatternsHelper.createJson(EdenPatterns.ITEM_TINTED_OVERLAY, textures);
		return ModelsHelper.fromPattern(pattern);
	}

	@Environment(EnvType.CLIENT)
	private BlockModel makeModel(ResourceLocation stateId, String overlay) {
		String path = stateId.getPath();
		if (path.startsWith("block/")) {
			path = path.substring("block/".length());
		}
		Map<String, String> textures = Maps.newHashMap();
		ResourceLocation patternID;
		if (overlay == null) {
			textures.put("%texture%", stateId.getNamespace() + ":block/" + path);
			patternID = EdenPatterns.BLOCK_PLANE_TINT;
		}
		else {
			textures.put("%texture%", stateId.getNamespace() + ":block/" + path);
			textures.put("%overlay%", stateId.getNamespace() + ":block/" + path + overlay);
			patternID = EdenPatterns.BLOCK_PLANE_OVERLAY;
		}
		Optional<String> pattern = PatternsHelper.createJson(patternID, textures);
		return ModelsHelper.fromPattern(pattern);
	}
}
