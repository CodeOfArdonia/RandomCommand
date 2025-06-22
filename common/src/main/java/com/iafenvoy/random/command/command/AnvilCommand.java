package com.iafenvoy.random.command.command;

import com.iafenvoy.random.command.PermissionNodes;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public final class AnvilCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("anvil")
                .requires(PermissionNodes.ANVIL.require().and(ServerCommandSource::isExecutedByPlayer))
                .executes(ctx -> {
                    ServerCommandSource source = ctx.getSource();
                    ServerPlayerEntity player = source.getPlayerOrThrow();
                    player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inventory, p) -> new AnvilScreenHandler(syncId, inventory, ScreenHandlerContext.EMPTY), Text.translatable("container.repair")));
                    return 1;
                }));
    }
}
