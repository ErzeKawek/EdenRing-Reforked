package paulevs.datagen.recipes;

import org.betterx.bclib.api.v3.datagen.RecipeDataProvider;
import paulevs.edenring.EdenRing;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import java.util.List;

public class EdenRingRecipeDataProvider extends RecipeDataProvider {
    public EdenRingRecipeDataProvider(FabricDataOutput output) {
        super(List.of(EdenRing.MOD_ID), output);
    }

    public static void buildRecipes() {
        EdenRecipes.register();
    }
}
