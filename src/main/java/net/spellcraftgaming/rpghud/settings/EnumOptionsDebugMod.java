package net.spellcraftgaming.rpghud.settings;

import java.util.ArrayList;

import net.minecraft.client.resources.I18n;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public enum EnumOptionsDebugMod {

	FORCE_RENDER_CROSSHAIR(HudElementType.CROSSHAIR, EnumOptionsType.FORCE_RENDER, I18n.format("name.force_render", new Object[0]), I18n.format("tooltip.force_render", new Object[0])),
	RENDER_VANILLA_CROSSHAIR(HudElementType.CROSSHAIR, EnumOptionsType.RENDER_VANILLA, I18n.format("name.render_vanilla", new Object[0]), I18n.format("tooltip.render_vanilla", new Object[0])),
	PREVENT_EVENT_CROSSHAIR(HudElementType.CROSSHAIR, EnumOptionsType.PREVENT_EVENT, I18n.format("name.prevent_event", new Object[0]), I18n.format("tooltip.prevent_event", new Object[0])),
	PREVENT_ELEMENT_RENDER_CROSSHAIR(HudElementType.CROSSHAIR, EnumOptionsType.PREVENT_RENDER, I18n.format("name.prevent_render", new Object[0]), I18n.format("tooltip.prevent_render", new Object[0])),

	FORCE_RENDER_ARMOR(HudElementType.ARMOR, EnumOptionsType.FORCE_RENDER, I18n.format("name.force_render", new Object[0]), I18n.format("tooltip.force_render", new Object[0])),
	RENDER_VANILLA_ARMOR(HudElementType.ARMOR, EnumOptionsType.RENDER_VANILLA, I18n.format("name.render_vanilla", new Object[0]), I18n.format("tooltip.render_vanilla", new Object[0])),
	PREVENT_EVENT_ARMOR(HudElementType.ARMOR, EnumOptionsType.PREVENT_RENDER, I18n.format("name.prevent_event", new Object[0]), I18n.format("tooltip.prevent_event", new Object[0])),
	PREVENT_ELEMENT_RENDER_ARMOR(HudElementType.ARMOR, EnumOptionsType.PREVENT_RENDER, I18n.format("name.prevent_render", new Object[0]), I18n.format("tooltip.prevent_render", new Object[0])),

	FORCE_RENDER_HOTBAR(HudElementType.HOTBAR, EnumOptionsType.FORCE_RENDER, I18n.format("name.force_render", new Object[0]), I18n.format("tooltip.force_render", new Object[0])),
	RENDER_VANILLA_HOTBAR(HudElementType.HOTBAR, EnumOptionsType.RENDER_VANILLA, I18n.format("name.render_vanilla", new Object[0]), I18n.format("tooltip.render_vanilla", new Object[0])),
	PREVENT_EVENT_HOTBAR(HudElementType.HOTBAR, EnumOptionsType.PREVENT_EVENT, I18n.format("name.prevent_event", new Object[0]), I18n.format("tooltip.prevent_event", new Object[0])),
	PREVENT_ELEMENT_RENDER_HOTBAR(HudElementType.HOTBAR, EnumOptionsType.PREVENT_RENDER, I18n.format("name.prevent_render", new Object[0]), I18n.format("tooltip.prevent_render", new Object[0])),

	FORCE_RENDER_AIR(HudElementType.AIR, EnumOptionsType.FORCE_RENDER, I18n.format("name.force_render", new Object[0]), I18n.format("tooltip.force_render", new Object[0])),
	RENDER_VANILLA_AIR(HudElementType.AIR, EnumOptionsType.RENDER_VANILLA, I18n.format("name.render_vanilla", new Object[0]), I18n.format("tooltip.render_vanilla", new Object[0])),
	PREVENT_EVENT_AIR(HudElementType.AIR, EnumOptionsType.PREVENT_EVENT, I18n.format("name.prevent_event", new Object[0]), I18n.format("tooltip.prevent_event", new Object[0])),
	PREVENT_ELEMENT_RENDER_AIR(HudElementType.AIR, EnumOptionsType.PREVENT_RENDER, I18n.format("name.prevent_render", new Object[0]), I18n.format("tooltip.prevent_render", new Object[0])),

	FORCE_RENDER_HEALTH(HudElementType.HEALTH, EnumOptionsType.FORCE_RENDER, I18n.format("name.force_render", new Object[0]), I18n.format("tooltip.force_render", new Object[0])),
	RENDER_VANILLA_HEALTH(HudElementType.HEALTH, EnumOptionsType.RENDER_VANILLA, I18n.format("name.render_vanilla", new Object[0]), I18n.format("tooltip.render_vanilla", new Object[0])),
	PREVENT_EVENT_HEALTH(HudElementType.HEALTH, EnumOptionsType.PREVENT_EVENT, I18n.format("name.prevent_event", new Object[0]), I18n.format("tooltip.prevent_event", new Object[0])),
	PREVENT_ELEMENT_RENDER_HEALTH(HudElementType.HEALTH, EnumOptionsType.PREVENT_RENDER, I18n.format("name.prevent_render", new Object[0]), I18n.format("tooltip.prevent_render", new Object[0])),

	FORCE_RENDER_FOOD(HudElementType.FOOD, EnumOptionsType.FORCE_RENDER, I18n.format("name.force_render", new Object[0]), I18n.format("tooltip.force_render", new Object[0])),
	RENDER_VANILLA_FOOD(HudElementType.FOOD, EnumOptionsType.RENDER_VANILLA, I18n.format("name.render_vanilla", new Object[0]), I18n.format("tooltip.render_vanilla", new Object[0])),
	PREVENT_EVENT_FOOD(HudElementType.FOOD, EnumOptionsType.PREVENT_EVENT, I18n.format("name.prevent_event", new Object[0]), I18n.format("tooltip.prevent_event", new Object[0])),
	PREVENT_ELEMENT_RENDER_FOOD(HudElementType.FOOD, EnumOptionsType.PREVENT_RENDER, I18n.format("name.prevent_render", new Object[0]), I18n.format("tooltip.prevent_render", new Object[0])),

	FORCE_RENDER_EXPERIENCE(HudElementType.EXPERIENCE, EnumOptionsType.FORCE_RENDER, I18n.format("name.force_render", new Object[0]), I18n.format("tooltip.force_render", new Object[0])),
	RENDER_VANILLA_EXPERIENCE(HudElementType.EXPERIENCE, EnumOptionsType.RENDER_VANILLA, I18n.format("name.render_vanilla", new Object[0]), I18n.format("tooltip.render_vanilla", new Object[0])),
	PREVENT_EVENT_EXPERIENCE(HudElementType.EXPERIENCE, EnumOptionsType.PREVENT_EVENT, I18n.format("name.prevent_event", new Object[0]), I18n.format("tooltip.prevent_event", new Object[0])),
	PREVENT_ELEMENT_RENDER_EXPERIENCE(HudElementType.EXPERIENCE, EnumOptionsType.PREVENT_RENDER, I18n.format("name.prevent_render", new Object[0]), I18n.format("tooltip.prevent_render", new Object[0])),

	FORCE_RENDER_LEVEL(HudElementType.LEVEL, EnumOptionsType.FORCE_RENDER, I18n.format("name.force_render", new Object[0]), I18n.format("tooltip.force_render", new Object[0])),
	RENDER_VANILLA_LEVEL(HudElementType.LEVEL, EnumOptionsType.RENDER_VANILLA, I18n.format("name.render_vanilla", new Object[0]), I18n.format("tooltip.render_vanilla", new Object[0])),
	PREVENT_EVENT_LEVEL(HudElementType.LEVEL, EnumOptionsType.PREVENT_EVENT, I18n.format("name.prevent_event", new Object[0]), I18n.format("tooltip.prevent_event", new Object[0])),
	PREVENT_ELEMENT_RENDER_LEVEL(HudElementType.LEVEL, EnumOptionsType.PREVENT_RENDER, I18n.format("name.prevent_render", new Object[0]), I18n.format("tooltip.prevent_render", new Object[0])),

	FORCE_RENDER_HEALTH_MOUNT(HudElementType.HEALTH_MOUNT, EnumOptionsType.FORCE_RENDER, I18n.format("name.force_render", new Object[0]), I18n.format("tooltip.force_render", new Object[0])),
	RENDER_VANILLA_HEALTH_MOUNT(HudElementType.HEALTH_MOUNT, EnumOptionsType.RENDER_VANILLA, I18n.format("name.render_vanilla", new Object[0]), I18n.format("tooltip.render_vanilla", new Object[0])),
	PREVENT_EVENT_HEALTH_MOUNT(HudElementType.HEALTH_MOUNT, EnumOptionsType.PREVENT_EVENT, I18n.format("name.prevent_event", new Object[0]), I18n.format("tooltip.prevent_event", new Object[0])),
	PREVENT_ELEMENT_RENDER_HEALTH_MOUNT(HudElementType.HEALTH_MOUNT, EnumOptionsType.PREVENT_RENDER, I18n.format("name.prevent_render", new Object[0]), I18n.format("tooltip.prevent_render", new Object[0])),

	FORCE_RENDER_JUMP_BAR(HudElementType.JUMP_BAR, EnumOptionsType.FORCE_RENDER, I18n.format("name.force_render", new Object[0]), I18n.format("tooltip.force_render", new Object[0])),
	RENDER_VANILLA_JUMP_BAR(HudElementType.JUMP_BAR, EnumOptionsType.RENDER_VANILLA, I18n.format("name.render_vanilla", new Object[0]), I18n.format("tooltip.render_vanilla", new Object[0])),
	PREVENT_EVENT_JUMP_BAR(HudElementType.JUMP_BAR, EnumOptionsType.PREVENT_EVENT, I18n.format("name.prevent_event", new Object[0]), I18n.format("tooltip.prevent_event", new Object[0])),
	PREVENT_ELEMENT_RENDER_JUMP_BAR(HudElementType.JUMP_BAR, EnumOptionsType.PREVENT_RENDER, I18n.format("name.prevent_render", new Object[0]), I18n.format("tooltip.prevent_render", new Object[0])),

	FORCE_RENDER_CHAT(HudElementType.CHAT, EnumOptionsType.FORCE_RENDER, I18n.format("name.force_render", new Object[0]), I18n.format("tooltip.force_render", new Object[0])),
	RENDER_VANILLA_CHAT(HudElementType.CHAT, EnumOptionsType.RENDER_VANILLA, I18n.format("name.render_vanilla", new Object[0]), I18n.format("tooltip.render_vanilla", new Object[0])),
	PREVENT_EVENT_CHAT(HudElementType.CHAT, EnumOptionsType.PREVENT_EVENT, I18n.format("name.prevent_event", new Object[0]), I18n.format("tooltip.prevent_event", new Object[0])),
	PREVENT_ELEMENT_RENDER_CHAT(HudElementType.CHAT, EnumOptionsType.PREVENT_RENDER, I18n.format("name.prevent_render", new Object[0]), I18n.format("tooltip.prevent_render", new Object[0]));

	public enum EnumOptionsType {
		FORCE_RENDER,
		RENDER_VANILLA,
		PREVENT_EVENT,
		PREVENT_RENDER;
	}

	/** The name of the setting */
	private final String enumString;
	/** The HudElementType the setting belongs to */
	private final HudElementType type;
	/** The EnumOptionsType of the setting */
	private final EnumOptionsType optionType;
	/** The description of this setting */
	private final String tooltip;

	private EnumOptionsDebugMod(HudElementType type, EnumOptionsType optionType, String enumString, String tooltip) {
		this.type = type;
		this.optionType = optionType;
		this.enumString = enumString;
		this.tooltip = tooltip;
	}

	/**
	 * Function to get the setting with a specified ordinal
	 * 
	 * @param the
	 *            ordinal
	 * @return the setting
	 */
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

	/**
	 * Function to get the settings which belong to the specified HudElemenType
	 * 
	 * @param type
	 *            the HudElementType whose setting will be returned
	 * @return the settings
	 */
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
		return options.toArray(new EnumOptionsDebugMod[0]);
	}

	/**
	 * Function to get the HudElementTypes which have settings tied to them
	 * 
	 * @return the HudElementTypes
	 */
	public static HudElementType[] getDebugTypes() {
		EnumOptionsDebugMod[] aenumoptions = values();
		ArrayList<HudElementType> options = new ArrayList<HudElementType>();
		int j = aenumoptions.length;
		for (int k = 0; k < j; k++) {
			if (!options.contains(aenumoptions[k].type)) {
				options.add(aenumoptions[k].type);
			}
		}
		return options.toArray(new HudElementType[0]);
	}

	/**
	 * Function to get the setting which is of the a specified type and belongs
	 * to the specified HudElementType
	 * 
	 * @param type
	 *            the HudElementType this setting is belongs to
	 * @param settingType
	 *            the type of this setting
	 * @return the setting
	 */
	public static EnumOptionsDebugMod getEnumOptionOfWith(HudElementType type, EnumOptionsType settingType) {
		EnumOptionsDebugMod[] aenumoptions = values();
		int j = aenumoptions.length;
		for (int k = 0; k < j; k++) {
			EnumOptionsDebugMod enumoptions = aenumoptions[k];
			if (enumoptions.type == type && enumoptions.optionType == settingType) {
				return enumoptions;
			}
		}
		return null;
	}

	/** Returns the ordinal of this setting */
	public int returnEnumOrdinal() {
		return ordinal();
	}

	/** Returns the name of this setting */
	public String getEnumString() {
		return this.enumString;
	}

	/** Returns the HudElementType this setting belongs to */
	public HudElementType getType() {
		return this.type;
	}

	/** Returns the type of this setting */
	public EnumOptionsType getOptionType() {
		return this.optionType;
	}

	/** Returns the description of this setting */
	public String getTooltip() {
		return this.tooltip;
	}
}
