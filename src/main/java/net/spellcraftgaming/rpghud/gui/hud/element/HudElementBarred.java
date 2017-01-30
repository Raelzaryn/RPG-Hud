package net.spellcraftgaming.rpghud.gui.hud.element;

import net.minecraft.client.gui.Gui;

public abstract class HudElementBarred extends HudElement{

	
	public int[] colorRed = {0xFFC10000, 0xFF8B0000};
	public int[] colorBlue = {0xFF005BC2, 0xFF00428C};
	public int[] colorYellow = {0xFFEEEE00, 0xFFDADA00};
	public int[] colorGreen = {0xFF3BC200, 0xFF2B8C00};
	public int[] colorWhite = {0xFFF2F2F2, 0xFFE6E6E6};
	public int[] colorGrey = {0xFFBFBFBF, 0xFF808080};
	public int[] colorDefault = {0xFF4C4C4C, 0xFF3D3D3D};
	public int colorBlack = 0xFF000000;
	
	public HudElementBarred(HudElementType type, int posX, int posY, int width, int height, boolean moveable) {
		super(type, posX, posY, width, height, moveable);
	}
	
	public int[] getColor(int setting) {
		int[] color = new int[2];
		switch (setting)
		{
		case 0:
			color[0] = this.colorRed[0];
			color[1] = this.colorRed[1];
			break;
		case 1:
			color[0] = this.colorBlue[0];
			color[1] = this.colorBlue[1];
			break;
		case 2:
			color[0] = this.colorGreen[0];
			color[1] = this.colorGreen[1];
			break;
		case 3:
			color[0] = this.colorYellow[0];
			color[1] = this.colorYellow[1];
			break;
		case 4:
			color[0] = this.colorWhite[0];
			color[1] = this.colorWhite[1];
			break;
		case 5:
			color[0] = this.colorGrey[0];
			color[1] = this.colorGrey[1];
		}
		return color;
	}
	
	protected static void drawOutline(int x, int y, int width, int height, int color) {
		Gui.drawRect(x, y, x + width, y + 1, color);
		Gui.drawRect(x, y, x + 1, y + height, color);
		Gui.drawRect(x + width - 1, y, x + width, y + height, color);
		Gui.drawRect(x, y + height - 1, x + width, y + height, color);
	}
	
	protected void drawCustomBar(int x, int y, int width, int height, double value, int colorBarLight, int colorBarDark) {
		drawCustomBar(x, y, width, height, value, this.colorDefault[0], this.colorDefault[1], colorBarLight, colorBarDark, true, this.colorBlack);
	}
	
	protected void drawCustomBar(int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark) {
		drawCustomBar(x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, true, this.colorBlack);
	}
	
	protected static void drawCustomBar(int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, int colorOutline) {
		drawCustomBar(x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, true, colorOutline);
	}
	
	protected static void drawCustomBar(int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, boolean outlined, int colorOutline) {
		if (value < 0.0D) {
			value = 0.0D;
		}
		int percentFilled = (int) Math.round(value / 100.0D * (width - 2));
		if (outlined)
			drawOutline(x, y, width, height, colorOutline);
		int var2 = width - 2;
		if (var2 < 0)
			var2 = 0;
		int var3 = height - 2;
		if (var3 < 0)
			var3 = 0;
		int var4 = (int) Math.round(var3 / 6.0D * 2.75D);
		Gui.drawRect(x + 1, y + 1, x + 1 + percentFilled, y + var4 + 1, colorBarLight);
		Gui.drawRect(x + 1, y + 1 + var4, x + 1 + percentFilled, y + var3 + 1, colorBarDark);
		if (var2 - percentFilled > 0) {
			Gui.drawRect(x + 1 + percentFilled, y + 1, x + 1 + var2, y + var4 + 1, colorGroundLight);
			Gui.drawRect(x + 1 + percentFilled, y + 1 + var4, x + 1 + var2, y + var3 + 1, colorGroundDark);
		}
	}

}
