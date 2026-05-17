package com.pvpfps.mod.mixin;

import com.pvpfps.mod.PvpFpsMod;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MIXIN: ParticleManager
 * Purpose: Block particle spawning entirely when configured
 * FPS Impact: HIGH - particles are one of the biggest FPS killers in PvP
 */
@Mixin(ParticleManager.class)
public class ParticleManagerMixin {

    /**
     * Cancel ALL particle addition when disableAllParticles is enabled
     * This is the most effective single optimization for PvP scenarios
     */
    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V",
            at = @At("HEAD"), cancellable = true)
    private void pvpfps$blockParticles(ParticleEffect parameters, double x, double y, double z,
                                        double velocityX, double velocityY, double velocityZ,
                                        CallbackInfo ci) {
        if (PvpFpsMod.CONFIG.disableAllParticles) {
            ci.cancel();
        }
    }

    /**
     * Cancel the world particle spawning path as well
     */
    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;ZDDDDDD)V",
            at = @At("HEAD"), cancellable = true)
    private void pvpfps$blockWorldParticles(ParticleEffect parameters, boolean alwaysSpawn,
                                             double x, double y, double z,
                                             double velocityX, double velocityY, double velocityZ,
                                             CallbackInfo ci) {
        if (PvpFpsMod.CONFIG.disableAllParticles) {
            ci.cancel();
        }
    }
}
