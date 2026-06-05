package paulevs.edenring.registries;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import paulevs.edenring.particles.LeafParticle;
import paulevs.edenring.particles.OscillatingParticle;
import paulevs.edenring.particles.WindParticle;

@Environment(EnvType.CLIENT)
public class EdenParticleFactories {
	public static void register() {
		ParticleProviderRegistry.getInstance().register(EdenParticles.AURITIS_LEAF_PARTICLE, LeafParticle.ParticleFactory::new);
		ParticleProviderRegistry.getInstance().register(EdenParticles.WIND_PARTICLE, WindParticle.ParticleFactory::new);
		ParticleProviderRegistry.getInstance().register(EdenParticles.YOUNG_VOLVOX, OscillatingParticle.ParticleFactory::new);
	}
}
