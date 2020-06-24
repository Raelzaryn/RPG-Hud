package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.tags.FluidTags;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementAirModern extends HudElement {

	public HudElementAirModern() {
		super(HudElementType.AIR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return (this.mc.player.areEyesInFluid(FluidTags.WATER) || this.mc.player.getAir() < this.mc.player.getMaxAir()) && !this.mc.gameSettings.hideGUI && this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(AbstractGui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int height = scaledHeight + this.settings.getPositionValue(Settings.air_position)[1];
		int airAmount = this.mc.player.getAir();
		double maxAir = this.mc.player.getMaxAir();
		if(airAmount < 0) airAmount = 0;
		int posX = this.settings.getPositionValue(Settings.air_position)[0];
		RenderSystem.disableLighting();
		drawRect(scaledWidth / 2 - 72 + posX, height - 78, 144, 2, 0xA0000000);
		drawRect(scaledWidth / 2 - 72 + posX, height - 70, 144, 2, 0xA0000000);
		drawRect(scaledWidth / 2 - 72 + posX, height - 76, 2, 6, 0xA0000000);
		drawRect(scaledWidth / 2 + 70 + posX, height - 76, 2, 6, 0xA0000000);
		drawRect(scaledWidth / 2 - 70 + posX, height - 76, 140, 6, 0x20FFFFFF);
		drawRect(scaledWidth / 2 - 70 + posX, height - 76, (int) (140 * (airAmount / maxAir)), 6, this.settings.getIntValue(Settings.color_air));
	}
}
