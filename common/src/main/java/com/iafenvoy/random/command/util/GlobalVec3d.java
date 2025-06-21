package com.iafenvoy.random.command.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
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
}
