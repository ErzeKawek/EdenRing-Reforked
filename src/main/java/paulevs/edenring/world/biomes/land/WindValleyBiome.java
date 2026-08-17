package paulevs.edenring.world.biomes.land;

import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.GenerationStep;
import paulevs.edenring.registries.EdenBiomes;
import paulevs.edenring.registries.EdenFeatures;
import paulevs.edenring.registries.EdenParticles;
import paulevs.edenring.registries.EdenSounds;
import paulevs.edenring.world.biomes.BiomesCommonMethods;
import paulevs.edenring.world.biomes.EdenBiomeBuilder;
import paulevs.edenring.world.biomes.EdenRingBiome;

public class WindValleyBiome extends EdenRingBiome.Config {
    public WindValleyBiome() {
        super(EdenBiomes.WIND_VALLEY.location());
    }

    @Override
    protected void addCustomBuildData(EdenBiomeBuilder builder) {
        BiomesCommonMethods.addDefaultLandFeatures(builder);
        builder
                .fogDensity(1F)
                .skyColor(113, 178, 255)
                .fogColor(183, 212, 255)
                .grassColor(225, 84, 72)
                .plantsColor(230, 63, 50)
                .loop(EdenSounds.AMBIENCE_WIND_VALLEY)
                .music(EdenSounds.MUSIC_COMMON)
                .particles(EdenParticles.WIND_PARTICLE, 0.001F)
                .feature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_FOREST)
                .feature(EdenFeatures.VIOLUM_RARE)
                .feature(EdenFeatures.LONLIX)
                .feature(EdenFeatures.PARIGNUM);
    }
}
