package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.network.chat.Component;
import net.spellcraftgaming.rpghud.RPGHudUtils;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementLevelDefault extends HudElement {

	public HudElementLevelDefault() {
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
		graphics.centeredText(this.mc.font, level, (this.settings.getBoolValue(Settings.render_player_face) ? 38 : 12) - (this.mc.font.width(level) / 2) + this.settings.getPositionValue(Settings.level_position)[0], (this.settings.getBoolValue(Settings.render_player_face) ? 38 : 14) + this.settings.getPositionValue(Settings.level_position)[1], 0xFF80FF20);
	}

}
