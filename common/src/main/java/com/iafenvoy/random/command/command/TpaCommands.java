package com.iafenvoy.random.command.command;

import com.iafenvoy.random.command.data.DataManager;
import com.iafenvoy.random.command.data.component.builtin.TpaComponent;
import com.iafenvoy.random.command.util.TextUtil;
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

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("tpa")
                .requires(ServerCommandSource::isExecutedByPlayer)
                .then(argument("player", EntityArgumentType.player())
                        .executes(ctx -> {
                            ServerCommandSource source = ctx.getSource();
                            ServerPlayerEntity player = source.getPlayerOrThrow(), target = EntityArgumentType.getPlayer(ctx, "player");
                            if (DataManager.getData(target).getComponent(TpaComponent.class).map(x -> x.blacklist().contains(player.getUuid())).orElse(false)) {
                                player.sendMessage(ServerI18n.translateToLiteral(player, "message.random_command.tpa.blacklisted"));
                                return 0;
                            }
                            RECEIVED_REQUESTS.computeIfAbsent(target, p -> new HashMap<>()).put(player, LongBooleanPair.of(System.currentTimeMillis(), true));
                            player.sendMessage(ServerI18n.translateToLiteral(player, "message.random_command.tpa.request_sent"));
                            target.sendMessage(ServerI18n.translateToLiteral(target, "message.random_command.tpa.request_tpa", player.getEntityName()).copy()
                                    .append(TextUtil.buttonFormat(ServerI18n.translateToLiteral(player, "message.random_command.accept"), "/tpaccept " + player.getEntityName(), Formatting.GREEN, Formatting.BOLD))
                                    .append(TextUtil.buttonFormat(ServerI18n.translateToLiteral(player, "message.random_command.deny"), "/tpdeny " + player.getEntityName(), Formatting.RED, Formatting.BOLD))
                                    .append(TextUtil.buttonFormat(ServerI18n.translateToLiteral(player, "message.random_command.blacklist"), "/tpblacklist " + player.getEntityName(), Formatting.GRAY, Formatting.BOLD))
                            );
                            return 1;
                        })
                ));
    }
}
