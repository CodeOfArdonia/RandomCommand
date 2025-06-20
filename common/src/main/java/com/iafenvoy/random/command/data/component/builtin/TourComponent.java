package com.iafenvoy.random.command.data.component.builtin;

import com.iafenvoy.random.command.data.component.Component;
import com.iafenvoy.random.command.data.component.ComponentType;
import com.iafenvoy.random.command.data.component.ComponentTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

public record TourComponent(GameMode lastGameMode, GlobalPos pos) implements Component<TourComponent> {
    public static final Codec<TourComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
            GameMode.CODEC.fieldOf("lastGameMode").forGetter(TourComponent::lastGameMode),
            GlobalPos.CODEC.fieldOf("pos").forGetter(TourComponent::pos)
    ).apply(i, TourComponent::new));

    public TourComponent(GameMode lastGameMode, RegistryKey<World> dimension, BlockPos pos) {
        this(lastGameMode, GlobalPos.create(dimension, pos));
    }

    @Override
    public ComponentType<TourComponent> getType() {
        return ComponentTypes.TOUR;
    }
}
