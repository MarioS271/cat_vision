package net.marios271.cat_vision.handler;

import net.marios271.cat_vision.CatVision;
import net.marios271.cat_vision.config.ConfigData;
import net.marios271.cat_vision.config.VisionSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class VisionHandler {

	private static String lastDimension = null;

	private static volatile boolean curveActive = false;
	private static volatile float strength = 0.0F;

	private static final int OUR_EFFECT = 20 * 60 * 60;

	public static boolean curveActive() {
		return curveActive;
	}

	public static float strength() {
		return strength;
	}

	public static String dimensionOf(Minecraft client) {
		return client.level == null ? null : client.level.dimension().identifier().toString();
	}

	public static void tick(Minecraft client) {
		String dimension = dimensionOf(client);

		if (dimension != null && !dimension.equals(lastDimension)) {
			lastDimension = dimension;
			applyNightVision(client, dimension);
		}

		applyCurve(client, CatVision.CONFIG.forDimension(dimension));
	}

	public static void reset() {
		lastDimension = null;
		curveActive = false;
		strength = 0.0F;
	}

	public static void applyNightVision(Minecraft client, String dimension) {
		ConfigData config = CatVision.CONFIG;
		VisionSettings settings = config.forDimension(dimension);
		VisionSettings override = dimension == null ? null : config.dimension_overrides.get(dimension);
		boolean had_nv = config.has_nv;

		if (override != null)
			config.has_nv = override.auto_nv;
		else
			config.has_nv = config.auto_nv || (config.remember_nv && config.has_nv);

		if (client.player == null)
			return;

		if (config.has_nv && !hasRealNightVision(client.player))
			client.player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, Integer.MAX_VALUE));
		else if (!config.has_nv && had_nv && !settings.nv_curve && !hasRealNightVision(client.player))
			client.player.removeEffect(MobEffects.NIGHT_VISION);
	}

	private static boolean hasRealNightVision(LivingEntity player) {
		MobEffectInstance effect = player.getEffect(MobEffects.NIGHT_VISION);
		return effect != null && effect.getDuration() <= OUR_EFFECT;
	}

	private static void applyCurve(Minecraft client, VisionSettings settings) {
		ConfigData config = CatVision.CONFIG;

		if (!settings.nv_curve || !config.has_nv || client.player == null || client.level == null
				|| hasRealNightVision(client.player)) {
			if (curveActive) {
				curveActive = false;
				strength = 0.0F;
				if (client.player != null && !config.has_nv && !hasRealNightVision(client.player))
					client.player.removeEffect(MobEffects.NIGHT_VISION);
			}
			return;
		}

		if (!client.player.hasEffect(MobEffects.NIGHT_VISION))
			client.player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, Integer.MAX_VALUE));

		int light = client.level.getMaxLocalRawBrightness(client.player.blockPosition());
		double target = settings.strengthFor(light);
		double speed = VisionSettings.clamp(settings.nv_speed, 0.01, 1.0);

		strength += (float) ((target - strength) * speed);
		if (Math.abs(target - strength) < 0.001)
			strength = (float) target;
		curveActive = true;
	}
}
