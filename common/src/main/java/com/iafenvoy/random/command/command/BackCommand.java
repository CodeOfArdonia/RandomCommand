package com.iafenvoy.random.command.command;

import com.iafenvoy.random.command.PermissionNodes;
import com.iafenvoy.random.command.data.DataManager;
import com.iafenvoy.random.command.data.component.builtin.BackComponent;
import com.iafenvoy.server.i18n.ServerI18n;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;

import static net.minecraft.server.command.CommandManager.literal;

public final class BackCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("back")
                .requires(PermissionNodes.BACK.require().and(ServerCommandSource::isExecutedByPlayer))
                .executes(ctx -> {
                    ServerCommandSource source = ctx.getSource();
                    ServerPlayerEntity player = source.getPlayerOrThrow();
                    Optional<BackComponent> optional = DataManager.getData(player).getComponent(BackComponent.class);
                    if (optional.isPresent() && optional.get().pos().teleport(source.getServer(), player)) {
                        ServerI18n.sendMessage(player, "message.random_command.teleporting");
                        return 1;
                    }
                    ServerI18n.sendMessage(player, "message.random_command.back.no_back_point");
                    return 0;
                }));
    }
}
