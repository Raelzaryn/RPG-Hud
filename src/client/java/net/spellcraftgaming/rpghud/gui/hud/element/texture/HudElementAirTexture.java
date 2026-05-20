package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.tags.FluidTags;
import net.spellcraftgaming.rpghud.RPGHudUtils;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementAirTexture extends HudElement {

	public HudElementAirTexture() {
		super(HudElementType.AIR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return (this.mc.player.isEyeInFluid(FluidTags.WATER) || this.mc.player.getAirSupply() < this.mc.player.getMaxAirSupply()) && RPGHudUtils.isSurvival();
	}

	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
		int height = scaledHeight + this.settings.getPositionValue(Settings.air_position)[1];
		int adjustedWidth = (scaledWidth / 2) + this.settings.getPositionValue(Settings.air_position)[0];
		int airAmount = this.mc.player.getAirSupply();
		double maxAir = this.mc.player.getMaxAirSupply();
		graphics.blit(RenderPipelines.GUI_TEXTURED, INTERFACE, adjustedWidth - 70, height - 80, 0, 160, 141, 10, 256, 256);
		graphics.blit(RenderPipelines.GUI_TEXTURED, INTERFACE, adjustedWidth - 70, height - 80, 0, 140, (int) (141.0D * (airAmount / maxAir)), 10, 256, 256);
	}

}
