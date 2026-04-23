package net.marios271.cat_vision.event;

import net.marios271.cat_vision.CatVision;
import net.marios271.cat_vision.config.ConfigData;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class RespawnListener {
    public static void onEntityLoad(Entity entity) {
        if (!(entity instanceof LocalPlayer))
            return;

        LocalPlayer player = (LocalPlayer) entity;
        ConfigData config = CatVision.CONFIG;

        if (config.has_nv)
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, Integer.MAX_VALUE));
    }
}
