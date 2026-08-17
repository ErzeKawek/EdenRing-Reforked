package paulevs.edenring.interfaces;

import net.minecraft.util.RandomSource;
import org.betterx.wover.biome.api.data.BiomeData;

@FunctionalInterface
public interface BiomeCountProvider {
	int getCount(BiomeData biome, RandomSource random);
}
