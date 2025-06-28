package com.iafenvoy.random.command.forge;

import com.iafenvoy.random.command.CommandHelper;
import com.iafenvoy.random.command.RandomCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(RandomCommand.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class RandomCommandForge {
    public RandomCommandForge() {
        RandomCommand.init();
    }

    @SubscribeEvent
    public static void onRegisterCommand(RegisterCommandsEvent event) {
        CommandHelper.registerCommands(event.getDispatcher());
    }
}
