package net.spellcraftgaming.rpghud.gui;


import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.BaseComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

@OnlyIn(Dist.CLIENT)
public class GuiButtonTooltip extends GuiButtonLib {

	/** Variable to contain the (possible) setting of this button */
	public final String enumOptions;
	public int id;
	/** Array that contains the tooltip of this button */
	private String[] tooltip;

	public GuiButtonTooltip(int x, int y, BaseComponent buttonText, Button.OnPress ip) {
		super(x, y, buttonText, ip);
		this.enumOptions = null;
	}


	public GuiButtonTooltip(int x, int y, int width, int height, BaseComponent buttonText, Button.OnPress ip) {
		super(x, y, width, height, buttonText, ip);
		this.enumOptions = null;
	}
	
	public GuiButtonTooltip(int id, int x, int y, int width, int height, BaseComponent buttonText, Button.OnPress ip) {
		super(x, y, width, height, buttonText, ip);
		this.id = id;
		this.enumOptions = null;
	}

	public GuiButtonTooltip(int x, int y, String setting, BaseComponent buttonText, Button.OnPress ip) {
		super(x, y, 150, 20, buttonText, ip);
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
