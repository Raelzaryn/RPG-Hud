package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementExperienceDefault extends HudElementBarred{

	public HudElementExperienceDefault() {
		super(HudElementType.EXPERIENCE, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.gameSettings.showDebugInfo && this.mc.playerController.shouldDrawHUD();
	}
	
	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int width = res.getScaledWidth();
		int height = res.getScaledHeight();
		int[] experienceColor = getColor(this.settings.color_experience);
		int exp = (int) (this.mc.player.xpBarCap() * this.mc.player.experience);
		double full = 100D / this.mc.player.xpBarCap();
		GlStateManager.disableLighting();
		drawCustomBar(0, height - 10, width, 10, exp * full, experienceColor[0], experienceColor[1]);
		String stringExp = exp + "/" + this.mc.player.xpBarCap();
		int var7 = width / 2;
		if (this.settings.show_numbers_experience) gui.drawCenteredString(this.mc.fontRendererObj, stringExp, var7, height - 9, -1);
	}

}
