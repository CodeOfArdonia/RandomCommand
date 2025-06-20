package com.iafenvoy.random.command.fabric;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class NodeHolderImpl {
    @NotNull
    public static Predicate<ServerCommandSource> register(@NotNull String permission, int defaultRequiredLevel) {
        return Permissions.require(permission, defaultRequiredLevel);
    }
}
