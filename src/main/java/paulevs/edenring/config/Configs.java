package paulevs.edenring.config;

import org.betterx.bclib.BCLib;
import org.betterx.bclib.config.EntryConfig;
import org.betterx.bclib.config.IdConfig;

import paulevs.edenring.EdenRing;
import paulevs.edenring.client.EdenClientConfig;

public class Configs {
    public static final IdConfig BIOMES = new EntryConfig(EdenRing.MOD_ID, "biomes");
    public static final EdenPathConfig GENERATOR = new EdenPathConfig(EdenRing.MOD_ID, "generator");
    public static final EdenPathConfig ITEMS = new EdenPathConfig(EdenRing.MOD_ID, "items");
    public static final EdenPathConfig RECIPES = new EdenPathConfig(EdenRing.MOD_ID, "recipes");

    public static final EdenClientConfig CLIENT_CONFIG = new EdenClientConfig();

    public static void saveConfigs() {
        BIOMES.saveChanges();
        GENERATOR.saveChanges();
        ITEMS.saveChanges();
        RECIPES.saveChanges();

        if (BCLib.isClient()) {
            CLIENT_CONFIG.saveChanges();
        }
    }
}
