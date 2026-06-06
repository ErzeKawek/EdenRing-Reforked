package paulevs.edenring.world.features;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import paulevs.edenring.EdenRing;
import paulevs.edenring.registries.EdenBlocks;

import java.util.List;

public class EdenPlacedFeatures {

    public static final ResourceKey<PlacedFeature> STONE_PILLAR = of("stone_pillar");
    public static final ResourceKey<PlacedFeature> MOSS_LAYER = of("moss_layer");
    public static final ResourceKey<PlacedFeature> AURITIS_TREE = of("auritis_tree");
    public static final ResourceKey<PlacedFeature> BALLOON_MUSHROOM_TREE = of("balloon_mushroom_tree");
    public static final ResourceKey<PlacedFeature> PULSE_TREE = of("pulse_tree");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {

        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(
                STONE_PILLAR,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(EdenConfiguredFeatures.STONE_PILLAR),
                        VegetationPlacements.treePlacement(
                                PlacementUtils.countExtra(1, 0.1F, 1)
                        )
                )
        );
        context.register(
                MOSS_LAYER,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(EdenConfiguredFeatures.MOSS_LAYER),
                        List.of(
                                InSquarePlacement.spread(),
                                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                                PlacementUtils.filteredByBlockSurvival(Blocks.MOSS_CARPET)
                        )
                )
        );
        context.register(
                AURITIS_TREE,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(EdenConfiguredFeatures.AURITIS_TREE),
                        List.of(
                                (net.minecraft.world.level.levelgen.placement.PlacementModifier) VegetationPlacements.treePlacement(
                                        PlacementUtils.countExtra(1, 0.4F, 1),
                                        EdenBlocks.AURITIS_SAPLING
                                )
                        )
                )
        );

        context.register(
                BALLOON_MUSHROOM_TREE,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(EdenConfiguredFeatures.BALLOON_MUSHROOM_TREE),
                        List.of(
                                (net.minecraft.world.level.levelgen.placement.PlacementModifier) VegetationPlacements.treePlacement(
                                        PlacementUtils.countExtra(8, 0.5F, 1),
                                        EdenBlocks.BALLOON_MUSHROOM_SMALL
                                )
                        )
                )
        );

        context.register(
                PULSE_TREE,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(EdenConfiguredFeatures.PULSE_TREE),
                        List.of(
                                (net.minecraft.world.level.levelgen.placement.PlacementModifier) VegetationPlacements.treePlacement(
                                        PlacementUtils.countExtra(14, 0.5F, 2),
                                        EdenBlocks.PULSE_TREE_SAPLING
                                )
                        )
                )
        );



                }
    public static ResourceKey<PlacedFeature> of(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, EdenRing.of(id));
    }
}
