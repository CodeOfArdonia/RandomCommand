package com.iafenvoy.random.command.data.component.builtin;

import com.iafenvoy.random.command.data.component.Component;
import com.iafenvoy.random.command.data.component.ComponentType;
import com.iafenvoy.random.command.data.component.ComponentTypes;
import com.iafenvoy.random.command.util.GlobalVec3d;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record BackComponent(GlobalVec3d pos) implements Component<BackComponent> {
    public static final Codec<BackComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
            GlobalVec3d.CODEC.fieldOf("pos").forGetter(BackComponent::pos)
    ).apply(i, BackComponent::new));

    @Override
    public ComponentType<BackComponent> getType() {
        return ComponentTypes.BACK;
    }
}
