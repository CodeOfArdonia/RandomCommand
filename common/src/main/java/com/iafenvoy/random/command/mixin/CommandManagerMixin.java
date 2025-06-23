package com.iafenvoy.random.command.mixin;

import com.iafenvoy.random.command.command.*;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
public class CommandManagerMixin {
    @Shadow
    @Final
    private CommandDispatcher<ServerCommandSource> dispatcher;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void registerCommands(CommandManager.RegistrationEnvironment environment, CommandRegistryAccess commandRegistryAccess, CallbackInfo ci) {
        AnvilCommand.register(this.dispatcher);
        BackCommand.register(this.dispatcher);
        EnderChestCommand.register(this.dispatcher);
        HatCommand.register(this.dispatcher);
        HomeCommands.register(this.dispatcher);
        LightningCommand.register(this.dispatcher);
        SkullCommand.register(this.dispatcher);
        TourCommand.register(this.dispatcher);
        TpaCommands.register(this.dispatcher);
    }
}
