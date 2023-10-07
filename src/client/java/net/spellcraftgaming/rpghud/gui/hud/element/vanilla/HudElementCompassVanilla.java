package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementCompassVanilla extends HudElement {

	public HudElementCompassVanilla() {
		super(HudElementType.COMPASS, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.settings.getBoolValue(Settings.enable_compass) && (this.settings.getBoolValue(Settings.enable_immersive_compass) ? this.mc.player.getInventory().contains(new ItemStack(Items.COMPASS)) : true);
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int width = scaledWidth / 2 + this.settings.getPositionValue(Settings.compass_position)[0];
		int posY = this.settings.getPositionValue(Settings.compass_position)[1];
		int swapSides = this.settings.getBoolValue(Settings.invert_compass) ? -1 : 1;
		int rotation = Math.round(((this.mc.player.headYaw % 360) / 360) * 200);
		if (rotation < 0)
			rotation = 200 + rotation;

		dc.drawTexture(INTERFACE, width - 56, posY, 34, 234, 112, 9);
		if (rotation > 0 && rotation <= 100) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, "W", width + (50 * swapSides) - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation > 25 && rotation <= 125) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, ".", width + (75 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation > 50 && rotation <= 150) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, "N", width + (100 * swapSides) - (rotation * swapSides), posY + 1, this.settings.getBoolValue(Settings.enable_compass_color) ? 0xE60909 : -1);
		}

		if (rotation > 75 && rotation <= 175) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, ".", width + (125 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation > 100 && rotation <= 200) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, "E", width + (150 * swapSides) - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation >= 125) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, ".", width + (175 * swapSides) - (rotation * swapSides), posY - 2, -1);
		} else if (rotation <= 25) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, ".", width - (25 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation >= 150) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, "S", width + (200 * swapSides) - (rotation * swapSides), posY + 1, -1);
		} else if (rotation <= 50) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, "S", width - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation >= 175) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, ".", width + (225 * swapSides) - (rotation * swapSides), posY - 2, -1);
		} else if (rotation <= 75) {
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, ".", width + (25 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (this.settings.getBoolValue(Settings.enable_compass_coordinates)) {
			if (this.settings.getBoolValue(Settings.reduce_size))
				dc.getMatrices().scale(0.5f, 0.5f, 0.5f);
			int[] pos = getPlayerPos();
			dc.drawTextWithShadow(this.mc.textRenderer, String.valueOf(pos[0]), (width - 50) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), (posY + 11) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), -1);
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, String.valueOf(pos[1]), width * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), (posY + 11) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), -1);
			dc.drawTextWithShadow(this.mc.textRenderer, String.valueOf(pos[2]), (width + 50) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1) - mc.textRenderer.getWidth(String.valueOf(pos[2])), (posY + 11) * (this.settings.getBoolValue(Settings.reduce_size) ? 2 : 1), -1);
			if (this.settings.getBoolValue(Settings.reduce_size))
				dc.getMatrices().scale(2f, 2f, 2f);
		}
	}

	public static int[] getPlayerPos() {
		MinecraftClient mc = MinecraftClient.getInstance();
		int[] pos = new int[3];
		pos[0] = (int) mc.player.getX();
		pos[1] = (int) mc.player.getY();
		pos[2] = (int) mc.player.getZ();
		return pos;
	}
}
