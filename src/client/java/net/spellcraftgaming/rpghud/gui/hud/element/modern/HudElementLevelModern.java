package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.network.chat.Component;
import net.spellcraftgaming.rpghud.RPGHudUtils;
import net.spellcraftgaming.rpghud.gui.hud.HudModern;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementLevelModern extends HudElement {

	public HudElementLevelModern() {
		super(HudElementType.LEVEL, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return RPGHudUtils.isSurvival();
	}

	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
		String level = String.valueOf(this.mc.player.experienceLevel);
		
		int xOffset = ((HudModern) this.rpgHud.huds.get("modern")).getPosX();
		int width = this.mc.font.width(level);
		if(width < 16) width = 16;
		if(width < xOffset) width = xOffset;
		else ((HudModern) this.rpgHud.huds.get("modern")).setPosX(width);
		
		if (this.mc.font.width(level) > (width + 2))
			width = this.mc.font.width(level) + 2;
		
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 23 : 2) + this.settings.getPositionValue(Settings.level_position)[0];
		int posY = ((this.settings.getBoolValue(Settings.show_numbers_health) && this.settings.getBoolValue(Settings.show_numbers_food)) ? 22 : 26) + this.settings.getPositionValue(Settings.level_position)[1];
		
		if (this.settings.getStringValue(Settings.clock_time_format) == "time.24" || !this.settings.getBoolValue(Settings.render_player_face)) {
			drawRect(graphics, posX, posY, width, 7, 0xA0000000);
		} else {
			drawRect(graphics, 26 + this.settings.getPositionValue(Settings.level_position)[0], posY, width, 7, 0xA0000000);
		}
		graphics.pose().scale(0.5f, 0.5f);

		if (this.settings.getStringValue(Settings.clock_time_format) == "time.24" || !this.settings.getBoolValue(Settings.render_player_face)) {
			graphics.centeredText(this.mc.font, level, (posX * 2) + width, posY * 2 + 3, 0xFF80FF20);
		} else {
			graphics.centeredText(this.mc.font, level, 70 + this.settings.getPositionValue(Settings.level_position)[0] * 2, posY * 2 + 3, 0xFF80FF20);
		}
		graphics.pose().scale(2.0f, 2.0f);
	}

}
