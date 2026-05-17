package com.pvpfps.mod.mixin;

import com.pvpfps.mod.PvpFpsMod;
import net.minecraft.client.render.DimensionEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * MIXIN: DimensionEffects (Fog)
 * Purpose: Override dimension fog settings
 * FPS Impact: MEDIUM - nether/end fog is very dense and expensive
 */
@Mixin(DimensionEffects.class)
public class FogMixin {

    /**
     * Report no fog density for all dimensions when disabled
     * This removes the thick nether fog which destroys FPS
     */
    @Inject(method = "useThickFog", at = @At("HEAD"), cancellable = true)
    private void pvpfps$noThickFog(int camX, int camY, CallbackInfoReturnable<Boolean> cir) {
        if (PvpFpsMod.CONFIG.disableFog) {
            cir.setReturnValue(false);
        }
    }
}
