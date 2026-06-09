package net.spellcraftgaming.rpghud.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

import java.util.ArrayList;
import java.util.List;

public class GuiScreenTooltip extends Screen {

    protected GuiScreenTooltip(Component titleIn) {
        super(titleIn);
    }

    protected final List<GuiTextLabel> labelList = new ArrayList<>();

    @Override
    public void extractRenderState(GuiGraphicsExtractor gg, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(gg, mouseX, mouseY, partialTicks);
        for(GuiTextLabel label : labelList) {
            label.render(this, gg);
        }
        if(ModRPGHud.instance.settings.getBoolValue(Settings.enable_button_tooltip)) {
            drawTooltip(gg, mouseX, mouseY);
        }
    }

    /**
     * Checks if a tooltip should be rendered and if so renders it on the screen.
     */
    private void drawTooltip(GuiGraphicsExtractor gg, int mouseX, int mouseY) {
        Minecraft mc = Minecraft.getInstance();
        Font fontRenderer = mc.font;
        GuiScreenTooltip gui;
        if(mc.screen instanceof GuiScreenTooltip)
            gui = (GuiScreenTooltip) mc.screen;
        else
            return;

        boolean shouldRenderTooltip = false;
        GuiButtonTooltip button = null;
        for(int x = 0; x < this.children().size(); x++) {
            GuiEventListener b = this.children().get(x);
            if(b instanceof GuiButtonTooltip)
                button = (GuiButtonTooltip) b;

            if(button != null) {
                if(button.isHoveredOrFocused()) {
                    shouldRenderTooltip = true;
                    break;
                }
            }
        }
        if(shouldRenderTooltip) {
            int posX = mouseX + 5;
            int posY = mouseY + 5;
            int totalWidth = 0;
            boolean reverseY = false;
            String[] tooltip = button.getTooltipNew();
            if(!(tooltip == null)) {
                int counter = 0;
	            for(String s : tooltip) {
		            int width = fontRenderer.width(s);
		            if(totalWidth < width)
			            totalWidth = fontRenderer.width(s);
		            counter++;
	            }
                posX -= totalWidth / 2;
                if((posX + totalWidth + 10) > gui.width)
                    posX -= (posX + totalWidth + 10) - gui.width;
                if(posX < 0)
                    posX = 0;

                if((posY + 3 + tooltip.length * 12 + 2) > gui.height)
                    reverseY = true;

                if(reverseY)
                	HudElement.drawRect(gg, posX, posY - 3 - tooltip.length * 12 - 2, totalWidth + 10, 3 + tooltip.length * 12 + 2, 0xC0000000);
                else
                	HudElement.drawRect(gg, posX, posY, totalWidth + 10, 3 + tooltip.length * 12 + 2, 0xC0000000);
                for(int id = 0; id < tooltip.length; id++) {
                    if(!tooltip[id].isEmpty()) {
                        if(reverseY) {
                            gg.text(fontRenderer, tooltip[id], posX + 5, posY - 2 - 12 * (counter - id - 1) - 10, 0xFFBBBBBB);
                        } else {
                            gg.text(fontRenderer, tooltip[id], posX + 5, posY + 5 + 12 * id, 0xFFBBBBBB);
                        }
                    }
                }

            }
        }
    }

    public class GuiTextLabel {
        final int x;
        final int y;
        final String text;

        public GuiTextLabel(int x, int y, String text) {
            this.x = x;
            this.y = y;
            this.text = text;
        }

        public void render(Screen gui, GuiGraphicsExtractor gg) {
            gg.text(minecraft.font, text, x, y, 0xFFFFFFFF);
        }
    }

}
