package net.spellcraftgaming.rpghud.gui.hud.element;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum HudElementType {
	VOID(I18n.format("name.void", new Object[0])),
	HOTBAR(I18n.format("name.hotbar", new Object[0])),
	HEALTH(I18n.format("name.health", new Object[0])),
	ARMOR(I18n.format("name.armor", new Object[0])),
	FOOD(I18n.format("name.food", new Object[0])),
	HEALTH_MOUNT(I18n.format("name.health_mount", new Object[0])),
	AIR(I18n.format("name.air", new Object[0])),
	JUMP_BAR(I18n.format("name.jump_bar", new Object[0])),
	EXPERIENCE(I18n.format("name.experience", new Object[0])),
	LEVEL(I18n.format("name.level", new Object[0])),
	CLOCK(I18n.format("name.clock", new Object[0])),
	DETAILS(I18n.format("name.details", new Object[0])),
	WIDGET(I18n.format("name.widget", new Object[0])),
	COMPASS(I18n.format("name.compass", new Object[0])),
	ENTITY_INSPECT(I18n.format("name.entity_inspect", new Object[0])),
	STATUS_EFFECTS(I18n.format("name.status_effects", new Object[0]));

	private String displayName;

	private HudElementType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}
}
