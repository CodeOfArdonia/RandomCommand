package com.iafenvoy.random.command.data.component.builtin;

import com.iafenvoy.random.command.data.component.Component;
import com.iafenvoy.random.command.data.component.ComponentType;
import com.iafenvoy.random.command.data.component.ComponentTypes;
import com.iafenvoy.random.command.util.GlobalVec3d;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class GlobalDataComponent implements Component {
    public static final Codec<GlobalDataComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.INT.fieldOf("lastActionTick").forGetter(GlobalDataComponent::getLastActionTick),
            GlobalVec3d.CODEC.fieldOf("lastPos").forGetter(GlobalDataComponent::getLastPos),
            Codec.BOOL.fieldOf("combating").forGetter(GlobalDataComponent::isCombating)
    ).apply(i, GlobalDataComponent::new));

    private int lastActionTick;
    private GlobalVec3d lastPos;
    private boolean combating;

    public GlobalDataComponent() {
        this(0, new GlobalVec3d(World.OVERWORLD, Vec3d.ZERO), false);
    }

    public GlobalDataComponent(int lastActionTick, GlobalVec3d lastPos, boolean combating) {
        this.lastActionTick = lastActionTick;
        this.lastPos = lastPos;
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

    public GlobalVec3d getLastPos() {
        return this.lastPos;
    }

    public void setLastPos(GlobalVec3d lastPos) {
        this.lastPos = lastPos;
    }

    public boolean isCombating() {
        return this.combating;
    }

    public void setCombating(boolean combating) {
        this.combating = combating;
    }

    @Override
    public ComponentType<?> getType() {
        return ComponentTypes.GLOBAL;
    }
}
