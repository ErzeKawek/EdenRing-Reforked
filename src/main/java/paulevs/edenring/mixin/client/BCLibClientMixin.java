package paulevs.edenring.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier;
import net.minecraft.client.resources.model.UnbakedModel;
import org.betterx.bclib.client.BCLibClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(BCLibClient.class)
public abstract class BCLibClientMixin {
	@Inject(method = "modifyModelOnLoad", at = @At("HEAD"), cancellable = true)
	private static void edenring$keepPerStateModels(
			UnbakedModel model,
			ModelModifier.OnLoad.Context ctx,
			CallbackInfoReturnable<UnbakedModel> cir
	) {
		cir.setReturnValue(model);
	}
}
