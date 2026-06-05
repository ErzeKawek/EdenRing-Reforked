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

import java.util.List;

public class EdenPlacedFeatures {

    public static final ResourceKey<PlacedFeature> STONE_PILLAR = of("stone_pillar");
    public static final ResourceKey<PlacedFeature> MOSS_LAYER = of("stone_pillar");

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
                }
    public static ResourceKey<PlacedFeature> of(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, EdenRing.of(id));
    }
}
