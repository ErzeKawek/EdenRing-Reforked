package paulevs.edenring.blocks.complex;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;
import org.betterx.bclib.complexmaterials.WoodenComplexMaterial;
import org.betterx.bclib.complexmaterials.entry.SlotMap;
import org.betterx.bclib.complexmaterials.set.wood.WoodSlots;
import org.betterx.wover.core.api.ModCore;
import paulevs.edenring.registries.EdenBlocks;
import paulevs.edenring.registries.EdenItems;

public class EdenWoodenComplexMaterial extends WoodenComplexMaterial {
    private Block bark;
    private Block log;

    public EdenWoodenComplexMaterial(ModCore modCore, String baseName, String receipGroupPrefix, MapColor woodColor, MapColor planksColor) {
        super(modCore, baseName, receipGroupPrefix, woodColor, planksColor);
        setFurnitureCloth(Blocks.RED_WOOL);
    }

    public EdenWoodenComplexMaterial init() {
        return (EdenWoodenComplexMaterial) super.init(
                EdenBlocks.getBlockRegistry(),
                EdenItems.getItemRegistry()
        );
    }

    @Override
    protected SlotMap<WoodenComplexMaterial> createMaterialSlots() {
        return super.createMaterialSlots()
                .add(WoodSlots.HANGING_SIGN)
                .add(WoodSlots.TABURET)
                .add(WoodSlots.BAR_STOOL)
                .add(WoodSlots.CHAIR)
                ;
    }
}
