package paulevs.edenring.registries;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import paulevs.edenring.EdenRing;

import paulevs.edenring.items.AstralliumItem;
import paulevs.edenring.items.EdenPaintingItem;
import paulevs.edenring.items.GuideBookItem;

import java.util.function.Function;

public class EdenItems {

	public static final Item GUIDE_BOOK = register("guide_book", GuideBookItem::new, new Item.Properties().stacksTo(16));
	public static final Item LIMPHIUM_LEAF = register("limphium_leaf", new ModelProviderItem(REGISTRY.makeItemSettings()));
	public static final Item LIMPHIUM_LEAF_DRYED = register("limphium_leaf_dryed", new ModelProviderItem(REGISTRY.makeItemSettings()));
	public static final Item LIMPHIUM_PAINTING = register("limphium_painting", new EdenPaintingItem(EdenEntities.LIMPHIUM_PAINTING, REGISTRY.makeItemSettings()));
	public static final Item ASTRALLIUM = register("astrallium", new AstralliumItem(REGISTRY.makeItemSettings()));

	public static Item register(String name) {
		return register(name, new Item.Properties());
	}

	public static Item register(String name, Item.Properties settings) {
		return register(name, Item::new, settings);
	}

	public static Item register(String name, Function<Item.Properties, Item> factory, Item.Properties settings) {
		Identifier id = EdenRing.of(name);
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id);
		return Registry.register(BuiltInRegistries.ITEM, key, factory.apply(settings.setId(key)));
	}

	public static void init() {
	}

}
