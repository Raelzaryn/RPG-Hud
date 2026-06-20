package net.spellcraftgaming.rpghud.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.spellcraftgaming.rpghud.gui.GuiSettingsMod;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

public class ClientEventHandler {

    public static void init() {
        NeoForge.EVENT_BUS.register(new ClientEventHandler());
    }

    @SubscribeEvent
    public void onGuiInit(ScreenEvent.Init.Pre event) {
        if (ModRPGHud.instance.settings.getBoolValue(Settings.enable_config_button) && (ModRPGHud.instance.settings.getBoolValue(Settings.enable_config_button) && (event.getScreen() instanceof TitleScreen || event.getScreen() instanceof PauseScreen))) {
            Minecraft mc = Minecraft.getInstance();
            Component s = Component.translatable("name.rpghud");

            event.addListener(Button.builder(s, _ -> mc.setScreen(new GuiSettingsMod(event.getScreen(), Component.translatable("gui.rpg.settings")))).bounds(event.getScreen().width - mc.font.width(s.getString()) - 8, 0, mc.font.width(s.getString()) + 8, 20).build());
        }
    }
    
    @SubscribeEvent
    public void onPlayerCloseContainer(PlayerContainerEvent.Close event) {
        ModRPGHud.renderDetailsAgain[0] = true;
        ModRPGHud.renderDetailsAgain[1] = true;
        ModRPGHud.renderDetailsAgain[2] = true;
    }

}
