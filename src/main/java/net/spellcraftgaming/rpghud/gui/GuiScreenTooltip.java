package net.spellcraftgaming.rpghud.gui;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

public class GuiScreenTooltip extends Screen {

    protected GuiScreenTooltip(ITextComponent titleIn) {
        super(titleIn);
    }

    protected List<GuiTextLabel> labelList = new ArrayList<GuiTextLabel>();

    @Override
    public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
        super.render(ms, mouseX, mouseY, partialTicks);
        for(GuiTextLabel label : labelList) {
            label.render(this, ms);
        }
        if(ModRPGHud.instance.settings.getBoolValue(Settings.enable_button_tooltip)) {
            drawTooltip(ms, mouseX, mouseY);
        }
    }

    /**
     * Checks if a tooltip should be rendered and if so renders it on the screen.
     */
    private void drawTooltip(MatrixStack ms, int mouseX, int mouseY) {
        Minecraft mc = Minecraft.getInstance();
        FontRenderer fontRenderer = mc.fontRenderer;
        GuiScreenTooltip gui = null;
        if(mc.currentScreen instanceof GuiScreenTooltip)
            gui = (GuiScreenTooltip) mc.currentScreen;
        else
            return;

        boolean shouldRenderTooltip = false;
        GuiButtonTooltip button = null;
        for(int x = 0; x < this.buttons.size(); x++) {
            Widget b = this.buttons.get(x);
            if(b instanceof GuiButtonTooltip)
                button = (GuiButtonTooltip) b;

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
            String[] tooltip = button.getTooltip();
            if(!(tooltip == null)) {
                int counter = 0;
                for(int id = 0; id < tooltip.length; id++) {
                    int width = fontRenderer.getStringWidth(tooltip[id]);
                    if(totalWidth < width)
                        totalWidth = fontRenderer.getStringWidth(tooltip[id]);
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
                    fill(ms, posX, posY - 3 - tooltip.length * 12 - 2, posX + totalWidth + 10, posY, 0xA0000000);
                else
                    fill(ms, posX, posY, posX + totalWidth + 10, posY + 3 + tooltip.length * 12 + 2, 0xA0000000);
                for(int id = 0; id < tooltip.length; id++) {
                    if(!tooltip[id].isEmpty()) {
                        if(reverseY)
                            AbstractGui.drawString(ms, fontRenderer, tooltip[id], posX + 5, posY - 2 - 12 * (counter - id - 1) - 10, 0xBBBBBB);
                        else
                            AbstractGui.drawString(ms, fontRenderer, tooltip[id], posX + 5, posY + 5 + 12 * id, 0xBBBBBB);
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

        public void render(Screen gui, MatrixStack ms) {
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                    GlStateManager.DestFactor.ZERO);
            AbstractGui.drawString(ms, minecraft.fontRenderer, text, x, y, 0xFFFFFFFF);
        }
    }

}
