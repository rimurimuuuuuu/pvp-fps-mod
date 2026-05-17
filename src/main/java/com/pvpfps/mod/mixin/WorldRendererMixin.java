package com.pvpfps.mod.mixin;

import com.pvpfps.mod.PvpFpsMod;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MIXIN: WorldRenderer
 * Purpose: Skip expensive world rendering passes
 * FPS Impact: HIGH - weather and cloud rendering can cost 10-30 FPS
 */
@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    /**
     * Skip weather rendering (rain/snow particles) completely
     * In PvP you don't need rain effects - just noise
     */
    @Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
    private void pvpfps$skipWeather(net.minecraft.client.util.math.MatrixStack matrices,
                                     float tickDelta, double cameraX, double cameraY, double cameraZ,
                                     CallbackInfo ci) {
        if (PvpFpsMod.CONFIG.disableWeatherRendering) {
            ci.cancel();
        }
    }

    /**
     * Skip cloud chunk rebuild - clouds are pure FPS waste for PvP
     */
    @Inject(method = "renderClouds", at = @At("HEAD"), cancellable = true)
    private void pvpfps$skipClouds(net.minecraft.client.util.math.MatrixStack matrices,
                                    net.joml.Matrix4f matrix4f, float tickDelta,
                                    double cameraX, double cameraY, double cameraZ,
                                    CallbackInfo ci) {
        if (PvpFpsMod.CONFIG.disableClouds) {
            ci.cancel();
        }
    }
}
