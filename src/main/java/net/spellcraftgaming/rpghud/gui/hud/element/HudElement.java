package net.spellcraftgaming.rpghud.gui.hud.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
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

	/**
	 * Constructor
	 * 
	 * @param type
	 *            The HudElementType of this element
	 * @param posX
	 *            The initial position of this element (not yet implemented)
	 * @param posY
	 *            The initial position of this element (not yet implemented)
	 * @param width
	 *            The width of this element (not yet implemented)
	 * @param height
	 *            The height of this element (not yet implemented)
	 * @param moveable
	 *            Whether this element should be allowed to be moved around
	 */
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

	/**
	 * This function must return true when the element should be rendered<br>
	 * For example:<br>
	 * For the air element this should return true whenever the player is in
	 * survival/adventure and under water
	 */
	public boolean checkConditions() {
		return true;
	}

	/**
	 * Draws a rectangle on the screen
	 * 
	 * @param posX
	 *            the x positon on the screen
	 * @param posY
	 *            the y positon on the screen
	 * @param width
	 *            the width of the rectangle
	 * @param height
	 *            the height of the rectangle
	 * @param color
	 *            the color of the rectangle
	 */
	public static void drawRect(int posX, int posY, int width, int height, int color) {
		if (color == -1) return;
		float f3;
		if(color <= 0xFFFFFF && color >= 0) f3 = 1.0F;
		else f3 = (color >> 24 & 255) / 255.0F;
		float f = (color >> 16 & 255) / 255.0F;
		float f1 = (color >> 8 & 255) / 255.0F;
		float f2 = (color & 255) / 255.0F;
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.color(f, f1, f2, f3);
		GlStateManager.disableDepth();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(posX, (double) posY + height, 0.0D).endVertex();
		vertexbuffer.pos((double) posX + width, (double) posY + height, 0.0D).endVertex();
		vertexbuffer.pos((double) posX + width, posY, 0.0D).endVertex();
		vertexbuffer.pos(posX, posY, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GlStateManager.enableDepth();
		GlStateManager.color(1f, 1f, 1f);
	}
}
