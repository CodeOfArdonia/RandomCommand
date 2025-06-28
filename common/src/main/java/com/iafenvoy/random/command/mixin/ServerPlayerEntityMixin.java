package com.iafenvoy.random.command.mixin;

import com.iafenvoy.random.command.data.DataManager;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "enterCombat", at = @At("RETURN"))
    public void onEnterCombat(CallbackInfo ci) {
        DataManager.getData(this.getUuid()).getGlobalData().setCombating(true);
    }

    @Inject(method = "endCombat", at = @At("RETURN"))
    public void onExitCombat(CallbackInfo ci) {
        DataManager.getData(this.getUuid()).getGlobalData().setCombating(false);
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void onTick(CallbackInfo ci) {
        DataManager.getData(this.getUuid()).tick((ServerPlayerEntity) (Object) this);
    }
}
