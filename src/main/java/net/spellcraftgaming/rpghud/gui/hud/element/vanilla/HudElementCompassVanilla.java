package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementCompassVanilla extends HudElement {

	public HudElementCompassVanilla() {
		super(HudElementType.COMPASS, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.settings.getBoolValue(Settings.enable_compass) && (this.settings.getBoolValue(Settings.enable_immersive_compass) ? this.mc.player.getInventory().contains(new ItemStack(Items.COMPASS)) : true);
	}

	@Override
	public void drawElement(GuiGraphics gg, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int width = scaledWidth / 2 + this.settings.getPositionValue(Settings.compass_position)[0];
		int posY = this.settings.getPositionValue(Settings.compass_position)[1];
		int swapSides = this.settings.getBoolValue(Settings.invert_compass) ? -1 : 1;
		int rotation = Math.round(((this.mc.gameRenderer.getMainCamera().getYRot() % 360) / 360) * 200);
		if (rotation < 0)
			rotation = 200 + rotation;

		gg.blit(INTERFACE, width - 56, posY, 34, 234, 112, 9);
		if (rotation > 0 && rotation <= 100) {
			gg.drawCenteredString( this.mc.font, "W", width + (50 * swapSides) - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation > 25 && rotation <= 125) {
			gg.drawCenteredString( this.mc.font, ".", width + (75 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation > 50 && rotation <= 150) {
			gg.drawCenteredString( this.mc.font, "N", width + (100 * swapSides) - (rotation * swapSides), posY + 1, this.settings.getBoolValue(Settings.enable_compass_color) ? 0xE60909 : -1);
		}

		if (rotation > 75 && rotation <= 175) {
			gg.drawCenteredString( this.mc.font, ".", width + (125 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation > 100 && rotation <= 200) {
			gg.drawCenteredString( this.mc.font, "E", width + (150 * swapSides) - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation >= 125) {
			gg.drawCenteredString( this.mc.font, ".", width + (175 * swapSides) - (rotation * swapSides), posY - 2, -1);
		} else if (rotation <= 25) {
			gg.drawCenteredString( this.mc.font, ".", width - (25 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation >= 150) {
			gg.drawCenteredString( this.mc.font, "S", width + (200 * swapSides) - (rotation * swapSides), posY + 1, -1);
		} else if (rotation <= 50) {
			gg.drawCenteredString( this.mc.font, "S", width - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation >= 175) {
			gg.drawCenteredString( this.mc.font, ".", width + (225 * swapSides) - (rotation * swapSides), posY - 2, -1);
		} else if (rotation <= 75) {
			gg.drawCenteredString( this.mc.font, ".", width + (25 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (this.settings.getBoolValue(Settings.enable_compass_coordinates)) {
			if (this.settings.getBoolValue(Settings.reduce_size))
				gg.pose().scale(0.5f, 0.5f, 0.5f);
			int[] pos = getPlayerPos();
			gg.drawString(this.mc.font, String.valueOf(pos[0]), (width - 50) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), (posY + 11) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), -1);
			gg.drawCenteredString( this.mc.font, String.valueOf(pos[1]), width * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), (posY + 11) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), -1);
			gg.drawString(this.mc.font, String.valueOf(pos[2]), (width + 50) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1) - mc.font.width(String.valueOf(pos[2])), (posY + 11) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), -1);
			if (this.settings.getBoolValue(Settings.reduce_size))
				gg.pose().scale(2f, 2f, 2f);
		}
	}

	public static int[] getPlayerPos() {
		Minecraft mc = Minecraft.getInstance();
		int[] pos = new int[3];
		pos[0] = (int) mc.player.getX();
		pos[1] = (int) mc.player.getY();
		pos[2] = (int) mc.player.getZ();
		return pos;
	}
}
