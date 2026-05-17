package com.pvpfps.mod.util;

import com.pvpfps.mod.PvpFpsMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.sound.SoundCategory;

/**
 * PvP FPS Boost - Core Optimizer
 * Applies all FPS-maximizing settings at runtime
 */
public class FpsOptimizer {

    private static int tickCounter = 0;
    private static final int GC_INTERVAL = 6000; // Every 5 minutes (6000 ticks)

    /**
     * Apply JVM-level optimizations on startup
     */
    public static void applyStartupOptimizations() {
        // Hint JVM to optimize frequently used code paths
        Runtime runtime = Runtime.getRuntime();

        // Log memory info
        long maxMemory = runtime.maxMemory() / 1024 / 1024;
        PvpFpsMod.LOGGER.info("[PvP FPS Boost] Max memory: {}MB", maxMemory);

        if (maxMemory < 2048) {
            PvpFpsMod.LOGGER.warn("[PvP FPS Boost] Low memory detected! Recommend allocating 2-4GB for best PvP performance.");
        }
    }

    /**
     * Apply all graphics settings for maximum FPS
     */
    public static void applyGraphicsSettings(MinecraftClient client) {
        if (client == null || client.options == null) return;

        GameOptions opts = client.options;
        var config = PvpFpsMod.CONFIG;

        // === GRAPHICS MODE ===
        // Fast mode skips many rendering features
        opts.graphicsMode.setValue(GraphicsMode.FAST);

        // === PARTICLES ===
        if (config.disableAllParticles) {
            opts.getParticles().setValue(ParticlesMode.MINIMAL);
        }

        // === CLOUDS ===
        if (config.disableClouds) {
            opts.getCloudsMode().setValue(net.minecraft.client.option.CloudRenderMode.OFF);
        }

        // === VIEW BOBBING ===
        // Disable for cleaner PvP experience and slight FPS gain
        opts.getBobView().setValue(false);

        // === ENTITY DISTANCE ===
        // Reduce entity render distance for PvP
        opts.getEntityDistanceScaling().setValue((double) config.entityRenderDistance / 500.0);

        // === MIPMAP ===
        opts.getMipmapLevels().setValue(config.mipmapLevel);

        // === VSYNC ===
        // ALWAYS disable VSync for PvP - lower latency
        opts.getEnableVsync().setValue(false);

        // === MAX FPS ===
        // Uncap FPS for maximum performance
        opts.getMaxFps().setValue(260);

        // === SMOOTH LIGHTING ===
        // Enabled per user preference
        opts.getSmoothLighting().setValue(true);

        // === BIOME BLEND ===
        // 0 = disabled = FPS boost
        opts.getBiomeBlendRadius().setValue(0);

        // === SOUND ===
        if (config.disableAmbientSounds) {
            opts.getSoundVolumeOption(SoundCategory.AMBIENT).setValue(0.0);
            opts.getSoundVolumeOption(SoundCategory.WEATHER).setValue(0.0);
        }

        // Save the settings
        opts.write();

        PvpFpsMod.LOGGER.info("[PvP FPS Boost] Graphics settings applied for maximum FPS!");
    }

    /**
     * Called every client tick - handles periodic optimizations
     */
    public static void onClientTick(MinecraftClient client) {
        tickCounter++;

        // Periodic GC hint to prevent memory spikes during PvP
        if (tickCounter >= GC_INTERVAL) {
            tickCounter = 0;
            // Suggest GC during low-activity periods
            if (client.world != null && !client.isPaused()) {
                System.gc();
            }
        }
    }

    /**
     * Calculate if an entity should be skipped for rendering based on distance and size
     */
    public static boolean shouldSkipEntityRender(double distanceSq, float entityWidth, float entityHeight) {
        if (!PvpFpsMod.CONFIG.enableEntityCulling) return false;

        // Skip tiny entities far away
        float volume = entityWidth * entityHeight;
        if (volume < 0.5f && distanceSq > 1024) return true;  // 32 blocks
        if (volume < 1.0f && distanceSq > 4096) return true;  // 64 blocks

        return false;
    }
}
