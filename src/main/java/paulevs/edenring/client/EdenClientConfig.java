package paulevs.edenring.client;

import org.betterx.bclib.config.Config;
import org.betterx.bclib.config.ConfigKey;
import paulevs.edenring.EdenRing;

public class EdenClientConfig extends Config {
	private static final ConfigKey RENDER_SKY = new ConfigKey("renderSky", "rendering");
	private static final ConfigKey RENDER_BUFFER = new ConfigKey("renderInBuffer", "rendering");

	public EdenClientConfig() {
		super(EdenRing.MOD_ID, "client");
	}

	@Override
	protected void registerEntries() {
		getBoolean(RENDER_SKY, true);
		getBoolean(RENDER_BUFFER, false);
	}

	public boolean renderSky() {
		return getBoolean(RENDER_SKY, true);
	}

	public boolean renderInBuffer() {
		return getBoolean(RENDER_BUFFER, false);
	}
}
