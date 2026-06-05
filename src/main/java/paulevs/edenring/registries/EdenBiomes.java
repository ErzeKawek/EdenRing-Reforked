package paulevs.edenring.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import paulevs.edenring.EdenRing;
import paulevs.edenring.world.biomes.EdenRingBiome;
import paulevs.edenring.world.biomes.air.AirOceanBiome;
import paulevs.edenring.world.biomes.air.GraviliteDebrisFieldBiome;
import paulevs.edenring.world.biomes.air.SkyColonyBiome;
import paulevs.edenring.world.biomes.cave.EmptyCaveBiome;
import paulevs.edenring.world.biomes.cave.ErodedCaveBiome;
import paulevs.edenring.world.biomes.land.*;

public class EdenBiomes {

    // LAND //
    public static final ResourceKey<Biome> STONE_GARDEN = register("stone_garden");
    public static final ResourceKey<Biome> GOLDEN_FOREST = register("golden_forest");
    public static final ResourceKey<Biome> MYCOTIC_FOREST = register("mycotic_forest");
    public static final ResourceKey<Biome> OLD_MYCOTIC_FOREST = register("old_mycotic_forest")
    public static final ResourceKey<Biome> PULSE_FOREST = register("pulse_forest");
    public static final ResourceKey<Biome> BRAINSTORM = register("brainstorm");
    public static final ResourceKey<Biome> LAKESIDE_DESERT = register("lakeside_desert");
    public static final ResourceKey<Biome> WIND_VALLEY = register("wind_valley");
    public static final ResourceKey<Biome> AIR_OCEAN = register("air_ocean");
    public static final ResourceKey<Biome> GRAVILITE_DEBRIS_FIELD = register("gravilite_debris_field");
    public static final ResourceKey<Biome> SKY_COLONY = register("sky_colony");
    public static final ResourceKey<Biome> EMPTY_CAVE = register("empty_cave");
    public static final ResourceKey<Biome> ERODED_CAVE = register("eroded_cave");
    // public static final ResourceKey<Biome> COLD_CAVE = cKey("cold_cave");
    // public static final ResourceKey<Biome> CLOUD_FOREST = cKey("cloud_forest");

    private static ResourceKey<Biome> register(String name) {
        ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, EdenRing.of(name));
        return key;
    }

    public static void bootstrap(BootstrapContext<Biome> context) {
        context.register(STONE_GARDEN, StoneGardenBiome.create(context));
        context.register(GOLDEN_FOREST, GoldenForestBiome.create(context));
        context.register(MYCOTIC_FOREST, MycoticForestBiome.create(context));
        context.register(OLD_MYCOTIC_FOREST, OldMycoticForestBiome.create(context));
        context.register(PULSE_FOREST, PulseForestBiome.create(context));
        context.register(BRAINSTORM, BrainStormBiome.create(context));
        context.register(LAKESIDE_DESERT, LakesideDesertBiome.create(context));
        context.register(WIND_VALLEY, WindValleyBiome.create(context));
        context.register(AIR_OCEAN, AirOceanBiome.create(context));
        context.register(GRAVILITE_DEBRIS_FIELD, GraviliteDebrisFieldBiome.create(context));
        context.register(SKY_COLONY, SkyColonyBiome.create(context));
        context.register(EMPTY_CAVE, EmptyCaveBiome.create(context));
        context.register(ERODED_CAVE, ErodedCaveBiome.create(context));
    }
}
