package net.spellcraftgaming.rpghud.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.spellcraftgaming.rpghud.gui.GuiSettingsMod;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

public class ClientEventHandler {

    public static void init() {
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }

    @SubscribeEvent
    public void onGuiInit(ScreenEvent.Init event) {
        if (event.getScreen() instanceof TitleScreen ||event.getScreen() instanceof PauseScreen) {
            Minecraft mc = Minecraft.getInstance();
            Component s = Component.translatable("name.rpghud");
            
            event.addListener(new Button(event.getScreen().width - mc.font.width(s.getString()) - 8, 0, mc.font.width(s.getString()) + 8, 20, s, (button) -> {
            	mc.setScreen(new GuiSettingsMod(event.getScreen(), Component.translatable("gui.settings.rpghud")));
            }));
        }
    }
    
    @SubscribeEvent
    public void onPlayerCloseContainer(PlayerContainerEvent.Close event) {
        ModRPGHud.renderDetailsAgain[0] = true;
        ModRPGHud.renderDetailsAgain[1] = true;
        ModRPGHud.renderDetailsAgain[2] = true;
    }

}
