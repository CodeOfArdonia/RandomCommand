package com.iafenvoy.random.command.data.component;

import com.mojang.serialization.Codec;

public interface Component<T extends Component<T>> {
    Codec<Component<?>> CODEC = ComponentType.REGISTRY.getCodec().dispatch("type", Component::getType, ComponentType::codec);

    ComponentType<T> getType();
}
