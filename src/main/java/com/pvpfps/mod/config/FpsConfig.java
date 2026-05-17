package com.pvpfps.mod.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;

/**
 * PvP FPS Boost - Configuration
 * All settings default to maximum FPS performance
 */
public class FpsConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir().resolve("pvpfps.json");

    // ========== PARTICLE SETTINGS ==========
    /** Disable all particles completely for max FPS */
    public boolean disableAllParticles = true;
    /** Keep critical particles (fire, lava) visible */
    public boolean keepCriticalParticles = false;
    /** Max particles rendered simultaneously */
    public int maxParticleCount = 0;

    // ========== RENDERING SETTINGS ==========
    /** Disable clouds entirely */
    public boolean disableClouds = true;
    /** Disable weather effects (rain/snow rendering) */
    public boolean disableWeatherRendering = true;
    /** Disable fog for extra visibility and FPS */
    public boolean disableFog = true;
    /** Disable entity shadows */
    public boolean disableEntityShadows = true;
    /** Disable beacon beam rendering */
    public boolean disableBeaconBeam = true;
    /** Disable boss bar rendering (configurable) */
    public boolean disableBossBar = false;
    /** Reduce entity render distance */
    public int entityRenderDistance = 32;
    /** Skip rendering entities below this size when far away */
    public boolean enableEntityCulling = true;

    // ========== CHUNK SETTINGS ==========
    /** Aggressive chunk update batching */
    public boolean aggressiveChunkUpdates = true;
    /** Skip chunk updates when not needed */
    public boolean skipUnnecessaryChunkUpdates = true;

    // ========== TEXTURE SETTINGS ==========
    /** Disable animated textures (water, lava, fire) */
    public boolean disableAnimatedTextures = true;
    /** Mipmap level - lower = more FPS */
    public int mipmapLevel = 0;

    // ========== SOUND SETTINGS ==========
    /** Reduce sound channel count */
    public int soundChannelCount = 8;
    /** Disable ambient sounds */
    public boolean disableAmbientSounds = true;

    // ========== PVP SPECIFIC ==========
    /** Show hitbox overlay for PvP aiming */
    public boolean showHitboxes = false;
    /** Custom attack indicator */
    public boolean showAttackCooldown = true;
    /** Optimize attack reach calculation */
    public boolean optimizeAttackReach = true;

    // ========== HUD SETTINGS ==========
    /** Show FPS counter in corner */
    public boolean showFpsCounter = true;
    /** Disable unnecessary HUD elements */
    public boolean minimalHud = true;
    /** Disable vignette effect */
    public boolean disableVignette = true;
    /** Disable title/subtitle text rendering */
    public boolean disableTitleText = false;

    public static FpsConfig load() {
        if (CONFIG_PATH.toFile().exists()) {
            try (Reader reader = new FileReader(CONFIG_PATH.toFile())) {
                return GSON.fromJson(reader, FpsConfig.class);
            } catch (IOException e) {
                System.err.println("[PvP FPS Boost] Failed to load config, using defaults");
            }
        }
        FpsConfig config = new FpsConfig();
        config.save();
        return config;
    }

    public void save() {
        try (Writer writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            System.err.println("[PvP FPS Boost] Failed to save config");
        }
    }
}
