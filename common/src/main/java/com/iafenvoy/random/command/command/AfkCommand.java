package com.iafenvoy.random.command.command;

import com.iafenvoy.random.command.PermissionNodes;
import com.iafenvoy.random.command.data.helper.AfkHelper;
import com.iafenvoy.random.command.data.DataManager;
import com.iafenvoy.random.command.data.PlayerData;
import com.iafenvoy.random.command.data.component.builtin.AfkComponent;
import com.iafenvoy.random.command.data.component.builtin.GlobalDataComponent;
import com.iafenvoy.server.i18n.ServerI18n;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;

import static net.minecraft.server.command.CommandManager.literal;

public final class AfkCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("afk")
                .requires(PermissionNodes.AFK.require().and(ServerCommandSource::isExecutedByPlayer))
                .executes(ctx -> {
                    ServerCommandSource source = ctx.getSource();
                    ServerPlayerEntity player = source.getPlayerOrThrow();
                    PlayerData data = DataManager.getData(player);
                    GlobalDataComponent component = data.getGlobalData();
                    if (component.isCombating()) {
                        ServerI18n.sendMessage(player, "message.random_command.afk.in_combat");
                        return 0;
                    }
                    Optional<AfkComponent> optional = data.getComponent(AfkComponent.class);
                    if (optional.isPresent()) AfkHelper.leave(player, data);
                    else AfkHelper.enter(player, data);
                    component.updateLastActionTick(source.getServer());
                    return 1;
                }));
    }
}
