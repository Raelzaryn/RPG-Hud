package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.registry.tag.FluidTags;
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
		return (this.mc.player.isSubmergedIn(FluidTags.WATER) || this.mc.player.getAir() < this.mc.player.getMaxAir()) && this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int height = scaledHeight + this.settings.getPositionValue(Settings.air_position)[1];
		int adjustedWidth = scaledWidth / 2 - 91 + this.settings.getPositionValue(Settings.air_position)[0];
		int airAmount = this.mc.player.getAir();
		double maxAir = this.mc.player.getMaxAir();
		drawCustomBar(dc, adjustedWidth + 21, height - 80, 141, 10, airAmount / maxAir * 100.0D, this.settings.getIntValue(Settings.color_air), offsetColorPercent(this.settings.getIntValue(Settings.color_air), OFFSET_PERCENT));
	}

}
