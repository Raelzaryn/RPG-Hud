package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementLevelDefault extends HudElement {

	public HudElementLevelDefault() {
		super(HudElementType.LEVEL, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.options.hideGui;
	}

	@Override
	public void drawElement(GuiGraphics gg, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		RenderSystem.enableBlend();
		String level = String.valueOf(this.mc.player.experienceLevel);
		gg.drawString(this.mc.font, level, (this.settings.getBoolValue(Settings.render_player_face) ? 38 : 12) - (this.mc.font.width(level) / 2) + this.settings.getPositionValue(Settings.level_position)[0], (this.settings.getBoolValue(Settings.render_player_face) ? 38 : 14) + this.settings.getPositionValue(Settings.level_position)[1], 0x80FF20);
		RenderSystem.disableBlend();
	}

}
