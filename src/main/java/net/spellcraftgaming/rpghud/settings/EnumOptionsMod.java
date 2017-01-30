package net.spellcraftgaming.rpghud.settings;

import net.minecraft.client.resources.I18n;

public enum EnumOptionsMod {

	BUTTON_TOOLTIP_ENABLED("Button Tooltip", I18n.format("tooltip.button_tooltip", new Object[0]), false, true),
	SHOW_ARMOR("Show Armor", I18n.format("tooltip.show_armor", new Object[0]), false, true),
	SHOW_ITEMDURABILITY("Show Item Durabiliy", I18n.format("tooltip.item_durability", new Object[0]), false, true),
	SHOW_ITEMCOUNT("Show Block count", I18n.format("tooltip.item_count", new Object[0]), false, true),
	SHOW_ARROWCOUNT("Show Arrow count", I18n.format("tooltip.arrow_count", new Object[0]), false, true),
	SHOW_NUMBERS_HEALTH("Show Health Value", I18n.format("tooltip.numbers_health", new Object[0]), false, true),  
	SHOW_NUMBERS_STAMINA("Show Stamina Value", I18n.format("tooltip.numbers_stamina", new Object[0]), false, true),  
	SHOW_NUMBERS_EXPERIENCE("Show Experience Value", I18n.format("tooltip.numbers_exp", new Object[0]), false, true),  
	ENABLE_CLOCK("Enable Clock", I18n.format("tooltip.enable_clock", new Object[0]), false, true),
	ENABLE_IMMERSIVE_CLOCK("Enable Immersive Clock", I18n.format("tooltip.enable_immersive_clock", new Object[0]), false, true),
	ENABLE_TIMECOLOR("Enable Colored Clock", I18n.format("tooltip.enable_timecolor", new Object[0]), false, true), 
	RENDER_PLAYER_FACE("Render Player Face", I18n.format("tooltip.player_face", new Object[0]), false, true),  
	HUD_TYPE("Hud Type", I18n.format("tooltip.hud_type", new Object[0]), false, false),
	COLOR_HEALTH("Health Color", I18n.format("tooltip.color_health", new Object[0]), false, false),
	COLOR_STAMINA("Stamina Color", I18n.format("tooltip.color_stamina", new Object[0]), false, false),  
	COLOR_AIR("Air Color", I18n.format("tooltip.color_air", new Object[0]), false, false),  
	COLOR_EXPERIENCE("Experience Color", I18n.format("tooltip.color_exp", new Object[0]), false, false),  
	COLOR_JUMPBAR("Jumpbar Color", I18n.format("tooltip.color_jumpbar", new Object[0]), false, false),
	SHOW_HUNGERPREVIEW("Show Hunger Preview", I18n.format("tooltip.show_hungerpreview", new Object[0]), false, true),
	CLOCK_TIME_FORMAT("Clock Time Format", I18n.format("tooltip.clock_time_format", new Object[0]), false, false),
	REDUCE_SIZE("Reduced Detail Size", I18n.format("tooltip.reduce_size", new Object[0]), false, true);
	
	private final boolean isFloat;
	private final boolean isBoolean;
	private final String enumName;
	private final String tooltip;

	public static EnumOptionsMod getEnumOptions(int par0) {
		EnumOptionsMod[] aenumoptions = values();
		int j = aenumoptions.length;
		for (int k = 0; k < j; k++) {
			EnumOptionsMod enumoptions = aenumoptions[k];
			if (enumoptions.returnEnumOrdinal() == par0) {
				return enumoptions;
			}
		}
		return null;
	}

	private EnumOptionsMod(String name, String tooltip, boolean isFloat, boolean isBoolean) {
		this.enumName = name;
		this.isFloat = isFloat;
		this.isBoolean = isBoolean;
		this.tooltip = tooltip;
	}

	public boolean isFloat() {
		return this.isFloat;
	}

	public boolean isBoolean() {
		return this.isBoolean;
	}

	public int returnEnumOrdinal() {
		return ordinal();
	}

	public String getName() {
		return this.enumName;
	}

	public String getTooltip() {
		return this.tooltip;
	}
}
