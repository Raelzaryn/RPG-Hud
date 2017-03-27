package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementHealthHotbar extends HudElementBarred {

	public HudElementHealthHotbar() {
		super(HudElementType.HEALTH, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return GameData.shouldDrawHUD() && !(GameData.isRidingLivingMount());
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int height = res.getScaledHeight();
		int health = GameData.getPlayerHealth();
		int posX = this.settings.render_player_face ? 49 : 25;
		int healthMax = GameData.getPlayerMaxHealth();
		int absorption = GameData.getPlayerAbsorption();
		int offset = GameData.getHotbarWidgetWidthOffset();
		
		if(absorption > 1) drawCustomBar(posX, height - 56, 200 + offset, 10, (double) (health + absorption) / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.color_absorption, offsetColorPercent(this.settings.color_absorption, OFFSET_PERCENT));
		
		if (GameData.isPlayerPoisoned()) {
			drawCustomBar(posX, height - 56, 200 + offset, 10, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.color_poison, offsetColorPercent(this.settings.color_poison, OFFSET_PERCENT));
		} else if (GameData.isPlayerWithering()){
			drawCustomBar(posX, height - 56, 200 + offset, 10, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.color_wither, offsetColorPercent(this.settings.color_wither, OFFSET_PERCENT));
		} else {
			drawCustomBar(posX, height - 56, 200 + offset, 10, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.color_health, offsetColorPercent(this.settings.color_health, OFFSET_PERCENT));
		}
		String stringHealth = (health + absorption) + "/" + healthMax;
		if (this.settings.show_numbers_health)
			gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, posX + 100 + (offset / 2), height - 55, -1);
	}
}
