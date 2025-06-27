package com.iafenvoy.random.command.mixin;

import com.iafenvoy.random.command.RandomCommand;
import com.iafenvoy.random.command.data.DataManager;
import com.iafenvoy.random.command.data.PlayerData;
import com.iafenvoy.random.command.data.component.builtin.AfkComponent;
import com.iafenvoy.random.command.data.component.builtin.NickComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @ModifyVariable(method = "getDisplayName", at = @At("STORE"))
    public MutableText injected(MutableText origin) {
        PlayerEntity self = (PlayerEntity) (Object) this;
        if (self instanceof ServerPlayerEntity) {
            PlayerData data = DataManager.getData(self);
            MutableText name = random_command$getNicknameStyledName(self, origin, data);
            return data.getComponent(AfkComponent.class).isPresent() ? Text.empty().append("[AFK] ").append(name) : name;
        } else return origin;
    }

    @Unique
    private static MutableText random_command$getNicknameStyledName(PlayerEntity player, MutableText teamedName, PlayerData data) {
        try {
            Optional<NickComponent> optional = data.getComponent(NickComponent.class);
            if (optional.isPresent())
                return Team.decorateName(player.getScoreboardTeam(), Text.literal(optional.get().nick()).setStyle(Style.EMPTY.withClickEvent(teamedName.getStyle().getClickEvent())));
        } catch (Exception e) {
            RandomCommand.LOGGER.error("Failed to get nick name", e);
        }
        return teamedName;
    }
}
