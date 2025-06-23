package com.iafenvoy.random.command.data.component.builtin;

import com.iafenvoy.random.command.data.component.Component;
import com.iafenvoy.random.command.data.component.ComponentType;
import com.iafenvoy.random.command.data.component.ComponentTypes;
import com.iafenvoy.random.command.util.GlobalVec3d;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

public record TourComponent(GameMode lastGameMode, GlobalVec3d pos) implements Component {
    public static final Codec<TourComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
            GameMode.CODEC.fieldOf("lastGameMode").forGetter(TourComponent::lastGameMode),
            GlobalVec3d.CODEC.fieldOf("pos").forGetter(TourComponent::pos)
    ).apply(i, TourComponent::new));

    public TourComponent(GameMode lastGameMode, RegistryKey<World> dimension, Vec3d pos) {
        this(lastGameMode, new GlobalVec3d(dimension, pos));
    }

    @Override
    public ComponentType<?> getType() {
        return ComponentTypes.TOUR;
    }
}
