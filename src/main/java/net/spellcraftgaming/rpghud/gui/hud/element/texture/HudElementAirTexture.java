package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementAirTexture extends HudElement {

	public HudElementAirTexture() {
		super(HudElementType.AIR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return (this.mc.player.isUnderWater() || this.mc.player.getAirSupply() < this.mc.player.getMaxAirSupply()) && !this.mc.options.hideGui;
	}

	@Override
	public void drawElement(GuiGraphics gg, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		int height = scaledHeight + this.settings.getPositionValue(Settings.air_position)[1];
		int adjustedWidth = (scaledWidth / 2) + this.settings.getPositionValue(Settings.air_position)[0];
		int airAmount = this.mc.player.getAirSupply();
		double maxAir = this.mc.player.getMaxAirSupply();
		gg.blit(INTERFACE, adjustedWidth - 70, height - 80, 0, 160, 141, 10);
		gg.blit(INTERFACE, adjustedWidth - 70, height - 80, 0, 140, (int) (141.0D * (airAmount / maxAir)), 10);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
	}

}
