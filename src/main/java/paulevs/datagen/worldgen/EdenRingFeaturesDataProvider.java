package paulevs.datagen.worldgen;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.datagen.api.provider.multi.WoverFeatureProvider;
import paulevs.edenring.registries.EdenFeatures;

public class EdenRingFeaturesDataProvider extends WoverFeatureProvider {
	public EdenRingFeaturesDataProvider(ModCore modCore) {
		super(modCore);
	}

	@Override
	protected void bootstrapConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		EdenFeatures.bootstrapConfigured(context);
	}

	@Override
	protected void bootstrapPlaced(BootstrapContext<PlacedFeature> context) {
		EdenFeatures.bootstrapPlaced(context);
	}
}
