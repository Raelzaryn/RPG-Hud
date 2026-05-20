package net.spellcraftgaming.rpghud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.level.GameType;

import java.util.Optional;

public class RPGHudUtils {
    public static boolean isSurvival() {
        return Optional.ofNullable(Minecraft.getInstance().player)
                .map(AbstractClientPlayer::gameMode)
                .map(GameType::isSurvival)
                .orElse(false);
    }
}
