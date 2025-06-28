package com.iafenvoy.random.command.command;

import com.iafenvoy.random.command.PermissionNodes;
import com.iafenvoy.random.command.RandomCommand;
import com.iafenvoy.random.command.data.DataManager;
import com.iafenvoy.random.command.data.helper.WarpHelper;
import com.iafenvoy.random.command.util.GlobalVec3d;
import com.iafenvoy.server.i18n.ServerI18n;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public final class WarpCommands {
    public static final SuggestionProvider<ServerCommandSource> WARP_POINTS = SuggestionProviders.register(Identifier.of(RandomCommand.MOD_ID, "warp_points"), (context, builder) -> context.getSource() instanceof ServerCommandSource ? CommandSource.suggestMatching(WarpHelper.DATA.keySet(), builder) : context.getSource().getCompletions(context));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("warp")
                .requires(PermissionNodes.WARP.require().and(ServerCommandSource::isExecutedByPlayer))

                .then(argument("name", StringArgumentType.greedyString())
                        .suggests(WARP_POINTS)
                        .executes(ctx -> {
                            ServerCommandSource source = ctx.getSource();
                            ServerPlayerEntity player = source.getPlayerOrThrow();
                            String point = StringArgumentType.getString(ctx, "name");
                            GlobalVec3d pos = WarpHelper.DATA.get(point);
                            if (pos == null) {
                                ServerI18n.sendMessage(player, "message.random_command.wrap.unknown_warp", point);
                                return 0;
                            } else {
                                pos.teleport(source.getServer(), player);
                                ServerI18n.sendMessage(player, "message.random_command.wrap.warp", point);
                                return 1;
                            }
                        })));
        dispatcher.register(literal("warpm")
                .requires(PermissionNodes.WARP_MANAGE.require().and(ServerCommandSource::isExecutedByPlayer))
                .then(literal("add")
                        .then(argument("name", StringArgumentType.greedyString())
                                .executes(ctx -> {
                                    ServerCommandSource source = ctx.getSource();
                                    ServerPlayerEntity player = source.getPlayerOrThrow();
                                    String point = StringArgumentType.getString(ctx, "name");
                                    if (WarpHelper.DATA.containsKey(point)) {
                                        ServerI18n.sendMessage(player, "message.random_command.wrap.duplicate_id", point);
                                        return 0;
                                    } else {
                                        WarpHelper.DATA.put(point, new GlobalVec3d(player));
                                        WarpHelper.save(source.getServer());
                                        ServerI18n.sendMessage(player, "message.random_command.success");
                                        return 1;
                                    }
                                })))
                .then(literal("remove")
                        .then(argument("name", StringArgumentType.greedyString())
                                .suggests(WARP_POINTS)
                                .executes(ctx -> {
                                    ServerCommandSource source = ctx.getSource();
                                    ServerPlayerEntity player = source.getPlayerOrThrow();
                                    String point = StringArgumentType.getString(ctx, "name");
                                    if (WarpHelper.DATA.containsKey(point)) {
                                        WarpHelper.DATA.remove(point);
                                        WarpHelper.save(source.getServer());
                                        ServerI18n.sendMessage(player, "message.random_command.success");
                                        return 1;
                                    } else {
                                        ServerI18n.sendMessage(player, "message.random_command.wrap.unknown_warp", point);
                                        return 0;
                                    }
                                })))
        );
    }
}
