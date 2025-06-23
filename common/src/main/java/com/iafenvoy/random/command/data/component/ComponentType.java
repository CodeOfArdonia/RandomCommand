package com.iafenvoy.random.command.data.component;

import com.iafenvoy.random.command.RandomCommand;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public record ComponentType<T extends Component>(Codec<T> codec) {
    public static final RegistryKey<Registry<ComponentType<?>>> REGISTRY_KEY = RegistryKey.ofRegistry(Identifier.of(RandomCommand.MOD_ID, "component_type"));
    public static final Registry<ComponentType<?>> REGISTRY = new SimpleRegistry<>(REGISTRY_KEY, Lifecycle.stable());

    public ComponentType(Identifier id, Codec<T> codec) {
        this(codec);
        Registry.register(REGISTRY, id, this);
    }
}
