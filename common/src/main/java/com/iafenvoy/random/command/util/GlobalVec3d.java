package com.iafenvoy.random.command.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public record GlobalVec3d(RegistryKey<World> world, Vec3d pos) {
    public static final Codec<GlobalVec3d> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                    World.CODEC.fieldOf("world").forGetter(GlobalVec3d::world),
                    Vec3d.CODEC.fieldOf("pos").forGetter(GlobalVec3d::pos))
            .apply(instance, GlobalVec3d::new));

    public GlobalVec3d(PlayerEntity player) {
        this(player.getWorld().getRegistryKey(), player.getPos());
    }

    public boolean teleport(MinecraftServer server, ServerPlayerEntity player) {
        ServerWorld world = server.getWorld(this.world);
        if (world == null) return false;
        player.teleport(world, this.pos.x, this.pos.y, this.pos.z, player.getYaw(), player.getPitch());
        return true;
    }
}
