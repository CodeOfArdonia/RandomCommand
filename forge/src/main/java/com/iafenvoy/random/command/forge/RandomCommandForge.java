package com.iafenvoy.random.command.forge;

import com.iafenvoy.random.command.RandomCommand;
import net.minecraftforge.fml.common.Mod;

@Mod(RandomCommand.MOD_ID)
public final class RandomCommandForge {
    public RandomCommandForge() {
        RandomCommand.init();
    }
}
