package net.spellcraftgaming.rpghud.settings;

import java.util.ArrayList;

import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public enum EnumOptionsDebugMod {
	
	FORCE_RENDER_CROSSHAIR(HudElementType.CROSSHAIR, EnumOptionsType.FORCE_RENDER, "Force rendering", false, true),
	RENDER_VANILLA_CROSSHAIR(HudElementType.CROSSHAIR, EnumOptionsType.RENDER_VANILLA, "Render vanilla element", false, true),
	PREVENT_EVENT_CROSSHAIR(HudElementType.CROSSHAIR, EnumOptionsType.PREVENT_EVENT, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_CROSSHAIR(HudElementType.CROSSHAIR, EnumOptionsType.PREVENT_RENDER, "Prevent rendering", false, true),
	
	FORCE_RENDER_ARMOR(HudElementType.ARMOR, EnumOptionsType.FORCE_RENDER, "Force rendering", false, true),
	RENDER_VANILLA_ARMOR(HudElementType.ARMOR, EnumOptionsType.RENDER_VANILLA, "Render vanilla element", false, true),
	PREVENT_EVENT_ARMOR(HudElementType.ARMOR, EnumOptionsType.PREVENT_RENDER, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_ARMOR(HudElementType.ARMOR, EnumOptionsType.PREVENT_RENDER, "Prevent rendering", false, true),
	
	FORCE_RENDER_HOTBAR(HudElementType.HOTBAR, EnumOptionsType.FORCE_RENDER, "Force rendering", false, true),
	RENDER_VANILLA_HOTBAR(HudElementType.HOTBAR, EnumOptionsType.RENDER_VANILLA, "Render vanilla element", false, true),
	PREVENT_EVENT_HOTBAR(HudElementType.HOTBAR, EnumOptionsType.PREVENT_EVENT, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_HOTBAR(HudElementType.HOTBAR, EnumOptionsType.PREVENT_RENDER, "Prevent rendering", false, true),
	
	FORCE_RENDER_AIR(HudElementType.AIR, EnumOptionsType.FORCE_RENDER, "Force rendering", false, true),
	RENDER_VANILLA_AIR(HudElementType.AIR, EnumOptionsType.RENDER_VANILLA, "Render vanilla element", false, true),
	PREVENT_EVENT_AIR(HudElementType.AIR, EnumOptionsType.PREVENT_EVENT, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_AIR(HudElementType.AIR, EnumOptionsType.PREVENT_RENDER, "Prevent rendering", false, true),
	
	FORCE_RENDER_HEALTH(HudElementType.HEALTH, EnumOptionsType.FORCE_RENDER, "Force rendering", false, true),
	RENDER_VANILLA_HEALTH(HudElementType.HEALTH, EnumOptionsType.RENDER_VANILLA, "Render vanilla element", false, true),
	PREVENT_EVENT_HEALTH(HudElementType.HEALTH, EnumOptionsType.PREVENT_EVENT, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_HEALTH(HudElementType.HEALTH, EnumOptionsType.PREVENT_RENDER, "Prevent rendering", false, true),
	
	FORCE_RENDER_FOOD(HudElementType.FOOD, EnumOptionsType.FORCE_RENDER, "Force rendering", false, true),
	RENDER_VANILLA_FOOD(HudElementType.FOOD, EnumOptionsType.RENDER_VANILLA, "Render vanilla element", false, true),
	PREVENT_EVENT_FOOD(HudElementType.FOOD, EnumOptionsType.PREVENT_EVENT, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_FOOD(HudElementType.FOOD, EnumOptionsType.PREVENT_RENDER, "Prevent rendering", false, true),
	
	FORCE_RENDER_EXPERIENCE(HudElementType.EXPERIENCE, EnumOptionsType.FORCE_RENDER, "Force rendering", false, true),
	RENDER_VANILLA_EXPERIENCE(HudElementType.EXPERIENCE, EnumOptionsType.RENDER_VANILLA, "Render vanilla element", false, true),
	PREVENT_EVENT_EXPERIENCE(HudElementType.EXPERIENCE, EnumOptionsType.PREVENT_EVENT, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_EXPERIENCE(HudElementType.EXPERIENCE, EnumOptionsType.PREVENT_RENDER, "Prevent rendering", false, true),
	
	FORCE_RENDER_LEVEL(HudElementType.LEVEL, EnumOptionsType.FORCE_RENDER, "Force rendering", false, true),
	RENDER_VANILLA_LEVEL(HudElementType.LEVEL, EnumOptionsType.RENDER_VANILLA, "Render vanilla element", false, true),
	PREVENT_EVENT_LEVEL(HudElementType.LEVEL, EnumOptionsType.PREVENT_EVENT, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_LEVEL(HudElementType.LEVEL, EnumOptionsType.PREVENT_RENDER, "Prevent rendering", false, true),
	
	FORCE_RENDER_HEALTH_MOUNT(HudElementType.HEALTH_MOUNT, EnumOptionsType.FORCE_RENDER, "Force rendering", false, true),
	RENDER_VANILLA_HEALTH_MOUNT(HudElementType.HEALTH_MOUNT, EnumOptionsType.RENDER_VANILLA, "Render vanilla element", false, true),
	PREVENT_EVENT_HEALTH_MOUNT(HudElementType.HEALTH_MOUNT, EnumOptionsType.PREVENT_EVENT, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_HEALTH_MOUNT(HudElementType.HEALTH_MOUNT, EnumOptionsType.PREVENT_RENDER, "Prevent rendering", false, true),
	
	FORCE_RENDER_JUMP_BAR(HudElementType.JUMP_BAR, EnumOptionsType.FORCE_RENDER, "Force rendering", false, true),
	RENDER_VANILLA_JUMP_BAR(HudElementType.JUMP_BAR, EnumOptionsType.RENDER_VANILLA, "Render vanilla element", false, true),
	PREVENT_EVENT_JUMP_BAR(HudElementType.JUMP_BAR, EnumOptionsType.PREVENT_EVENT, "Prevent event", false, true),
	PREVENT_ELEMENT_RENDER_JUMP_BAR(HudElementType.JUMP_BAR, EnumOptionsType.PREVENT_RENDER, "Prevent rendering", false, true);
	
	public enum EnumOptionsType{
		FORCE_RENDER,
		RENDER_VANILLA,
		PREVENT_EVENT,
		PREVENT_RENDER;
	}
	
	private final boolean enumFloat;
	private final boolean enumBoolean;
	private final String enumString;
	private final HudElementType type;
	private final EnumOptionsType optionType;
	
	private EnumOptionsDebugMod(HudElementType type, EnumOptionsType optionType, String enumString, boolean enumFloat, boolean enumBoolean) {
		this.type = type;
		this.optionType = optionType;
		this.enumString = enumString;
		this.enumFloat = enumFloat;
		this.enumBoolean = enumBoolean;
	}

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
	
	public static EnumOptionsDebugMod[] getEnumOptionsOf(HudElementType type) {
		EnumOptionsDebugMod[] aenumoptions = values();
		ArrayList<EnumOptionsDebugMod> options = new ArrayList<EnumOptionsDebugMod>();
		int j = aenumoptions.length;
		for (int k = 0; k < j; k++) {
			EnumOptionsDebugMod enumoptions = aenumoptions[k];
			if (enumoptions.type == type) {
				options.add(enumoptions);
			}
		}
		return options.toArray(aenumoptions);
	}
	
	public static EnumOptionsDebugMod getEnumOptionOfWith(HudElementType type, EnumOptionsType type2) {
		EnumOptionsDebugMod[] aenumoptions = values();
		int j = aenumoptions.length;
		for (int k = 0; k < j; k++) {
			EnumOptionsDebugMod enumoptions = aenumoptions[k];
			if (enumoptions.type == type && enumoptions.optionType == type2) {
				return enumoptions;
			}
		}
		return null;
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
	
	public HudElementType getType() {
		return this.type;
	}

	public EnumOptionsType getOptionType() {
		return this.optionType;
	}

	public static String getTooltip() {
		return null;
	}
}
