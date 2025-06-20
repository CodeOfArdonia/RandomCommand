package com.iafenvoy.random.command;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class NodeHolder {
    private final String name;
    private final Predicate<ServerCommandSource> requirePredicate;

    public NodeHolder(String name, int defaultRequiredLevel) {
        this.name = "%s.%s".formatted(RandomCommand.MOD_ID, name);
        this.requirePredicate = register(name, defaultRequiredLevel);
    }

    public String getName() {
        return this.name;
    }

    public Predicate<ServerCommandSource> require() {
        return this.requirePredicate;
    }

    @ExpectPlatform
    @NotNull
    private static Predicate<ServerCommandSource> register(@NotNull String permission, int defaultRequiredLevel) {
        throw new AssertionError("This method should be replaced by architectury");
    }
}
