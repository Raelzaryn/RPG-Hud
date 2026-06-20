package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.RPGHudUtils;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementAirTexture extends HudElement {

	public HudElementAirTexture() {
		super(HudElementType.AIR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return (this.mc.player.isUnderWater() || this.mc.player.getAirSupply() < this.mc.player.getMaxAirSupply()) && RPGHudUtils.isSurvival();
	}

	@Override
	public void drawElement(GuiGraphicsExtractor gg, float zLevel, DeltaTracker partialTicks, int scaledWidth, int scaledHeight) {
		int height = scaledHeight + this.settings.getPositionValue(Settings.air_position)[1];
		int adjustedWidth = (scaledWidth / 2) + this.settings.getPositionValue(Settings.air_position)[0];
		int airAmount = this.mc.player.getAirSupply();
		double maxAir = this.mc.player.getMaxAirSupply();
		gg.blit(RenderPipelines.GUI_TEXTURED, INTERFACE, adjustedWidth - 70, height - 80, 0, 160, 141, 10, 256, 256);
		gg.blit(RenderPipelines.GUI_TEXTURED, INTERFACE, adjustedWidth - 70, height - 80, 0, 140, (int) (141.0D * (airAmount / maxAir)), 10, 256, 256);
	}

}
