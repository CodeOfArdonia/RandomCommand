package com.iafenvoy.random.command.data.helper;

import com.google.gson.JsonParser;
import com.iafenvoy.random.command.RandomCommand;
import com.iafenvoy.random.command.mixin.WorldSavePathAccessor;
import com.iafenvoy.random.command.util.GlobalVec3d;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;
import org.apache.commons.io.FileUtils;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class WarpHelper {
    private static final WorldSavePath PATH = WorldSavePathAccessor.create("%s/warp.json".formatted(RandomCommand.MOD_ID));
    private static final Codec<Map<String, GlobalVec3d>> CODEC = Codec.unboundedMap(Codec.STRING, GlobalVec3d.CODEC);
    public static final Map<String, GlobalVec3d> DATA = new HashMap<>();

    public static void load(MinecraftServer server) {
        DATA.clear();
        Path path = server.getSavePath(PATH);
        try {
            DATA.putAll(CODEC.parse(JsonOps.INSTANCE, JsonParser.parseReader(new FileReader(path.toFile()))).resultOrPartial(RandomCommand.LOGGER::error).orElseThrow());
        } catch (Exception e) {
            RandomCommand.LOGGER.error("Failed to load {}", path, e);
        }
    }

    public static void save(MinecraftServer server) {
        Path path = server.getSavePath(PATH);
        try {
            FileUtils.write(path.toFile(), CODEC.encodeStart(JsonOps.INSTANCE, DATA).resultOrPartial(RandomCommand.LOGGER::error).orElseThrow().toString(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            RandomCommand.LOGGER.error("Failed to save {}", path, e);
        }
    }
}
