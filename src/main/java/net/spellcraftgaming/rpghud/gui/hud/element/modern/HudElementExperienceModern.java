package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementExperienceModern extends HudElement {

	public HudElementExperienceModern() {
		super(HudElementType.EXPERIENCE, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return GameData.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int width = res.getScaledWidth();
		int height = res.getScaledHeight();
		int exp = GameData.getPlayerXP();
		double full = ((double) (width - 2)) / GameData.getPlayerXPCap();

		drawRect(0, height - 7, width, 7, 0xA0000000);
		drawRect(1, height - 6, (int) (exp * full), 4, this.settings.color_experience);
		String stringExp = exp + "/" + GameData.getPlayerXPCap();

		if (this.settings.show_numbers_experience) {
			int width2 = this.mc.fontRendererObj.getStringWidth(stringExp) / 2;
			drawRect(1, height - 15, width2 + 4, 8, 0xA0000000);
			GlStateManager.scale(0.5D, 0.5D, 0.5D);
			gui.drawString(this.mc.fontRendererObj, stringExp, 6, (height - 12) * 2 - 1, -1);
			GlStateManager.scale(2.0D, 2.0D, 2.0D);
		}
	}

}
