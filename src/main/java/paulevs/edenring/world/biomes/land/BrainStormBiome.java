package paulevs.edenring.world.biomes.land;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import paulevs.edenring.registries.EdenBiomes;
import paulevs.edenring.registries.EdenEntities;
import paulevs.edenring.registries.EdenFeatures;
import paulevs.edenring.registries.EdenSounds;
import paulevs.edenring.world.biomes.BiomesCommonMethods;
import paulevs.edenring.world.biomes.EdenRingBiome;

public class BrainStormBiome extends EdenRingBiome.Config {

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
