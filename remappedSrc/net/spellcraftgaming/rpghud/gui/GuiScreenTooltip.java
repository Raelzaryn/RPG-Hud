package net.spellcraftgaming.rpghud.gui;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(EnvType.CLIENT)
public class GuiScreenTooltip extends Screen {

    protected GuiScreenTooltip(Text titleIn) {
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
        MinecraftClient mc = MinecraftClient.getInstance();
        TextRenderer fontRenderer = mc.textRenderer;
        GuiScreenTooltip gui = null;
        if(mc.currentScreen instanceof GuiScreenTooltip)
            gui = (GuiScreenTooltip) mc.currentScreen;
        else
            return;

        boolean shouldRenderTooltip = false;
        GuiButtonTooltip button = null;
        for(int x = 0; x < this.children().size(); x++) {
            Element b = this.children().get(x);
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
                    int width = fontRenderer.getWidth(tooltip[id]);
                    if(totalWidth < width)
                        totalWidth = fontRenderer.getWidth(tooltip[id]);
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
                            DrawableHelper.drawStringWithShadow(ms, fontRenderer, tooltip[id], posX + 5, posY - 2 - 12 * (counter - id - 1) - 10, 0xBBBBBB);
                        else
                            DrawableHelper.drawStringWithShadow(ms, fontRenderer,  tooltip[id], posX + 5, posY + 5 + 12 * id, 0xBBBBBB);
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
            RenderSystem.defaultBlendFunc();
            client.textRenderer.draw(ms, text, x, y, 0xFFFFFFFF);
        }
    }

}
