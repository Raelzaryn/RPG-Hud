package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementExperienceExtended extends HudElement {

	public HudElementExperienceExtended() {
		super(HudElementType.EXPERIENCE, 0, 0, 0, 0, false);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return GameData.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int exp = GameData.getPlayerXPCap();
		double full = 100D / GameData.getPlayerXPCap();
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.experience_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 35 : 31) + this.settings.getPositionValue(Settings.experience_position)[1];

		drawCustomBar(posX, posY, 88, 8, exp * full, -1, -1, this.settings.getIntValue(Settings.color_experience), offsetColorPercent(this.settings.getIntValue(Settings.color_experience), 25));

		String stringExp =  this.settings.getBoolValue(Settings.experience_percentage) ? (int) Math.floor((double) exp / (double) GameData.getPlayerXPCap() * 100) + "%" : exp + "/" + GameData.getPlayerXPCap();

		if (this.settings.getBoolValue(Settings.show_numbers_experience)) {
			GlStateManager.scale(0.5D, 0.5D, 0.5D);
			gui.drawCenteredString(GameData.getFontRenderer(), stringExp, posX * 2 + 88, posY * 2 + 4, -1);
			GlStateManager.scale(2.0D, 2.0D, 2.0D);
		}
	}

}
