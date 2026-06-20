package net.spellcraftgaming.rpghud.gui.hud.element.simple;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementLevelSimple extends HudElement{

	public HudElementLevelSimple() {
		super(HudElementType.LEVEL, 0, 0, 0, 0, true);
	}
	
	@Override
	public boolean checkConditions() {
		return !this.mc.gui.hud.isHidden();
	}

	@Override
	public void drawElement(GuiGraphicsExtractor gg, float zLevel, DeltaTracker partialTicks, int scaledWidth, int scaledHeight) {
		String level = String.valueOf(this.mc.player.experienceLevel);
		
		int width = 12;
		
		int posX = ((scaledWidth - width) /2) + this.settings.getPositionValue(Settings.level_position)[0];
		int posY = scaledHeight - 32 - 8 + this.settings.getPositionValue(Settings.level_position)[1];
		
		if (this.settings.getStringValue(Settings.clock_time_format) == "time.24" || !this.settings.getBoolValue(Settings.render_player_face)) {
			drawRect(gg, posX, posY, width, 8, 0xA0000000);
		} else {
			drawRect(gg, 26 + this.settings.getPositionValue(Settings.level_position)[0], posY, width, 7, 0xA0000000);
		}
		gg.pose().scale(0.5f, 0.5f);

		gg.centeredText( this.mc.font, level, (posX * 2) + width, posY * 2 + 4, 0xFF80FF20);
		gg.pose().scale(2.0f, 2.0f);
	}

}
