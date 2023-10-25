package net.spellcraftgaming.rpghud.client.gui.overlay;

import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElement.Type;

public class OverlayParentAnchor {

	public boolean hasParent = false;
	public Type parentType;
	public int parentOffsetX;
	public int parentOffsetY;
	public int parentAnchorX;
	public int parentAnchorY;
	
	public OverlayParentAnchor(Type parent, int anchorX, int anchorY, int offsetX, int offsetY) {
		this.parentType = parent;
		this.parentAnchorX = anchorX;
		this.parentAnchorY = anchorY;
		this.parentOffsetX = offsetX;
		this.parentOffsetY = offsetY;
	}
}
