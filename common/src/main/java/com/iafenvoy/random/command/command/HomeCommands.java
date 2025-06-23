package com.iafenvoy.random.command.command;

import com.iafenvoy.random.command.PermissionNodes;
import com.iafenvoy.random.command.data.DataManager;
import com.iafenvoy.random.command.data.component.builtin.HomeComponent;
import com.iafenvoy.random.command.util.GlobalVec3d;
import com.iafenvoy.server.i18n.ServerI18n;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class HomeCommands {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("home")
                .requires(PermissionNodes.HOME.require().and(ServerCommandSource::isExecutedByPlayer))
                .executes(ctx -> {
                    ServerCommandSource source = ctx.getSource();
                    ServerPlayerEntity player = source.getPlayerOrThrow();
                    Optional<HomeComponent> optional = DataManager.getData(player).getComponent(HomeComponent.class);
                    if (optional.isEmpty()) {
                        ServerI18n.sendMessage(player, "message.random_command.home.no_home");
                        return 0;
                    }
                    HomeComponent component = optional.get();
                    ServerWorld world = source.getServer().getWorld(component.pos().world());
                    Vec3d pos = component.pos().pos();
                    if (world != null) {
                        player.teleport(world, pos.x, pos.y, pos.z, player.getYaw(), player.getPitch());
                        ServerI18n.sendMessage(player, "message.random_command.teleporting");
                        return 1;
                    }
                    return 0;
                }).then(argument("player", EntityArgumentType.player())
                        .requires(PermissionNodes.HOME_OTHER.require().and(ServerCommandSource::isExecutedByPlayer))
                        .executes(ctx -> {
                            ServerCommandSource source = ctx.getSource();
                            ServerPlayerEntity player = source.getPlayerOrThrow(), target = EntityArgumentType.getPlayer(ctx, "player");
                            Optional<HomeComponent> optional = DataManager.getData(target).getComponent(HomeComponent.class);
                            if (optional.isEmpty()) {
                                ServerI18n.sendMessage(player, "message.random_command.home.no_home_other");
                                return 0;
                            }
                            HomeComponent component = optional.get();
                            ServerWorld world = source.getServer().getWorld(component.pos().world());
                            Vec3d pos = component.pos().pos();
                            if (world != null) {
                                player.teleport(world, pos.x, pos.y, pos.z, player.getYaw(), player.getPitch());
                                ServerI18n.sendMessage(player, "message.random_command.teleporting");
                                return 1;
                            }
                            return 0;
                        })));
        dispatcher.register(literal("sethome")
                .requires(PermissionNodes.SET_HOME.require().and(ServerCommandSource::isExecutedByPlayer))
                .executes(ctx -> {
                    ServerCommandSource source = ctx.getSource();
                    ServerPlayerEntity player = source.getPlayerOrThrow();
                    DataManager.getData(player).setComponent(new HomeComponent(new GlobalVec3d(player.getWorld().getRegistryKey(), player.getPos())));
                    ServerI18n.sendMessage(player, "message.random_command.home.set_home");
                    return 1;
                })
        );
    }
}
