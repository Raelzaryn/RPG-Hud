package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.HudModern;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementHealthModern extends HudElementBarred{

	public HudElementHealthModern() {
		super(HudElementType.HEALTH, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int health = GameData.getPlayerHealth();
		int healthMax = GameData.getPlayerMaxHealth();
		int absorption = GameData.getPlayerAbsorption();
		
		int xOffset = ((HudModern) this.rpgHud.huds.get("modern")).getPosX();
		int width = this.mc.fontRendererObj.getStringWidth(healthMax + "/" + healthMax) / 2 + 4;
		((HudModern) this.rpgHud.huds.get("modern")).setPosX(width);
		String stringHealth = (health + absorption) + "/" + healthMax;
		
		if (this.settings.show_numbers_health && this.settings.show_numbers_stamina) {
			drawRect(this.settings.render_player_face ? 23 : 2, 4, width, 8, 0xA0000000);
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, this.settings.render_player_face ? (xOffset * 2) + 28: 24, 12, -1);
			GL11.glScaled(2.0D, 2.0D, 2.0D);
		}
		
		int posX = (this.settings.render_player_face ? 24 : 2) + ((this.settings.show_numbers_health && this.settings.show_numbers_stamina) ? xOffset + 1: 0);
		drawTetragon(posX, posX, 3, 3, 97, 83, 10, 10, 0xA0000000);
		drawTetragon(posX + 2, posX + 2, 5, 5, 89, 79, 6, 6, 0x20FFFFFF);
		
		if(absorption > 1) drawTetragon(posX + 2, posX + 2, 5, 5, (int) (89 * ((double)(health + absorption) / (double) (healthMax + absorption))), (int) (89 * ((double)(health + absorption)/ (double) (healthMax + absorption))) - 10, 6, 6, this.settings.color_absorption);
		if (GameData.isPlayerPoisoned()) {
			drawTetragon(posX + 2, posX + 2, 5, 5, (int) (89 * ((double)health / (double) (healthMax + absorption))), (int) (89 * ((double)health / (double) (healthMax + absorption))) - 10, 6, 6, this.settings.color_poison);
		} else if (GameData.isPlayerWithering()){
			drawTetragon(posX + 2, posX + 2, 5, 5, (int) (89 * ((double)health / (double) (healthMax + absorption))), (int) (89 * ((double)health / (double) (healthMax + absorption))) - 10, 6, 6, this.settings.color_wither);
		} else {
			drawTetragon(posX + 2, posX + 2, 5, 5, (int) (89 * ((double)health / (double) (healthMax + absorption))), (int) (89 * ((double)health / (double) (healthMax + absorption))) - 10, 6, 6, this.settings.color_health);
		}
	}

}
