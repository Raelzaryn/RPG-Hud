package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementClockVanilla;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementClockExtended extends HudElementClockVanilla {

	public HudElementClockExtended() {
		super();
	}

	@Override
	public void drawElement(GuiGraphics gg, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int clockColor = 0xFFFFFF;
		if (this.settings.getBoolValue(Settings.enable_clock_color)) {
			clockColor = getClockColor();
		}
		if (this.settings.getBoolValue(Settings.reduce_size))
			gg.pose().scale(0.5f, 0.5f, 0.5f);
		gg.drawString(this.mc.font, getTime(), (this.settings.getBoolValue(Settings.reduce_size) ? 8 : 4) + this.settings.getPositionValue(Settings.clock_position)[0], (this.settings.getBoolValue(Settings.reduce_size) ? 124 : 62) + this.settings.getPositionValue(Settings.clock_position)[1], clockColor);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		if (this.settings.getBoolValue(Settings.reduce_size))
			gg.pose().scale(2f, 2f, 2f);
	}

}
