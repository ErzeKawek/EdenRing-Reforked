package paulevs.edenring.registries;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import paulevs.edenring.EdenRing;
import paulevs.edenring.blocks.entities.EdenPortalBlockEntity;
import paulevs.edenring.blocks.bases.EdenSignBlock.*;

public class EdenBlockEntities {
	public final static BlockEntityType<EdenPortalBlockEntity> EDEN_PORTAL = register("eden_portal", FabricBlockEntityTypeBuilder.create(EdenPortalBlockEntity::new, EdenBlocks.PORTAL_CENTER));
	
	private static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder<T> builder) {
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, EdenRing.makeID(id), builder.build(null));
	}

	public static final BlockEntityType<EdenSignBlockEntity> SIGN = Registry.register(
			BuiltInRegistries.BLOCK_ENTITY_TYPE,
			EdenRing.of("sign"),
			FabricBlockEntityTypeBuilder.create(
					EdenSignBlockEntity::new,
					LighterEndBlocks.TENANEA.sign,
					LighterEndBlocks.TENANEA.wallSign
			).build(null));

	public static final BlockEntityType<EdenHangingSignBlockEntity> HANGING_SIGN = Registry.register(
			BuiltInRegistries.BLOCK_ENTITY_TYPE,
			EdenRing.of("hanging_sign"),
			FabricBlockEntityTypeBuilder.create(
					EdenHangingSignBlockEntity::new,
					LighterEndBlocks.TENANEA.hangingSign,
					LighterEndBlocks.TENANEA.wallHangingSign
			).build(null));


	public static void init() {}
}