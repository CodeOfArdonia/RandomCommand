package com.iafenvoy.random.command.command;

import com.iafenvoy.random.command.PermissionNodes;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SkullItem;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public final class SkullCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("skull")
                .requires(PermissionNodes.SKULL.require().and(ServerCommandSource::isExecutedByPlayer))
                .then(argument("player", StringArgumentType.word())
                        .executes(ctx -> {
                            ServerCommandSource source = ctx.getSource();
                            ServerPlayerEntity player = source.getPlayerOrThrow();
                            String name = StringArgumentType.getString(ctx, "player");
                            ItemStack stack = new ItemStack(Items.PLAYER_HEAD);
                            stack.getOrCreateNbt().putString(SkullItem.SKULL_OWNER_KEY, name);
                            player.getInventory().offerOrDrop(stack);
                            return 1;
                        })));
    }
}
