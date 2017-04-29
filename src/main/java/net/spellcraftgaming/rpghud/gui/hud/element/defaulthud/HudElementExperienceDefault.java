package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementExperienceDefault extends HudElement {

	public HudElementExperienceDefault() {
		super(HudElementType.EXPERIENCE, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return GameData.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks, double scale) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int width = res.getScaledWidth();
		int height = res.getScaledHeight();
		int exp = GameData.getPlayerXP();
		int expCap = GameData.getPlayerXPCap();
		double full = 100D / expCap;
		GlStateManager.disableLighting();
		drawCustomBar(0, height - 10, width, 10, exp * full, this.settings.getIntValue(Settings.color_experience), offsetColorPercent(this.settings.getIntValue(Settings.color_experience), 25));
		String stringExp = exp + "/" + expCap;
		int var7 = width / 2;
		if (this.settings.getBoolValue(Settings.show_numbers_experience))
			gui.drawCenteredString(this.mc.fontRendererObj, stringExp, var7, height - 9, -1);
	}

}
