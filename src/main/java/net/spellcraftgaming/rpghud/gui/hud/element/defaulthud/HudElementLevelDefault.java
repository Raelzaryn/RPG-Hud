package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import com.mojang.blaze3d.platform.GlStateManager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(EnvType.CLIENT)
public class HudElementLevelDefault extends HudElement {

	public HudElementLevelDefault() {
		super(HudElementType.LEVEL, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawableHelper gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		GlStateManager.disableBlend();
		String level = String.valueOf(this.mc.player.experienceLevel);
		this.mc.textRenderer.draw(ms,level, (this.settings.getBoolValue(Settings.render_player_face) ? 38 : 12) - (this.mc.textRenderer.getWidth(level) / 2) + this.settings.getPositionValue(Settings.level_position)[0], (this.settings.getBoolValue(Settings.render_player_face) ? 38 : 14) + this.settings.getPositionValue(Settings.level_position)[1], 0x80FF20);
		GlStateManager.enableBlend();
	}

}
