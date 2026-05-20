package net.spellcraftgaming.rpghud.gui;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class GuiScreenTooltip extends Screen {

    protected GuiScreenTooltip(Component titleIn) {
        super(titleIn);
    }

    protected List<GuiTextLabel> labelList = new ArrayList<GuiTextLabel>();

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        for(GuiTextLabel label : labelList) {
            label.render(this, graphics);
        }
        if(ModRPGHud.instance.settings.getBoolValue(Settings.enable_button_tooltip)) {
            drawTooltip(graphics, mouseX, mouseY);
        }
    }

    /**
     * Checks if a tooltip should be rendered and if so renders it on the screen.
     */
    private void drawTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        Minecraft mc = Minecraft.getInstance();
        if (!(mc.screen instanceof GuiScreenTooltip gui)) return;

        boolean shouldRenderTooltip = false;
        GuiButtonTooltip button = null;
        for(int x = 0; x < this.children().size(); x++) {
            GuiEventListener guiEventListener = this.children().get(x);
            if(guiEventListener instanceof GuiButtonTooltip)
                button = (GuiButtonTooltip) guiEventListener;

            if(button != null) {
                if(button.isHovered()) {
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
                for(int id = 0; id < tooltip.length; id++) {
                    int width = Minecraft.getInstance().font.width(tooltip[id]);
                    if(totalWidth < width)
                        totalWidth = Minecraft.getInstance().font.width(tooltip[id]);
                    counter++;
                }
                posX -= totalWidth / 2;
                if((posX + totalWidth + 10) > gui.width)
                    posX -= (posX + totalWidth + 10) - gui.width;
                if(posX < 0)
                    posX = 0;

                if((posY + 3 + tooltip.length * 12 + 2) > gui.height)
                    reverseY = true;

                if(reverseY) {
                	HudElement.drawRect(graphics, posX, posY - 3 - tooltip.length * 12 - 2, totalWidth + 10, 3 + tooltip.length * 12 + 2, 0xC0000000);
                } else {
                	HudElement.drawRect(graphics, posX, posY, totalWidth + 10, 3 + tooltip.length * 12 + 2, 0xC0000000);
                }
                for(int id = 0; id < tooltip.length; id++) {
                    if(!tooltip[id].isEmpty()) {
                        if(reverseY) {
                            graphics.text(this.font, tooltip[id], posX + 5, posY - 2 - 12 * (counter - id - 1) - 10, 0xFFBBBBBB, false);
                        } else {
                            graphics.text(this.font, tooltip[id], posX + 5, posY + 5 + 12 * id, 0xFFBBBBBB, false);
                        }
                    }
                }
            }
        }
    }

    public class GuiTextLabel {
        int x;
        int y;
        String text;

        public GuiTextLabel(int x, int y, String text) {
            this.x = x;
            this.y = y;
            this.text = text;
        }

        public void render(Screen gui, GuiGraphicsExtractor graphics) {
            graphics.text(Minecraft.getInstance().font, this.text, x, y, 0xFFFFFFFF, false);
        }
    }

}
