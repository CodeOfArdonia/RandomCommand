package com.iafenvoy.random.command;

import com.iafenvoy.random.command.command.*;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;

public class CommandHelper {
    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        AfkCommand.register(dispatcher);
        AnvilCommand.register(dispatcher);
        BackCommand.register(dispatcher);
        EnderChestCommand.register(dispatcher);
        HatCommand.register(dispatcher);
        HomeCommands.register(dispatcher);
        InvSeeCommand.register(dispatcher);
        LightningCommand.register(dispatcher);
        NickCommand.register(dispatcher);
        SkullCommand.register(dispatcher);
        SpawnCommand.register(dispatcher);
        TourCommand.register(dispatcher);
        TpaCommands.register(dispatcher);
        WarpCommands.register(dispatcher);
    }
}
