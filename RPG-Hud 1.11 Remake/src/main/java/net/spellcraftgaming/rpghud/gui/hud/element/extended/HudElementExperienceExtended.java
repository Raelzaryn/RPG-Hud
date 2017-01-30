package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.minecraft.client.gui.Gui;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementExperienceExtended extends HudElementBarred{

	public HudElementExperienceExtended() {
		super(HudElementType.EXPERIENCE, 0, 0, 0, 0, false);
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.gameSettings.showDebugInfo && this.mc.playerController.shouldDrawHUD();
	}
	
	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int[] experienceColor = getColor(this.settings.color_experience);
		int exp = (int) (this.mc.player.xpBarCap() * this.mc.player.experience);
		double full = 100D / this.mc.player.xpBarCap();
		drawCustomBar(49, 35, 88, 8, exp * full, 0, 0, experienceColor[2], experienceColor[1]);
		String stringExp = exp + "/" + this.mc.player.xpBarCap();
		if (this.settings.show_numbers_experience) {
			gui.drawCenteredString(this.mc.fontRendererObj, stringExp, 180, 74, -1);
		}
	}

}
