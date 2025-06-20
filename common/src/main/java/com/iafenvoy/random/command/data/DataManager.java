package com.iafenvoy.random.command.data;

import com.iafenvoy.random.command.RandomCommand;
import com.iafenvoy.random.command.Static;
import com.iafenvoy.random.command.command.TpaCommands;
import com.iafenvoy.random.command.mixin.WorldSavePathAccessor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.WorldSavePath;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataManager {
    private static final Map<UUID, PlayerData> DATA = new HashMap<>();
    private static final WorldSavePath ROOT_FOLDER = WorldSavePathAccessor.create(RandomCommand.MOD_ID);
    private static int CD;

    public static void tick() {
        if (Static.SERVER == null) return;
        CD++;
        if (CD >= 20) {
            CD = 0;
            DATA.values().stream().filter(PlayerData::isDirty).forEach(x -> x.save(Static.SERVER));
            TpaCommands.tick();
        }
    }

    public static PlayerData getData(PlayerEntity player) {
        return getData(player.getUuid());
    }

    public static PlayerData getData(UUID uuid) {
        DATA.computeIfAbsent(uuid, u -> Static.SERVER == null ? new PlayerData(u) : PlayerData.load(Static.SERVER, u));
        return DATA.get(uuid);
    }
}
