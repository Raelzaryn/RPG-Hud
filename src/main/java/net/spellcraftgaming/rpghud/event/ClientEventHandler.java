package net.spellcraftgaming.rpghud.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
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
    public void onGuiInit(InitGuiEvent event) {
        if (event.getGui() instanceof MainMenuScreen || event.getGui() instanceof IngameMenuScreen) {
            Minecraft mc = Minecraft.getInstance();
            ITextComponent s = new TranslationTextComponent("name.rpghud");
            event.addWidget(new Button(event.getGui().width - mc.fontRenderer.getStringWidth(s.getString()) - 8, 0, mc.fontRenderer.getStringWidth(s.getString()) + 8, 20, s, button -> {
                mc.displayGuiScreen(new GuiSettingsMod(event.getGui(), new TranslationTextComponent("gui.settings.rpghud")));
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
