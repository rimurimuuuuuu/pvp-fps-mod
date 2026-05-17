package com.pvpfps.mod.mixin;

import com.pvpfps.mod.PvpFpsMod;
import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Mixin;

/**
 * MIXIN: ChunkBuilder
 * Purpose: Optimize chunk building thread behavior
 * FPS Impact: MEDIUM - reduces stuttering from chunk updates
 *
 * Note: Chunk building is heavily multi-threaded in 1.21.1
 * We primarily benefit from the settings applied in FpsOptimizer
 */
@Mixin(ChunkBuilder.class)
public class ChunkBuilderMixin {
    // Chunk builder optimizations handled via config settings:
    // - Reduced render distance limits chunk count
    // - Fast graphics mode skips translucency sorting
    // - No smooth lighting reduces geometry complexity
}
