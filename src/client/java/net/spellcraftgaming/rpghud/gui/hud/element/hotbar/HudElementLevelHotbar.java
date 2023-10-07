package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementLevelHotbar extends HudElement {

	public HudElementLevelHotbar() {
		super(HudElementType.LEVEL, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		RenderSystem.enableBlend();
		String level = String.valueOf(this.mc.player.experienceLevel);
		dc.drawTextWithShadow(this.mc.textRenderer, level, (this.settings.getBoolValue(Settings.render_player_face) ? 25 : 13) + this.settings.getPositionValue(Settings.level_position)[0] - this.mc.textRenderer.getWidth(level) / 2, scaledHeight - (this.settings.getBoolValue(Settings.render_player_face) ? 22 : 40) + this.settings.getPositionValue(Settings.level_position)[1], 0x80FF20);
		RenderSystem.disableBlend();
	}

}
