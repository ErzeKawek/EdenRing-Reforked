package paulevs.edenring.registries;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.betterx.bclib.api.v2.ComposterAPI;
import org.betterx.bclib.items.ModelProviderItem;
import org.betterx.wover.item.api.ItemRegistry;
import paulevs.edenring.EdenRing;
import paulevs.edenring.items.AstralliumItem;
import paulevs.edenring.items.EdenPaintingItem;
import paulevs.edenring.items.GuideBookItem;

import java.util.function.BiFunction;
import java.util.function.Function;

public class EdenItems {
	public static final ItemRegistry REGISTRY = ItemRegistry.forMod(EdenRing.C);
	
	public static final Item GUIDE_BOOK = register("guide_book", new GuideBookItem(REGISTRY.createDefaultItemSettings().stacksTo(16)));
	public static final Item LIMPHIUM_LEAF = register("limphium_leaf", new ModelProviderItem(REGISTRY.createDefaultItemSettings()));
	public static final Item LIMPHIUM_LEAF_DRYED = register("limphium_leaf_dryed", new ModelProviderItem(REGISTRY.createDefaultItemSettings()));
	public static final Item LIMPHIUM_PAINTING = register("limphium_painting", new EdenPaintingItem(EdenEntities.LIMPHIUM_PAINTING, REGISTRY.createDefaultItemSettings()));
	public static final Item ASTRALLIUM = register("astrallium", new AstralliumItem(REGISTRY.createDefaultItemSettings()));
	public static final Item ASTRALLIUM_INGOT = register("astrallium_ingot", new ModelProviderItem(REGISTRY.createDefaultItemSettings()));

	public static void init() {
		ComposterAPI.allowCompost(0.3F, LIMPHIUM_LEAF);
		ComposterAPI.allowCompost(0.3F, LIMPHIUM_LEAF_DRYED);
	}

	private static Item register(String name, Item item) {
		REGISTRY.register(name, item);
		return item;
	}

	private static ResourceKey<Item> createResourceKey(String name) {
		return ResourceKey.create(Registries.ITEM, EdenRing.id(name));
	}

	private static ResourceKey<Item> blockIdToItemId(ResourceKey<Block> resourceKey) {
		return ResourceKey.create(Registries.ITEM, resourceKey.location());
	}

	public static Item registerBlock(Block block) {
		return registerBlock(block, BlockItem::new, new Item.Properties());
	}

	public static Item registerBlock(Block block, BiFunction<Block, Item.Properties, Item> biFunction, Item.Properties properties) {
		return registerItem(blockIdToItemId(block.builtInRegistryHolder().key()), propertiesx -> biFunction.apply(block, propertiesx), properties);
	}

	public static Item registerItem(String string, Function<Item.Properties, Item> function, Item.Properties properties) {
		return registerItem(createResourceKey(string), function, properties);
	}

	public static Item registerItem(String string, Item.Properties properties) {
		return registerItem(createResourceKey(string), Item::new, properties);
	}

	public static Item registerItem(String string) {
		return registerItem(createResourceKey(string), Item::new, new Item.Properties());
	}

	public static Item registerItem(ResourceKey<Item> resourceKey, Function<Item.Properties, Item> function, Item.Properties properties) {
		Item item = function.apply(properties);
		if (item instanceof BlockItem blockItem) {
			blockItem.registerBlocks(Item.BY_BLOCK, item);
		}

		return Registry.register(BuiltInRegistries.ITEM, resourceKey, item);
	}

	public static ItemRegistry getItemRegistry() {
		return REGISTRY;
	}
}
