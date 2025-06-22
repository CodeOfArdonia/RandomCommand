package com.iafenvoy.random.command.command;

import com.iafenvoy.random.command.PermissionNodes;
import com.iafenvoy.server.i18n.ServerI18n;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.literal;

public final class HatCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("hat")
                .requires(PermissionNodes.HAT.require().and(ServerCommandSource::isExecutedByPlayer))
                .executes(ctx -> {
                    ServerCommandSource source = ctx.getSource();
                    ServerPlayerEntity player = source.getPlayerOrThrow();
                    ItemStack stack = player.getMainHandStack(), helmet = player.getEquippedStack(EquipmentSlot.HEAD);
                    if (stack.isEmpty()) {
                        player.sendMessage(ServerI18n.translateToLiteral(player, "message.random_command.hat.empty"));
                        return 0;
                    }
                    player.equipStack(EquipmentSlot.HEAD, stack.split(1));
                    if (!helmet.isEmpty()) player.getInventory().offerOrDrop(helmet);
                    return 1;
                }));
    }
}
