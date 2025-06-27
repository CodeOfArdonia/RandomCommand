package com.iafenvoy.random.command.data.component;

import com.iafenvoy.random.command.RandomCommand;
import com.iafenvoy.random.command.data.component.builtin.*;
import net.minecraft.util.Identifier;

public interface ComponentTypes {
    ComponentType<GlobalDataComponent> GLOBAL = new ComponentType<>(Identifier.of(RandomCommand.MOD_ID, "global"), GlobalDataComponent.CODEC);

    ComponentType<AfkComponent> AFK = new ComponentType<>(Identifier.of(RandomCommand.MOD_ID, "afk"), AfkComponent.CODEC);
    ComponentType<BackComponent> BACK = new ComponentType<>(Identifier.of(RandomCommand.MOD_ID, "back"), BackComponent.CODEC);
    ComponentType<HomeComponent> HOME = new ComponentType<>(Identifier.of(RandomCommand.MOD_ID, "home"), HomeComponent.CODEC);
    ComponentType<NickComponent> NICK = new ComponentType<>(Identifier.of(RandomCommand.MOD_ID, "nick"), NickComponent.CODEC);
    ComponentType<TourComponent> TOUR = new ComponentType<>(Identifier.of(RandomCommand.MOD_ID, "tour"), TourComponent.CODEC);
    ComponentType<TpaComponent> TPA = new ComponentType<>(Identifier.of(RandomCommand.MOD_ID, "tpa"), TpaComponent.CODEC);
}
