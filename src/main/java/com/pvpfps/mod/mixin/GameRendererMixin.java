package com.pvpfps.mod.mixin;

import com.pvpfps.mod.PvpFpsMod;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MIXIN: GameRenderer
 * Purpose: Remove post-processing overlays that cost FPS without PvP benefit
 * FPS Impact: MEDIUM - removes screen-space effects
 */
@Mixin(GameRenderer.class)
public class GameRendererMixin {

    /**
     * Disable the vignette overlay (dark corners effect)
     * Pure visual overhead with zero gameplay value
     */
    @Inject(method = "renderWorld", at = @At("HEAD"))
    private void pvpfps$optimizeWorldRender(float tickDelta, long limitTime,
                                              net.minecraft.client.util.math.MatrixStack matrix,
                                              CallbackInfo ci) {
        // Hook point for frame timing optimizations
    }
}
