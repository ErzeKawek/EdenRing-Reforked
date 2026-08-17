package paulevs.edenring.config;

import org.betterx.bclib.config.Config;
import org.betterx.bclib.config.ConfigKey;

/**
 * Replacement for the removed BCLib {@code PathConfig} (path/section-style config).
 * <p>
 * BCLib 21.x kept only {@link org.betterx.bclib.config.IdConfig} and
 * {@link org.betterx.bclib.config.EntryConfig} (both {@code ResourceLocation}-keyed). This
 * class re-exposes the old {@code getFloat(path, entry, default)}-style API on top of the
 * {@link Config} base class, splitting dotted section paths like {@code terrain.layers.bigIslands}.
 */
public class EdenPathConfig extends Config {
    public EdenPathConfig(String modID, String group) {
        super(modID, group);
    }

    public EdenPathConfig(String modID, String group, boolean ignored, boolean ignored2) {
        this(modID, group);
    }

    @Override
    protected void registerEntries() {
    }

    public float getFloat(String path, String entry, float def) {
        return getFloat(key(path, entry), def);
    }

    public int getInt(String path, String entry, int def) {
        return getInt(key(path, entry), def);
    }

    public boolean getBoolean(String path, String entry, boolean def) {
        return getBoolean(key(path, entry), def);
    }

    /**
     * Reads a root-level boolean entry (no section path).
     *
     * @param entry the entry name (e.g. an entity id).
     * @param def   the default value.
     */
    public boolean getBooleanRoot(String entry, boolean def) {
        return getBoolean(key("", entry), def);
    }

    private static ConfigKey key(String path, String entry) {
        if (path == null || path.isEmpty()) {
            return new ConfigKey(entry);
        }
        return new ConfigKey(entry, path.split("\\."));
    }
}
