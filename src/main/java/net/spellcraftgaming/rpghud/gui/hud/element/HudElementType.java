package net.spellcraftgaming.rpghud.gui.hud.element;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.I18n;

@Environment(EnvType.CLIENT)
public enum HudElementType {
	VOID(I18n.translate("name.void", new Object[0])),
	HOTBAR(I18n.translate("name.hotbar", new Object[0])),
	HEALTH(I18n.translate("name.health", new Object[0])),
	ARMOR(I18n.translate("name.armor", new Object[0])),
	FOOD(I18n.translate("name.food", new Object[0])),
	HEALTH_MOUNT(I18n.translate("name.health_mount", new Object[0])),
	AIR(I18n.translate("name.air", new Object[0])),
	JUMP_BAR(I18n.translate("name.jump_bar", new Object[0])),
	EXPERIENCE(I18n.translate("name.experience", new Object[0])),
	LEVEL(I18n.translate("name.level", new Object[0])),
	CLOCK(I18n.translate("name.clock", new Object[0])),
	DETAILS(I18n.translate("name.details", new Object[0])),
	WIDGET(I18n.translate("name.widget", new Object[0])),
	COMPASS(I18n.translate("name.compass", new Object[0])),
	ENTITY_INSPECT(I18n.translate("name.entity_inspect", new Object[0]));

	private String displayName;

	private HudElementType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}
}
