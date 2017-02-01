package net.spellcraftgaming.rpghud.gui.hud.element;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public abstract class HudElementBarred extends HudElement {

	/** The values of the color red */
	public static final int[] COLOR_RED = { 0xFFC10000, 0xFF8B0000 };

	/** The values of the color blue */
	public static final int[] COLOR_BLUE = { 0xFF005BC2, 0xFF00428C };

	/** The values of the color yellow */
	public static final int[] COLOR_YELLOW = { 0xFFEEEE00, 0xFFDADA00 };

	/** The values of the color green */
	public static final int[] COLOR_GREEN = { 0xFF3BC200, 0xFF2B8C00 };

	/** The values of the color white */
	public static final int[] COLOR_WHITE = { 0xFFF2F2F2, 0xFFE6E6E6 };

	/** The values of the color grey */
	public static final int[] COLOR_GREY = { 0xFFBFBFBF, 0xFF808080 };

	/** The values of the default color */
	public static final int[] COLOR_DEFAULT = { 0xFF4C4C4C, 0xFF3D3D3D };

	/** The value of the color black */
	public static final int COLOR_BLACK = 0xFF000000;

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
	public HudElementBarred(HudElementType type, int posX, int posY, int width, int height, boolean moveable) {
		super(type, posX, posY, width, height, moveable);
	}

	/**
	 * Returns the color for the parameter
	 * 
	 * @param setting
	 *            should the value of a ModSettings color setting
	 * @return the color
	 */
	public int[] getColor(int setting) {
		int[] color = new int[2];
		switch (setting) {
		case 0:
			color[0] = HudElementBarred.COLOR_RED[0];
			color[1] = HudElementBarred.COLOR_RED[1];
			break;
		case 1:
			color[0] = HudElementBarred.COLOR_BLUE[0];
			color[1] = HudElementBarred.COLOR_BLUE[1];
			break;
		case 2:
			color[0] = HudElementBarred.COLOR_GREEN[0];
			color[1] = HudElementBarred.COLOR_GREEN[1];
			break;
		case 3:
			color[0] = HudElementBarred.COLOR_YELLOW[0];
			color[1] = HudElementBarred.COLOR_YELLOW[1];
			break;
		case 4:
			color[0] = HudElementBarred.COLOR_WHITE[0];
			color[1] = HudElementBarred.COLOR_WHITE[1];
			break;
		case 5:
			color[0] = HudElementBarred.COLOR_GREY[0];
			color[1] = HudElementBarred.COLOR_GREY[1];
		}
		return color;
	}

	/**
	 * Draw an outline on the screen
	 * 
	 * @param x
	 *            the x position on the screen
	 * @param y
	 *            the y position on the screen
	 * @param width
	 *            the width of the outline
	 * @param height
	 *            the height of the outline
	 * @param color
	 */
	protected static void drawOutline(int x, int y, int width, int height, int color) {
		drawRect(x, y, width, 1, color);
		drawRect(x, y, 1, height, color);
		drawRect(x + width - 1, y, 1, height, color);
		drawRect(x, y + height - 1, width, 1, color);
	}

	/**
	 * Draws a bar on the screen
	 * 
	 * @param x
	 *            the x position on the screen
	 * @param y
	 *            the y position on the screen
	 * @param width
	 *            the width of the bar (with outline)
	 * @param height
	 *            the height of the bar (with outline)
	 * @param value
	 *            the converted value of the bar (maxed at 100.0D)
	 * @param colorBarLight
	 *            the color for the bar (light)
	 * @param colorBarDark
	 *            the color for the bar (dark
	 */
	protected void drawCustomBar(int x, int y, int width, int height, double value, int colorBarLight, int colorBarDark) {
		drawCustomBar(x, y, width, height, value, HudElementBarred.COLOR_DEFAULT[0], HudElementBarred.COLOR_DEFAULT[1], colorBarLight, colorBarDark, true, HudElementBarred.COLOR_BLACK);
	}

	/**
	 * Draws a bar on the screen
	 * 
	 * @param x
	 *            the x position on the screen
	 * @param y
	 *            the y position on the screen
	 * @param width
	 *            the width of the bar (with outline)
	 * @param height
	 *            the height of the bar (with outline)
	 * @param value
	 *            the converted value of the bar (maxed at 100.0D)
	 * @param colorGroundLight
	 *            the color for the background (light)
	 * @param colorGroundDark
	 *            the color for the background (dark)
	 * @param colorBarLight
	 *            the color for the bar (light)
	 * @param colorBarDark
	 *            the color for the bar (dark
	 */
	protected void drawCustomBar(int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark) {
		drawCustomBar(x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, true, HudElementBarred.COLOR_BLACK);
	}

	/**
	 * Draws a bar on the screen
	 * 
	 * @param x
	 *            the x position on the screen
	 * @param y
	 *            the y position on the screen
	 * @param width
	 *            the width of the bar (with outline)
	 * @param height
	 *            the height of the bar (with outline)
	 * @param value
	 *            the converted value of the bar (maxed at 100.0D)
	 * @param colorGroundLight
	 *            the color for the background (light)
	 * @param colorGroundDark
	 *            the color for the background (dark)
	 * @param colorBarLight
	 *            the color for the bar (light)
	 * @param colorBarDark
	 *            the color for the bar (dark
	 * @param outlined
	 *            whether this bar has an outline or not
	 */
	protected void drawCustomBar(int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, boolean outlined) {
		drawCustomBar(x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, outlined, HudElementBarred.COLOR_BLACK);
	}

	/**
	 * Draws a bar on the screen
	 * 
	 * @param x
	 *            the x position on the screen
	 * @param y
	 *            the y position on the screen
	 * @param width
	 *            the width of the bar (with outline)
	 * @param height
	 *            the height of the bar (with outline)
	 * @param value
	 *            the converted value of the bar (maxed at 100.0D)
	 * @param colorGroundLight
	 *            the color for the background (light)
	 * @param colorGroundDark
	 *            the color for the background (dark)
	 * @param colorBarLight
	 *            the color for the bar (light)
	 * @param colorBarDark
	 *            the color for the bar (dark
	 * @param colorOutline
	 *            the color of the outline
	 */
	protected void drawCustomBar(int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, int colorOutline) {
		drawCustomBar(x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, true, colorOutline);
	}

	/**
	 * Draws a bar on the screen
	 * 
	 * @param x
	 *            the x position on the screen
	 * @param y
	 *            the y position on the screen
	 * @param width
	 *            the width of the bar (with outline)
	 * @param height
	 *            the height of the bar (with outline)
	 * @param value
	 *            the converted value of the bar (maxed at 100.0D)
	 * @param colorGroundLight
	 *            the color for the background (light)
	 * @param colorGroundDark
	 *            the color for the background (dark)
	 * @param colorBarLight
	 *            the color for the bar (light)
	 * @param colorBarDark
	 *            the color for the bar (dark
	 * @param outlined
	 *            whether this bar has an outline or not
	 * @param colorOutline
	 *            the color of the outline
	 */
	protected void drawCustomBar(int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, boolean outlined, int colorOutline) {
		if (value < 0.0D) {
			value = 0.0D;
		}

		int offset = 0;
		if (outlined)
			offset = 1;

		int filledWidth = width;
		filledWidth = width - (offset * 2);
		if (filledWidth < 0)
			filledWidth = 0;
		int filledHeight = width;
		filledHeight = height - (offset * 2);
		if (filledHeight < 0)
			filledHeight = 0;

		int percentFilled = (int) Math.round(value / 100.0D * filledWidth);

		if (outlined)
			drawOutline(x, y, width, height, colorOutline);
		int halfedFilledHeight = filledHeight / 2;

		drawRect(x + offset, y + offset, percentFilled, halfedFilledHeight, colorBarLight);
		drawRect(x + offset, y + offset + halfedFilledHeight, percentFilled, filledHeight - halfedFilledHeight, colorBarDark);

		if (filledWidth - percentFilled > 0) {
			drawRect(x + offset + percentFilled, y + offset, filledWidth - percentFilled, halfedFilledHeight, colorGroundLight);
			drawRect(x + offset + percentFilled, y + offset + halfedFilledHeight, filledWidth - percentFilled, filledHeight - halfedFilledHeight, colorGroundDark);
		}
	}

	/**
	 * Draws a tetragon on the screen
	 * 
	 * @param posX1
	 *            x position of the upper left corner
	 * @param posX2
	 *            x position of the lower left corner
	 * @param posY1
	 *            y position of the upper left corner
	 * @param posY2
	 *            y position of the lower left corner
	 * @param width1
	 *            width of the top edge
	 * @param width2
	 *            width of the bottom edge
	 * @param height1
	 *            height of the left edge
	 * @param height2
	 *            height of the right edge
	 * @param color
	 *            color of the tetragon (hexa format 0xAARRGGBB)
	 */
	public void drawTetragon(int posX1, int posX2, int posY1, int posY2, int width1, int width2, int height1, int height2, int color) {
		float f3 = (color >> 24 & 255) / 255.0F;
		float f = (color >> 16 & 255) / 255.0F;
		float f1 = (color >> 8 & 255) / 255.0F;
		float f2 = (color & 255) / 255.0F;
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.color(f, f1, f2, f3);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(posX1, (double) posY1 + height1, 0.0D).endVertex();
		vertexbuffer.pos((double) posX2 + width2, (double) posY2 + height2, 0.0D).endVertex();
		vertexbuffer.pos((double) posX1 + width1, posY2, 0.0D).endVertex();
		vertexbuffer.pos(posX2, posY1, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

}
