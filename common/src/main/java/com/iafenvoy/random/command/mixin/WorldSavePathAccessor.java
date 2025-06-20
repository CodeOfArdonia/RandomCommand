package com.iafenvoy.random.command.mixin;

import net.minecraft.util.WorldSavePath;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WorldSavePath.class)
public interface WorldSavePathAccessor {
    @Invoker("<init>")
    static WorldSavePath create(String relativePath) {
        throw new AssertionError("This method should be replaced by Mixin.");
    }
}
