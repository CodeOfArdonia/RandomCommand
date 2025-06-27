package com.iafenvoy.random.command.command;

import com.iafenvoy.random.command.PermissionNodes;
import com.iafenvoy.random.command.data.DataManager;
import com.iafenvoy.random.command.data.PlayerData;
import com.iafenvoy.random.command.data.component.builtin.TourComponent;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;

import java.util.Optional;

import static net.minecraft.server.command.CommandManager.literal;

public final class TourCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("tour")
                .requires(PermissionNodes.TOUR.require().and(ServerCommandSource::isExecutedByPlayer))
                .executes(ctx -> {
                    ServerCommandSource source = ctx.getSource();
                    ServerPlayerEntity player = source.getPlayerOrThrow();
                    PlayerData data = DataManager.getData(player);
                    if (player.isSpectator()) {
                        Optional<TourComponent> optional = data.getComponent(TourComponent.class);
                        if (optional.isEmpty()) return 0;
                        TourComponent component = optional.get();
                        component.pos().teleport(source.getServer(), player);
                        player.changeGameMode(component.lastGameMode());
                        data.removeComponent(TourComponent.class);
                    } else {
                        data.setComponent(new TourComponent(player.interactionManager.getGameMode(), player.getWorld().getRegistryKey(), player.getPos()));
                        player.changeGameMode(GameMode.SPECTATOR);
                    }
                    return 1;
                }));
    }
}
