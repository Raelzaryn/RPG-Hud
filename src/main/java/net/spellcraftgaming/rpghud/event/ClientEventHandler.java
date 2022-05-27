package net.spellcraftgaming.rpghud.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.spellcraftgaming.rpghud.gui.GuiSettingsMod;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {

    public static void init() {
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }

    @SubscribeEvent
    public void onGuiInit(ScreenEvent.InitScreenEvent event) {
        if (event.getScreen() instanceof TitleScreen ||event.getScreen() instanceof PauseScreen) {
            Minecraft mc = Minecraft.getInstance();
            BaseComponent s = new TranslatableComponent("name.rpghud");
            event.addListener(new Button(event.getScreen().width - mc.font.width(s.getString()) - 8, 0, mc.font.width(s.getString()) + 8, 20, s, button -> {
                mc.setScreen(new GuiSettingsMod(event.getScreen(), new TranslatableComponent("gui.settings.rpghud")));
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
