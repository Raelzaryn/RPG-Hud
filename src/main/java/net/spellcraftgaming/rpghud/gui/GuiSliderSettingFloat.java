package net.spellcraftgaming.rpghud.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.SettingFloat;

public class GuiSliderSettingFloat extends GuiButtonTooltip {

	private float sliderValue;
	public boolean dragging;

	public GuiSliderSettingFloat(int buttonId, int x, int y, String optionIn) {
		super(buttonId, x, y, optionIn, "");
		this.sliderValue = 1.0F;
		this.sliderValue = SettingFloat.normalizeValue((SettingFloat)ModRPGHud.instance.settings.getSetting(optionIn), ModRPGHud.instance.settings.getFloatValue(optionIn));
		this.displayString = ModRPGHud.instance.settings.getButtonString(optionIn);
	}

	/**
	 * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over
	 * this button and 2 if it IS hovering over this button.
	 */
	@Override
	protected int getHoverState(boolean mouseOver) {
		return 0;
	}

	/**
	 * Fired when the mouse button is dragged. Equivalent of
	 * MouseListener.mouseDragged(MouseEvent e).
	 */
	@Override
	protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			if (this.dragging) {
				this.sliderValue = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);
				this.sliderValue = GameData.clamp(this.sliderValue, 0.0F, 1.0F);
				float f = SettingFloat.denormalizeValue((SettingFloat)ModRPGHud.instance.settings.getSetting(this.enumOptions), this.sliderValue);
				ModRPGHud.instance.settings.setSetting(this.enumOptions, f);
				this.sliderValue = SettingFloat.normalizeValue((SettingFloat)ModRPGHud.instance.settings.getSetting(this.enumOptions), f);
				this.displayString = ModRPGHud.instance.settings.getButtonString(this.enumOptions);
			}

			GameData.bindButtonTextures();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
			this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
		}
	}

	/**
	 * Returns true if the mouse has been pressed on this control. Equivalent of
	 * MouseListener.mousePressed(MouseEvent e).
	 */
	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		if (super.mousePressed(mc, mouseX, mouseY)) {
			this.sliderValue = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);
			this.sliderValue = GameData.clamp(this.sliderValue, 0.0F, 1.0F);
			ModRPGHud.instance.settings.setSetting(this.enumOptions, SettingFloat.denormalizeValue((SettingFloat)ModRPGHud.instance.settings.getSetting(this.enumOptions), this.sliderValue));
			this.displayString = ModRPGHud.instance.settings.getButtonString(this.enumOptions);
			this.dragging = true;
			return true;
		}
		return false;
	}

	/**
	 * Fired when the mouse button is released. Equivalent of
	 * MouseListener.mouseReleased(MouseEvent e).
	 */
	@Override
	public void mouseReleased(int mouseX, int mouseY) {
		this.dragging = false;
	}
}
