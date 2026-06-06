package paulevs.edenring.registries;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import paulevs.edenring.EdenRing;
import paulevs.edenring.blocks.complex.BrainTreeWoodBlock;
import paulevs.edenring.blocks.complex.EdenWoodBlocks.*;

public class EdenTags {

    public static final TagKey<Block> PLANTABLE_BLOCKS = TagKey.create(
            Registries.BLOCK,
            EdenRing.of("plantable_blocks")
    );

    public static final Map<String, TagKey<Item>> LOGS = new HashMap<>();
    public static final Map<String, TagKey<Item>> STRIPPED_LOG = new HashMap<>();

    public static void init() {

        for (EdenWoodSet wood : Arrays.asList(
                EdenBlocks.AURITIS_MATERIAL,
                EdenBlocks.BALLOON_MUSHROOM_MATERIAL,
                EdenBlocks.PULSE_TREE_MATERIAL
        )) {
            LOGS.put(
                    wood.baseName,
                    TagKey.create(Registries.ITEM, EdenRing.of(wood.baseName + "_logs"))
            );
            STRIPPED_LOG.put(
                    wood.baseName,
                    TagKey.create(Registries.ITEM, EdenRing.of("stripped_" + wood.baseName + "_logs"))
            );
        }
        for (BrainTreeWoodBlock.BrainTreeWoodSet wood : Collections.singletonList(
                EdenBlocks.BRAIN_TREE_MATERIAL
        )) {
            LOGS.put(
                    wood.baseName,
                    TagKey.create(Registries.ITEM, EdenRing.of(wood.baseName + "_logs"))
            );
            STRIPPED_LOG.put(
                    wood.baseName,
                    TagKey.create(Registries.ITEM, EdenRing.of("stripped_" + wood.baseName + "_logs"))
            );
        }
    }
}
