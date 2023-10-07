package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementClockVanilla;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementClockExtended extends HudElementClockVanilla {

	public HudElementClockExtended() {
		super();
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int clockColor = 0xFFFFFF;
		if (this.settings.getBoolValue(Settings.enable_clock_color)) {
			clockColor = getClockColor();
		}
		if (this.settings.getBoolValue(Settings.reduce_size))
			dc.getMatrices().scale(0.5f, 0.5f, 0.5f);
		dc.drawText(this.mc.textRenderer, getTime(), (this.settings.getBoolValue(Settings.reduce_size) ? 8 : 4) + this.settings.getPositionValue(Settings.clock_position)[0], (this.settings.getBoolValue(Settings.reduce_size) ? 124 : 62) + this.settings.getPositionValue(Settings.clock_position)[1], clockColor, false);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		if (this.settings.getBoolValue(Settings.reduce_size))
			dc.getMatrices().scale(2f, 2f, 2f);
	}

}
