package com.pvpfps.mod.mixin;

import com.pvpfps.mod.PvpFpsMod;
import com.pvpfps.mod.util.FpsOptimizer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * MIXIN: Entity
 * Purpose: Optimize entity visibility checks
 * FPS Impact: MEDIUM - skip rendering of irrelevant entities
 */
@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract EntityType<?> getType();
    @Shadow public abstract double squaredDistanceTo(double x, double y, double z);

    /**
     * Enhanced entity visibility check
     * Skip rendering tiny/irrelevant entities at distance
     */
    @Inject(method = "shouldRender(D)Z", at = @At("RETURN"), cancellable = true)
    private void pvpfps$optimizeEntityRender(double squaredDistance, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) return; // Already culled by vanilla
        if (!PvpFpsMod.CONFIG.enableEntityCulling) return;

        Entity self = (Entity)(Object)this;
        float width = self.getWidth();
        float height = self.getHeight();

        // Apply our culling logic
        if (FpsOptimizer.shouldSkipEntityRender(squaredDistance, width, height)) {
            cir.setReturnValue(false);
        }
    }
}
