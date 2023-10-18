package net.spellcraftgaming.rpghud.gui.hud.element.simple;

import static net.minecraft.client.gui.GuiComponent.GUI_ICONS_LOCATION;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementArmorSimple extends HudElement{

	public HudElementArmorSimple() {
		super(HudElementType.ARMOR, 0, 0, 0, 0, true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.options.hideGui;
	}

	@Override
	public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
	    float scale = getScale();
        ms.scale(scale, scale, scale);
		int left = getPosX(scaledWidth);
		int top = getPosY(scaledHeight);

		int level = this.mc.player.getArmorValue();
		if (level > 0) {
	        int height = getHeight(scaledHeight);
			int width2 = 1 + 9 + 2 + this.mc.font.width(String.valueOf(level)) + 2;
			drawRect(ms, left, top, width2, height, 0xA0000000);
			this.mc.font.draw(ms,String.valueOf(level), left + 12, top + 2, -1);
			bind(GUI_ICONS_LOCATION);
			GuiComponent.blit(ms, left + 1, top + 1, 34, 9, 9, 9);
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
        return (int) ((scaledHeight - 29 - 11)*getInvertedScale() - getHeight(scaledHeight) + this.settings.getPositionValue(Settings.armor_position)[1]);
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
        return 0.5f;
    }

}
