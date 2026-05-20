package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.spellcraftgaming.rpghud.RPGHudUtils;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementArmorModern extends HudElement {

	public HudElementArmorModern() {
		super(HudElementType.ARMOR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return RPGHudUtils.isSurvival();
	}

	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
	    float scale = getScale();
        graphics.pose().scale(scale, scale);
		int left = getPosX(scaledWidth);
		int top = getPosY(scaledHeight);

		int level = this.mc.player.getArmorValue();
		if (level > 0) {
            int height = getHeight(scaledHeight);
            int width2 = 1 + 9 + 2 + this.mc.font.width(String.valueOf(level)) + 2;
            drawRect(graphics, left, top, width2, height, 0xA0000000);
            graphics.text(this.mc.font, String.valueOf(level), left + 12, top + 2, -1, true);
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ARMOR_FULL_TEXTURE, left + 1, top + 1, 9, 9);
        }
		
		scale = getInvertedScale();
        graphics.pose().scale(scale, scale);
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
