package com.pvpfps.mod;

import com.pvpfps.mod.config.FpsConfig;
import com.pvpfps.mod.util.FpsOptimizer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PvpFpsMod implements ClientModInitializer {

    public static final String MOD_ID = "pvpfps";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static FpsConfig CONFIG;

    @Override
    public void onInitializeClient() {
        LOGGER.info("[PvP FPS Boost] Initializing ultra-performance PvP client...");

        // Load config
        CONFIG = FpsConfig.load();

        // Apply JVM-level optimizations on startup
        FpsOptimizer.applyStartupOptimizations();

        // Register tick event for runtime optimizations
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            FpsOptimizer.onClientTick(client);
        });

        // Apply settings when world loads
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            FpsOptimizer.applyGraphicsSettings(client);
            LOGGER.info("[PvP FPS Boost] FPS optimizations applied! GG ez frames.");
        });

        LOGGER.info("[PvP FPS Boost] Loaded! Enjoy your extra FPS in PvP!");
    }
}
