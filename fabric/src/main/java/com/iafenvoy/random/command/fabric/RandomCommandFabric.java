package com.iafenvoy.random.command.fabric;

import com.iafenvoy.random.command.CommandHelper;
import com.iafenvoy.random.command.RandomCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public final class RandomCommandFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        RandomCommand.init();
        CommandRegistrationCallback.EVENT.register((dispatcher, access, env) -> CommandHelper.registerCommands(dispatcher));
    }
}
