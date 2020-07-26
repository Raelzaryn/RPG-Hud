package net.spellcraftgaming.rpghud.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class GuiScreenTooltip extends GuiScreen {

    protected List<GuiTextLabel> labelList = new ArrayList<>();

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        for(GuiTextLabel label : this.labelList)
            label.render(this);
        if(ModRPGHud.instance.settings.getBoolValue(Settings.enable_button_tooltip))
            this.drawTooltip(mouseX, mouseY);
    }

    /**
     * Checks if a tooltip should be rendered and if so renders it on the screen.
     */
    private void drawTooltip(int mouseX, int mouseY) {
        Minecraft mc = Minecraft.getInstance();
        FontRenderer fontRenderer = mc.fontRenderer;
        GuiScreenTooltip gui = null;
        if(mc.currentScreen instanceof GuiScreenTooltip)
            gui = (GuiScreenTooltip) mc.currentScreen;
        else
            return;

        boolean shouldRenderTooltip = false;
        GuiButtonTooltip button = null;
        for(GuiButton b : this.buttons) {
            if(b instanceof GuiButtonTooltip)
                button = (GuiButtonTooltip) b;

            if(button != null)
                if(button.isMouseOver()) {
                    shouldRenderTooltip = true;
                    break;
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
                for(String element : tooltip) {
                    int width = fontRenderer.getStringWidth(element);
                    if(totalWidth < width)
                        totalWidth = fontRenderer.getStringWidth(element);
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
                    drawRect(posX, posY - 3 - tooltip.length * 12 - 2, posX + totalWidth + 10, posY, 0xA0000000);
                else
                    drawRect(posX, posY, posX + totalWidth + 10, posY + 3 + tooltip.length * 12 + 2, 0xA0000000);
                for(int id = 0; id < tooltip.length; id++)
                    if(!tooltip[id].isEmpty())
                        if(reverseY)
                            gui.drawString(fontRenderer, tooltip[id], posX + 5, posY - 2 - 12 * (counter - id - 1) - 10, 0xBBBBBB);
                        else
                            gui.drawString(fontRenderer, tooltip[id], posX + 5, posY + 5 + 12 * id, 0xBBBBBB);
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

        public void render(GuiScreen gui) {
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                    GlStateManager.DestFactor.ZERO);
            gui.drawString(Minecraft.getInstance().fontRenderer, this.text, this.x, this.y, 0xFFFFFFFF);
        }
    }

}
