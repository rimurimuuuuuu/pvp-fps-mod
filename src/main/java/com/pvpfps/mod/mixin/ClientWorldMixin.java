package com.pvpfps.mod.mixin;

import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    // tickAnimatedTexture removed in 1.21.11
}
