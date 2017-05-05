package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementCompassVanilla extends HudElement {

	public HudElementCompassVanilla() {
		super(HudElementType.COMPASS, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.settings.getBoolValue(Settings.enable_compass) && !this.mc.gameSettings.showDebugInfo && (this.settings.getBoolValue(Settings.enable_immersive_compass) ? GameData.hasPlayerCompass() : true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks, double scale) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int width = res.getScaledWidth() / 2;
		int swapSides = this.settings.getBoolValue(Settings.invert_compass) ? -1 : 1;
		int rotation = Math.round(((GameData.getRotationYaw() % 360) / 360) * 200);
		if (rotation < 0)
			rotation = 200 + rotation;

		bind(INTERFACE);
		drawTexturedModalRect(gui, width - 56, 0, 34, 234, 112, 9, scale);
		if (rotation > 0 && rotation <= 100) {
			drawCenteredString(gui, this.mc.fontRendererObj, "W", width + (50 * swapSides) - (rotation * swapSides), 1, -1, scale);
		}

		if (rotation > 25 && rotation <= 125) {
			drawCenteredString(gui, this.mc.fontRendererObj, ".", width + (75 * swapSides) - (rotation * swapSides), -2, -1, scale);
		}

		if (rotation > 50 && rotation <= 150) {
			drawCenteredString(gui, this.mc.fontRendererObj, "N", width + (100 * swapSides) - (rotation * swapSides), 1, this.settings.getBoolValue(Settings.enable_compass_color) ? 0xE60909 : -1, scale);
		}

		if (rotation > 75 && rotation <= 175) {
			drawCenteredString(gui, this.mc.fontRendererObj, ".", width + (125 * swapSides) - (rotation * swapSides), -2, -1, scale);
		}

		if (rotation > 100 && rotation <= 200) {
			drawCenteredString(gui, this.mc.fontRendererObj, "E", width + (150 * swapSides) - (rotation * swapSides), 1, -1, scale);
		}

		if (rotation >= 125) {
			drawCenteredString(gui, this.mc.fontRendererObj, ".", width + (175 * swapSides) - (rotation * swapSides), -2, -1, scale);
		} else if (rotation <= 25) {
			drawCenteredString(gui, this.mc.fontRendererObj, ".", width - (25 * swapSides) - (rotation * swapSides), -2, -1, scale);
		}

		if (rotation >= 150) {
			drawCenteredString(gui, this.mc.fontRendererObj, "S", width + (200 * swapSides) - (rotation * swapSides), 1, -1, scale);
		} else if (rotation <= 50) {
			drawCenteredString(gui, this.mc.fontRendererObj, "S", width - (rotation * swapSides), 1, -1, scale);
		}

		if (rotation >= 175) {
			drawCenteredString(gui, this.mc.fontRendererObj, ".", width + (225 * swapSides) - (rotation * swapSides), -2, -1, scale);
		} else if (rotation <= 75) {
			drawCenteredString(gui, this.mc.fontRendererObj, ".", width + (25 * swapSides) - (rotation * swapSides), -2, -1, scale);
		}

		if (this.settings.getBoolValue(Settings.enable_compass_coordinates)) {
			if (this.settings.getBoolValue(Settings.reduce_size))
				GlStateManager.scale(0.5D, 0.5D, 0.5D);
			int[] pos = GameData.getPlayerPos();
			drawString(this.mc.fontRendererObj, String.valueOf(pos[0]), (width - 50) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), 11 * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), -1, scale);
			drawCenteredString(gui, this.mc.fontRendererObj, String.valueOf(pos[1]), width * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), 11 * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), -1, scale);
			drawString(this.mc.fontRendererObj, String.valueOf(pos[2]), (width + 50) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1) - this.mc.fontRendererObj.getStringWidth(String.valueOf(pos[2])), 11 * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), -1, scale);
			if (this.settings.getBoolValue(Settings.reduce_size))
				GlStateManager.scale(2D, 2D, 2D);
		}
	}

}
