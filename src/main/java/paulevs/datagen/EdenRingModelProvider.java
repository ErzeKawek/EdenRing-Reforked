package paulevs.datagen;

import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.world.level.block.Block;
import org.betterx.bclib.complexmaterials.ComplexMaterial;
import org.betterx.bclib.interfaces.RuntimeBlockModelProvider;
import org.betterx.wover.block.api.model.WoverBlockModelGenerators;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.datagen.api.provider.WoverModelProvider;
import paulevs.edenring.registries.EdenBlocks;

public class EdenRingModelProvider extends WoverModelProvider {
    public EdenRingModelProvider(ModCore modCore) {
        super(modCore);
    }

    @Override
    protected void bootstrapBlockStateModels(WoverBlockModelGenerators generator) {
        ModelOverides overrides = ModelOverides.create();
        ignore(EdenBlocks.AURITIS_LEAVES, overrides);
        ignore(EdenBlocks.ASTRALLIUM_ORE, overrides);
        ignore(EdenBlocks.LIMPHIUM, overrides);

        ignoreMaterialSlots(EdenBlocks.AURITIS_MATERIAL, overrides,
                "sign", "wall_sign", "hanging_sign", "wall_hanging_sign",
                "taburet", "chair", "bar_stool");
        ignoreMaterialSlots(EdenBlocks.BALLOON_MUSHROOM_MATERIAL, overrides,
                "log", "bark", "stripped_log", "stripped_bark",
                "sign", "wall_sign", "hanging_sign", "wall_hanging_sign",
                "taburet", "chair", "bar_stool");
        ignoreMaterialSlots(EdenBlocks.BRAIN_TREE_MATERIAL, overrides,
                "sign", "wall_sign", "hanging_sign", "wall_hanging_sign",
                "taburet", "chair", "bar_stool");
        ignoreMaterialSlots(EdenBlocks.PULSE_TREE_MATERIAL, overrides,
                "sign", "wall_sign", "hanging_sign", "wall_hanging_sign",
                "taburet", "chair", "bar_stool");
        EdenBlocks.getBlockRegistry().allBlocks().forEach(block -> {
            if (block instanceof RuntimeBlockModelProvider && !overrides.contain(block)) {
                overrides.ignore(block);
            }
        });

        addFromRegistry(generator, EdenBlocks.getBlockRegistry(), true, overrides);
    }

    @Override
    protected void bootstrapItemModels(ItemModelGenerators itemModelGenerator) {
    }

    private static void ignore(Block block, ModelOverides overrides) {
        if (block != null && !overrides.contain(block)) {
            overrides.ignore(block);
        }
    }

    private static void ignoreMaterialSlots(ComplexMaterial material, ModelOverides overrides, String... suffixes) {
        for (String suffix : suffixes) {
            Block block = material.getBlock(suffix);
            if (block != null && !overrides.contain(block)) {
                overrides.ignore(block);
            }
        }
    }
}
