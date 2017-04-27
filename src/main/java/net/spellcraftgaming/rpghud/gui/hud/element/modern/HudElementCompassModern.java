package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementCompassModern extends HudElement {

	public HudElementCompassModern() {
		super(HudElementType.COMPASS, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.settings.getBoolValue(Settings.enable_compass) && !this.mc.gameSettings.showDebugInfo && (this.settings.getBoolValue(Settings.enable_immersive_compass) ? GameData.hasPlayerCompass() : true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int width = res.getScaledWidth() / 2;
		int swapSides = this.settings.getBoolValue(Settings.invert_compass) ? -1 : 1;

		int rotation = Math.round(((GameData.getRotationYaw() % 360) / 360) * 200);
		if (rotation < 0)
			rotation = 200 + rotation;
		drawRect(width - 50, 2, 100, 6, 0xAA000000);

		gui.drawTexturedModalRect(width - 56, 0, 34, 234, 112, 9);
		if (rotation > 0 && rotation <= 100) {
			gui.drawCenteredString(this.mc.fontRendererObj, "W", width + (50 * swapSides) - (rotation * swapSides), 1, -1);
		}

		if (rotation > 25 && rotation <= 125) {
			gui.drawCenteredString(this.mc.fontRendererObj, ".", width + (75 * swapSides) - (rotation * swapSides), -2, -1);
		}

		if (rotation > 50 && rotation <= 150) {
			gui.drawCenteredString(this.mc.fontRendererObj, "N", width + (100 * swapSides) - (rotation * swapSides), 1, this.settings.getBoolValue(Settings.enable_compass_color) ? 0xE60909 : -1);
		}

		if (rotation > 75 && rotation <= 175) {
			gui.drawCenteredString(this.mc.fontRendererObj, ".", width + (125 * swapSides) - (rotation * swapSides), -2, -1);
		}

		if (rotation > 100 && rotation <= 200) {
			gui.drawCenteredString(this.mc.fontRendererObj, "E", width + (150 * swapSides) - (rotation * swapSides), 1, -1);
		}

		if (rotation >= 125) {
			gui.drawCenteredString(this.mc.fontRendererObj, ".", width + (175 * swapSides) - (rotation * swapSides), -2, -1);
		} else if (rotation <= 25) {
			gui.drawCenteredString(this.mc.fontRendererObj, ".", width - (25 * swapSides) - (rotation * swapSides), -2, -1);
		}

		if (rotation >= 150) {
			gui.drawCenteredString(this.mc.fontRendererObj, "S", width + (200 * swapSides) - (rotation * swapSides), 1, -1);
		} else if (rotation <= 50) {
			gui.drawCenteredString(this.mc.fontRendererObj, "S", width - (rotation * swapSides), 1, -1);
		}

		if (rotation >= 175) {
			gui.drawCenteredString(this.mc.fontRendererObj, ".", width + (225 * swapSides) - (rotation * swapSides), -2, -1);
		} else if (rotation <= 75) {
			gui.drawCenteredString(this.mc.fontRendererObj, ".", width + (25 * swapSides) - (rotation * swapSides), -2, -1);
		}

		if (this.settings.getBoolValue(Settings.enable_compass_coordinates)) {
			int[] pos = GameData.getPlayerPos();
			drawRect(width - 50, 11, this.mc.fontRendererObj.getStringWidth(String.valueOf(pos[0])) / 2 + 4, 6, 0xA0000000);
			drawRect((int) (width - ((float) this.mc.fontRendererObj.getStringWidth(String.valueOf(pos[1])) / 4) - 2), 11, this.mc.fontRendererObj.getStringWidth(String.valueOf(pos[1])) / 2 + 4, 6, 0xA0000000);
			drawRect((width + 48) - (this.mc.fontRendererObj.getStringWidth(String.valueOf(pos[2])) / 2) - 2, 11, this.mc.fontRendererObj.getStringWidth(String.valueOf(pos[2])) / 2 + 4, 6, 0xA0000000);

			GlStateManager.scale(0.5D, 0.5D, 0.5D);
			gui.drawString(this.mc.fontRendererObj, String.valueOf(pos[0]), (width - 48) * 2, 12 * 2, -1);
			gui.drawCenteredString(this.mc.fontRendererObj, String.valueOf(pos[1]), width * 2, 12 * 2, -1);
			gui.drawString(this.mc.fontRendererObj, String.valueOf(pos[2]), (width + 48) * 2 - this.mc.fontRendererObj.getStringWidth(String.valueOf(pos[2])), 12 * 2, -1);
			GlStateManager.scale(2D, 2D, 2D);
		}
	}

}
