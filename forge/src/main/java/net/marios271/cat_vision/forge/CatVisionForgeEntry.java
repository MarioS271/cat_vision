package net.marios271.cat_vision.forge;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.marios271.cat_vision.CatVision;
import net.marios271.cat_vision.config.ConfigScreen;
import net.marios271.cat_vision.event.DisconnectListener;
import net.marios271.cat_vision.event.EndTickListener;
import net.marios271.cat_vision.event.JoinListener;
import net.marios271.cat_vision.event.RespawnListener;
import net.marios271.cat_vision.handler.KeyInputHandler;

@Mod(CatVision.MOD_ID)
public final class CatVisionForgeEntry {
    public CatVisionForgeEntry() {
        CatVision.init(FMLPaths.CONFIGDIR.get().toFile());

        ModLoadingContext.get().registerExtensionPoint(
                ExtensionPoint.CONFIGGUIFACTORY,
                () -> (mc, parent) -> ConfigScreen.create(parent, CatVision.CONFIG)
        );

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(KeyInputHandler.toggleNightVisionKey);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;
        Minecraft client = Minecraft.getInstance();
        EndTickListener.onEndTick(client);
        KeyInputHandler.onKeyTick(client);
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(ClientPlayerNetworkEvent.LoggedInEvent event) {
        JoinListener.onJoin(Minecraft.getInstance());
    }

    @SubscribeEvent
    public void onPlayerLoggedOut(ClientPlayerNetworkEvent.LoggedOutEvent event) {
        DisconnectListener.onDisconnect();
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        RespawnListener.onEntityLoad(event.getEntity());
    }
}
