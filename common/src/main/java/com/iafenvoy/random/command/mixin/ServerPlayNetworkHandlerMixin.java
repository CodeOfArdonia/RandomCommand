package com.iafenvoy.random.command.mixin;

import com.iafenvoy.random.command.data.helper.AfkHelper;
import com.iafenvoy.random.command.data.DataManager;
import com.iafenvoy.random.command.data.PlayerData;
import com.iafenvoy.random.command.data.component.builtin.AfkComponent;
import com.iafenvoy.random.command.data.component.builtin.BackComponent;
import com.iafenvoy.random.command.util.GlobalVec3d;
import net.minecraft.network.packet.s2c.play.PositionFlag;
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

import java.util.Set;

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

    @Inject(method = "requestTeleport(DDDFFLjava/util/Set;)V", at = @At("HEAD"))
    private void onTeleport(double x, double y, double z, float yaw, float pitch, Set<PositionFlag> flags, CallbackInfo ci) {
        DataManager.getData(this.player.getUuid()).setComponent(new BackComponent(new GlobalVec3d(this.player)));
    }
}
