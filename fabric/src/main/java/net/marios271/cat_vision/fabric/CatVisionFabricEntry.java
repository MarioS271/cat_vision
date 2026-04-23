package net.marios271.cat_vision.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.marios271.cat_vision.CatVision;
import net.marios271.cat_vision.event.DisconnectListener;
import net.marios271.cat_vision.event.EndTickListener;
import net.marios271.cat_vision.event.JoinListener;
import net.marios271.cat_vision.event.RespawnListener;
import net.marios271.cat_vision.handler.KeyInputHandler;

public final class CatVisionFabricEntry implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CatVision.init(FabricLoader.getInstance().getConfigDir().toFile());

        KeyBindingHelper.registerKeyBinding(KeyInputHandler.toggleNightVisionKey);

        ClientTickEvents.END_CLIENT_TICK.register(EndTickListener::onEndTick);
        ClientTickEvents.END_CLIENT_TICK.register(KeyInputHandler::onKeyTick);
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> JoinListener.onJoin(client));
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> DisconnectListener.onDisconnect());
        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> RespawnListener.onEntityLoad(entity));
    }
}
