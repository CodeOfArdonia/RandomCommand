package com.iafenvoy.random.command.data.component.builtin;

import com.iafenvoy.random.command.data.component.Component;
import com.iafenvoy.random.command.data.component.ComponentType;
import com.iafenvoy.random.command.data.component.ComponentTypes;
import com.iafenvoy.random.command.util.GlobalVec3d;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record AfkComponent(GlobalVec3d pos) implements Component {
    public static final Codec<AfkComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
            GlobalVec3d.CODEC.fieldOf("pos").forGetter(AfkComponent::pos)
    ).apply(i, AfkComponent::new));

    @Override
    public ComponentType<?> getType() {
        return ComponentTypes.AFK;
    }
}
