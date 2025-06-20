package com.iafenvoy.random.command.data.component.builtin;

import com.iafenvoy.random.command.data.component.Component;
import com.iafenvoy.random.command.data.component.ComponentType;
import com.iafenvoy.random.command.data.component.ComponentTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Uuids;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public record TpaComponent(List<UUID> blacklist) implements Component<TpaComponent> {
    public static final Codec<TpaComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
            Uuids.CODEC.listOf().fieldOf("blacklist").forGetter(TpaComponent::blacklist)
    ).apply(i, TpaComponent::new));

    public TpaComponent(List<UUID> blacklist) {
        this.blacklist = new LinkedList<>(blacklist);
    }

    public TpaComponent() {
        this(new LinkedList<>());
    }

    @Override
    public ComponentType<TpaComponent> getType() {
        return ComponentTypes.TPA;
    }
}
