package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementCompassVanilla;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementCompassModern extends HudElementCompassVanilla {

	public HudElementCompassModern() {
		super();
	}

	@Override
	public boolean checkConditions() {
		return this.settings.getBoolValue(Settings.enable_compass) && !this.mc.gameSettings.showDebugInfo && (this.settings.getBoolValue(Settings.enable_immersive_compass) ? this.mc.player.inventory.hasItemStack(new ItemStack(Items.COMPASS)) : true);
	}

	@Override
	public void drawElement(AbstractGui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int width = (scaledWidth / 2) + this.settings.getPositionValue(Settings.compass_position)[0];
		int posY = this.settings.getPositionValue(Settings.compass_position)[1];
		int swapSides = this.settings.getBoolValue(Settings.invert_compass) ? -1 : 1;

		int rotation = Math.round(((this.mc.player.rotationYaw % 360) / 360) * 200);
		if (rotation < 0)
			rotation = 200 + rotation;
		drawRect(width - 50, posY + 2, 100, 6, 0xAA000000);

		gui.blit(width - 56, 0, 34, 234, 112, 9);
		if (rotation > 0 && rotation <= 100) {
			gui.drawCenteredString(this.mc.fontRenderer, "W", width + (50 * swapSides) - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation > 25 && rotation <= 125) {
			gui.drawCenteredString(this.mc.fontRenderer, ".", width + (75 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation > 50 && rotation <= 150) {
			gui.drawCenteredString(this.mc.fontRenderer, "N", width + (100 * swapSides) - (rotation * swapSides), posY + 1, this.settings.getBoolValue(Settings.enable_compass_color) ? 0xE60909 : -1);
		}

		if (rotation > 75 && rotation <= 175) {
			gui.drawCenteredString(this.mc.fontRenderer, ".", width + (125 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation > 100 && rotation <= 200) {
			gui.drawCenteredString(this.mc.fontRenderer, "E", width + (150 * swapSides) - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation >= 125) {
			gui.drawCenteredString(this.mc.fontRenderer, ".", width + (175 * swapSides) - (rotation * swapSides), posY - 2, -1);
		} else if (rotation <= 25) {
			gui.drawCenteredString(this.mc.fontRenderer, ".", width - (25 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation >= 150) {
			gui.drawCenteredString(this.mc.fontRenderer, "S", width + (200 * swapSides) - (rotation * swapSides), posY + 1, -1);
		} else if (rotation <= 50) {
			gui.drawCenteredString(this.mc.fontRenderer, "S", width - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation >= 175) {
			gui.drawCenteredString(this.mc.fontRenderer, ".", width + (225 * swapSides) - (rotation * swapSides), posY - 2, -1);
		} else if (rotation <= 75) {
			gui.drawCenteredString(this.mc.fontRenderer, ".", width + (25 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (this.settings.getBoolValue(Settings.enable_compass_coordinates)) {
			int[] pos = getPlayerPos();
			drawRect(width - 50, posY + 11, this.mc.fontRenderer.getStringWidth(String.valueOf(pos[0])) / 2 + 4, 6, 0xA0000000);
			drawRect((int) (width - ((float) this.mc.fontRenderer.getStringWidth(String.valueOf(pos[1])) / 4) - 2), posY + 11, this.mc.fontRenderer.getStringWidth(String.valueOf(pos[1])) / 2 + 4, 6, 0xA0000000);
			drawRect((width + 48) - (this.mc.fontRenderer.getStringWidth(String.valueOf(pos[2])) / 2) - 2, posY + 11, this.mc.fontRenderer.getStringWidth(String.valueOf(pos[2])) / 2 + 4, 6, 0xA0000000);

			GlStateManager.scaled(0.5D, 0.5D, 0.5D);
			gui.drawString(this.mc.fontRenderer, String.valueOf(pos[0]), (width - 48) * 2, (posY + 12) * 2, -1);
			gui.drawCenteredString(this.mc.fontRenderer, String.valueOf(pos[1]), width * 2, (posY + 12) * 2, -1);
			gui.drawString(this.mc.fontRenderer, String.valueOf(pos[2]), (width + 48) * 2 - this.mc.fontRenderer.getStringWidth(String.valueOf(pos[2])), (posY + 12) * 2, -1);
			GlStateManager.scaled(2D, 2D, 2D);
		}
	}

}
