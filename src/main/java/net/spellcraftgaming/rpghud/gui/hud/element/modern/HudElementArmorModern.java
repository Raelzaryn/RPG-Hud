package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.AbstractGui;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class HudElementArmorModern extends HudElement {

	public HudElementArmorModern() {
		super(HudElementType.ARMOR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(AbstractGui gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
	    double scale = getScale();
        RenderSystem.scaled(scale, scale, scale);
		int left = getPosX(scaledWidth);
		int top = getPosY(scaledHeight);

		int level = this.mc.player.getTotalArmorValue();
		if (level > 0) {
	        int height = getHeight(scaledHeight);
			int width2 = 1 + 9 + 2 + this.mc.fontRenderer.getStringWidth(String.valueOf(level)) + 2;
			drawRect(left, top, width2, height, 0xA0000000);
			this.mc.fontRenderer.func_238421_b_(ms,String.valueOf(level), left + 12, top + 2, -1);
			this.mc.getTextureManager().bindTexture(AbstractGui.field_230665_h_);
			gui.func_238474_b_(ms, left + 1, top + 1, 34, 9, 9, 9);
		    ForgeIngameGui.left_height += height;
		}
		
		scale = getInvertedScale();
        RenderSystem.scaled(scale, scale, scale);
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
    public double getScale() {
        return 1;
    }

}
