package com.iafenvoy.random.command.data.component.builtin;

import com.iafenvoy.random.command.data.component.Component;
import com.iafenvoy.random.command.data.component.ComponentType;
import com.iafenvoy.random.command.data.component.ComponentTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record NickComponent(String nick) implements Component {
    public static final Codec<NickComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.STRING.fieldOf("nick").forGetter(NickComponent::nick)
    ).apply(i, NickComponent::new));

    @Override
    public ComponentType<?> getType() {
        return ComponentTypes.NICK;
    }
}
