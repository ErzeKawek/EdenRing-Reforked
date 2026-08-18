package paulevs.edenring.registries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.betterx.wover.feature.api.FeatureManager;
import org.betterx.wover.feature.api.configured.ConfiguredFeatureKey;
import org.betterx.wover.feature.api.configured.ConfiguredFeatureManager;
import org.betterx.wover.feature.api.configured.configurators.WithConfiguration;
import org.betterx.wover.feature.api.placed.FeaturePlacementBuilder;
import org.betterx.wover.feature.api.placed.PlacedConfiguredFeatureKey;
import org.betterx.wover.feature.api.placed.PlacedFeatureManager;
import paulevs.edenring.EdenRing;
import paulevs.edenring.blocks.SixSidePlant;
import paulevs.edenring.world.features.basic.*;
import paulevs.edenring.world.features.plants.*;
import paulevs.edenring.world.features.terrain.*;
import paulevs.edenring.world.features.trees.*;

public class EdenFeatures {
	private static final List<PlacedFeatureEntry> ALL_FEATURES = new ArrayList<>();
	private static final Map<PlacedConfiguredFeatureKey, ConfiguredFeatureKey<?>> CONFIGURED_KEYS = new HashMap<>();

	public static final StonePillar STONE_PILLAR_FEATURE = inlineBuild("stone_pillar", new StonePillar());

	public static final PlacedConfiguredFeatureKey MOSS_LAYER = registerVegetation("moss_layer", new ScatterFeature(Blocks.MOSS_CARPET), 4);
	public static final PlacedConfiguredFeatureKey EDEN_MOSS_LAYER = registerVegetation("eden_moss_layer", new ScatterFeature(EdenBlocks.EDEN_MOSS), 6);

	public static final PlacedConfiguredFeatureKey MOSS_FLOOR = registerVegetation(
		"moss_floor",
		new FloorScatterFeature(Blocks.MOSS_BLOCK, EdenBlocks.EDEN_GRASS_BLOCK, Blocks.DIRT), 16
	);
	public static final PlacedConfiguredFeatureKey COBBLE_FLOOR = registerVegetation(
		"cobble_floor",
		new FloorScatterFeature(Blocks.MOSSY_COBBLESTONE, EdenBlocks.EDEN_GRASS_BLOCK, Blocks.DIRT), 8
	);
	public static final PlacedConfiguredFeatureKey GRASS_FLOOR = registerVegetation(
		"grass_floor",
		new FloorScatterFeature(EdenBlocks.EDEN_GRASS_BLOCK, true, EdenBlocks.EDEN_MYCELIUM, Blocks.SAND), 6
	);
	public static final PlacedConfiguredFeatureKey GRAVEL_FLOOR = registerVegetation(
		"gravel_floor",
		new FloorScatterFeature(Blocks.GRAVEL, Blocks.SAND), 6
	);

	public static final PlacedConfiguredFeatureKey STONE_PILLAR = registerRawGen("stone_pillar", STONE_PILLAR_FEATURE, 15);

	public static final PlacedConfiguredFeatureKey SLATE_LAYER = registerChunk("slate_layer", new StoneLayer(Blocks.DEEPSLATE));
	public static final PlacedConfiguredFeatureKey CALCITE_LAYER = registerChunk("calcite_layer", new StoneLayer(Blocks.CALCITE));
	public static final PlacedConfiguredFeatureKey TUFF_LAYER = registerChunk("tuff_layer", new StoneLayer(Blocks.TUFF));

