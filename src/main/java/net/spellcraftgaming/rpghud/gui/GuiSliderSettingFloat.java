package net.spellcraftgaming.rpghud.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.SettingFloat;

public class GuiSliderSettingFloat extends GuiButtonTooltip {

	private float sliderValue;
	public boolean dragging;

	public GuiSliderSettingFloat(int buttonId, int x, int y, String optionIn, IPressable ip) {
		super(x, y, optionIn, "", ip);
		this.sliderValue = 1.0F;
		this.sliderValue = SettingFloat.normalizeValue((SettingFloat)ModRPGHud.instance.settings.getSetting(optionIn), ModRPGHud.instance.settings.getFloatValue(optionIn));
		this.setMessage(ModRPGHud.instance.settings.getButtonString(optionIn));
	}

	@Override
	protected int getYImage(boolean p_getYImage_1_) {
		return 0;
	}
	
	/**
	 * Fired when the mouse button is dragged. Equivalent of
	 * MouseListener.mouseDragged(MouseEvent e).
	 */
	@Override
	public void onClick(double mouseX, double mouseY) {
		if (this.visible) {
			if (this.dragging) {
				this.sliderValue = (float) (mouseX - (this.x + 4)) / (float) (this.width - 8);
				this.sliderValue = MathHelper.clamp(this.sliderValue, 0.0F, 1.0F);
				float f = SettingFloat.denormalizeValue((SettingFloat)ModRPGHud.instance.settings.getSetting(this.enumOptions), this.sliderValue);
				ModRPGHud.instance.settings.setSetting(this.enumOptions, f);
				this.sliderValue = SettingFloat.normalizeValue((SettingFloat)ModRPGHud.instance.settings.getSetting(this.enumOptions), f);
				this.setMessage(ModRPGHud.instance.settings.getButtonString(this.enumOptions));
			}

			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.blit(this.x + (int) (this.sliderValue * (this.width - 8)), this.y, 0, 66, 4, 20);
			this.blit(this.x + (int) (this.sliderValue * (this.width - 8)) + 4, this.y, 196, 66, 4, 20);
		}
	}

	/**
	 * Fired when the mouse button is released. Equivalent of
	 * MouseListener.mouseReleased(MouseEvent e).
	 */
	@Override
    public void onRelease(double mouseX, double mouseY) {
		this.dragging = false;
	}
}
