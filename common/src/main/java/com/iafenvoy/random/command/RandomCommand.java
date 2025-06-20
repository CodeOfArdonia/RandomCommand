package com.iafenvoy.random.command;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public final class RandomCommand {
    public static final String MOD_ID = "random_command";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        PermissionNodes.init();
    }
}
