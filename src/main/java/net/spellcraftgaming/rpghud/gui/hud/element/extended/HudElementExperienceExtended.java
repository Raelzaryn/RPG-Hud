package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementExperienceExtended extends HudElement {

	public HudElementExperienceExtended() {
		super(HudElementType.EXPERIENCE, 0, 0, 0, 0, false);
	}

	@Override
	public boolean checkConditions() {
		return GameData.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int exp = GameData.getPlayerXPCap();
		double full = 100D / GameData.getPlayerXPCap();
		int posX = this.settings.render_player_face ? 49 : 25;
		int posY = this.settings.render_player_face ? 35 : 31;
		drawCustomBar(posX, posY, 88, 8, exp * full, -1, -1, this.settings.color_experience, offsetColorPercent(this.settings.color_experience, 25));
		String stringExp = exp + "/" + GameData.getPlayerXPCap();
		if (this.settings.show_numbers_experience) {
			GlStateManager.scale(0.5D, 0.5D, 0.5D);
			gui.drawCenteredString(this.mc.fontRendererObj, stringExp, posX * 2 + 82, posY * 2 + 4, -1);
			GlStateManager.scale(2.0D, 2.0D, 2.0D);
		}
	}

}
