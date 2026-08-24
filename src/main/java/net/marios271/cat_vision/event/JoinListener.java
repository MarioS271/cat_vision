package net.marios271.cat_vision.event;

import net.marios271.cat_vision.handler.VisionHandler;
import net.minecraft.client.Minecraft;

public class JoinListener {
    public static void onJoin(Minecraft client) {
        if (client.player == null)
            return;

        VisionHandler.applyNightVision(client, VisionHandler.dimensionOf(client));
    }
}
