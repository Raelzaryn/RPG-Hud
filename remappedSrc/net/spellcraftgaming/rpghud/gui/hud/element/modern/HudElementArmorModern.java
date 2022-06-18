package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(EnvType.CLIENT)
public class HudElementArmorModern extends HudElement {

	public HudElementArmorModern() {
		super(HudElementType.ARMOR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawableHelper gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
	    float scale = getScale();
        ms.scale(scale, scale, scale);
		int left = getPosX(scaledWidth);
		int top = getPosY(scaledHeight);

		int level = this.mc.player.getArmor();
		if (level > 0) {
	        int height = getHeight(scaledHeight);
			int width2 = 1 + 9 + 2 + this.mc.textRenderer.getWidth(String.valueOf(level)) + 2;
			drawRect(ms, left, top, width2, height, 0xA0000000);
			this.mc.textRenderer.draw(ms,String.valueOf(level), left + 12, top + 2, -1);
			bind(AbstractParentElement.GUI_ICONS_TEXTURE);
			gui.drawTexture(ms, left + 1, top + 1, 34, 9, 9, 9);
		}
		
		scale = getInvertedScale();
        ms.scale(scale, scale, scale);
	}
	
    @Override
    public int getPosX(int scaledWidth) {
        return (int) ((scaledWidth / 2 - 91)*getInvertedScale() + this.settings.getPositionValue(Settings.armor_position)[0]);
    }

    @Override
    public int getPosY(int scaledHeight) {
        return (int) ((scaledHeight - 29 + 2)*getInvertedScale() - getHeight(scaledHeight) + this.settings.getPositionValue(Settings.armor_position)[1]);
    }

    @Override
    public int getWidth(int scaledWidth) {
        return 144;
    }

    public int getHeight(int scaledHeight) {
        return 10;
    }

    @Override
    public float getScale() {
        return 1;
    }

}
