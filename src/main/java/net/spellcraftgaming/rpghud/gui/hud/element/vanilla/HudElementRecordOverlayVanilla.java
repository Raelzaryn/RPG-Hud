package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import java.awt.Color;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.rpghud.gui.GuiIngameRPGHud;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementRecordOverlayVanilla extends HudElement {

	public HudElementRecordOverlayVanilla() {
		super(HudElementType.RECORD_OVERLAY, 0, 0, 0, 0, true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int width = res.getScaledWidth();
		int height = res.getScaledHeight();
		GuiIngameRPGHud guiIngame = (GuiIngameRPGHud) gui;
		if (guiIngame.getOverlayMessageTime() > 0) {
			float hue = guiIngame.getOverlayMessageTime() - partialTicks;
			int opacity = (int) (hue * 256.0F / 20.0F);
			if (opacity > 255)
				opacity = 255;

			if (opacity > 0) {
				GlStateManager.pushMatrix();
				GlStateManager.translate(width / 2, height - 68, 0.0F);
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				int color = (guiIngame.getAnimateOverlayMessageColor() ? Color.HSBtoRGB(hue / 50.0F, 0.7F, 0.6F) & 0xFFFFFF : 0xFFFFFF);
				this.mc.fontRendererObj.drawString(guiIngame.getOverlayMessage(), -this.mc.fontRendererObj.getStringWidth(guiIngame.getOverlayMessage()) / 2, -4, color | (opacity << 24));
				GlStateManager.disableBlend();
				GlStateManager.popMatrix();
			}
		}
	}

}
