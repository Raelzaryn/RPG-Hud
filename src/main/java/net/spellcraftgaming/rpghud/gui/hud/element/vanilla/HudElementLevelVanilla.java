package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementLevelVanilla extends HudElement {

	public HudElementLevelVanilla() {
		super(HudElementType.LEVEL, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD() && this.mc.thePlayer.experienceLevel > 0;
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		GlStateManager.disableBlend();
		ScaledResolution res = new ScaledResolution(this.mc);
		int width = res.getScaledWidth();
		int height = res.getScaledHeight();
		int color = 8453920;
		String text = String.valueOf(this.mc.thePlayer.experienceLevel);
		int x = (width - this.mc.fontRendererObj.getStringWidth(text)) / 2;
		int y = height - 31 - 4;
		this.mc.fontRendererObj.drawString(text, x + 1, y, 0);
		this.mc.fontRendererObj.drawString(text, x - 1, y, 0);
		this.mc.fontRendererObj.drawString(text, x, y + 1, 0);
		this.mc.fontRendererObj.drawString(text, x, y - 1, 0);
		this.mc.fontRendererObj.drawString(text, x, y, color);
		GlStateManager.enableBlend();
	}

}
