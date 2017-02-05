package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementExperienceExtended extends HudElementBarred {

	public HudElementExperienceExtended() {
		super(HudElementType.EXPERIENCE, 0, 0, 0, 0, false);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int exp = (int) (this.mc.player.xpBarCap() * this.mc.player.experience);
		double full = 100D / this.mc.player.xpBarCap();
		drawCustomBar(49, 35, 88, 8, exp * full, -1, -1, this.settings.color_experience, offsetColorPercent(this.settings.color_experience, 25));
		String stringExp = exp + "/" + this.mc.player.xpBarCap();
		if (this.settings.show_numbers_experience) {
			GlStateManager.scale(0.5D, 0.5D, 0.5D);
			gui.drawCenteredString(this.mc.fontRendererObj, stringExp, 180, 74, -1);
			GlStateManager.scale(2.0D, 2.0D, 2.0D);
		}
	}

}
