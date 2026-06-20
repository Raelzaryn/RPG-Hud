package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.RPGHudUtils;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementLevelHotbar extends HudElement {

	public HudElementLevelHotbar() {
		super(HudElementType.LEVEL, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return RPGHudUtils.isSurvival();
	}

	@Override
	public void drawElement(GuiGraphicsExtractor gg, float zLevel, DeltaTracker partialTicks, int scaledWidth, int scaledHeight) {
		String level = String.valueOf(this.mc.player.experienceLevel);
		gg.text(mc.font, level, (this.settings.getBoolValue(Settings.render_player_face) ? 25 : 13) + this.settings.getPositionValue(Settings.level_position)[0] - this.mc.font.width(level) / 2, scaledHeight - (this.settings.getBoolValue(Settings.render_player_face) ? 22 : 40) + this.settings.getPositionValue(Settings.level_position)[1], 0x80FF20);
	}

}
