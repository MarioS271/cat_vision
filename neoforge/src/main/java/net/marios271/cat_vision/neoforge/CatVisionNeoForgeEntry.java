package net.marios271.cat_vision.neoforge;

import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.marios271.cat_vision.CatVision;
import net.marios271.cat_vision.config.ConfigScreen;
import net.marios271.cat_vision.event.DisconnectListener;
import net.marios271.cat_vision.event.EndTickListener;
import net.marios271.cat_vision.event.JoinListener;
import net.marios271.cat_vision.event.RespawnListener;
import net.marios271.cat_vision.handler.KeyInputHandler;

@Mod(CatVision.MOD_ID)
public final class CatVisionNeoForgeEntry {
    public CatVisionNeoForgeEntry(IEventBus modEventBus, ModContainer modContainer) {
        CatVision.init(FMLPaths.CONFIGDIR.get().toFile());

        modContainer.registerExtensionPoint(IConfigScreenFactory.class,
                (mc, parent) -> ConfigScreen.create(parent, CatVision.CONFIG));

        modEventBus.addListener(this::onRegisterKeyMappings);
        NeoForge.EVENT_BUS.register(this);
    }

    private void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KeyInputHandler.toggleNightVisionKey);
    }

    @SubscribeEvent
    public void onClientTickPost(ClientTickEvent.Post event) {
        Minecraft client = Minecraft.getInstance();
        EndTickListener.onEndTick(client);
        KeyInputHandler.onKeyTick(client);
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(ClientPlayerNetworkEvent.LoggingIn event) {
        JoinListener.onJoin(Minecraft.getInstance());
    }

    @SubscribeEvent
    public void onPlayerLoggedOut(ClientPlayerNetworkEvent.LoggingOut event) {
        DisconnectListener.onDisconnect();
    }

    @SubscribeEvent
    public void onEntityJoinLevel(EntityJoinLevelEvent event) {
        RespawnListener.onEntityLoad(event.getEntity());
    }
}
