package com.iafenvoy.random.command.mixin;

import com.iafenvoy.random.command.data.AfkHelper;
import com.iafenvoy.random.command.data.DataManager;
import com.iafenvoy.random.command.data.PlayerData;
import com.iafenvoy.random.command.data.component.builtin.AfkComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Shadow
    @Final
    private MinecraftServer server;

    @Unique
    private void random_command$onAct() {
        PlayerData data = DataManager.getData(this.player);
        data.getGlobalData().updateLastActionTick(this.server);
        if (data.getComponent(AfkComponent.class).isPresent()) AfkHelper.leave(this.player, data);
    }

    @Inject(method = "onPlayerAction", at = @At("RETURN"))
    public void onPlayerAction(CallbackInfo ci) {
        this.random_command$onAct();
    }

    @Inject(method = "onPlayerInteractBlock", at = @At("RETURN"))
    public void onPlayerInteractBlock(CallbackInfo ci) {
        this.random_command$onAct();
    }

    @Inject(method = "onPlayerInteractItem", at = @At("RETURN"))
    public void onPlayerInteractItem(CallbackInfo ci) {
        this.random_command$onAct();
    }

    @Inject(method = "onBoatPaddleState", at = @At("RETURN"))
    public void onBoatPaddleState(CallbackInfo ci) {
        this.random_command$onAct();
    }

    @Inject(method = "onPlayerInteractEntity", at = @At("RETURN"))
    public void onPlayerInteractEntity(CallbackInfo ci) {
        this.random_command$onAct();
    }

    @Inject(method = "onChatMessage", at = @At("RETURN"))
    public void onChatMessage(CallbackInfo ci) {
        this.random_command$onAct();
    }
}
