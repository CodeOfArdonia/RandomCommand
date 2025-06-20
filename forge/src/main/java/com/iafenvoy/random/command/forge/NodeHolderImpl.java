package com.iafenvoy.random.command.forge;

import com.iafenvoy.random.command.RandomCommand;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.permission.PermissionAPI;
import net.minecraftforge.server.permission.events.PermissionGatherEvent;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import net.minecraftforge.server.permission.nodes.PermissionTypes;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NodeHolderImpl {
    private static final List<PermissionNode<?>> REGISTRY = new LinkedList<>();

    @NotNull
    public static Predicate<ServerCommandSource> register(@NotNull String permission, int defaultRequiredLevel) {
        PermissionNode<Boolean> node = new PermissionNode<>(RandomCommand.MOD_ID, permission, PermissionTypes.BOOLEAN, (player, uuid, ctx) -> player != null && player.hasPermissionLevel(defaultRequiredLevel));
        REGISTRY.add(node);
        return source -> {
            try {
                return PermissionAPI.getOfflinePermission(source.getPlayerOrThrow().getUuid(), node);
            } catch (CommandSyntaxException e) {
                return false;
            }
        };
    }

    @SubscribeEvent
    public static void registerNodes(PermissionGatherEvent.Nodes event) {
        event.addNodes(REGISTRY);
    }
}
