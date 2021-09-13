package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.spellcraftgaming.rpghud.gui.hud.HudModern;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(EnvType.CLIENT)
public class HudElementLevelModern extends HudElement {

	public HudElementLevelModern() {
		super(HudElementType.LEVEL, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawableHelper gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		String level = String.valueOf(this.mc.player.experienceLevel);
		
		int xOffset = ((HudModern) this.rpgHud.huds.get("modern")).getPosX();
		int width = this.mc.textRenderer.getWidth(level);
		if(width < 16) width = 16;
		if(width < xOffset) width = xOffset;
		else ((HudModern) this.rpgHud.huds.get("modern")).setPosX(width);
		
		if (this.mc.textRenderer.getWidth(level) > (width + 2))
			width = this.mc.textRenderer.getWidth(level) + 2;
		
		RenderSystem.disableBlend();
		
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 23 : 2) + this.settings.getPositionValue(Settings.level_position)[0];
		int posY = ((this.settings.getBoolValue(Settings.show_numbers_health) && this.settings.getBoolValue(Settings.show_numbers_food)) ? 22 : 26) + this.settings.getPositionValue(Settings.level_position)[1];
		
		if (this.settings.getStringValue(Settings.clock_time_format) == "time.24" || !this.settings.getBoolValue(Settings.render_player_face)) {
			drawRect(ms, posX, posY, width, 7, 0xA0000000);
		} else {
			drawRect(ms, 26 + this.settings.getPositionValue(Settings.level_position)[0], posY, width, 7, 0xA0000000);
		}
		ms.scale(0.5f, 0.5f, 0.5f);

		if (this.settings.getStringValue(Settings.clock_time_format) == "time.24" || !this.settings.getBoolValue(Settings.render_player_face)) {
			DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, level, (posX * 2) + width, posY * 2 + 3, 0x80FF20);
		} else {
			DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, level, 70 + this.settings.getPositionValue(Settings.level_position)[0] * 2, posY * 2 + 3, 0x80FF20);
		}
		ms.scale(2.0f, 2.0f, 2.0f);
		RenderSystem.enableBlend();
	}

}