	public static final PlacedConfiguredFeatureKey ORE_MOSSY_COBBLE = registerChunk(
		"ore_mossy_cobble",
		new DepthScatterFeature(Blocks.MOSSY_COBBLESTONE, Blocks.STONE, Blocks.DEEPSLATE, Blocks.CALCITE, Blocks.TUFF)
	);
	public static final PlacedConfiguredFeatureKey ORE_COBBLE = registerChunk(
		"ore_cobble",
		new DepthScatterFeature(Blocks.COBBLESTONE, Blocks.STONE, Blocks.DEEPSLATE, Blocks.CALCITE, Blocks.TUFF)
	);
	public static final PlacedConfiguredFeatureKey ORE_COAL = registerChunk(
		"ore_coal",
		new DepthScatterFeature(Blocks.COAL_ORE, 20, 5, Blocks.STONE, Blocks.DEEPSLATE, Blocks.CALCITE, Blocks.TUFF)
	);
	public static final PlacedConfiguredFeatureKey ORE_IRON = registerChunk(
		"ore_iron",
		new DepthScatterFeature(Blocks.IRON_ORE, 16, 4, Blocks.STONE, Blocks.DEEPSLATE, Blocks.CALCITE, Blocks.TUFF)
	);
	public static final PlacedConfiguredFeatureKey ORE_COPPER = registerChunk(
		"ore_copper",
		new DepthScatterFeature(Blocks.COPPER_ORE, 16, 4, Blocks.STONE, Blocks.DEEPSLATE, Blocks.CALCITE, Blocks.TUFF)
	);
	public static final PlacedConfiguredFeatureKey ORE_GOLD = registerChunk(
		"ore_gold",
		new DepthScatterFeature(Blocks.GOLD_ORE, 8, 2, Blocks.STONE, Blocks.DEEPSLATE, Blocks.CALCITE, Blocks.TUFF)
	);

	public static final PlacedConfiguredFeatureKey LAYERED_IRON = registerChunk(
		"layered_iron",
		new LayeredBulbFeature(new Block[] { Blocks.RAW_IRON_BLOCK, Blocks.IRON_ORE }, 32, 6, Blocks.STONE, Blocks.DEEPSLATE, Blocks.CALCITE, Blocks.TUFF)
	);
	public static final PlacedConfiguredFeatureKey LAYERED_COPPER = registerChunk(
		"layered_copper",
		new LayeredBulbFeature(new Block[] { Blocks.RAW_COPPER_BLOCK, Blocks.COPPER_ORE }, 32, 6, Blocks.STONE, Blocks.DEEPSLATE, Blocks.CALCITE, Blocks.TUFF)
	);
	public static final PlacedConfiguredFeatureKey LAYERED_GOLD = registerChunk(
		"layered_gold",
		new LayeredBulbFeature(new Block[] { Blocks.RAW_GOLD_BLOCK, Blocks.GOLD_ORE }, 16, 4, Blocks.STONE, Blocks.DEEPSLATE, Blocks.CALCITE, Blocks.TUFF)
	);

	public static final PlacedConfiguredFeatureKey MYCOTIC_GRASS = registerVegetation("mycotic_grass", new ScatterFeature(EdenBlocks.MYCOTIC_GRASS), 12);
	public static final PlacedConfiguredFeatureKey GOLDEN_GRASS = registerVegetation("golden_grass", new ScatterFeature(EdenBlocks.GOLDEN_GRASS), 8);
	public static final PlacedConfiguredFeatureKey BALLOON_MUSHROOM_SMALL = registerVegetation("balloon_mushroom_small", new ScatterFeature(EdenBlocks.BALLOON_MUSHROOM_SMALL), 6);
	public static final PlacedConfiguredFeatureKey COPPER_GRASS = registerVegetation("copper_grass", new ScatterFeature(EdenBlocks.COPPER_GRASS), 3);
	public static final PlacedConfiguredFeatureKey IRON_GRASS = registerVegetation("iron_grass", new ScatterFeature(EdenBlocks.IRON_GRASS), 3);
	public static final PlacedConfiguredFeatureKey GOLD_GRASS = registerVegetation("gold_grass", new ScatterFeature(EdenBlocks.GOLD_GRASS), 3);
	public static final PlacedConfiguredFeatureKey LONLIX = registerVegetation("lonlix", new ScatterFeature(EdenBlocks.LONLIX), 3);
	public static final PlacedConfiguredFeatureKey ALAESPES = registerVegetation("alaespes_patch", new DoubleScatterFeature(EdenBlocks.ALAESPES, 5), 3);

