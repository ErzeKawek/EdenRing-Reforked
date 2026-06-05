package paulevs.edenring.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import paulevs.edenring.EdenRing;
import paulevs.edenring.world.biomes.EdenRingBiome;
import paulevs.edenring.world.biomes.land.StoneGardenBiome;

public class EdenBiomes {
	public static final BiomeAPI.BiomeType EDEN = new BiomeAPI.BiomeType("EDEN");
    public static final BiomeAPI.BiomeType EDEN_CAVE = new BiomeAPI.BiomeType("EDEN_CAVE", EDEN);
    public static final BiomeAPI.BiomeType EDEN_LAND = new BiomeAPI.BiomeType("EDEN_LAND", EDEN);
    public static final BiomeAPI.BiomeType EDEN_VOID = new BiomeAPI.BiomeType("EDEN_VOID", EDEN);

    // LAND //
    public static final ResourceKey<Biome> STONE_GARDEN = register("stone_garden");
    public static final ResourceKey<Biome> GOLDEN_FOREST = register("golden_forest");
    public static final ResourceKey<Biome> MYCOTIC_FOREST = register("mycotic_forest");
    public static final ResourceKey<Biome> PULSE_FOREST = register("pulse_forest");
    public static final ResourceKey<Biome> BRAINSTORM = register("brainstorm");
    public static final ResourceKey<Biome> LAKESIDE_DESERT = register("lakeside_desert");
    public static final ResourceKey<Biome> WIND_VALLEY = register("wind_valley");
    // public static final ResourceKey<Biome> CLOUD_FOREST = cKey("cloud_forest");

    // VOID //
    public static final ResourceKey<Biome> AIR_OCEAN = register("air_ocean");
    public static final ResourceKey<Biome> GRAVILITE_DEBRIS_FIELD = register("gravilite_debris_field");
    public static final ResourceKey<Biome> SKY_COLONY = register("sky_colony");

    // CAVES
    public static final ResourceKey<Biome> EMPTY_CAVE = register("empty_cave");
    public static final ResourceKey<Biome> ERODED_CAVE = register("eroded_cave");
    // public static final ResourceKey<Biome> COLD_CAVE = cKey("cold_cave");

    // SUBBIOMES //
    public static final ResourceKey<Biome> OLD_MYCOTIC_FOREST = register("old_mycotic_forest");

    private static ResourceKey<Biome> register(String name) {
        ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, EdenRing.of(name));
        return key;
    }

    public static void bootstrap(BootstrapContext<Biome> context) {
        context.register(STONE_GARDEN, StoneGardenBiome.create(context));

    }
}
