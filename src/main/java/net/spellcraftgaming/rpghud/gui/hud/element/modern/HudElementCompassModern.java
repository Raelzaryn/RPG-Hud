package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

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
	public void drawElement(AbstractGui gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int posX = getPosX(scaledWidth);
		int posY = getPosY(scaledHeight);
		int swapSides = this.settings.getBoolValue(Settings.invert_compass) ? -1 : 1;

		int rotation = Math.round(((this.mc.gameRenderer.getActiveRenderInfo().getYaw() % 360) / 360) * 200);
		if (rotation < 0)
			rotation = 200 + rotation;
		drawRect(posX - 50, posY + 2, 100, 6, 0xAA000000);

		gui.blit(ms, posX - 56, 0, 34, 234, 112, 9);
		if (rotation > 0 && rotation <= 100) {
			AbstractGui.drawCenteredString(ms, this.mc.fontRenderer, "W", posX + (50 * swapSides) - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation > 25 && rotation <= 125) {
			AbstractGui.drawCenteredString(ms, this.mc.fontRenderer, ".", posX + (75 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation > 50 && rotation <= 150) {
			AbstractGui.drawCenteredString(ms, this.mc.fontRenderer, "N", posX + (100 * swapSides) - (rotation * swapSides), posY + 1, this.settings.getBoolValue(Settings.enable_compass_color) ? 0xE60909 : -1);
		}

		if (rotation > 75 && rotation <= 175) {
			AbstractGui.drawCenteredString(ms, this.mc.fontRenderer, ".", posX + (125 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation > 100 && rotation <= 200) {
			AbstractGui.drawCenteredString(ms, this.mc.fontRenderer, "E", posX + (150 * swapSides) - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation >= 125) {
			AbstractGui.drawCenteredString(ms, this.mc.fontRenderer, ".", posX + (175 * swapSides) - (rotation * swapSides), posY - 2, -1);
		} else if (rotation <= 25) {
			AbstractGui.drawCenteredString(ms, this.mc.fontRenderer, ".", posX - (25 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation >= 150) {
			AbstractGui.drawCenteredString(ms, this.mc.fontRenderer, "S", posX + (200 * swapSides) - (rotation * swapSides), posY + 1, -1);
		} else if (rotation <= 50) {
			AbstractGui.drawCenteredString(ms, this.mc.fontRenderer, "S", posX - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation >= 175) {
			AbstractGui.drawCenteredString(ms, this.mc.fontRenderer, ".", posX + (225 * swapSides) - (rotation * swapSides), posY - 2, -1);
		} else if (rotation <= 75) {
			AbstractGui.drawCenteredString(ms, this.mc.fontRenderer, ".", posX + (25 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (this.settings.getBoolValue(Settings.enable_compass_coordinates)) {
			int[] pos = getPlayerPos();
			drawRect(posX - 50, posY + 11, this.mc.fontRenderer.getStringWidth(String.valueOf(pos[0])) / 2 + 4, 6, 0xA0000000);
			drawRect((int) (posX - ((float) this.mc.fontRenderer.getStringWidth(String.valueOf(pos[1])) / 4) - 2), posY + 11, this.mc.fontRenderer.getStringWidth(String.valueOf(pos[1])) / 2 + 4, 6, 0xA0000000);
			drawRect((posX + 48) - (this.mc.fontRenderer.getStringWidth(String.valueOf(pos[2])) / 2) - 2, posY + 11, this.mc.fontRenderer.getStringWidth(String.valueOf(pos[2])) / 2 + 4, 6, 0xA0000000);

			RenderSystem.scaled(0.5D, 0.5D, 0.5D);
			AbstractGui.drawString(ms, this.mc.fontRenderer, String.valueOf(pos[0]), (posX - 48) * 2, (posY + 12) * 2, -1);
			AbstractGui.drawCenteredString(ms, this.mc.fontRenderer, String.valueOf(pos[1]), posX * 2, (posY + 12) * 2, -1);
			AbstractGui.drawString(ms, this.mc.fontRenderer, String.valueOf(pos[2]), (posX + 48) * 2 - this.mc.fontRenderer.getStringWidth(String.valueOf(pos[2])), (posY + 12) * 2, -1);
			RenderSystem.scaled(2D, 2D, 2D);
		}
	}
	
    @Override
    public int getPosY(int scaledHeight) {
        return (int) (this.settings.getPositionValue(Settings.compass_position)[1]);
    }

    @Override
    public int getPosX(int scaledWidth) {
        return (scaledWidth / 2) + this.settings.getPositionValue(Settings.compass_position)[0];
    }
    
    @Override
    public int getWidth(int scaledWidth) {
        return 100;
    }

    public int getHeight(int scaledHeight) {
        return 6;
    }

    @Override
    public double getScale() {
        return 1;
    }

}
