package paulevs.edenring.world.biomes.land;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.attribute.AmbientSounds;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder;

import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.GenerationStep;
import paulevs.edenring.registries.EdenBiomes;
import paulevs.edenring.registries.EdenEntities;
import paulevs.edenring.registries.EdenFeatures;
import paulevs.edenring.registries.EdenSounds;
import paulevs.edenring.world.biomes.BiomesCommonMethods;
import paulevs.edenring.world.biomes.EdenRingBiome;
import paulevs.edenring.world.features.EdenPlacedFeatures;

import java.util.List;
import java.util.Optional;

public class StoneGardenBiome {

    public static Biome create(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> features = context.lookup(
                Registries.PLACED_FEATURE
        );
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(
                Registries.CONFIGURED_CARVER
        );

        MobSpawnSettings spawns = new MobSpawnSettings.Builder()
                .addSpawn(
                        MobCategory.AMBIENT, //dirty hack for issues with the creature group
                        1,
                        new MobSpawnSettings.SpawnerData(EdenEntities.DISKWING.mob, 1, 2)
                )
                .build();

        var genSettingsBuilder = new BiomeGenerationSettings.Builder(features, carvers)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EdenPlacedFeatures.STONE_PILLAR)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_FOREST)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_TALL_GRASS)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.FOREST_ROCK);


        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.5F)
                .downfall(0.5F)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .foliageColorOverride(0xA2BE71)
                        .grassColorOverride(0xA2BE71)
                        .build()
                )
                .mobSpawnSettings(spawns)
                .generationSettings(genSettingsBuilder.build())
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 0x000000)
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0x050533)
                .setAttribute(
                        EnvironmentAttributes.BACKGROUND_MUSIC,
                        new BackgroundMusic(EdenSounds.MUSIC_COMMON)
                ).build();
    }
}



    /*    @Override
    protected void addCustomBuildData(BCLBiomeBuilder builder) {
        BiomesCommonMethods.addDefaultLandFeatures(builder);
        builder
                .fogDensity(1F)
                .plantsColor(162, 190, 113)
                .music(EdenSounds.MUSIC_COMMON)
                .feature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_FOREST)
                .feature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_TALL_GRASS)
                .feature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.FOREST_ROCK)
                .feature(EdenFeatures.COBBLE_FLOOR)
                .feature(EdenFeatures.MOSS_FLOOR)
                .feature(EdenFeatures.MOSS_LAYER)
                .feature(EdenFeatures.EDEN_MOSS_LAYER)
                .feature(EdenFeatures.STONE_PILLAR)
                .feature(EdenFeatures.LIMPHIUM)
                .feature(EdenFeatures.VIOLUM_RARE)
                .feature(EdenFeatures.EDEN_VINE)
                .feature(EdenFeatures.ROOTS)
                .feature(EdenFeatures.PARIGNUM);
    }
}
