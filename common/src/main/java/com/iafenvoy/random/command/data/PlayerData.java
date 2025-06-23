package com.iafenvoy.random.command.data;

import com.google.gson.JsonParser;
import com.iafenvoy.random.command.RandomCommand;
import com.iafenvoy.random.command.data.component.Component;
import com.iafenvoy.random.command.mixin.WorldSavePathAccessor;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Uuids;
import net.minecraft.util.WorldSavePath;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class PlayerData {
    private static final WorldSavePath PLAYER_DATA = WorldSavePathAccessor.create("%s/player".formatted(RandomCommand.MOD_ID));
    public static final Codec<PlayerData> CODEC = RecordCodecBuilder.create(i -> i.group(
            Uuids.CODEC.fieldOf("uuid").forGetter(PlayerData::getUuid),
            Codec.STRING.fieldOf("name").forGetter(PlayerData::getName),
            Codec.STRING.fieldOf("ip").forGetter(PlayerData::getIp),
            Component.CODEC.listOf().fieldOf("components").forGetter(x -> x.components)
    ).apply(i, PlayerData::new));
    private boolean dirty;
    private final UUID uuid;
    private String name = "";
    private String ip = "127.0.0.1";
    private final List<Component> components = new LinkedList<>();

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }

    private PlayerData(UUID uuid, String name, String ip, List<Component> components) {
        this.uuid = uuid;
        this.name = name;
        this.ip = ip;
        this.components.addAll(components);
    }

    private static Path joinPath(@NotNull MinecraftServer server, UUID uuid) {
        return server.getSavePath(PLAYER_DATA).resolve(uuid.toString() + ".json");
    }

    public static PlayerData load(@NotNull MinecraftServer server, UUID uuid) {
        Path path = joinPath(server, uuid);
        if (Files.exists(path))
            try {
                return CODEC.parse(JsonOps.INSTANCE, JsonParser.parseReader(new FileReader(path.toFile()))).resultOrPartial(RandomCommand.LOGGER::error).orElseThrow();
            } catch (Exception e) {
                RandomCommand.LOGGER.error("Failed to load {}", path, e);
            }
        return new PlayerData(uuid);
    }

    public void save(@NotNull MinecraftServer server) {
        Path path = joinPath(server, this.uuid);
        try {
            FileUtils.write(path.toFile(), CODEC.encodeStart(JsonOps.INSTANCE, this).resultOrPartial(RandomCommand.LOGGER::error).orElseThrow().toString(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            RandomCommand.LOGGER.error("Failed to save {}", path, e);
        }
    }

    public void markDirty() {
        this.dirty = true;
    }

    public boolean isDirty() {
        boolean d = this.dirty;
        this.dirty = false;
        return d;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public void updateName(String name) {
        this.name = name;
        this.markDirty();
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
        this.markDirty();
    }

    public <T extends Component> T getOrCreateComponent(@NotNull Class<T> clazz, Supplier<T> mapper) {
        Optional<T> optional = this.getComponent(clazz);
        if (optional.isPresent()) return optional.get();
        T component = mapper.get();
        this.setComponent(component);
        return component;
    }

    public <T extends Component> Optional<T> getComponent(@NotNull Class<T> clazz) {
        return this.components.stream().filter(x -> clazz.isAssignableFrom(x.getClass())).map(clazz::cast).findAny();
    }

    public <T extends Component> void setComponent(@NotNull T component) {
        this.components.removeIf(x -> component.getClass().isAssignableFrom(x.getClass()));
        this.components.add(component);
        this.markDirty();
    }

    public <T extends Component> void removeComponent(@NotNull Class<T> clazz) {
        this.components.removeIf(x -> clazz.isAssignableFrom(x.getClass()));
        this.markDirty();
    }
}
