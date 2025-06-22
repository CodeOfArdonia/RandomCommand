package com.iafenvoy.random.command.command;

import com.iafenvoy.random.command.PermissionNodes;
import com.iafenvoy.server.i18n.ServerI18n;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public final class LightningCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("lightning")
                .requires(PermissionNodes.LIGHTNING.require().and(ServerCommandSource::isExecutedByPlayer))
                .then(argument("player", EntityArgumentType.player())
                        .executes(ctx -> {
                            ServerCommandSource source = ctx.getSource();
                            ServerPlayerEntity player = source.getPlayerOrThrow(), target = EntityArgumentType.getPlayer(ctx, "player");
                            LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, source.getWorld());
                            lightning.setPosition(target.getPos());
                            lightning.setCosmetic(false);
                            source.getWorld().spawnEntity(lightning);
                            player.sendMessage(ServerI18n.translateToLiteral(player, "message.random_command.lightning.hit_player", target.getEntityName()));
                            target.sendMessage(ServerI18n.translateToLiteral(target, "message.random_command.lightning.hit"));
                            return 1;
                        })));
    }
}
