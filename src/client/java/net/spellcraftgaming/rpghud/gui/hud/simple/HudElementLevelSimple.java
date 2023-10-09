package net.spellcraftgaming.rpghud.gui.hud.simple;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementLevelSimple extends HudElement{

	public HudElementLevelSimple() {
		super(HudElementType.LEVEL, 0, 0, 0, 0, true);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		String level = String.valueOf(this.mc.player.experienceLevel);
		
		int width = 12;
		RenderSystem.disableBlend();
		
		int posX = ((scaledWidth - width) /2) + this.settings.getPositionValue(Settings.level_position)[0];
		int posY = scaledHeight - 32 - 8 + this.settings.getPositionValue(Settings.level_position)[1];
		
		if (this.settings.getStringValue(Settings.clock_time_format) == "time.24" || !this.settings.getBoolValue(Settings.render_player_face)) {
			drawRect(dc, posX, posY, width, 8, 0xA0000000);
		} else {
			drawRect(dc, 26 + this.settings.getPositionValue(Settings.level_position)[0], posY, width, 7, 0xA0000000);
		}
		float scale =0.5f;
		if(this.settings.getBoolValue(Settings.debug_number_size)) scale = 0.666666666f;
		float invertedScale = 1f/scale;
		dc.getMatrices().scale(scale, scale, scale);
		dc.drawCenteredTextWithShadow( this.mc.textRenderer, level, Math.round((posX + (width/2))* invertedScale), (int) Math.round(((posY)* invertedScale) + Math.ceil(invertedScale*4-4)), 0x80FF20);
		dc.getMatrices().scale(invertedScale, invertedScale, invertedScale);
		RenderSystem.enableBlend();
	}

}
