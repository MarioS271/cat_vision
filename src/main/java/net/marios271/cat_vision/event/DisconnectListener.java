package net.marios271.cat_vision.event;

import net.marios271.cat_vision.CatVision;
import net.marios271.cat_vision.handler.VisionHandler;

public class DisconnectListener {
    public static void onDisconnect() {
        VisionHandler.reset();
        CatVision.CONFIG.save();
    }
}
