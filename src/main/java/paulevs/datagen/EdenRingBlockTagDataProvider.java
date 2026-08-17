package paulevs.datagen;

import java.util.List;
import java.util.Set;

import net.minecraft.world.level.block.Block;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.datagen.api.WoverTagProvider;
import org.betterx.wover.tag.api.event.context.TagBootstrapContext;
import org.betterx.wover.tag.api.predefined.CommonBlockTags;
import paulevs.edenring.EdenRing;

public class EdenRingBlockTagDataProvider extends WoverTagProvider.ForBlocks {
    public EdenRingBlockTagDataProvider(ModCore modCore) {
        super(modCore, List.of(EdenRing.MOD_ID), Set.of(CommonBlockTags.NETHER_MYCELIUM));
    }

    @Override
    public void prepareTags(TagBootstrapContext<Block> context) {
    }
}
