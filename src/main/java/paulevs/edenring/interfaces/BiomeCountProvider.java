package paulevs.edenring.interfaces;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;

@FunctionalInterface
public interface BiomeCountProvider {
	int getCount(Biome biome, RandomSource random);
}
