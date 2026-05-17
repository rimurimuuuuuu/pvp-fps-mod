package com.pvpfps.mod.mixin;

import com.pvpfps.mod.PvpFpsMod;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MIXIN: MinecraftClient
 * Purpose: Hook into core client loop for optimizations
 * FPS Impact: LOW-MEDIUM - reduces unnecessary work per frame
 */
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    /**
     * After window resize, re-apply our performance settings
     * to prevent vanilla from reverting vsync, etc.
     */
    @Inject(method = "onWindowFocusChanged", at = @At("TAIL"))
    private void pvpfps$onFocusChange(boolean focused, CallbackInfo ci) {
        // When refocusing, ensure vsync stays disabled
        if (focused) {
            MinecraftClient client = (MinecraftClient)(Object)this;
            if (client.getWindow() != null) {
                client.getWindow().setVsync(false);
            }
        }
    }
}
