package net.marios271.cat_vision.mixin;

import net.marios271.cat_vision.handler.VisionHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

	//? >= 26.2 {
	@Inject(method = "nightVisionScale", at = @At("HEAD"), cancellable = true)
	//?} else {
	/*@Inject(method = "getNightVisionScale", at = @At("HEAD"), cancellable = true)
	*///?}
	private static void cat_vision$nightVisionCurve(LivingEntity entity, float partialTick, CallbackInfoReturnable<Float> cir) {
		if (VisionHandler.curveActive() && entity == Minecraft.getInstance().player)
			cir.setReturnValue(VisionHandler.strength());
	}
}
