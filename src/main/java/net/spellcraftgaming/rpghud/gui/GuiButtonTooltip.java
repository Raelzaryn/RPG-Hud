package net.spellcraftgaming.rpghud.gui;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.lib.gui.GuiButtonLib;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

@SideOnly(Side.CLIENT)
public class GuiButtonTooltip extends GuiButtonLib {

	/** Variable to contain the (possible) setting of this button */
	public final String enumOptions;

	public int x = GameData.getButtonX(this);
	public int y = GameData.getButtonX(this);
	
	/** Array that contains the tooltip of this button */
	private String[] tooltip;
	/**
	 * Initiates a new button
	 * 
	 * @param buttonId
	 *            The ID of the button
	 * @param x
	 *            The x position on the screen
	 * @param y
	 *            The y position on the screen
	 * @param buttonText
	 *            The display Text of this button
	 */
	public GuiButtonTooltip(int buttonId, int x, int y, String buttonText) {
		super(buttonId, x, y, buttonText);
		this.enumOptions = null;
	}

	/**
	 * Initiates a new button
	 * 
	 * @param buttonId
	 *            The ID of the button
	 * @param x
	 *            The x position on the screen
	 * @param y
	 *            The y position on the screen
	 * @param width
	 *            the width of the button
	 * @param height
	 *            the height of the button
	 * @param buttonText
	 *            The display Text of this button
	 */
	public GuiButtonTooltip(int buttonId, int x, int y, int width, int height, String buttonText) {
		super(buttonId, x, y, width, height, buttonText);
		this.enumOptions = null;
	}

	/**
	 * Initiates a new button
	 * 
	 * @param buttonId
	 *            The ID of the button
	 * @param x
	 *            The x position on the screen
	 * @param y
	 *            The y position on the screen
	 * @param setting
	 *            The possible setting of this button
	 * @param buttonText
	 *            The display Text of this button
	 */
	public GuiButtonTooltip(int buttonId, int x, int y, String setting, String buttonText) {
		super(buttonId, x, y, 150, 20, buttonText);
		this.enumOptions = setting;
	}

	/**
	 * Sets the tooltip of this button. Should be appended at the constructor.
	 * 
	 * @param tooltip
	 *            The String which'll be the button's tooltip. Line breaks are
	 *            managed via the /n symbol combination.
	 * @return the button
	 */
	public GuiButtonTooltip setTooltip(String tooltip) {
		this.tooltip = tooltip.split("/n");
		return this;
	}

	/**
	 * Sets the tooltip to the one the setting of hits button contain.
	 * 
	 * @return the button
	 */
	public GuiButtonTooltip setTooltip() {
		if (this.enumOptions != null)
			return setTooltip(ModRPGHud.instance.settings.getSetting(this.enumOptions).getTooltip());
		return this;
	}

	/**
	 * Returns the EnumOptionsMod of this button
	 * 
	 * @return the EnumOptionsMod of this button
	 */
	public String returnOptions() {
		return this.enumOptions;
	}

	/**
	 * Gives the tooltip of this button
	 * 
	 * @return the Tooltip
	 */
	public String[] getTooltip() {
		return this.tooltip;
	}
}
