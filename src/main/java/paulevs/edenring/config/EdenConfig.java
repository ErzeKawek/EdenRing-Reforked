package paulevs.edenring.config;

import net.fabricmc.loader.api.FabricLoader;
import paulevs.edenring.EdenRing;

import java.nio.file.Path;

public class EdenConfig {

    /**
     * Path to the config file
     */
    private static final Path config_path = FabricLoader.getInstance().getConfigDir()
            .resolve(EdenRing.MOD_ID + ".yaml").toAbsolutePath();

    /**
     * Whether to generate iron and copper ores in Biomes
     */
    protected boolean generateOres;

    /**
     * Decide whether Eden Plants can grow in the overworld or not
     */
    protected boolean edenPlantsOnlyGrowInEdenRing;

    /**
     * Whether to use a custom loot table when you are trying to fish in Kalmik Marsh
     */
    protected boolean customEdenFishing;

    /**
     * Spawn in Eden
     */
    protected boolean edenSpawn;

    public boolean generateOres() {
        return this.generateOres;
    }

    public boolean edenPlantsOnlyGrowInEdenRing() {
        return this.edenPlantsOnlyGrowInEdenRing;
    }

    public boolean customEdenFishing() {
        return this.customEdenFishing;
    }

    public boolean enableEdenSpawn() {
        return this.edenSpawn;
    }

    /**
     * Default config values
     */
    private static final boolean DEFAULT_GENERATE_ORES = false;
    private static final boolean DEFAULT_EDEN_PLANTS_ONLY_GROW_IN_THE_END = true;
    private static final boolean DEFAULT_CUSTOM_EDEN_FISHING = true;
    private static final boolean DEFAULT_END_SPAWN = false;

}
