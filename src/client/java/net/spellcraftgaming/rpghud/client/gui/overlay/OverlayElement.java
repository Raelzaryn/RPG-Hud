package net.spellcraftgaming.rpghud.client.gui.overlay;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.resource.language.I18n;

public abstract class OverlayElement {

	public final int X_ANCHOR_LEFT = 0;
	public final int X_ANCHOR_CENTER = 1;
	public final int X_ANCHOR_RIGHT = 2;
	
	public final int Y_ANCHOR_TOP = 0;
	public final int Y_ANCHOR_CENTER = 1;
	public final int Y_ANCHOR_BOTTOM = 2;
	
	public final Type type;
	
	public int xAnchor = 0;
	public int yAnchor = 0;
	
	public OverlayElement parent;
	public int parentXOffset;
	public int parentYOffset;
	
	public OverlayElement(Type type, int width, int height) {
		this.type = type;
		this.width = width;
		this.height = height;
	}
	
	protected final int width;
	protected final int height;
	
	public abstract int getPosX();
	public abstract int getPosY();
	
	public abstract float getScale();
	
	public float getInvertedScale() {
		return 1 / getScale();
	}
	public abstract void render(DrawContext dc, float tickDelta);
	
	public enum Type{
		
		DEBUG("name.debug"),
	    HOTBAR("name.hotbar"),
	    HOTBAR_OFFHAND("name.hotbar_offhand");
		
		private String displayName;

		private Type(String displayName) {
			this.displayName = displayName;
		}

	    public String getDisplayName() {
	        return I18n.translate(this.displayName);
	    }
	}
}
