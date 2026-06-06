package paulevs.edenring.world.features;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import paulevs.edenring.EdenRing;
import paulevs.edenring.world.features.basic.ScatterFeature;
import paulevs.edenring.world.features.terrain.StonePillar;
import paulevs.edenring.world.features.trees.AuritisTreeFeature;
import paulevs.edenring.world.features.trees.BalloonMushroomTreeFeature;
import paulevs.edenring.world.features.trees.PulseTreeFeature;

public class EdenConfiguredFeatures {

    public static final Feature<NoneFeatureConfiguration> STONE_PILLAR_FEATURE = Registry.register(
            BuiltInRegistries.FEATURE,
            EdenRing.of("stone_pillar"),
            new StonePillar()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> STONE_PILLAR = of("stone_pillar");

    public static final Feature<NoneFeatureConfiguration> MOSS_LAYER_FEATURE = Registry.register(
            BuiltInRegistries.FEATURE,
            EdenRing.of("moss_layer"),
            new ScatterFeature(Blocks.MOSS_CARPET)
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> MOSS_LAYER = of("moss_layer");

    public static final Feature<NoneFeatureConfiguration> AURITIS_TREE_FEATURE = Registry.register(
            BuiltInRegistries.FEATURE,
            EdenRing.of("auritis_tree"),
            new AuritisTreeFeature()
    );
    public static final ResourceKey<ConfiguredFeature<?, ?>> AURITIS_TREE = of("auritis_tree");

    public static final Feature<NoneFeatureConfiguration> BALLOON_MUSHROOM_TREE_FEATURE = Registry.register(
            BuiltInRegistries.FEATURE,
            EdenRing.of("balloon_mushroom_tree"),
            new BalloonMushroomTreeFeature()
    );
    public static final ResourceKey<ConfiguredFeature<?, ?>> BALLOON_MUSHROOM_TREE = of("balloon_mushroom_tree");

    public static final Feature<NoneFeatureConfiguration> PULSE_TREE_FEATURE = Registry.register(
            BuiltInRegistries.FEATURE,
            EdenRing.of("pulse_tree"),
            new PulseTreeFeature()
    );
    public static final ResourceKey<ConfiguredFeature<?, ?>> PULSE_TREE = of("pulse_tree");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<ConfiguredFeature<?, ?>> lookup = context.lookup(
                Registries.CONFIGURED_FEATURE
        );
        FeatureUtils.register(context, STONE_PILLAR, STONE_PILLAR_FEATURE);
        FeatureUtils.register(context, MOSS_LAYER, MOSS_LAYER_FEATURE);
        FeatureUtils.register(context, AURITIS_TREE, AURITIS_TREE_FEATURE);
        FeatureUtils.register(context, BALLOON_MUSHROOM_TREE, BALLOON_MUSHROOM_TREE_FEATURE);
        FeatureUtils.register(context, PULSE_TREE, PULSE_TREE_FEATURE);
    }

    public static void init() {
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> of(String id) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, EdenRing.of(id));
    }
}

