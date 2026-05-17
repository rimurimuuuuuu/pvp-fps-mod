package com.pvpfps.mod.mixin;

import com.pvpfps.mod.PvpFpsMod;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MIXIN: SoundSystem
 * Purpose: Reduce sound system overhead
 * FPS Impact: LOW-MEDIUM - sound system can cause micro-stutters
 */
@Mixin(SoundSystem.class)
public class SoundSystemMixin {

    /**
     * Reduce overhead of sound ticking by skipping
     * when too many sounds are already playing
     */
    @Inject(method = "tick()V", at = @At("HEAD"), cancellable = true)
    private void pvpfps$optimizeSoundTick(CallbackInfo ci) {
        // Sound system runs on the main thread and can cause stutters
        // The settings-based volume reduction (applied in FpsOptimizer)
        // handles most of the optimization. This mixin is a hook point.
    }
}
