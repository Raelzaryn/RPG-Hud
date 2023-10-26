package net.spellcraftgaming.rpghud.client.gui.overlay;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.spellcraftgaming.rpghud.client.main.RPGHudClient;

public abstract class OverlayElement {

	public final int X_ANCHOR_LEFT = 0;
	public final int X_ANCHOR_CENTER = 1;
	public final int X_ANCHOR_RIGHT = 2;
	
	public final int Y_ANCHOR_TOP = 0;
	public final int Y_ANCHOR_CENTER = 1;
	public final int Y_ANCHOR_BOTTOM = 2;
	
	public final OverlayElementType type;
	
	public int xAnchor = 0;
	public int yAnchor = 0;
	
	public int anchoredX;
	public int anchoredY;
	public int scale;
	public boolean isGuiScale;
	
	protected OverlayParentAnchor parentAnchor;
	
	public OverlayElement(OverlayElementType type, int width, int height) {
		this.type = type;
		this.width = width;
		this.height = height;
		initialize();
	}
	
	protected int width;
	protected int height;
	
	public float getInvertedScale() {
		return 1 / getScale();
	}
	
	public boolean shouldRender() {
		return true;
	}
	
	public abstract void render(DrawContext dc, float tickDelta);
	
	public abstract void initialize();
	
	public int getPosX() {
		MinecraftClient instance = MinecraftClient.getInstance();
		int scaledWidth = instance.getWindow().getScaledWidth();
		
		int posX = 0;
		
		if(hasParent()) {
			OverlayElement parentElement = RPGHudClient.currentHud.elements.get(parentAnchor.parentType);
			int x = parentElement.getPosX();
			int x2 = parentElement.width;
			posX = x;
			if(parentAnchor.parentAnchorX == X_ANCHOR_CENTER) posX += (x2 /2);
			if(parentAnchor.parentAnchorX == X_ANCHOR_RIGHT) posX += x2;
			posX *= (parentElement.getScale() / getScale());
			posX += parentAnchor.parentOffsetX;
		} else {
			posX = (int) ((scaledWidth / 1000D) * anchoredX * getInvertedScale());
		}
		
		if(this.xAnchor == X_ANCHOR_CENTER) posX -= (width / 2);
		if(this.xAnchor == X_ANCHOR_RIGHT) posX -= width;
		
		return posX;
	}

	public int getPosY() {
		MinecraftClient instance = MinecraftClient.getInstance();
		int scaledHeight = instance.getWindow().getScaledHeight();
		
		int posY = 0;
		if(hasParent()) {
			OverlayElement parentElement = RPGHudClient.currentHud.elements.get(parentAnchor.parentType);
			int y = parentElement.getPosY();
			int y2 = parentElement.height;
			posY = y;
			if(parentAnchor.parentAnchorY == Y_ANCHOR_CENTER) posY += (y2 /2);
			if(parentAnchor.parentAnchorY == Y_ANCHOR_BOTTOM) posY += y + y2;

			posY *= (parentElement.getScale() / getScale());
			posY += parentAnchor.parentOffsetY;
		} else {
			posY = (int) ((scaledHeight / 1000D) * anchoredY * getInvertedScale());

		}
		
		if(this.yAnchor == Y_ANCHOR_CENTER) posY -= (height / 2);
		if(this.yAnchor == Y_ANCHOR_BOTTOM) posY -= height;
		
		return posY;
	}

	public float getScale() {
		MinecraftClient instance = MinecraftClient.getInstance();
		double guiScale = instance.getWindow().getScaleFactor();

		float scaleFloat = (float) (scale / guiScale);

		if(isGuiScale) {
			scaleFloat = 1f;
		}
		return scaleFloat;
	}
	
	public boolean hasParent() {
		if(this.parentAnchor != null) return true;
		return false;
	}

}
