package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementClockVanilla;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementClockExtended extends HudElementClockVanilla {

	public HudElementClockExtended() {
		super();
	}

	@Override
	public boolean checkConditions() {
		return super.checkConditions() && this.settings.getBoolValue(Settings.enable_clock) && !this.mc.gameSettings.showDebugInfo && (this.settings.getBoolValue(Settings.enable_immersive_clock) ? GameData.hasPlayerClock() : true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks, double scale) {
		int clockColor = 0xFFFFFF;
		if (this.settings.getBoolValue(Settings.enable_clock_color)) {
			clockColor = getClockColor();
		}
		if (this.settings.getBoolValue(Settings.reduce_size))
			GL11.glScaled(0.5D, 0.5D, 0.5D);
		gui.drawString(this.mc.fontRendererObj, getTime(), this.settings.getBoolValue(Settings.reduce_size) ? 8 : 4, this.settings.getBoolValue(Settings.reduce_size) ? 124 : 62, clockColor);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if (this.settings.getBoolValue(Settings.reduce_size))
			GL11.glScaled(2.0D, 2.0D, 2.0D);
	}

}
