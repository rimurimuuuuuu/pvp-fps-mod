package com.pvpfps.mod.mixin;

import net.minecraft.client.render.DimensionEffects;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DimensionEffects.class)
public class FogMixin {
    // DimensionEffects.useThickFog removed in 1.21.11
}
