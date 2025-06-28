package com.iafenvoy.random.command.command;

import com.iafenvoy.random.command.PermissionNodes;
import com.iafenvoy.server.i18n.ServerI18n;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import static net.minecraft.server.command.CommandManager.literal;

public class SpawnCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("spawn")
                .requires(PermissionNodes.SPAWN.require().and(ServerCommandSource::isExecutedByPlayer))
                .executes(ctx -> {
                    ServerCommandSource source = ctx.getSource();
                    ServerPlayerEntity player = source.getPlayerOrThrow();
                    ServerWorld overworld = source.getServer().getOverworld();
                    Vec3d pos = overworld.getSpawnPos().toCenterPos();
                    player.teleport(overworld, pos.x, pos.y, pos.z, player.getYaw(), player.getPitch());
                    ServerI18n.sendMessage(player, "message.random_command.spawn.teleport");
                    return 1;
                }));
    }
}
