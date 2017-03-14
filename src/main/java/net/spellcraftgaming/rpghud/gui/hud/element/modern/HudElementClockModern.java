package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementClockVanilla;

public class HudElementClockModern extends HudElementClockVanilla{

	public HudElementClockModern() {
		super();
		this.posX = 0;
		this.posY = 0;
		this.elementWidth = 0;
		this.elementHeight = 0;
		this.moveable = true;
	}

	@Override
	public boolean checkConditions() {
		return this.settings.enable_clock && !this.mc.gameSettings.showDebugInfo && (this.settings.enable_immersive_clock ? GameData.hasPlayerClock() : true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int yOffset = (this.settings.render_player_face ? 0 : 8) + ((this.settings.show_numbers_health && this.settings.show_numbers_stamina) ? 0 : 4);
		int clockColor = 0xFFFFFF;
		if (this.settings.enable_clock_color) {
			clockColor = getClockColor();
		}
		if(this.settings.clock_time_format == 0){
			drawRect(2, 23 + yOffset, 20, 6, 0xA0000000);
		} else {
			drawRect(2, 23 + yOffset, 23, 6, 0xA0000000);
		}
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		
		if(this.settings.clock_time_format == 0){
			gui.drawCenteredString(this.mc.fontRendererObj, getTime(), 24, 48 + 2* yOffset, clockColor);
		} else {
			gui.drawCenteredString(this.mc.fontRendererObj, getTime(), 28, 48 + 2* yOffset, clockColor);
		}
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		GL11.glScaled(2.0D, 2.0D, 2.0D);
	}

}
