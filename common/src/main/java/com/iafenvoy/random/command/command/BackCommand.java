package com.iafenvoy.random.command.command;

import com.iafenvoy.random.command.PermissionNodes;
import com.iafenvoy.random.command.data.DataManager;
import com.iafenvoy.random.command.data.component.builtin.BackComponent;
import com.iafenvoy.server.i18n.ServerI18n;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

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
                    if (optional.isPresent()) {
                        BackComponent component = optional.get();
                        ServerWorld world = source.getServer().getWorld(component.pos().world());
                        Vec3d pos = component.pos().pos();
                        if (world != null) {
                            player.teleport(world, pos.x, pos.y, pos.z, player.getYaw(), player.getPitch());
                            ServerI18n.sendMessage(player, "message.random_command.teleporting");
                            return 1;
                        }
                    }
                    return 0;
                }));
    }
}
