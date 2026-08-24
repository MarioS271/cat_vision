package net.marios271.cat_vision.handler;

import net.marios271.cat_vision.CatVision;
import net.marios271.cat_vision.config.ConfigData;
import net.marios271.cat_vision.config.VisionSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class VisionHandler {

	private static volatile boolean curveActive = false;
	private static volatile float strength = 0.0F;

	public static boolean curveActive() {
		return curveActive;
	}

	public static float strength() {
		return strength;
	}

	public static void tick(Minecraft client) {
		ConfigData config = CatVision.CONFIG;

		if (!config.nv_curve || !config.has_nv || client.player == null || client.level == null) {
			curveActive = false;
			strength = 0.0F;
			return;
		}

		if (!client.player.hasEffect(MobEffects.NIGHT_VISION))
			client.player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, Integer.MAX_VALUE));

		int light = client.level.getMaxLocalRawBrightness(client.player.blockPosition());
		double target = config.strengthFor(light);
		double speed = VisionSettings.clamp(config.nv_speed, 0.01, 1.0);

		strength += (float) ((target - strength) * speed);
		if (Math.abs(target - strength) < 0.001)
			strength = (float) target;
		curveActive = true;
	}
}
