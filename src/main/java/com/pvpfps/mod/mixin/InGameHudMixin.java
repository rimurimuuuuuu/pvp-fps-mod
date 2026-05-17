package com.pvpfps.mod.mixin;

import com.pvpfps.mod.PvpFpsMod;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MIXIN: InGameHud
 * Purpose: Remove unnecessary HUD elements that consume rendering time
 * FPS Impact: LOW - every skipped draw call helps
 * PvP Benefit: Cleaner screen = better focus
 */
@Mixin(InGameHud.class)
public class InGameHudMixin {

    /**
     * Skip vignette rendering (dark gradient border effect)
     * Zero gameplay value, just wastes render time
     */
    @Inject(method = "renderVignetteOverlay", at = @At("HEAD"), cancellable = true)
    private void pvpfps$skipVignette(DrawContext context, net.minecraft.entity.Entity entity, CallbackInfo ci) {
        if (PvpFpsMod.CONFIG.disableVignette) {
            ci.cancel();
        }
    }

    /**
     * Skip title/subtitle text rendering if configured
     * Servers sometimes spam titles which can cause lag spikes
     */
    @Inject(method = "renderTitle", at = @At("HEAD"), cancellable = true)
    private void pvpfps$skipTitle(DrawContext context, net.minecraft.client.render.RenderTickCounter counter, CallbackInfo ci) {
        if (PvpFpsMod.CONFIG.disableTitleText) {
            ci.cancel();
        }
    }
}
