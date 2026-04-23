package net.marios271.cat_vision.event;

import net.marios271.cat_vision.CatVision;
import net.marios271.cat_vision.config.ConfigData;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class EndTickListener {
    public static void onEndTick(Minecraft client) {
        if (client.player == null)
            return;

        ConfigData config = CatVision.CONFIG;

        if (!client.player.hasEffect(MobEffects.NIGHT_VISION) && config.has_nv)
            client.player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, Integer.MAX_VALUE));

        if (client.player.hasEffect(MobEffects.BLINDNESS) && config.blindness_immunity)
            client.player.removeEffect(MobEffects.BLINDNESS);

        if (client.player.hasEffect(MobEffects.CONFUSION) && config.nausea_immunity)
            client.player.removeEffect(MobEffects.CONFUSION);
    }
}
