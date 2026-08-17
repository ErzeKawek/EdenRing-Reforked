package paulevs.edenring.blocks.complex;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import paulevs.edenring.EdenRing;

public class BrainTreeComplexMaterial extends EdenWoodenComplexMaterial {
	public BrainTreeComplexMaterial(String baseName) {
		super(EdenRing.C, baseName, "eden", MapColor.COLOR_LIGHT_GRAY, MapColor.COLOR_LIGHT_GRAY);
	}
	
	@Override
	protected BlockBehaviour.Properties getBlockSettings() {
		return BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.NETHERITE_BLOCK).mapColor(planksColor);
	}
}
