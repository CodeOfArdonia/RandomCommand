package com.iafenvoy.random.command.command;

import com.iafenvoy.random.command.PermissionNodes;
import com.iafenvoy.random.command.data.DataManager;
import com.iafenvoy.random.command.data.component.builtin.NickComponent;
import com.iafenvoy.server.i18n.ServerI18n;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public final class NickCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("nick")
                .requires(PermissionNodes.NICK.require().and(ServerCommandSource::isExecutedByPlayer))
                .then(argument("nick", StringArgumentType.greedyString())
                        .then(argument("player", EntityArgumentType.player())
                                .requires(PermissionNodes.NICK_OTHER.require().and(ServerCommandSource::isExecutedByPlayer))
                                .executes(ctx -> {
                                    ServerCommandSource source = ctx.getSource();
                                    ServerPlayerEntity player = source.getPlayerOrThrow(), target = EntityArgumentType.getPlayer(ctx, "player");
                                    String nick = StringArgumentType.getString(ctx, "nick");
                                    DataManager.getData(target).setComponent(new NickComponent(nick));
                                    ServerI18n.sendMessage(player, "message.random_command.success");
                                    return 1;
                                }))
                        .executes(ctx -> {
                            ServerCommandSource source = ctx.getSource();
                            ServerPlayerEntity player = source.getPlayerOrThrow();
                            String nick = StringArgumentType.getString(ctx, "nick");
                            DataManager.getData(player).setComponent(new NickComponent(nick));
                            ServerI18n.sendMessage(player, "message.random_command.success");
                            return 1;
                        }))
                .executes(ctx -> {
                    ServerCommandSource source = ctx.getSource();
                    ServerPlayerEntity player = source.getPlayerOrThrow();
                    DataManager.getData(player).removeComponent(NickComponent.class);
                    ServerI18n.sendMessage(player, "message.random_command.success");
                    return 1;
                }));
    }
}