	public static final PlacedConfiguredFeatureKey VIOLUM_DENSE = registerVegetation("violum_dense", new DoubleScatterFeature(EdenBlocks.VIOLUM), 8);
	public static final PlacedConfiguredFeatureKey VIOLUM_RARE = registerVegetation("violum_rare", new DoubleScatterFeature(EdenBlocks.VIOLUM), 1);
	public static final PlacedConfiguredFeatureKey TALL_MYCOTIC_GRASS = registerVegetation("tall_mycotic_grass", new DoubleScatterFeature(EdenBlocks.TALL_MYCOTIC_GRASS, 8), 6);
	public static final PlacedConfiguredFeatureKey LIMPHIUM = registerChanced("limphium", Decoration.VEGETAL_DECORATION, new LimphiumFeature(), 8);
	public static final PlacedConfiguredFeatureKey TALL_COPPER_GRASS = registerVegetation("tall_copper_grass", new DoubleScatterFeature(EdenBlocks.TALL_COPPER_GRASS, 6), 4);
	public static final PlacedConfiguredFeatureKey TALL_IRON_GRASS = registerVegetation("tall_iron_grass", new DoubleScatterFeature(EdenBlocks.TALL_IRON_GRASS, 6), 4);
	public static final PlacedConfiguredFeatureKey TALL_GOLD_GRASS = registerVegetation("tall_gold_grass", new DoubleScatterFeature(EdenBlocks.TALL_GOLD_GRASS, 6), 4);

	public static final PlacedConfiguredFeatureKey BALLOON_MUSHROOM_TREE = registerVegetation("balloon_mushroom_tree", new BalloonMushroomTreeFeature(), 16);
	public static final PlacedConfiguredFeatureKey OLD_BALLOON_MUSHROOM_TREE = registerVegetation("old_balloon_mushroom_tree", new OldBalloonMushroomTreeFeature(), 3);
	public static final PlacedConfiguredFeatureKey AURITIS_TREE = registerVegetation("auritis_tree", new AuritisTreeFeature(), 10);
	public static final PlacedConfiguredFeatureKey PULSE_TREE = registerVegetation("pulse_tree", new PulseTreeFeature(), 50);
	public static final PlacedConfiguredFeatureKey BRAIN_TREE = registerVegetation("brain_tree", new BrainTreeFeature(), 8);
	public static final PlacedConfiguredFeatureKey AQUATUS = registerVegetation("aquatus", new AquatusFeature(), 8);
	public static final PlacedConfiguredFeatureKey VOLVOX = registerChanced("volvox", Decoration.VEGETAL_DECORATION, new VolvoxFeature(), 3);
	public static final PlacedConfiguredFeatureKey GRAVILITE_DEBRIS = registerChanced("gravilite_debris", new GraviliteDebrisFeature(), 2);
	public static final PlacedConfiguredFeatureKey EDEN_VINE = registerVegetation("eden_vine", new VineFeature(), 2);
	public static final PlacedConfiguredFeatureKey ROOTS = registerVegetation("roots", new RootsFeature(), 4);

	public static final PlacedConfiguredFeatureKey PARIGNUM = registerVegetation("parignum", new SixSideScatter((SixSidePlant) EdenBlocks.PARIGNUM), 8);
	public static final PlacedConfiguredFeatureKey TALL_BALLOON_MUSHROOM = registerVegetation("tall_balloon_mushroom", new TallMushroomFeature(), 6);
	public static final PlacedConfiguredFeatureKey GRAVILITE_CRYSTAL = registerRawGen("gravilite_crystal", new GraviliteCrystalFeature(), 100);
	public static final PlacedConfiguredFeatureKey SMALL_ISLAND = registerRawGen("small_island", new SmallIslandFeature(), 50);

