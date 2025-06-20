package com.iafenvoy.random.command.mixin;

import com.iafenvoy.random.command.data.DataManager;
import com.iafenvoy.random.command.data.PlayerData;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.InetSocketAddress;
import java.util.UUID;

@Mixin(ServerLoginNetworkHandler.class)
public class ServerLoginNetworkHandlerMixin {
    @Shadow
    @Final
    ClientConnection connection;

    @Shadow
    @Nullable GameProfile profile;

    @Inject(method = "acceptPlayer", at = @At("RETURN"))
    private void onAcceptPlayer(CallbackInfo ci) {
        if (this.profile == null) return;
        UUID uuid = this.profile.getId();
        String name = this.profile.getName();
        PlayerData data = DataManager.getData(uuid);
        data.updateName(name);
        if (this.connection.getAddress() instanceof InetSocketAddress address)
            data.setIp(address.getHostString());
    }
}
