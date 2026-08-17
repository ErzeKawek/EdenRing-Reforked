package paulevs.datagen.recipes;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import org.betterx.bclib.api.v3.datagen.BlockLootTableProvider;
import paulevs.edenring.EdenRing;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EdenRingBlockLootTableProvider extends BlockLootTableProvider {
    public EdenRingBlockLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(EdenRing.MOD_ID));
    }
}