	@SuppressWarnings("unchecked")
	public static <F extends Feature<FC>, FC extends FeatureConfiguration> F inlineBuild(String name, F feature) {
		ResourceLocation l = EdenRing.makeID(name);
		if (BuiltInRegistries.FEATURE.containsKey(l)) {
			return (F) BuiltInRegistries.FEATURE.get(l);
		}
		return FeatureManager.register(l, feature);
	}

	private static PlacedConfiguredFeatureKey registerVegetation(String name, Feature<NoneFeatureConfiguration> feature, int density) {
		return registerPlaced(name, feature, Decoration.VEGETAL_DECORATION, builder -> builder.onEveryLayerMax(density).onlyInBiome());
	}

	protected static PlacedConfiguredFeatureKey registerChanced(String name, Feature<NoneFeatureConfiguration> feature, int chance) {
		return registerChanced(name, Decoration.SURFACE_STRUCTURES, feature, chance);
	}

	private static PlacedConfiguredFeatureKey registerChanced(String name, Decoration decoration, Feature<NoneFeatureConfiguration> feature, int chance) {
		return registerPlaced(name, feature, decoration, builder -> builder.onceEvery(chance).squarePlacement().onlyInBiome());
	}

	private static PlacedConfiguredFeatureKey registerRawGen(String name, Feature<NoneFeatureConfiguration> feature, int chance) {
		return registerChanced(name, Decoration.RAW_GENERATION, feature, chance);
	}

	public static PlacedConfiguredFeatureKey registerChunk(String name, Feature<NoneFeatureConfiguration> feature) {
		return registerPlaced(name, feature, Decoration.UNDERGROUND_DECORATION, builder -> builder.count(1).onlyInBiome());
	}

	private static PlacedConfiguredFeatureKey registerPlaced(
			String name,
			Feature<NoneFeatureConfiguration> feature,
			Decoration decoration,
			Function<FeaturePlacementBuilder, FeaturePlacementBuilder> placement
	) {
		feature = inlineBuild(name, feature);
		ResourceLocation id = EdenRing.makeID(name);
		ConfiguredFeatureKey<WithConfiguration<Feature<NoneFeatureConfiguration>, NoneFeatureConfiguration>> configuredKey =
				ConfiguredFeatureManager.configuration(id, feature);
		PlacedConfiguredFeatureKey placedKey = PlacedFeatureManager.createKey(id, configuredKey).setDecoration(decoration);
		ALL_FEATURES.add(new PlacedFeatureEntry(configuredKey, placedKey, placement));
		return placedKey;
	}

	public static void bootstrapConfigured(BootstrapContext<ConfiguredFeature<?, ?>> ctx) {
		for (PlacedFeatureEntry entry : ALL_FEATURES) {
			entry.configuredKey.bootstrap(ctx).configuration(NoneFeatureConfiguration.NONE).register();
		}
	}

	public static void bootstrapPlaced(BootstrapContext<PlacedFeature> ctx) {
		for (PlacedFeatureEntry entry : ALL_FEATURES) {
			entry.placement.apply(entry.placedKey.place(ctx)).register();
		}
	}

	public static boolean placeInWorld(PlacedConfiguredFeatureKey key, WorldGenLevel level, BlockPos pos, RandomSource random) {
		ConfiguredFeatureKey<?> configured = CONFIGURED_KEYS.get(key);
		return configured != null && configured.placeInWorld(level, pos, random);
	}

	public static void register() {
	}

	private record PlacedFeatureEntry(
			ConfiguredFeatureKey<WithConfiguration<Feature<NoneFeatureConfiguration>, NoneFeatureConfiguration>> configuredKey,
			PlacedConfiguredFeatureKey placedKey,
			Function<FeaturePlacementBuilder, FeaturePlacementBuilder> placement
	) {
		PlacedFeatureEntry {
			CONFIGURED_KEYS.put(placedKey, configuredKey);
		}
	}
}
