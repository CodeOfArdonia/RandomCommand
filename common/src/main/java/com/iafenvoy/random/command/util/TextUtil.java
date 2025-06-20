package com.iafenvoy.random.command.util;

import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public final class TextUtil {
    public static Text buttonFormat(Text text, String command, Formatting... formattings) {
        return text.copy().formatted(formattings).fillStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command)));
    }
}
