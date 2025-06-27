package com.iafenvoy.random.command.command;

import com.iafenvoy.random.command.PermissionNodes;
import com.iafenvoy.random.command.util.OverrideSizeInventory;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public final class InvSeeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("invsee")
                .requires(PermissionNodes.INV_SEE.require().and(ServerCommandSource::isExecutedByPlayer))
                .then(argument("player", EntityArgumentType.player())
                        .executes(ctx -> {
                            ServerCommandSource source = ctx.getSource();
                            ServerPlayerEntity player = source.getPlayerOrThrow(), target = EntityArgumentType.getPlayer(ctx, "player");
                            player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, playerInventory, p) -> GenericContainerScreenHandler.createGeneric9x6(syncId, playerInventory, new OverrideSizeInventory(target.getInventory(), 54)), Text.literal(target.getEntityName())));
                            return 1;
                        })));
    }
}
