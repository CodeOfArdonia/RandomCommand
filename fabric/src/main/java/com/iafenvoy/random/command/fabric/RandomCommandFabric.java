package com.iafenvoy.random.command.fabric;

import net.fabricmc.api.ModInitializer;

import com.iafenvoy.random.command.RandomCommand;

public final class RandomCommandFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        RandomCommand.init();
    }
}
