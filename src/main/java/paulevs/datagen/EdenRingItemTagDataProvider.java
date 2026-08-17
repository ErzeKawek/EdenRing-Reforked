package paulevs.datagen;

import java.util.List;

import net.minecraft.world.item.Item;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.datagen.api.WoverTagProvider;
import org.betterx.wover.tag.api.event.context.ItemTagBootstrapContext;
import paulevs.edenring.EdenRing;

public class EdenRingItemTagDataProvider extends WoverTagProvider.ForItems {
    public EdenRingItemTagDataProvider(ModCore modCore) {
        super(modCore, List.of(EdenRing.MOD_ID));
    }

    @Override
    public void prepareTags(ItemTagBootstrapContext context) {
    }
}
