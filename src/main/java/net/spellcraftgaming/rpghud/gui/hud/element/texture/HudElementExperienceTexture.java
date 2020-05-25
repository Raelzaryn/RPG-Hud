package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementExperienceTexture extends HudElement {

	public HudElementExperienceTexture() {
		super(HudElementType.EXPERIENCE, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		bind(INTERFACE);
		GlStateManager.color(1f, 1f, 1f);
		int exp = GameData.getPlayerXP();
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.experience_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 35 : 31) + this.settings.getPositionValue(Settings.experience_position)[1];
		gui.drawTexturedModalRect(posX, posY, 0, 132, (int) (88.0D * (exp / (double) GameData.getPlayerXPCap())), 8);
		String stringExp = exp + "/" + GameData.getPlayerXPCap();
		if (this.settings.getBoolValue(Settings.show_numbers_experience)) {
			GlStateManager.scale(0.5D, 0.5D, 0.5D);
			gui.drawCenteredString(GameData.getFontRenderer(), stringExp, posX * 2 + 82, posY * 2 + 4, -1);
			GlStateManager.scale(2.0D, 2.0D, 2.0D);
		}
		GlStateManager.color(1f, 1f, 1f);
		GameData.bindIcons();
	}

}
