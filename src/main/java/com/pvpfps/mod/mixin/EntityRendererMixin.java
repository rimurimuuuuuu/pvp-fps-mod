package com.pvpfps.mod.mixin;

import com.pvpfps.mod.PvpFpsMod;
import com.pvpfps.mod.util.FpsOptimizer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * MIXIN: EntityRenderer
 * Purpose: Skip shadow and culling optimizations per entity
 * FPS Impact: MEDIUM-HIGH in PvP scenes with many entities
 */
@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    /**
     * Skip entity shadow rendering entirely
     * Shadows require extra render calls per entity - skip for PvP
     */
    @Inject(method = "renderShadow", at = @At("HEAD"), cancellable = true)
    private static void pvpfps$skipEntityShadow(
            net.minecraft.client.render.VertexConsumerProvider vertexConsumers,
            net.minecraft.client.render.LightmapTextureManager manager,
            Entity entity, float opacity, float tickDelta,
            net.minecraft.world.WorldView world,
            CallbackInfo ci) {
        if (PvpFpsMod.CONFIG.disableEntityShadows) {
            ci.cancel();
        }
    }
}
