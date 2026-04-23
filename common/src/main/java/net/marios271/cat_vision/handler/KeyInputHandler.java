package net.marios271.cat_vision.handler;

import com.mojang.blaze3d.platform.InputConstants;
import net.marios271.cat_vision.CatVision;
import net.marios271.cat_vision.config.ConfigData;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String CAT_VISION_CATEGORY = "key.categories.cat_vision";
    public static final String KEY_TOGGLE_CLIENT_NV = "key.cat_vision.toggle_client_night_vision";

    public static KeyMapping toggleNightVisionKey = new KeyMapping(
            KEY_TOGGLE_CLIENT_NV,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_COMMA,
            CAT_VISION_CATEGORY
    );

    public static void onKeyTick(Minecraft client) {
        if (client.player == null)
            return;

        ConfigData config = CatVision.CONFIG;

        if (toggleNightVisionKey.consumeClick()) {
            if (!config.has_nv) {
                config.has_nv = true;
                client.player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, Integer.MAX_VALUE));
                client.player.displayClientMessage(new TranslatableComponent("message.cat_vision.activated"), true);
            } else {
                config.has_nv = false;
                client.player.removeEffect(MobEffects.NIGHT_VISION);
                client.player.displayClientMessage(new TranslatableComponent("message.cat_vision.deactivated"), true);
            }
        }
    }
}
