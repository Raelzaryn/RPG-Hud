package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.tags.FluidTags;
import net.spellcraftgaming.rpghud.RPGHudUtils;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementAirDefault extends HudElement {

	public HudElementAirDefault() {
		super(HudElementType.AIR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return (this.mc.player.isEyeInFluid(FluidTags.WATER) || this.mc.player.getAirSupply() < this.mc.player.getMaxAirSupply()) && RPGHudUtils.isSurvival();
	}

	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
		int height = scaledHeight + this.settings.getPositionValue(Settings.air_position)[1];
		int adjustedWidth = scaledWidth / 2 - 91 + this.settings.getPositionValue(Settings.air_position)[0];
		int airAmount = this.mc.player.getAirSupply();
		double maxAir = this.mc.player.getMaxAirSupply();
		drawCustomBar(graphics, adjustedWidth + 21, height - 80, 141, 10, airAmount / maxAir * 100.0D, this.settings.getIntValue(Settings.color_air), offsetColorPercent(this.settings.getIntValue(Settings.color_air), OFFSET_PERCENT));
	}

}
