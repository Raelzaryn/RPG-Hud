package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.HudModern;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementHealthModern extends HudElement {

	public HudElementHealthModern() {
		super(HudElementType.HEALTH, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledHeight, int scaledWidth) {
		int health = MathHelper.ceil(this.mc.player.getHealth());
		int absorption = MathHelper.ceil(this.mc.player.getAbsorptionAmount());
		int healthMax = MathHelper.ceil(this.mc.player.getMaxHealth());

		int xOffset = ((HudModern) this.rpgHud.huds.get("modern")).getPosX();
		
		String stringHealth = this.settings.getBoolValue(Settings.health_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%" : (health + absorption) + "/" + healthMax;
		int width = this.mc.textRenderer.getWidth(stringHealth) / 2 + 4;
		if(width < xOffset) width = xOffset;
		else ((HudModern) this.rpgHud.huds.get("modern")).setPosX(width);

		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 24 : 2) + ((this.settings.getBoolValue(Settings.show_numbers_health) && this.settings.getBoolValue(Settings.show_numbers_food)) ? xOffset : 0) + this.settings.getPositionValue(Settings.health_position)[0];
		int textPosX = this.settings.getPositionValue(Settings.health_position)[0];
		int posY = this.settings.getPositionValue(Settings.health_position)[1];

		if (this.settings.getBoolValue(Settings.show_numbers_health) && this.settings.getBoolValue(Settings.show_numbers_food)) {
			drawRect(dc, textPosX + (this.settings.getBoolValue(Settings.render_player_face) ? 23 : 2), posY + 4, width, 8, 0xA0000000);
			dc.getMatrices().scale(0.5f, 0.5f, 0.5f);
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, stringHealth, textPosX * 2 + (this.settings.getBoolValue(Settings.render_player_face) ? 42 : 0) + width + 4, posY * 2 + 12, -1);
			dc.getMatrices().scale(2f, 2f, 2f);
		}

		drawTetragon(posX, posX, 3 + posY, 3 + posY, 97, 83, 10, 10, 0xA0000000);
		drawTetragon(posX + 2, posX + 2, 5 + posY, 5 + posY, 89, 79, 6, 6, 0x20FFFFFF);

		if (absorption > 1)
			drawTetragon(posX + 2, posX + 2, 5 + posY, 5 + posY, (int) (89 * ((double) (health + absorption) / (double) (healthMax + absorption))), (int) (89 * ((double) (health + absorption) / (double) (healthMax + absorption))) - 10, 6, 6, this.settings.getIntValue(Settings.color_absorption));
		if (this.mc.player.hasStatusEffect(StatusEffects.POISON)) {
			drawTetragon(posX + 2, posX + 2, 5 + posY, 5 + posY, (int) (89 * ((double) health / (double) (healthMax + absorption))), (int) (89 * ((double) health / (double) (healthMax + absorption))) - 10, 6, 6, this.settings.getIntValue(Settings.color_poison));
		} else if (this.mc.player.hasStatusEffect(StatusEffects.WITHER)) {
			drawTetragon(posX + 2, posX + 2, 5 + posY, 5 + posY, (int) (89 * ((double) health / (double) (healthMax + absorption))), (int) (89 * ((double) health / (double) (healthMax + absorption))) - 10, 6, 6, this.settings.getIntValue(Settings.color_wither));
		} else {
			drawTetragon(posX + 2, posX + 2, 5 + posY, 5 + posY, (int) (89 * ((double) health / (double) (healthMax + absorption))), (int) (89 * ((double) health / (double) (healthMax + absorption))) - 10, 6, 6, this.settings.getIntValue(Settings.color_health));
		}
	}

}
