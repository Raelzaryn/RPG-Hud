package net.spellcraftgaming.rpghud.client.gui.overlay;

public class OverlayParentAnchor {

	public boolean hasParent = false;
	public OverlayElementType parentType;
	public int parentOffsetX;
	public int parentOffsetY;
	public int parentAnchorX;
	public int parentAnchorY;
	
	public OverlayParentAnchor(OverlayElementType parent, int anchorX, int anchorY, int offsetX, int offsetY) {
		this.parentType = parent;
		this.parentAnchorX = anchorX;
		this.parentAnchorY = anchorY;
		this.parentOffsetX = offsetX;
		this.parentOffsetY = offsetY;
	}
}
