package net.marios271.cat_vision.event;

import net.marios271.cat_vision.CatVision;
import net.marios271.cat_vision.config.ConfigData;
import net.marios271.cat_vision.handler.VisionHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class EndTickListener {
    public static void onEndTick(Minecraft client) {
        if (client.player == null)
            return;

        ConfigData config = CatVision.CONFIG;

        VisionHandler.tick(client);

        boolean isSingleplayer = client.getSingleplayerServer() != null
            && !client.getSingleplayerServer().isPublished();

        if (!client.player.hasEffect(MobEffects.NIGHT_VISION) && config.has_nv)
            client.player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, Integer.MAX_VALUE));

        if (client.player.hasEffect(MobEffects.BLINDNESS) && config.blindness_immunity && isSingleplayer)
            client.player.removeEffect(MobEffects.BLINDNESS);

		//? < 1.21.5 {
        /*if (client.player.hasEffect(MobEffects.CONFUSION) && config.nausea_immunity)
            client.player.removeEffect(MobEffects.CONFUSION);
		*///?} >= 1.21.5 {
		if (client.player.hasEffect(MobEffects.NAUSEA) && config.nausea_immunity)
			client.player.removeEffect(MobEffects.NAUSEA);
		//?}


		//? >= 1.19 {
		if (client.player.hasEffect(MobEffects.DARKNESS) && config.darkness_immunity)
			client.player.removeEffect(MobEffects.DARKNESS);
		//?}
    }
}
