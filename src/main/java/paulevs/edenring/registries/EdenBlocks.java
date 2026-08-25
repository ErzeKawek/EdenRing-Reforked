package paulevs.edenring.registries;

import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;
import paulevs.edenring.EdenRing;
import paulevs.edenring.blocks.*;
import paulevs.edenring.blocks.complex.BrainTreeComplexMaterial;
import paulevs.edenring.blocks.complex.EdenWoodenComplexMaterial;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class EdenBlocks {
	public static final Block EDEN_GRASS_BLOCK = register(
			"eden_grass",
			new EdenGrassBlock(),
			BlockTags.NYLIUM
	);
	public static final Block EDEN_MYCELIUM = register("eden_mycelium",
			new TexturedTerrainBlock(),
			BlockTags.NYLIUM
	);
	public static final Block MOSSY_STONE = register("mossy_stone", new MossyStoneBlock());

	//public static final Block AURITIS_SAPLING = registerBlock(
	//		"auritis_sapling",
	//		new AuritisSaplingBlock()
	//);

	public static final Block AURITIS_SAPLING = registerNew(true, "auritis_sapling", properties -> new AuritisSaplingBlock(EdenFeatures.AURITIS_TREE, properties));





	public static void init() {
	}

	public static List<Block> getModBlocks() {
		return getBlockRegistry().allBlocks().toList();
	}


	
	private static Block register(String name, Block block) {
		return BLOCKS_REGISTRY.register(name, block);
	}

	private static Block registerStair(String string, Block block) {
		return registerNew(true, string, properties -> new StairBlock(block.defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(block));
	}

	private static Block registerNew(boolean hasItem, String string, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties) {
		ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, EdenRing.id(string));
		Block block = function.apply(properties);

		Registry.register(BuiltInRegistries.BLOCK, key, block);

		if (hasItem) EdenItems.registerBlock(block);

		return block;
	}

	public static Block register(String name, Function<Properties, Block> factory) {
		return register(name, factory, true);
	}

	public static Block register(String name, Function<Properties, Block> factory, boolean hasItem) {
		return register(name, factory, Properties.of(), hasItem);
	}

	private static Block register(String name, Function<Properties, Block> factory, Properties settings) {
		return register(name, factory, settings, true);
	}

	private static Block register(
			String name,
			Function<Properties, Block> factory,
			Properties settings,
			boolean hasItem
	) {
		Identifier id = EdenRing.of(name);
		Block block = factory.apply(settings.setId(ResourceKey.create(Registries.BLOCK, id)));

		if (hasItem) {
			ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);
			Registry.register(BuiltInRegistries.ITEM, itemKey,
					new BlockItem(block, new Item.Properties().setId(itemKey)));
		}
		ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);
		return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
	}
	
	public static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return false;
	}
}
