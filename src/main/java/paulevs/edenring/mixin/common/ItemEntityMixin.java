package paulevs.edenring.mixin.common;

import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import paulevs.edenring.EdenRing;
import paulevs.edenring.world.GravityController;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
	@Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;applyGravity()V"))
	private void eden_changeGravity(ItemEntity entity) {
		double gravity = entity.getGravity();
		if (entity.level().dimension() == EdenRing.EDEN_RING_KEY) {
			gravity *= GravityController.getGravityMultiplier(entity.getY());
		}
		double gravilite = GravityController.getGraviliteMultiplier(entity);
		if (gravilite == 1) {
			gravilite = GravityController.getCompressorMultiplier(entity);
		}
		gravity *= gravilite;
		if (gravity != 0) {
			entity.setDeltaMovement(entity.getDeltaMovement().add(0, -gravity, 0));
		}
	}
}
