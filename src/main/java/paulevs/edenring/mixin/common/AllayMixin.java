package paulevs.edenring.mixin.common;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.allay.Allay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.edenring.EdenRing;

@Mixin(Allay.class)
public abstract class AllayMixin extends MobMixin {

    @Inject(method="tick()V", at=@At("TAIL"))
    public void considerConvertingToVex(CallbackInfo ci) {
        if (this.level().dimension() == EdenRing.EDEN_RING_KEY) {
            float timeOfDay = this.level().getGameTime();
            if ((timeOfDay > 0.25) && (timeOfDay < 0.75)){
                if (!this.level().isClientSide()) {
                    this.convertTo(EntityType.VEX, true).tick();
                }
            }
        }
    }

}
