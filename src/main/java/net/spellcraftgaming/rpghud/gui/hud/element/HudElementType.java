package net.spellcraftgaming.rpghud.gui.hud.element;

import net.minecraft.client.resources.language.I18n;

public enum HudElementType {
	DEBUG("name.debug"),
    HOTBAR("name.hotbar"),
    HEALTH("name.health"),
    ARMOR("name.armor"),
    FOOD("name.food"),
    HEALTH_MOUNT("name.health_mount"),
    AIR("name.air"),
    JUMP_BAR("name.jump_bar"),
    EXPERIENCE("name.experience"),
    LEVEL("name.level"),
    CLOCK("name.clock"),
    DETAILS("name.details"),
    WIDGET("name.widget"),
    COMPASS("name.compass"),
    ENTITY_INSPECT("name.entity_inspect"),
    STATUS_EFFECTS("name.status_effects"),
	MISC("name.misc");

	private String displayName;

	private HudElementType(String displayName) {
		this.displayName = displayName;
	}

    public String getDisplayName() {
        return I18n.get(this.displayName);
    }
}
