package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.gui.override.GuiIngameRPGHud;

public class HudElementRecordOverlayVanilla extends HudElement {

    public HudElementRecordOverlayVanilla() {
        super(HudElementType.RECORD_OVERLAY, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return ((GuiIngameRPGHud) this.mc.ingameGUI).getOverlayMessageTime() > 0;
    }

    @Override
    public void drawElement(AbstractGui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        GuiIngameRPGHud theGui = (GuiIngameRPGHud) gui;

        float hue = theGui.getOverlayMessageTime() - partialTicks;
        int opacity = (int) (hue * 256.0F / 20.0F);
        if(opacity > 255)
            opacity = 255;

        if(opacity > 0) {
            GlStateManager.pushMatrix();
            GlStateManager.translatef(scaledWidth / 2, scaledHeight - 68, 0.0F);
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                    GlStateManager.DestFactor.ZERO);
            int color = (theGui.getAnimateOverlayMessageColor() ? MathHelper.hsvToRGB(hue / 50.0F, 0.7F, 0.6F) & 0xFFFFFF : 0xFFFFFF);
            this.mc.fontRenderer.drawStringWithShadow(theGui.getOverlayMessage(), -this.mc.fontRenderer.getStringWidth(theGui.getOverlayMessage()) / 2, -4,
                    color | (opacity << 24));
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

}
