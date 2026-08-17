package paulevs.datagen.recipes;

import org.betterx.bclib.api.v3.datagen.RecipeDataProvider;
import paulevs.edenring.EdenRing;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EdenRingRecipeDataProvider extends RecipeDataProvider {
    public EdenRingRecipeDataProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(List.of(EdenRing.MOD_ID), output, registries);
    }
}
