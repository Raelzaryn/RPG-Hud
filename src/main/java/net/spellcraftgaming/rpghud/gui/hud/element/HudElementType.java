package net.spellcraftgaming.rpghud.gui.hud.element;

public enum HudElementType {
	VOID("Void"),
	HOTBAR("Hotbar"),
	CROSSHAIR("Crosshair"),
	HEALTH("Player health"),
	ARMOR("Player armor"),
	FOOD("Player hunger"),
	HEALTH_MOUNT("Mount health"),
	AIR("Player breath"),
	JUMP_BAR("Mount Jump bar"),
	EXPERIENCE("Player experience"),
	LEVEL("Player level"),
	CLOCK("Clock"),
	DETAILS("Item Details"),
	WIDGET("Widget"),
	RECORD_OVERLAY("Record Overlay"),
	CHAT("Chat");
	
	private String displayName;

	private HudElementType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}
}
