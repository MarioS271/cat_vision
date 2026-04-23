package net.marios271.cat_vision.event;

import net.marios271.cat_vision.CatVision;

public class DisconnectListener {
    public static void onDisconnect() {
        CatVision.CONFIG.save();
    }
}
