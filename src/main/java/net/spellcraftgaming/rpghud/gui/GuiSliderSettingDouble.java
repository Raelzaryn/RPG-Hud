package net.spellcraftgaming.rpghud.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.SettingDouble;

public class GuiSliderSettingDouble extends GuiButtonTooltip {

	private double sliderValue;
	public boolean dragging;

	public GuiSliderSettingDouble(int buttonId, int x, int y, String optionIn) {
		super(buttonId, x, y, optionIn, "");
		this.sliderValue = 1.0F;
		this.sliderValue = SettingDouble.normalizeValue((SettingDouble)ModRPGHud.instance.settings.getSetting(optionIn), ModRPGHud.instance.settings.getDoubleValue(optionIn));
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
				this.sliderValue = (double) (mouseX - (this.xPosition + 4)) / (double) (this.width - 8);
				this.sliderValue = GameData.clamp(this.sliderValue, 0.0F, 1.0F);
				double f = SettingDouble.denormalizeValue((SettingDouble)ModRPGHud.instance.settings.getSetting(this.enumOptions), this.sliderValue);
				ModRPGHud.instance.settings.setSetting(this.enumOptions, f);
				this.sliderValue = SettingDouble.normalizeValue((SettingDouble)ModRPGHud.instance.settings.getSetting(this.enumOptions), f);
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
			this.sliderValue = (double) (mouseX - (this.xPosition + 4)) / (double) (this.width - 8);
			this.sliderValue = GameData.clamp(this.sliderValue, 0.0F, 1.0F);
			ModRPGHud.instance.settings.setSetting(this.enumOptions, SettingDouble.denormalizeValue((SettingDouble)ModRPGHud.instance.settings.getSetting(this.enumOptions), this.sliderValue));
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
