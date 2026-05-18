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
    private static final int GC_INTERVAL = 6000;

    public static void applyStartupOptimizations() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory() / 1024 / 1024;
        PvpFpsMod.LOGGER.info("[PvP FPS Boost] Max memory: {}MB", maxMemory);
        if (maxMemory < 2048) {
            PvpFpsMod.LOGGER.warn("[PvP FPS Boost] Low memory detected! Recommend allocating 2-4GB.");
        }
    }

    public static void applyGraphicsSettings(MinecraftClient client) {
        if (client == null || client.options == null) return;

        GameOptions opts = client.options;
        var config = PvpFpsMod.CONFIG;

        // Graphics mode - Fast
        opts.graphicsMode.setValue(GraphicsMode.FAST);

        // Particles
        if (config.disableAllParticles) {
            opts.getParticles().setValue(ParticlesMode.MINIMAL);
        }

        // Clouds - 1.21.1 uses cloudRenderMode directly
        if (config.disableClouds) {
            opts.cloudRenderMode.setValue(net.minecraft.client.option.CloudRenderMode.OFF);
        }

        // View bobbing
        opts.getBobView().setValue(false);

        // Entity distance
        opts.getEntityDistanceScaling().setValue((double) config.entityRenderDistance / 500.0);

        // Mipmap
        opts.getMipmapLevels().setValue(config.mipmapLevel);

        // VSync off
        opts.getEnableVsync().setValue(false);

        // Max FPS uncapped
        opts.getMaxFps().setValue(260);

        // Smooth lighting - 1.21.1 uses smoothLighting directly
        opts.smoothLighting.setValue(true);

        // Biome blend
        opts.getBiomeBlendRadius().setValue(0);

        // Sound
        if (config.disableAmbientSounds) {
            opts.getSoundVolumeOption(SoundCategory.AMBIENT).setValue(0.0);
            opts.getSoundVolumeOption(SoundCategory.WEATHER).setValue(0.0);
        }

        opts.write();
        PvpFpsMod.LOGGER.info("[PvP FPS Boost] Graphics settings applied!");
    }

    public static void onClientTick(MinecraftClient client) {
        tickCounter++;
        if (tickCounter >= GC_INTERVAL) {
            tickCounter = 0;
            if (client.world != null && !client.isPaused()) {
                System.gc();
            }
        }
    }

    public static boolean shouldSkipEntityRender(double distanceSq, float entityWidth, float entityHeight) {
        if (!PvpFpsMod.CONFIG.enableEntityCulling) return false;
        float volume = entityWidth * entityHeight;
        if (volume < 0.5f && distanceSq > 1024) return true;
        if (volume < 1.0f && distanceSq > 4096) return true;
        return false;
    }
}
