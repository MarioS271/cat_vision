package net.marios271.cat_vision.forge;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.marios271.cat_vision.CatVision;
import net.marios271.cat_vision.config.ConfigScreen;
import net.marios271.cat_vision.event.DisconnectListener;
import net.marios271.cat_vision.event.EndTickListener;
import net.marios271.cat_vision.event.JoinListener;
import net.marios271.cat_vision.event.RespawnListener;
import net.marios271.cat_vision.handler.KeyInputHandler;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;

@Mod(CatVision.MOD_ID)
public final class CatVisionForgeEntry {
    public CatVisionForgeEntry() {
        CatVision.init(FMLPaths.CONFIGDIR.get().toFile());

        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((mc, parent) -> ConfigScreen.create(parent, CatVision.CONFIG))
        );

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onRegisterKeyMappings);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KeyInputHandler.toggleNightVisionKey);
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
