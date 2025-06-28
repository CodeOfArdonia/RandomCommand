package com.iafenvoy.random.command.data.helper;

import com.iafenvoy.random.command.data.PlayerData;
import com.iafenvoy.random.command.data.component.builtin.AfkComponent;
import com.iafenvoy.random.command.util.GlobalVec3d;
import com.iafenvoy.server.i18n.ServerI18n;
import net.minecraft.server.network.ServerPlayerEntity;

public final class AfkHelper {
    public static void enter(ServerPlayerEntity player, PlayerData data) {
        data.setComponent(new AfkComponent(new GlobalVec3d(player)));
        ServerI18n.broadcast(player.server, "message.random_command.afk.enter", player.getEntityName());
    }

    public static void leave(ServerPlayerEntity player, PlayerData data) {
        data.removeComponent(AfkComponent.class);
        ServerI18n.broadcast(player.server, "message.random_command.afk.leave", player.getEntityName());
    }
}
