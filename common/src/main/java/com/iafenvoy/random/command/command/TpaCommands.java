package com.iafenvoy.random.command.command;

import com.iafenvoy.random.command.PermissionNodes;
import com.iafenvoy.random.command.data.DataManager;
import com.iafenvoy.random.command.data.component.builtin.TpaComponent;
import com.iafenvoy.random.command.util.TextUtil;
import com.iafenvoy.random.command.util.Timeout;
import com.iafenvoy.server.i18n.ServerI18n;
import com.mojang.brigadier.CommandDispatcher;
import it.unimi.dsi.fastutil.longs.LongBooleanPair;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public final class TpaCommands {
    //NOTE true=come to here
    private static final Map<ServerPlayerEntity, Map<ServerPlayerEntity, LongBooleanPair>> RECEIVED_REQUESTS = new HashMap<>();

    public static void tick() {

    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("tpa")
                .requires(PermissionNodes.TPA.require().and(ServerCommandSource::isExecutedByPlayer))
                .then(argument("player", EntityArgumentType.player())
                        .executes(ctx -> {
                            ServerCommandSource source = ctx.getSource();
                            ServerPlayerEntity player = source.getPlayerOrThrow(), target = EntityArgumentType.getPlayer(ctx, "player");
                            if (DataManager.getData(target).getComponent(TpaComponent.class).map(x -> x.blacklist().contains(player.getUuid())).orElse(false)) {
                                player.sendMessage(ServerI18n.translateToLiteral(player, "message.random_command.tpa.blacklisted"));
                                return 0;
                            }
                            Map<ServerPlayerEntity, LongBooleanPair> map = RECEIVED_REQUESTS.computeIfAbsent(target, p -> new HashMap<>());
                            if (map.containsKey(player)) {
                                player.sendMessage(ServerI18n.translateToLiteral(player, "message.random_command.tpa.already_sent"));
                                return 0;
                            }
                            map.put(player, LongBooleanPair.of(System.currentTimeMillis(), true));
                            player.sendMessage(ServerI18n.translateToLiteral(player, "message.random_command.tpa.request_sent"));
                            target.sendMessage(ServerI18n.translateToLiteral(target, "message.random_command.tpa.request_tpa", player.getEntityName()).copy()
                                    .append(TextUtil.buttonFormat(ServerI18n.translateToLiteral(player, "message.random_command.accept"), "/tpaccept " + player.getEntityName(), Formatting.GREEN, Formatting.BOLD))
                                    .append(TextUtil.buttonFormat(ServerI18n.translateToLiteral(player, "message.random_command.deny"), "/tpdeny " + player.getEntityName(), Formatting.RED, Formatting.BOLD))
                                    .append(TextUtil.buttonFormat(ServerI18n.translateToLiteral(player, "message.random_command.blacklist"), "/tpblacklist " + player.getEntityName(), Formatting.GRAY, Formatting.BOLD))
                            );
                            return 1;
                        })
                ));
        dispatcher.register(literal("tpahere")
                .requires(PermissionNodes.TPAHERE.require().and(ServerCommandSource::isExecutedByPlayer))
                .then(argument("player", EntityArgumentType.player())
                        .executes(ctx -> {
                            ServerCommandSource source = ctx.getSource();
                            ServerPlayerEntity player = source.getPlayerOrThrow(), target = EntityArgumentType.getPlayer(ctx, "player");
                            if (DataManager.getData(target).getComponent(TpaComponent.class).map(x -> x.blacklist().contains(player.getUuid())).orElse(false)) {
                                player.sendMessage(ServerI18n.translateToLiteral(player, "message.random_command.tpa.blacklisted"));
                                return 0;
                            }
                            Map<ServerPlayerEntity, LongBooleanPair> map = RECEIVED_REQUESTS.computeIfAbsent(target, p -> new HashMap<>());
                            if (map.containsKey(player)) {
                                player.sendMessage(ServerI18n.translateToLiteral(player, "message.random_command.tpa.already_sent"));
                                return 0;
                            }
                            map.put(player, LongBooleanPair.of(System.currentTimeMillis(), false));
                            player.sendMessage(ServerI18n.translateToLiteral(player, "message.random_command.tpa.request_sent"));
                            target.sendMessage(ServerI18n.translateToLiteral(target, "message.random_command.tpa.request_tpahere", player.getEntityName()).copy()
                                    .append(TextUtil.buttonFormat(ServerI18n.translateToLiteral(player, "message.random_command.accept"), "/tpaccept " + player.getEntityName(), Formatting.GREEN, Formatting.BOLD))
                                    .append(TextUtil.buttonFormat(ServerI18n.translateToLiteral(player, "message.random_command.deny"), "/tpdeny " + player.getEntityName(), Formatting.RED, Formatting.BOLD))
                                    .append(TextUtil.buttonFormat(ServerI18n.translateToLiteral(player, "message.random_command.blacklist"), "/tpblacklist " + player.getEntityName(), Formatting.GRAY, Formatting.BOLD))
                            );
                            return 1;
                        })
                ));
        dispatcher.register(literal("tpaccept")
                .requires(PermissionNodes.TPACCEPT.require().and(ServerCommandSource::isExecutedByPlayer))
                .then(argument("player", EntityArgumentType.player())
                        .executes(ctx -> {
                            ServerCommandSource source = ctx.getSource();
                            ServerPlayerEntity player = source.getPlayerOrThrow(), target = EntityArgumentType.getPlayer(ctx, "player");
                            Map<ServerPlayerEntity, LongBooleanPair> map = RECEIVED_REQUESTS.computeIfAbsent(player, p -> new HashMap<>());
                            if (!map.containsKey(target)) {
                                player.sendMessage(ServerI18n.translateToLiteral(player, "message.random_command.tpa.no_sent"));
                                return 0;
                            }
                            boolean dir = map.get(target).rightBoolean();
                            map.remove(target);
                            player.sendMessage(ServerI18n.translateToLiteral(player, "message.random_command.tpa.accepted_request", target.getEntityName()));
                            target.sendMessage(ServerI18n.translateToLiteral(target, "message.random_command.tpa.other_accepted", player.getEntityName()));

                            player.sendMessage(ServerI18n.translateToLiteral(player, "message.random_command.tpa.teleport_in_second", "3"), true);
                            target.sendMessage(ServerI18n.translateToLiteral(target, "message.random_command.tpa.teleport_in_second", "3"), true);
                            Timeout.create(20, () -> {
                                player.sendMessage(ServerI18n.translateToLiteral(player, "message.random_command.tpa.teleport_in_second", "2"), true);
                                target.sendMessage(ServerI18n.translateToLiteral(target, "message.random_command.tpa.teleport_in_second", "2"), true);
                            });
                            Timeout.create(40, () -> {
                                player.sendMessage(ServerI18n.translateToLiteral(player, "message.random_command.tpa.teleport_in_second", "1"), true);
                                target.sendMessage(ServerI18n.translateToLiteral(target, "message.random_command.tpa.teleport_in_second", "1"), true);
                            });
                            Timeout.create(60, () -> {
                                ServerPlayerEntity from = dir ? target : player, to = dir ? player : target;
                                from.teleport(to.getServerWorld(), to.getX(), to.getY(), to.getZ(), to.getYaw(), to.getPitch());
                            });
                            return 1;
                        })
                ));
    }
}
