package net.spellcraftgaming.rpghud.client.gui.overlay;

import net.minecraft.client.resource.language.I18n;

public enum OverlayElementType {

	DEBUG("name.debug"),
    HOTBAR("name.hotbar"),
    HOTBAR_OFFHAND("name.hotbar_offhand"),
    ATTACK_INDICATOR("name.attack_indicator"),
    EXPERIENCE("name.experience"),
    LEVEL("name.level"),
    HEALTH("name.health"),
    ARMOR("name.armor"),
    HUNGER("name.hunger");
	
	private String displayName;

	private OverlayElementType(String displayName) {
		this.displayName = displayName;
	}

    public String getDisplayName() {
        return I18n.translate(this.displayName);
    }
}
