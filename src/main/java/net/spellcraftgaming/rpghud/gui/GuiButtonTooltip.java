package net.spellcraftgaming.rpghud.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.spellcraftgaming.rpghud.settings.EnumOptionsDebugMod;
import net.spellcraftgaming.rpghud.settings.EnumOptionsMod;

@SideOnly(Side.CLIENT)
public class GuiButtonTooltip extends GuiButton {

	/** Variable to contain the (possible) setting of this button */
	private final EnumOptionsMod enumOptions;

	/** Variable to contain the (possible) debug setting of this button */
	private final EnumOptionsDebugMod enumOptionsDebug;

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
		this.enumOptionsDebug = null;
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
		this.enumOptionsDebug = null;
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
	public GuiButtonTooltip(int buttonId, int x, int y, EnumOptionsMod setting, String buttonText) {
		super(buttonId, x, y, 150, 20, buttonText);
		this.enumOptions = setting;
		this.enumOptionsDebug = null;
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
	 *            The possible debug setting of this button
	 * @param buttonText
	 *            The display Text of this button
	 */
	public GuiButtonTooltip(int buttonId, int x, int y, EnumOptionsDebugMod setting, String buttonText) {
		super(buttonId, x, y, 150, 20, buttonText);
		this.enumOptions = null;
		this.enumOptionsDebug = setting;
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
		System.out.println(tooltip);
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
			return setTooltip(this.enumOptions.getTooltip());
		if (this.enumOptionsDebug != null)
			return setTooltip(this.enumOptionsDebug.getTooltip());
		return this;
	}

	public EnumOptionsMod returnOptions() {
		return this.enumOptions;
	}

	public EnumOptionsDebugMod returnOptionsDebug() {
		return this.enumOptionsDebug;
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
