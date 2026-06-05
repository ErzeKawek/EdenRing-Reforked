package paulevs.edenring.world.biomes.land;

import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.GenerationStep;
import paulevs.edenring.registries.EdenBiomes;
import paulevs.edenring.registries.EdenFeatures;
import paulevs.edenring.registries.EdenSounds;
import paulevs.edenring.world.biomes.BiomesCommonMethods;
import paulevs.edenring.world.biomes.EdenRingBiome;

public class BrainStormBiome extends EdenRingBiome.Config {
    public BrainStormBiome() {
        super(EdenBiomes.BRAINSTORM.location());
    }

    @Override
    protected void addCustomBuildData(BCLBiomeBuilder builder) {
        BiomesCommonMethods.addDefaultLandFeatures(builder);
        builder
                .fogDensity(1F)
                .skyColor(113, 178, 255)
                .fogColor(180, 180, 180)
                .plantsColor(200, 200, 200)
                .loop(EdenSounds.AMBIENCE_BRAINSTORM)
                .music(EdenSounds.MUSIC_COMMON)
                .feature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_FOREST)
                .feature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_TALL_GRASS)
                .feature(EdenFeatures.TALL_COPPER_GRASS)
                .feature(EdenFeatures.TALL_IRON_GRASS)
                .feature(EdenFeatures.TALL_GOLD_GRASS)
                .feature(EdenFeatures.COPPER_GRASS)
                .feature(EdenFeatures.IRON_GRASS)
                .feature(EdenFeatures.GOLD_GRASS)
                .feature(EdenFeatures.BRAIN_TREE)
                .feature(EdenFeatures.LAYERED_IRON)
                .feature(EdenFeatures.LAYERED_COPPER)
                .feature(EdenFeatures.LAYERED_GOLD);
    }


}
