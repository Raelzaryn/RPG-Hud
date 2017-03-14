package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.minecraft.client.gui.Gui;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementHealthExtended extends HudElementBarred {

	public HudElementHealthExtended() {
		super(HudElementType.HEALTH, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return GameData.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int health = GameData.getPlayerHealth();
		int absorption = GameData.getPlayerAbsorption();
		int healthMax = GameData.getPlayerMaxHealth();
		int posX = this.settings.render_player_face ? 49 : 25;
		int posY = this.settings.render_player_face ? 9 : 5;
		
		if(absorption > 1) drawCustomBar(posX, posY, 110, 12, (double) (health + absorption)/ (double) (healthMax + absorption) * 100D, -1, -1, this.settings.color_absorption, offsetColorPercent(this.settings.color_absorption, OFFSET_PERCENT));
		
		if (GameData.isPlayerPoisoned()) {
			drawCustomBar(posX, posY, 110, 12, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.color_poison, offsetColorPercent(this.settings.color_poison, OFFSET_PERCENT));
		} else if (GameData.isPlayerWithering()){
			drawCustomBar(posX, posY, 110, 12, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.color_wither, offsetColorPercent(this.settings.color_wither, OFFSET_PERCENT));
		} else {
			drawCustomBar(posX, posY, 110, 12, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.color_health, offsetColorPercent(this.settings.color_health, OFFSET_PERCENT));
		}
		String stringHealth = (health + absorption) + "/" + healthMax;
		if (this.settings.show_numbers_health)
			gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, posX + 55, posY + 2, -1);
	}
}
