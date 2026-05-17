package com.pvpfps.mod.mixin;

import com.pvpfps.mod.PvpFpsMod;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MIXIN: BackgroundRenderer
 * Purpose: Disable fog calculations
 * FPS Impact: MEDIUM - fog requires GPU shader math every frame
 * PvP Bonus: Better visibility = easier to track enemies!
 */
@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {

    /**
     * Skip fog rendering entirely
     * Fog in PvP = bad visibility + wasted GPU time
     * Win-win to disable it!
     */
    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void pvpfps$disableFog(Camera camera,
                                           BackgroundRenderer.FogType fogType,
                                           net.minecraft.client.render.RenderTickCounter counter,
                                           float viewDistance,
                                           boolean thickFog,
                                           float tickDelta,
                                           CallbackInfo ci) {
        if (PvpFpsMod.CONFIG.disableFog) {
            // Set fog to maximum distance effectively removing it
            com.mojang.blaze3d.systems.RenderSystem.setShaderFogStart(Float.MAX_VALUE);
            com.mojang.blaze3d.systems.RenderSystem.setShaderFogEnd(Float.MAX_VALUE);
            ci.cancel();
        }
    }
}
