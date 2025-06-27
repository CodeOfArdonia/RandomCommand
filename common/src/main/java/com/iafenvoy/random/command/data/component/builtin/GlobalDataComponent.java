package com.iafenvoy.random.command.data.component.builtin;

import com.iafenvoy.random.command.data.component.Component;
import com.iafenvoy.random.command.data.component.ComponentType;
import com.iafenvoy.random.command.data.component.ComponentTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.MinecraftServer;

public class GlobalDataComponent implements Component {
    public static final Codec<GlobalDataComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.INT.fieldOf("lastActionTick").forGetter(GlobalDataComponent::getLastActionTick),
            Codec.BOOL.fieldOf("combating").forGetter(GlobalDataComponent::isCombating)
    ).apply(i, GlobalDataComponent::new));

    private int lastActionTick;
    private boolean combating;

    public GlobalDataComponent() {
        this(0, false);
    }

    public GlobalDataComponent(int lastActionTick, boolean combating) {
        this.lastActionTick = lastActionTick;
        this.combating = combating;
    }

    public boolean isCombating() {
        return this.combating;
    }

    public void setCombating(boolean combating) {
        this.combating = combating;
    }

    public int getLastActionTick() {
        return this.lastActionTick;
    }

    public void setLastActionTick(int lastActionTick) {
        this.lastActionTick = lastActionTick;
    }

    public void updateLastActionTick(MinecraftServer server) {
        this.setLastActionTick(server.getTicks());
    }

    @Override
    public ComponentType<?> getType() {
        return ComponentTypes.GLOBAL;
    }
}
