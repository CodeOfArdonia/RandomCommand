package com.iafenvoy.random.command.mixin;

import com.iafenvoy.random.command.Static;
import com.iafenvoy.random.command.data.DataManager;
import com.iafenvoy.random.command.util.Timeout;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        MinecraftServer self = (MinecraftServer) (Object) this;
        Static.SERVER = self;
        DataManager.initialize(self);
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void onTick(CallbackInfo ci) {
        DataManager.tick();
        Timeout.runTimeout((MinecraftServer) (Object) this);
    }
}
