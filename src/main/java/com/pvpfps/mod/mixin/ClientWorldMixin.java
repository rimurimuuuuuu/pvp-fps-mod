package com.pvpfps.mod.mixin;

import com.pvpfps.mod.PvpFpsMod;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MIXIN: ClientWorld
 * Purpose: Skip expensive world-side animations and effects
 * FPS Impact: MEDIUM - animated textures and ambient spawning
 */
@Mixin(ClientWorld.class)
public class ClientWorldMixin {

    /**
     * Reduce the overhead of per-tick world processing
     * Skip animation updates when configured
     */
    @Inject(method = "tickAnimatedTexture", at = @At("HEAD"), cancellable = true)
    private void pvpfps$skipAnimatedTextures(int x, int y, int z, CallbackInfo ci) {
        if (PvpFpsMod.CONFIG.disableAnimatedTextures) {
            ci.cancel();
        }
    }
}
