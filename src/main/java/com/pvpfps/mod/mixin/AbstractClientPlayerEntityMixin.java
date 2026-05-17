package com.pvpfps.mod.mixin;

import com.pvpfps.mod.PvpFpsMod;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * MIXIN: AbstractClientPlayerEntity
 * Purpose: Optimize player-specific rendering options for PvP
 * FPS Impact: LOW-MEDIUM - reduces per-player rendering complexity
 */
@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin {

    /**
     * Disable player capes in PvP - capes have cloth physics simulation
     * and add polygon count to every player render
     */
    @Inject(method = "isPartVisible", at = @At("RETURN"), cancellable = true)
    private void pvpfps$optimizePlayerParts(net.minecraft.entity.player.PlayerModelPart modelPart,
                                              CallbackInfoReturnable<Boolean> cir) {
        // In extreme FPS mode, disable cape rendering for all players
        if (PvpFpsMod.CONFIG.disableAllParticles) { // reuse as "extreme mode" flag
            if (modelPart == net.minecraft.entity.player.PlayerModelPart.CAPE) {
                cir.setReturnValue(false);
            }
        }
    }
}
