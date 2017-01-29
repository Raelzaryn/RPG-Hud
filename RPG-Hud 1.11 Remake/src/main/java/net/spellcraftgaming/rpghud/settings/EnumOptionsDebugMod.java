package net.spellcraftgaming.rpghud.settings;

import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public enum EnumOptionsDebugMod {

	FORCE_RENDER_CROSSHAIR(HudElementType.CROSSHAIR, "Force rendering", false, true),
	RENDER_VANILLA_CROSSHAIR(HudElementType.CROSSHAIR, "Render vanilla element", false, true),
	PREVENT_EVENT_CROSSHAIR(HudElementType.CROSSHAIR, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_CROSSHAIR(HudElementType.CROSSHAIR, "Prevent rendering", false, true),
	
	FORCE_RENDER_ARMOR(HudElementType.ARMOR, "Force rendering", false, true),
	RENDER_VANILLA_ARMOR(HudElementType.ARMOR, "Render vanilla element", false, true),
	PREVENT_EVENT_ARMOR(HudElementType.ARMOR, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_ARMOR(HudElementType.ARMOR, "Prevent rendering", false, true),
	
	FORCE_RENDER_HOTBAR(HudElementType.HOTBAR, "Force rendering", false, true),
	RENDER_VANILLA_HOTBAR(HudElementType.HOTBAR, "Render vanilla element", false, true),
	PREVENT_EVENT_HOTBAR(HudElementType.HOTBAR, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_HOTBAR(HudElementType.HOTBAR, "Prevent rendering", false, true),
	
	FORCE_RENDER_AIR(HudElementType.AIR, "Force rendering", false, true),
	RENDER_VANILLA_AIR(HudElementType.AIR, "Render vanilla element", false, true),
	PREVENT_EVENT_AIR(HudElementType.AIR, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_AIR(HudElementType.AIR, "Prevent rendering", false, true),
	
	FORCE_RENDER_HEALTH(HudElementType.HEALTH, "Force rendering", false, true),
	RENDER_VANILLA_HEALTH(HudElementType.HEALTH, "Render vanilla element", false, true),
	PREVENT_EVENT_HEALTH(HudElementType.HEALTH, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_HEALTH(HudElementType.HEALTH, "Prevent rendering", false, true),
	
	FORCE_RENDER_FOOD(HudElementType.FOOD, "Force rendering", false, true),
	RENDER_VANILLA_FOOD(HudElementType.FOOD, "Render vanilla element", false, true),
	PREVENT_EVENT_FOOD(HudElementType.FOOD, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_FOOD(HudElementType.FOOD, "Prevent rendering", false, true),
	
	FORCE_RENDER_EXPERIENCE(HudElementType.EXPERIENCE, "Force rendering", false, true),
	RENDER_VANILLA_EXPERIENCE(HudElementType.EXPERIENCE, "Render vanilla element", false, true),
	PREVENT_EVENT_EXPERIENCE(HudElementType.EXPERIENCE, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_EXPERIENCE(HudElementType.EXPERIENCE, "Prevent rendering", false, true),
	
	FORCE_RENDER_LEVEL(HudElementType.LEVEL, "Force rendering", false, true),
	RENDER_VANILLA_LEVEL(HudElementType.LEVEL, "Render vanilla element", false, true),
	PREVENT_EVENT_LEVEL(HudElementType.LEVEL, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_LEVEL(HudElementType.LEVEL, "Prevent rendering", false, true),
	
	FORCE_RENDER_HEALTH_MOUNT(HudElementType.HEALTH_MOUNT, "Force rendering", false, true),
	RENDER_VANILLA_HEALTH_MOUNT(HudElementType.HEALTH_MOUNT, "Render vanilla element", false, true),
	PREVENT_EVENT_HEALTH_MOUNT(HudElementType.HEALTH_MOUNT, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_HEALTH_MOUNT(HudElementType.HEALTH_MOUNT, "Prevent rendering", false, true),
	
	FORCE_RENDER_JUMP_BAR(HudElementType.JUMP_BAR, "Force rendering", false, true),
	RENDER_VANILLA_JUMP_BAR(HudElementType.JUMP_BAR, "Render vanilla element", false, true),
	PREVENT_EVENT_JUMP_BAR(HudElementType.JUMP_BAR, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_JUMP_BAR(HudElementType.JUMP_BAR, "Prevent rendering", false, true);
	
	
	private final boolean enumFloat;
	private final boolean enumBoolean;
	private final String enumString;
	private final HudElementType type;
	
	public static EnumOptionsDebugMod getEnumOptions(int par0) {
		EnumOptionsDebugMod[] aenumoptions = values();
		int j = aenumoptions.length;
		for (int k = 0; k < j; k++) {
			EnumOptionsDebugMod enumoptions = aenumoptions[k];
			if (enumoptions.returnEnumOrdinal() == par0) {
				return enumoptions;
			}
		}
		return null;
	}

	public static EnumOptionsDebugMod[] getEnumOptions(HudElementType type) {
		EnumOptionsDebugMod[] aenumoptions = values();
		int j = aenumoptions.length;
		int n = 0;
		for (int k = 0; k < j; k++) {
			if(aenumoptions[k].type == type) n++;
		}
		n = 0;
		EnumOptionsDebugMod[] optionsType = new EnumOptionsDebugMod[n];
		for (int k = 0; k < j; k++) {
			if(aenumoptions[k].type == type) {
				optionsType[n] = aenumoptions[k];
				n++;
			}
		}
		return optionsType;
	}
	
	private EnumOptionsDebugMod(HudElementType type, String enumString, boolean enumFloat, boolean enumBoolean) {
		this.type = type;
		this.enumString = enumString;
		this.enumFloat = enumFloat;
		this.enumBoolean = enumBoolean;
	}

	public boolean getEnumFloat() {
		return this.enumFloat;
	}

	public boolean getEnumBoolean() {
		return this.enumBoolean;
	}

	public int returnEnumOrdinal() {
		return ordinal();
	}

	public String getEnumString() {
		return this.enumString;
	}
}
