package net.spellcraftgaming.rpghud.settings;

public enum EnumOptionsMod {

	BUTTON_TOOLTIP_ENABLED("Button Tooltip", false, true),
	SHOW_ARMOR("Show Armor", false, true),
	SHOW_ITEMDURABILITY("Show Item Durabiliy", false, true),
	SHOW_ITEMCOUNT("Show Block count", false, true),
	SHOW_ARROWCOUNT("Show Arrow count", false, true),
	SHOW_NUMBERS_HEALTH("Show Health Value", false, true),  
	SHOW_NUMBERS_STAMINA("Show Stamina Value", false, true),  
	SHOW_NUMBERS_EXPERIENCE("Show Experience Value", false, true),  
	ENABLE_CLOCK("Enable Clock", false, true),
	ENABLE_IMMERSIVE_CLOCK("Enable Immersive Clock", false, true),
	ENABLE_TIMECOLOR("Enable Colored Clock", false, true), 
	RENDER_PLAYER_FACE("Render Player Face", false, true),  
	HUD_TYPE("Hud Type", false, false),
	COLOR_HEALTH("Health Color", false, false),
	COLOR_STAMINA("Stamina Color", false, false),  
	COLOR_AIR("Air Color", false, false),  
	COLOR_EXPERIENCE("Experience Color", false, false),  
	COLOR_JUMPBAR("Jumpbar Color", false, false),
	SHOW_HUNGERPREVIEW("Show Hunger Preview", false, true),
	CLOCK_TIME_FORMAT("Clock Time Format", false, false),
	REDUCE_SIZE("Reduced Detail Size", false, true);
	
	private final boolean enumFloat;
	private final boolean enumBoolean;
	private final String enumString;

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

	private EnumOptionsMod(String par3Str, boolean par4, boolean par5) {
		this.enumString = par3Str;
		this.enumFloat = par4;
		this.enumBoolean = par5;
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
