package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.registry.tag.FluidTags;
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
		return (this.mc.player.isSubmergedIn(FluidTags.WATER) || this.mc.player.getAir() < this.mc.player.getMaxAir()) && this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		int height = scaledHeight + this.settings.getPositionValue(Settings.air_position)[1];
		int adjustedWidth = (scaledWidth / 2) + this.settings.getPositionValue(Settings.air_position)[0];
		int airAmount = this.mc.player.getAir();
		double maxAir = this.mc.player.getMaxAir();
		dc.drawTexture(INTERFACE, adjustedWidth - 70, height - 80, 0, 160, 141, 10);
		dc.drawTexture(INTERFACE, adjustedWidth - 70, height - 80, 0, 140, (int) (141.0D * (airAmount / maxAir)), 10);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
	}

}
