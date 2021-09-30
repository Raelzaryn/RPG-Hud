package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementCompassVanilla;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(EnvType.CLIENT)
public class HudElementCompassModern extends HudElementCompassVanilla {

	public HudElementCompassModern() {
		super();
	}

	@Override
	public boolean checkConditions() {
		return this.settings.getBoolValue(Settings.enable_compass) && !this.mc.options.debugEnabled && (this.settings.getBoolValue(Settings.enable_immersive_compass) ? this.mc.player.getInventory().contains(new ItemStack(Items.COMPASS)) : true);
	}

	@Override
	public void drawElement(DrawableHelper gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int posX = getPosX(scaledWidth);
		int posY = getPosY(scaledHeight);
		int swapSides = this.settings.getBoolValue(Settings.invert_compass) ? -1 : 1;

		int rotation = Math.round(((this.mc.player.headYaw % 360) / 360) * 200);
		if (rotation < 0)
			rotation = 200 + rotation;
		drawRect(ms, posX - 50, posY + 2, 100, 6, 0xAA000000);
		
		if (rotation > 0 && rotation <= 100) {
			DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, "W", posX + (50 * swapSides) - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation > 25 && rotation <= 125) {
			DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, ".", posX + (75 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation > 50 && rotation <= 150) {
			DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, "N", posX + (100 * swapSides) - (rotation * swapSides), posY + 1, this.settings.getBoolValue(Settings.enable_compass_color) ? 0xE60909 : -1);
		}

		if (rotation > 75 && rotation <= 175) {
			DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, ".", posX + (125 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation > 100 && rotation <= 200) {
			DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, "E", posX + (150 * swapSides) - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation >= 125) {
			DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, ".", posX + (175 * swapSides) - (rotation * swapSides), posY - 2, -1);
		} else if (rotation <= 25) {
			DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, ".", posX - (25 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (rotation >= 150) {
			DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, "S", posX + (200 * swapSides) - (rotation * swapSides), posY + 1, -1);
		} else if (rotation <= 50) {
			DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, "S", posX - (rotation * swapSides), posY + 1, -1);
		}

		if (rotation >= 175) {
			DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, ".", posX + (225 * swapSides) - (rotation * swapSides), posY - 2, -1);
		} else if (rotation <= 75) {
			DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, ".", posX + (25 * swapSides) - (rotation * swapSides), posY - 2, -1);
		}

		if (this.settings.getBoolValue(Settings.enable_compass_coordinates)) {
			int[] pos = getPlayerPos();
			drawRect(ms, posX - 50, posY + 11, this.mc.textRenderer.getWidth(String.valueOf(pos[0])) / 2 + 4, 6, 0xA0000000);
			drawRect(ms, (int) (posX - ((float) this.mc.textRenderer.getWidth(String.valueOf(pos[1])) / 4) - 2), posY + 11, this.mc.textRenderer.getWidth(String.valueOf(pos[1])) / 2 + 4, 6, 0xA0000000);
			drawRect(ms, (posX + 48) - (this.mc.textRenderer.getWidth(String.valueOf(pos[2])) / 2) - 2, posY + 11, this.mc.textRenderer.getWidth(String.valueOf(pos[2])) / 2 + 4, 6, 0xA0000000);

			ms.scale(0.5f, 0.5f, 0.5f);
			DrawableHelper.drawStringWithShadow(ms, this.mc.textRenderer, String.valueOf(pos[0]), (posX - 48) * 2, (posY + 12) * 2, -1);
			DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, String.valueOf(pos[1]), posX * 2, (posY + 12) * 2, -1);
			DrawableHelper.drawStringWithShadow(ms, this.mc.textRenderer, String.valueOf(pos[2]), (posX + 48) * 2 - this.mc.textRenderer.getWidth(String.valueOf(pos[2])), (posY + 12) * 2, -1);
			ms.scale(2f, 2f, 2f);
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
    public float getScale() {
        return 1;
    }

}
