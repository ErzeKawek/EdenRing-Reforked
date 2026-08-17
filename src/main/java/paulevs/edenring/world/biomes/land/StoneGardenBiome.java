package paulevs.edenring.world.biomes.land;

import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.GenerationStep;
import paulevs.edenring.registries.EdenBiomes;
import paulevs.edenring.registries.EdenFeatures;
import paulevs.edenring.registries.EdenSounds;
import paulevs.edenring.world.biomes.BiomesCommonMethods;
import paulevs.edenring.world.biomes.EdenBiomeBuilder;
import paulevs.edenring.world.biomes.EdenRingBiome;

public class StoneGardenBiome extends EdenRingBiome.Config {
    public StoneGardenBiome() {
        super(EdenBiomes.STONE_GARDEN.location());
    }

    @Override
    protected void addCustomBuildData(EdenBiomeBuilder builder) {
        BiomesCommonMethods.addDefaultLandFeatures(builder);
        builder
                .fogDensity(1F)
                .plantsColor(162, 190, 113)
                .music(EdenSounds.MUSIC_COMMON)
                .feature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_FOREST)
                .feature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_TALL_GRASS)
                .feature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.FOREST_ROCK)
                .feature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BAMBOO_LIGHT)
                .feature(EdenFeatures.COBBLE_FLOOR)
                .feature(EdenFeatures.MOSS_FLOOR)
                .feature(EdenFeatures.MOSS_LAYER)
                .feature(EdenFeatures.EDEN_MOSS_LAYER)
                .feature(EdenFeatures.STONE_PILLAR)
                .feature(EdenFeatures.LIMPHIUM)
                .feature(EdenFeatures.VIOLUM_RARE)
                .feature(EdenFeatures.EDEN_VINE)
                .feature(EdenFeatures.ROOTS)
                .feature(EdenFeatures.PARIGNUM)
                .feature(EdenFeatures.ALAESPES);
    }
}
