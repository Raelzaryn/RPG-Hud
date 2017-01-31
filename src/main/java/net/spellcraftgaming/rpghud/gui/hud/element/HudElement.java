package net.spellcraftgaming.rpghud.gui.hud.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.ModSettings;

public abstract class HudElement {

	/** The x coordinate the element will be rendered at on the screen */
	protected int posX;
	/** The y coordinate the element will be rendered at on the screen */
	protected int posY;

	/**
	 * The default x coordinate the element will be rendered at on the screen
	 */
	protected final int defaultPosX;
	/**
	 * The default y coordinate the element will be rendered at on the screen
	 */
	protected final int defaultPosY;

	/** The width of this element */
	protected int elementWidth;
	/** The height of this element */
	protected int elementHeight;
	/** Whether this element can be moved */
	protected boolean moveable;
	/** The Type of this element */
	protected HudElementType type;

	/** The Minecraft instance */
	protected Minecraft mc;

	/** The Mod instance */
	protected ModRPGHud rpgHud;

	/** The Mod settings */
	protected ModSettings settings;

	public HudElement(HudElementType type, int posX, int posY, int width, int height, boolean moveable) {
		this.type = type;
		this.posX = posX;
		this.posY = posY;
		this.defaultPosX = posX;
		this.defaultPosY = posY;
		this.elementWidth = width;
		this.elementHeight = height;
		this.moveable = moveable;
		this.mc = Minecraft.getMinecraft();
		this.rpgHud = ModRPGHud.instance;
		this.settings = this.rpgHud.settings;
	}

	/**
	 * Function called to draw this element on the screen
	 */
	public abstract void drawElement(Gui gui, float zLevel, float partialTicks);

	/**
	 * Returns the x coordinate of this element
	 * 
	 * @return x coordinate
	 */
	public int getPosX() {
		return this.posX;
	}

	/**
	 * Returns the y coordinate of this element
	 * 
	 * @return y coordinate
	 */
	public int getPosY() {
		return this.posY;
	}

	/**
	 * Returns the width of this element
	 * 
	 * @return width
	 */
	public int getWidth() {
		return this.elementWidth;
	}

	/**
	 * Returns the height of this element
	 * 
	 * @return height
	 */
	public int getHeight() {
		return this.elementHeight;
	}

	/**
	 * Returns whether this element can be moved or not
	 * 
	 * @return moveable
	 */
	public boolean isMoveable() {
		return this.moveable;
	}

	/**
	 * Returns the type of this element
	 * 
	 * @return type
	 */
	public HudElementType getType() {
		return this.type;
	}

	/**
	 * Sets the position of this element to posX and posY if they are valid
	 * 
	 * @param posX
	 * @param posY
	 * @return whether the position is valid or not
	 */
	public boolean setPos(int posX, int posY) {
		boolean xValid = false;
		boolean yValid = false;
		if (posX >= 0 && posX < (this.mc.displayWidth - this.elementWidth)) {
			xValid = true;
		}
		if (posY >= 0 && posY < (this.mc.displayHeight - this.elementHeight)) {
			yValid = true;
		}
		if (xValid && yValid) {
			this.posX = posX;
			this.posY = posY;
		}
		return xValid && yValid;
	}

	/**
	 * Resets the position of this element to it's default position
	 */
	public void setPositionToDefault() {
		this.posX = this.defaultPosX;
		this.posY = this.defaultPosY;
	}

	public boolean checkConditions() {
		return true;
	}
}
