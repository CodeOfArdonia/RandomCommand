package com.iafenvoy.random.command.data.component;

import com.iafenvoy.random.command.RandomCommand;
import com.iafenvoy.random.command.data.component.builtin.BackComponent;
import com.iafenvoy.random.command.data.component.builtin.HomeComponent;
import com.iafenvoy.random.command.data.component.builtin.TourComponent;
import com.iafenvoy.random.command.data.component.builtin.TpaComponent;
import net.minecraft.util.Identifier;

public interface ComponentTypes {
    ComponentType<BackComponent> BACK = new ComponentType<>(Identifier.of(RandomCommand.MOD_ID, "back"), BackComponent.CODEC);
    ComponentType<HomeComponent> HOME = new ComponentType<>(Identifier.of(RandomCommand.MOD_ID, "home"), HomeComponent.CODEC);
    ComponentType<TourComponent> TOUR = new ComponentType<>(Identifier.of(RandomCommand.MOD_ID, "tour"), TourComponent.CODEC);
    ComponentType<TpaComponent> TPA = new ComponentType<>(Identifier.of(RandomCommand.MOD_ID, "tpa"), TpaComponent.CODEC);
}
