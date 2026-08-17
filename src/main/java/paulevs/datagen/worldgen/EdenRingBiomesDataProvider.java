package paulevs.datagen.worldgen;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.betterx.wover.biome.api.builder.BiomeBootstrapContext;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.datagen.api.provider.multi.WoverBiomeProvider;
import paulevs.edenring.registries.EdenBiomes;
import paulevs.edenring.world.biomes.EdenBiomeBuilder;
import paulevs.edenring.world.biomes.EdenBiomeKey;
import paulevs.edenring.world.biomes.EdenRingBiome;
import paulevs.edenring.world.biomes.air.AirOceanBiome;
import paulevs.edenring.world.biomes.air.GraviliteDebrisFieldBiome;
import paulevs.edenring.world.biomes.air.SkyColonyBiome;
import paulevs.edenring.world.biomes.cave.EmptyCaveBiome;
import paulevs.edenring.world.biomes.cave.ErodedCaveBiome;
import paulevs.edenring.world.biomes.land.BrainStormBiome;
import paulevs.edenring.world.biomes.land.GoldenForestBiome;
import paulevs.edenring.world.biomes.land.LakesideDesertBiome;
import paulevs.edenring.world.biomes.land.MycoticForestBiome;
import paulevs.edenring.world.biomes.land.OldMycoticForestBiome;
import paulevs.edenring.world.biomes.land.PulseForestBiome;
import paulevs.edenring.world.biomes.land.StoneGardenBiome;
import paulevs.edenring.world.biomes.land.WindValleyBiome;

public class EdenRingBiomesDataProvider extends WoverBiomeProvider {
    public record BiomeInfo(EdenRingBiome.Config config, TagKey<Biome> tag) {
    }

    public static final Map<EdenBiomeKey<?, ?>, BiomeInfo> BIOMES = new HashMap<>();

    // LAND //
    protected static final EdenBiomeKey<?, ?> STONE_GARDEN = registerLand(new StoneGardenBiome());
    protected static final EdenBiomeKey<?, ?> GOLDEN_FOREST = registerLand(new GoldenForestBiome());
    protected static final EdenBiomeKey<?, ?> MYCOTIC_FOREST = registerLand(new MycoticForestBiome());
    protected static final EdenBiomeKey<?, ?> PULSE_FOREST = registerLand(new PulseForestBiome());
    protected static final EdenBiomeKey<?, ?> BRAINSTORM = registerLand(new BrainStormBiome());
    protected static final EdenBiomeKey<?, ?> LAKESIDE_DESERT = registerLand(new LakesideDesertBiome());
    protected static final EdenBiomeKey<?, ?> WIND_VALLEY = registerLand(new WindValleyBiome());

    // VOID //
    protected static final EdenBiomeKey<?, ?> AIR_OCEAN = registerVoid(new AirOceanBiome());
    protected static final EdenBiomeKey<?, ?> GRAVILITE_DEBRIS_FIELD = registerVoid(new GraviliteDebrisFieldBiome());
    protected static final EdenBiomeKey<?, ?> SKY_COLONY = registerVoid(new SkyColonyBiome());

    // CAVES //
    protected static final EdenBiomeKey<?, ?> EMPTY_CAVE = registerCave(new EmptyCaveBiome());
    protected static final EdenBiomeKey<?, ?> ERODED_CAVE = registerCave(new ErodedCaveBiome());

    // SUBBIOMES //
    protected static final EdenBiomeKey<?, ?> OLD_MYCOTIC_FOREST = registerSubBiome(new OldMycoticForestBiome(), MYCOTIC_FOREST);


    public EdenRingBiomesDataProvider(ModCore modCore) {
        super(modCore);
    }

    protected void bootstrap(BiomeBootstrapContext context) {
        for (Map.Entry<EdenBiomeKey<?, ?>, BiomeInfo> e : BIOMES.entrySet()) {
            final EdenBiomeBuilder builder = e.getKey().bootstrap(context, e.getValue().config, e.getValue().tag);
            builder.register();
        }
    }

    public static void ensureStaticallyLoaded() {
    }

    private static EdenBiomeKey<?, ?> registerBiome(
            EdenBiomeKey<?, ?> key,
            EdenRingBiome.Config config,
            TagKey<Biome> tag
    ) {
        BIOMES.put(key, new BiomeInfo(config, tag));
        return key;
    }

    private static EdenBiomeKey<?, ?> registerLand(EdenRingBiome.Config biomeConfig) {
        return registerBiome(EdenBiomeBuilder.createKey(biomeConfig.ID.getPath()), biomeConfig, EdenBiomes.EDEN_LAND);
    }

    private static EdenBiomeKey<?, ?> registerVoid(EdenRingBiome.Config biomeConfig) {
        return registerBiome(EdenBiomeBuilder.createKey(biomeConfig.ID.getPath()), biomeConfig, EdenBiomes.EDEN_VOID);
    }

    private static EdenBiomeKey<?, ?> registerCave(EdenRingBiome.Config biomeConfig) {
        return registerBiome(EdenBiomeBuilder.createKey(biomeConfig.ID.getPath()), biomeConfig, EdenBiomes.EDEN_CAVE);
    }

    private static <PC extends EdenRingBiome.Config> EdenBiomeKey<?, ?> registerSubBiome(
            EdenRingBiome.Config biomeConfig,
            EdenBiomeKey<PC, ?> parent
    ) {
        return registerBiome(EdenBiomeBuilder.createKey(biomeConfig.ID.getPath(), parent), biomeConfig, EdenBiomes.EDEN_LAND);
    }
}
