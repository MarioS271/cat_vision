package net.marios271.cat_vision.handler;

import com.mojang.blaze3d.platform.InputConstants;
import net.marios271.cat_vision.CatVision;
import net.marios271.cat_vision.config.ConfigData;
import net.marios271.cat_vision.config.ConfigScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
//? < 1.19 {
/*import net.minecraft.network.chat.TranslatableComponent;
*///?} >= 1.19 {
import net.minecraft.network.chat.Component;
//?}
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
//? < 26.3 {
/*import org.lwjgl.glfw.GLFW;
 *///?}

public class KeyInputHandler {
    public static final String KEY_TOGGLE_CLIENT_NV = "key.cat_vision.toggle_client_night_vision";
    public static final String KEY_OPEN_CONFIG = "key.cat_vision.open_config_screen";

    //? >= 1.21.9 {
    public static final KeyMapping.Category CAT_VISION_CATEGORY =
            KeyMapping.Category.register(net.minecraft.resources.Identifier.fromNamespaceAndPath(CatVision.MOD_ID, "cat_vision"));
    //?} else {
    /*public static final String CAT_VISION_CATEGORY = "key.categories.cat_vision";
	*///?}

    public static KeyMapping toggleNightVisionKey = new KeyMapping(
            KEY_TOGGLE_CLIENT_NV,
			//? < 26.3 {
			/*InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_COMMA,
			*///?} else {
			InputConstants.Type.KEYBOARD,
			InputConstants.KEY_COMMA,
			//?}
            CAT_VISION_CATEGORY
    );
	public static KeyMapping openConfigKey = new KeyMapping(
			KEY_OPEN_CONFIG,
			//? < 26.3 {
			/*InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_F12,
			*///?} else {
			InputConstants.Type.KEYBOARD,
			InputConstants.KEY_F12,
			//?}
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
				//? < 1.19 {
                /*client.player.displayClientMessage(
					new TranslatableComponent("message.cat_vision.activated"),
					true
				);
				*///?} >= 1.19 && < 26.1 {
				/*client.player.displayClientMessage(
					Component.translatable("message.cat_vision.activated"),
					true
				);
				*///?} >= 26.1 {
				client.player.sendOverlayMessage(
					Component.translatable("message.cat_vision.activated")
				);
				//?}
            } else {
                config.has_nv = false;
                client.player.removeEffect(MobEffects.NIGHT_VISION);
				//? < 1.19 {
                /*client.player.displayClientMessage(
					new TranslatableComponent("message.cat_vision.deactivated"),
					true
				);
				*///?} >= 1.19 && < 26.1 {
				/*client.player.displayClientMessage(
					Component.translatable("message.cat_vision.deactivated"),
					true
				);
				*///?} >= 26.1 {
				client.player.sendOverlayMessage(
					Component.translatable("message.cat_vision.deactivated")
				);
				//?}
            }
        }
		else if (openConfigKey.consumeClick()) {
			//? < 26.2 {
			/*if (Minecraft.getInstance().screen == null) {
				Minecraft.getInstance().setScreen(ConfigScreen.create(null, CatVision.CONFIG));
			}
			*///?} >= 26.2 {
			if (Minecraft.getInstance().gui.screen() == null) {
				Minecraft.getInstance().gui.setScreen(ConfigScreen.create(null, CatVision.CONFIG));
			}
			//?}
		}
    }
}
