package net.spellcraftgaming.rpghud.settings;

import net.minecraft.client.resources.I18n;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.EnumOptionType.*;

public enum EnumOptionsMod {

	BUTTON_TOOLTIP_ENABLED(BOOLEAN, "Button Tooltip", I18n.format("tooltip.button_tooltip", new Object[0])),
	SHOW_ARMOR(BOOLEAN, "Show Armor", I18n.format("tooltip.show_armor", new Object[0])),
	SHOW_ITEMDURABILITY(BOOLEAN, "Show Item Durabiliy", I18n.format("tooltip.item_durability", new Object[0])),
	SHOW_ITEMCOUNT(BOOLEAN, "Show Block count", I18n.format("tooltip.item_count", new Object[0])),
	SHOW_ARROWCOUNT(BOOLEAN, "Show Arrow count", I18n.format("tooltip.arrow_count", new Object[0])),
	SHOW_NUMBERS_HEALTH(BOOLEAN, "Show Health Value", I18n.format("tooltip.numbers_health", new Object[0])),  
	SHOW_NUMBERS_STAMINA(BOOLEAN, "Show Stamina Value", I18n.format("tooltip.numbers_stamina", new Object[0])),  
	SHOW_NUMBERS_EXPERIENCE(BOOLEAN, "Show Experience Value", I18n.format("tooltip.numbers_exp", new Object[0])),  
	ENABLE_CLOCK(BOOLEAN, "Enable Clock", I18n.format("tooltip.enable_clock", new Object[0])),
	ENABLE_IMMERSIVE_CLOCK(BOOLEAN, "Enable Immersive Clock", I18n.format("tooltip.enable_immersive_clock", new Object[0])),
	ENABLE_TIMECOLOR(BOOLEAN, "Enable Colored Clock", I18n.format("tooltip.enable_timecolor", new Object[0])), 
	RENDER_PLAYER_FACE(BOOLEAN, "Render Player Face", I18n.format("tooltip.player_face", new Object[0])),  
	HUD_TYPE(STRING, "Hud Type", I18n.format("tooltip.hud_type", new Object[0])),
	COLOR_HEALTH(INTEGER, "Health Color", I18n.format("tooltip.color_health", new Object[0])),
	COLOR_STAMINA(INTEGER, "Stamina Color", I18n.format("tooltip.color_stamina", new Object[0])),  
	COLOR_AIR(INTEGER, "Air Color", I18n.format("tooltip.color_air", new Object[0])),  
	COLOR_EXPERIENCE(INTEGER, "Experience Color", I18n.format("tooltip.color_exp", new Object[0])),  
	COLOR_JUMPBAR(INTEGER, "Jumpbar Color", I18n.format("tooltip.color_jumpbar", new Object[0])),
	SHOW_HUNGERPREVIEW(BOOLEAN, "Show Hunger Preview", I18n.format("tooltip.show_hungerpreview", new Object[0])),
	CLOCK_TIME_FORMAT(INTEGER, "Clock Time Format", I18n.format("tooltip.clock_time_format", new Object[0])),
	REDUCE_SIZE(BOOLEAN, "Reduced Detail Size", I18n.format("tooltip.reduce_size", new Object[0]));
	
	public enum EnumOptionType{
		INTEGER,
		BOOLEAN,
		STRING;
	}
	
	private final String enumName;
	private final String tooltip;
	private final EnumOptionType type;
	
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

	private EnumOptionsMod(EnumOptionType type, String name, String tooltip) {
		this.type = type;
		this.enumName = name;
		this.tooltip = tooltip;
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

	public EnumOptionType getType() {
		return this.type;
	}
	
	public boolean isBoolean() {
		return this.type == BOOLEAN ? true : false;
	}
}
