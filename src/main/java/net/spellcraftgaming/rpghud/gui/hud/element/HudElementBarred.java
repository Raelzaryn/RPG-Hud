package net.spellcraftgaming.rpghud.gui.hud.element;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public abstract class HudElementBarred extends HudElement {

	/** The values of the color red */
	public static final int COLOR_RED = 0xC10000;
	
	/** The values of the color red */
	public static final int COLOR_PINK = 0xFF69B4;
	
	/** The values of the color red */
	public static final int COLOR_BROWN = 0x8b4513;
	
	/** The values of the color white */
	public static final int COLOR_WHITE = 0xF2F2F2;
	
	/** The values of the color white */
	public static final int COLOR_ORANGE = 0xFF8400;
	
	/** The values of the color green */
	public static final int COLOR_GREEN = 0x3BC200;
	
	/** The values of the color red */
	public static final int COLOR_PURPLE = 0xA400F0;

	/** The values of the color blue */
	public static final int COLOR_BLUE = 0x005BC2;
	
	/** The values of the color blue */
	public static final int COLOR_AQUA = 0x00FFFF;
	
	/** The value of the color black */
	public static final int COLOR_BLACK = 0x121212;
	
	/** The values of the color grey */
	public static final int COLOR_GREY = 0x8A8A8A;
	
	/** The values of the color yellow */
	public static final int COLOR_YELLOW = 0xEEEE00;

	/** The values of the default color */
	public static final int[] COLOR_DEFAULT = { 0x4C4C4C, 0x3D3D3D };


	
	public static final int OFFSET_PERCENT = 25;
	
	public static final int OFFSET_PREVIEW = 0x5A5A5A;
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
	public static void drawCustomBar(int x, int y, int width, int height, double value, int colorBarLight, int colorBarDark) {
		drawCustomBar(x, y, width, height, value, HudElementBarred.COLOR_DEFAULT[0], HudElementBarred.COLOR_DEFAULT[1], colorBarLight, colorBarDark, true, 0x000000);
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
	public static void drawCustomBar(int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark) {
		drawCustomBar(x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, true, 0x000000);
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
	public static void drawCustomBar(int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, boolean outlined) {
		drawCustomBar(x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, outlined, 0x000000);
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
	public static void drawCustomBar(int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, int colorOutline) {
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
	public static void drawCustomBar(int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, boolean outlined, int colorOutline) {
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
		if(color == -1) return;
		float f3;
		if(color <= 0xFFFFFF && color >= 0) f3 = 1.0F;
		else f3 = (color >> 24 & 255) / 255.0F;
		float f = (color >> 16 & 255) / 255.0F;
		float f1 = (color >> 8 & 255) / 255.0F;
		float f2 = (color & 255) / 255.0F;
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer vertexbuffer = tessellator.getWorldRenderer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
		GlStateManager.disableDepth();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(posX1, (double) posY1 + height1, 0.0D).endVertex();
		vertexbuffer.pos((double) posX2 + width2, (double) posY2 + height2, 0.0D).endVertex();
		vertexbuffer.pos((double) posX1 + width1, posY2, 0.0D).endVertex();
		vertexbuffer.pos(posX2, posY1, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GlStateManager.enableDepth();
		GlStateManager.color(1f, 1f, 1f);
	}

	public static int offsetColorPercent(int color, int offsetPercent) {
		int colorOffset;
		
		int colorPart = (color >> 16 & 255);
		colorPart -= colorPart / (100 / offsetPercent);
		if(colorPart > 0xFF) colorPart = 0xFF;
		else if (colorPart < 0) colorPart = 0;
		
		colorOffset = colorPart << 16;
		colorPart = (color >> 8 & 255);
		colorPart -= colorPart / (100 / offsetPercent);
		if(colorPart > 0xFF) colorPart = 0xFF;
		else if (colorPart < 0) colorPart = 0;
		
		colorOffset += colorPart << 8;
		colorPart = (color & 255);
		colorPart -= colorPart / (100 / offsetPercent);
		if(colorPart > 0xFF) colorPart = 0xFF;
		else if (colorPart < 0) colorPart = 0;
		colorOffset += colorPart;
		return colorOffset;
	}
	
	public static int offsetColor(int color, int offset) {
		int colorOffset;
		
		int colorPart = (color >> 16 & 255);
		colorPart += (offset >> 16 & 255);
		if(colorPart > 0xFF) colorPart = 0xFF;
		else if (colorPart < 0) colorPart = 0;
		
		colorOffset = colorPart << 16;
		colorPart = (color >> 8 & 255);
		colorPart += (offset >> 8 & 255);
		if(colorPart > 0xFF) colorPart = 0xFF;
		else if (colorPart < 0) colorPart = 0;
		
		colorOffset += colorPart << 8;
		colorPart = (color & 255);
		colorPart += (offset & 255);
		if(colorPart > 0xFF) colorPart = 0xFF;
		else if (colorPart < 0) colorPart = 0;
		colorOffset += colorPart;
		return colorOffset;
	}
}
