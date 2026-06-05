package paulevs.edenring.registries;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import paulevs.edenring.EdenRing;
import paulevs.edenring.blocks.complex.Shelfs;
import paulevs.edenring.blocks.complex.Signs;
import paulevs.edenring.blocks.entities.EdenPortalBlockEntity;

public class EdenBlockEntities {
	public static final BlockEntityType<Signs.EdenSignBlockEntity> SIGN = Registry.register(
			BuiltInRegistries.BLOCK_ENTITY_TYPE,
			EdenRing.of("sign"),
			FabricBlockEntityTypeBuilder.create(
					Signs.EdenSignBlockEntity::new,
					EdenBlocks.AURITIS_MATERIAL.sign,
					EdenBlocks.AURITIS_MATERIAL.wallSign
			).build(null));

	public static final BlockEntityType<Signs.EdenHangingSignBlockEntity> HANGING_SIGN = Registry.register(
			BuiltInRegistries.BLOCK_ENTITY_TYPE,
			EdenRing.of("hanging_sign"),
			FabricBlockEntityTypeBuilder.create(
					Signs.EdenHangingSignBlockEntity::new,
					EdenBlocks.AURITIS_MATERIAL.hangingSign,
					EdenBlocks.AURITIS_MATERIAL.wallHangingSign
			).build(null));

	public static final BlockEntityType<Shelfs.Entity> SHELF = Registry.register(
			BuiltInRegistries.BLOCK_ENTITY_TYPE,
			EdenRing.of("shelf"),
			FabricBlockEntityTypeBuilder.create(
					Shelfs.Entity::new,
					EdenBlocks.AURITIS_MATERIAL.shelf
			).build(null));


	public final static BlockEntityType<EdenPortalBlockEntity> EDEN_PORTAL = register("eden_portal", FabricBlockEntityTypeBuilder.create(EdenPortalBlockEntity::new, EdenBlocks.PORTAL_CENTER));
	
	private static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder<T> builder) {
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, EdenRing.of(id), builder.build(null));
	}
	
	public static void init() {}
}