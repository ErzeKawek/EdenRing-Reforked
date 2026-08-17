package paulevs.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.resources.ResourceLocation;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.datagen.api.PackBuilder;
import org.betterx.wover.datagen.api.WoverDataGenEntryPoint;
import paulevs.datagen.recipes.EdenRingBlockLootTableProvider;
import paulevs.datagen.recipes.EdenRingRecipeDataProvider;
import paulevs.datagen.worldgen.EdenRingBiomesDataProvider;
import paulevs.datagen.worldgen.EdenRingFeaturesDataProvider;
import paulevs.edenring.EdenRing;

public class EdenRingDatagen extends WoverDataGenEntryPoint {
    @Override
    protected ModCore modCore() {
        return EdenRing.C;
    }

    @Override
    protected void onInitializeProviders(PackBuilder globalPack) {
        EdenRingBiomesDataProvider.ensureStaticallyLoaded();

        globalPack.addMultiProvider(EdenRingBiomesDataProvider::new);
        globalPack.addMultiProvider(EdenRingFeaturesDataProvider::new);
        globalPack.addProvider(EdenRingBlockTagDataProvider::new);
        globalPack.addProvider(EdenRingItemTagDataProvider::new);
        globalPack.addProvider(EdenRingModelProvider::new);
        globalPack.callOnInitializeDatapack(this::onInitializeDatapack);
    }

    private void onInitializeDatapack(
            FabricDataGenerator fabricDataGenerator,
            FabricDataGenerator.Pack pack,
            ResourceLocation location
    ) {
        pack.addProvider(EdenRingRecipeDataProvider::new);
        pack.addProvider(EdenRingBlockLootTableProvider::new);
    }
}
