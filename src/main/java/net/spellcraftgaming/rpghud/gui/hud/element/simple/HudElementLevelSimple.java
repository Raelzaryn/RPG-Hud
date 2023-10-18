package net.spellcraftgaming.rpghud.gui.hud.element.simple;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementLevelSimple extends HudElement{

	public HudElementLevelSimple() {
		super(HudElementType.LEVEL, 0, 0, 0, 0, true);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean checkConditions() {
		return !this.mc.options.hideGui;
	}

	@Override
	public void drawElement(GuiGraphics gg, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		String level = String.valueOf(this.mc.player.experienceLevel);
		
		int width = 12;
		RenderSystem.disableBlend();
		
		int posX = ((scaledWidth - width) /2) + this.settings.getPositionValue(Settings.level_position)[0];
		int posY = scaledHeight - 32 - 8 + this.settings.getPositionValue(Settings.level_position)[1];
		
		if (this.settings.getStringValue(Settings.clock_time_format) == "time.24" || !this.settings.getBoolValue(Settings.render_player_face)) {
			drawRect(gg, posX, posY, width, 8, 0xA0000000);
		} else {
			drawRect(gg, 26 + this.settings.getPositionValue(Settings.level_position)[0], posY, width, 7, 0xA0000000);
		}
		gg.pose().scale(0.5f, 0.5f, 0.5f);

		gg.drawCenteredString( this.mc.font, level, (posX * 2) + width, posY * 2 + 4, 0x80FF20);
		gg.pose().scale(2.0f, 2.0f, 2.0f);
		RenderSystem.enableBlend();
	}

}
