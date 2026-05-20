package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.spellcraftgaming.rpghud.RPGHudUtils;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementLevelExtended extends HudElement {

	public HudElementLevelExtended() {
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
		int x = (this.settings.getBoolValue(Settings.render_player_face) ? 38 : 13) + this.settings.getPositionValue(Settings.level_position)[0] - this.mc.font.width(level) / 2;
		int y = (this.settings.getBoolValue(Settings.render_player_face) ? 38 : 18) + this.settings.getPositionValue(Settings.level_position)[1];
		graphics.text(this.mc.font, level, x, y, 0xFF80FF20, false);
	}
}
