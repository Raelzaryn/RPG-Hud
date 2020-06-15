package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHealthTexture extends HudElement {

	public HudElementHealthTexture() {
		super(HudElementType.HEALTH, 0, 0, 0, 0, false);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return GameData.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		bind(INTERFACE);
		GlStateManager.color(1f, 1f, 1f);
		int health = GameData.getPlayerHealth();
		int healthMax = GameData.getPlayerMaxHealth();
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.health_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 9 : 5) + this.settings.getPositionValue(Settings.health_position)[1];
		int absorption = GameData.getPlayerAbsorption();
		if (absorption > 1)
			gui.drawTexturedModalRect(posX, posY, 0, 88, (int) (110.0D * ((double) (health + absorption) / (double) (healthMax + absorption))), 12);
		if (GameData.isPlayerPoisoned()) {
			gui.drawTexturedModalRect(posX, posY, 141, 160, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12);
		} else if (GameData.isPlayerWithering()) {
			gui.drawTexturedModalRect(posX, posY, 34, 244, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12);
		} else {
			gui.drawTexturedModalRect(posX, posY, 0, 100, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12);
		}

		String stringHealth = this.settings.getBoolValue(Settings.health_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%" : (health + absorption) + "/" + healthMax;
		if (this.settings.getBoolValue(Settings.show_numbers_health))
			gui.drawCenteredString(GameData.getFontRenderer(), stringHealth, posX + 55, posY + 2, -1);
		GlStateManager.color(1f, 1f, 1f);
		GameData.bindIcons();
	}

}
