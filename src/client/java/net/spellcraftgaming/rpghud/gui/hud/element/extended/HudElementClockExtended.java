package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementClockVanilla;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementClockExtended extends HudElementClockVanilla {

	public HudElementClockExtended() {
		super();
	}

	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
		final int clockColor;
		if (this.settings.getBoolValue(Settings.enable_clock_color)) {
			clockColor = getClockColor();
		} else {
			clockColor = 0xFFFFFFFF;
		}
		if (this.settings.getBoolValue(Settings.reduce_size))
			graphics.pose().scale(0.5f, 0.5f);
		int x = (this.settings.getBoolValue(Settings.reduce_size) ? 8 : 4) + this.settings.getPositionValue(Settings.clock_position)[0];
		int y = (this.settings.getBoolValue(Settings.reduce_size) ? 124 : 62) + this.settings.getPositionValue(Settings.clock_position)[1];
		graphics.text(this.mc.font, getTime(), x, y, clockColor, true);
		if (this.settings.getBoolValue(Settings.reduce_size))
			graphics.pose().scale(2f, 2f);
	}

}
