package net.spellcraftgaming.rpghud.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.spellcraftgaming.rpghud.gui.hud.Hud;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

public class ModSettings {

	/** Instance of Minecraft */
	protected Minecraft mc;

	/** The file the settings are read from and wrote into */
	private File optionsFile;

	/** The translated value of the time format setting */
	private static final String[] TIME_FORMAT = { "time.24", "time.12" };

	public boolean button_tooltip_enabled = true;
	
	public boolean show_armor = true;
	public boolean show_arrowcount = true;
	public boolean show_itemdurability = true;
	public boolean show_blockcount = true;
	
	public boolean show_numbers_health = true;
	public boolean show_numbers_stamina = true;
	public boolean show_numbers_experience = true;
	
	public boolean enable_clock = true;
	public boolean enable_clock_color = true;
	public boolean enable_immersive_clock = false;
	
	public boolean enable_compass = true;
	public boolean enable_compass_color = true;
	public boolean enable_immersive_compass = false;
	
	public boolean render_player_face = true;
	public boolean show_hunger_preview = true;
	public boolean reduce_size = false;

	/** The active HUD's type */
	public String hud_type = "vanilla";

	public int color_health = 0xC10000;
	public int color_stamina = 0x3BC200;
	public int color_air = 0x005BC2;
	public int color_experience = 0xEEEE00;
	public int color_jumpbar = 0xBFBFBF;
	public int color_poison = 0x800080;
	public int color_hunger = 0x9ba067;
	
	public int clock_time_format = 0;

	public ModSettings(File file) {
		this.mc = Minecraft.getMinecraft();
		this.optionsFile = new File(file, "RPGHud_settings.txt");
		this.loadOptions();
	}

	/**
	 * Increments the setting with the specified EnumOptionsDebugMod
	 * 
	 * @param option
	 *            the EnumOptionsDebugMod to increment
	 */
	public void setOptionValue(EnumOptionsMod options) {
		if (options == EnumOptionsMod.BUTTON_TOOLTIP_ENABLED) {
			this.button_tooltip_enabled = (!this.button_tooltip_enabled);
		}
		if (options == EnumOptionsMod.SHOW_ARMOR) {
			this.show_armor = (!this.show_armor);
		}
		if (options == EnumOptionsMod.SHOW_ARROWCOUNT) {
			this.show_arrowcount = (!this.show_arrowcount);
		}
		if (options == EnumOptionsMod.SHOW_ITEMDURABILITY) {
			this.show_itemdurability = (!this.show_itemdurability);
		}
		if (options == EnumOptionsMod.SHOW_ITEMCOUNT) {
			this.show_blockcount = (!this.show_blockcount);
		}
		if (options == EnumOptionsMod.SHOW_NUMBERS_HEALTH) {
			this.show_numbers_health = (!this.show_numbers_health);
		}
		if (options == EnumOptionsMod.SHOW_NUMBERS_STAMINA) {
			this.show_numbers_stamina = (!this.show_numbers_stamina);
		}
		if (options == EnumOptionsMod.SHOW_NUMBERS_EXPERIENCE) {
			this.show_numbers_experience = (!this.show_numbers_experience);
		}
		if (options == EnumOptionsMod.ENABLE_CLOCK) {
			this.enable_clock = (!this.enable_clock);
		}
		if (options == EnumOptionsMod.ENABLE_TIMECOLOR) {
			this.enable_clock_color = (!this.enable_clock_color);
		}
		if (options == EnumOptionsMod.ENABLE_IMMERSIVE_CLOCK) {
			this.enable_immersive_clock = (!this.enable_immersive_clock);
		}
		if (options == EnumOptionsMod.ENABLE_COMPASS) {
			this.enable_compass = (!this.enable_compass);
		}
		if (options == EnumOptionsMod.ENABLE_COMPASS_COLOR) {
			this.enable_compass_color = (!this.enable_compass_color);
		}
		if (options == EnumOptionsMod.ENABLE_IMMERSIVE_COMPASS) {
			this.enable_immersive_compass = (!this.enable_immersive_compass);
		}
		if (options == EnumOptionsMod.RENDER_PLAYER_FACE) {
			this.render_player_face = (!this.render_player_face);
		}
		if (options == EnumOptionsMod.SHOW_HUNGERPREVIEW) {
			this.show_hunger_preview = (!this.show_hunger_preview);
		}
		if (options == EnumOptionsMod.REDUCE_SIZE) {
			this.reduce_size = (!this.reduce_size);
		}
		if (options == EnumOptionsMod.CLOCK_TIME_FORMAT) {
			if (this.clock_time_format >= 1) {
				this.clock_time_format = 0;
			} else {
				this.clock_time_format++;
			}
		}
		if (options == EnumOptionsMod.HUD_TYPE) {
			this.incrementHudType();
		}
		saveOptions();
	}

	/** Increments the active HUD's type */
	private void incrementHudType() {
		Set<String> huds = ModRPGHud.instance.huds.keySet();
		String[] keys = huds.toArray(new String[huds.size()]);
		int size = keys.length;
		for (int n = 0; n < size; n++) {
			if (keys[n].equals(this.hud_type)) {
				n++;
				if (n == size)
					n = 0;
				this.hud_type = keys[n];
				return;
			}
		}

	}

	/** Returns the ordinal value of this setting */
	public boolean getOptionOrdinalValue(EnumOptionsMod options) {
		switch (ModSettings.SwitchOptions.optionIds[options.ordinal()]) {
		case 0:
			return this.show_armor;
		case 1:
			return this.show_numbers_health;
		case 2:
			return this.show_numbers_stamina;
		case 3:
			return this.show_numbers_experience;
		case 4:
			return this.render_player_face;
		case 5:
			return this.show_hunger_preview;
		case 6:
			return this.enable_clock;
		case 7:
			return this.enable_clock_color;
		case 8:
			return this.enable_immersive_clock;
		case 9:
			return this.button_tooltip_enabled;
		case 10:
			return this.show_itemdurability;
		case 11:
			return this.show_arrowcount;
		case 12:
			return this.show_blockcount;
		case 13:
			return this.reduce_size;
		case 14:
			return this.enable_compass;
		case 15:
			return this.enable_compass_color;
		case 16:
			return this.enable_immersive_compass;
		default:
			return false;
		}
	}

	static final class SwitchOptions {
		static final int[] optionIds = new int[EnumOptionsMod.values().length];

		static {
			try {
				optionIds[EnumOptionsMod.SHOW_ARMOR.ordinal()] = 0;
			} catch (NoSuchFieldError e) {
			}
			try {
				optionIds[EnumOptionsMod.SHOW_NUMBERS_HEALTH.ordinal()] = 1;
			} catch (NoSuchFieldError e) {
			}
			try {
				optionIds[EnumOptionsMod.SHOW_NUMBERS_STAMINA.ordinal()] = 2;
			} catch (NoSuchFieldError e) {
			}
			try {
				optionIds[EnumOptionsMod.SHOW_NUMBERS_EXPERIENCE.ordinal()] = 3;
			} catch (NoSuchFieldError e) {
			}
			try {
				optionIds[EnumOptionsMod.RENDER_PLAYER_FACE.ordinal()] = 4;
			} catch (NoSuchFieldError e) {
			}
			try {
				optionIds[EnumOptionsMod.SHOW_HUNGERPREVIEW.ordinal()] = 5;
			} catch (NoSuchFieldError e) {
			}
			try {
				optionIds[EnumOptionsMod.ENABLE_CLOCK.ordinal()] = 6;
			} catch (NoSuchFieldError e) {
			}
			try {
				optionIds[EnumOptionsMod.ENABLE_TIMECOLOR.ordinal()] = 7;
			} catch (NoSuchFieldError e) {
			}
			try {
				optionIds[EnumOptionsMod.ENABLE_IMMERSIVE_CLOCK.ordinal()] = 8;
			} catch (NoSuchFieldError e) {
			}
			try {
				optionIds[EnumOptionsMod.BUTTON_TOOLTIP_ENABLED.ordinal()] = 9;
			} catch (NoSuchFieldError e) {
			}
			try {
				optionIds[EnumOptionsMod.SHOW_ITEMDURABILITY.ordinal()] = 10;
			} catch (NoSuchFieldError e) {
			}
			try {
				optionIds[EnumOptionsMod.SHOW_ARROWCOUNT.ordinal()] = 11;
			} catch (NoSuchFieldError e) {
			}
			try {
				optionIds[EnumOptionsMod.SHOW_ITEMCOUNT.ordinal()] = 12;
			} catch (NoSuchFieldError e) {
			}
			try {
				optionIds[EnumOptionsMod.REDUCE_SIZE.ordinal()] = 13;
			} catch (NoSuchFieldError e) {
			}
			try {
				optionIds[EnumOptionsMod.ENABLE_COMPASS.ordinal()] = 14;
			} catch (NoSuchFieldError e) {
			}
			try {
				optionIds[EnumOptionsMod.ENABLE_COMPASS_COLOR.ordinal()] = 15;
			} catch (NoSuchFieldError e) {
			}
			try {
				optionIds[EnumOptionsMod.ENABLE_IMMERSIVE_COMPASS.ordinal()] = 16;
			} catch (NoSuchFieldError e) {
			}
		}
	}

	/** Loads the settings from the file */
	public void loadOptions() {
		try {
			if (!this.optionsFile.exists()) {
				return;
			}
			BufferedReader reader = new BufferedReader(new FileReader(this.optionsFile));
			String s = "";
			while ((s = reader.readLine()) != null) {
				try {
					String[] string = s.split(":");
					if (string[0].equals("button_tooltip_enabled")) {
						this.button_tooltip_enabled = string[1].equals("true");
					}
					if (string[0].equals("color_health")) {
						if(string[1].startsWith("#")) {
							this.color_health = Integer.parseInt(string[1].replace("#", ""), 16);
						} else {
							this.color_health = getColor(this.color_health);
						}
					}
					if (string[0].equals("color_stamina")) {
						if(string[1].startsWith("#")) {
							this.color_health = Integer.parseInt(string[1].replace("#", ""), 16);
						} else {
							this.color_health = getColor(this.color_health);
						}
					}
					if (string[0].equals("color_air")) {
						if(string[1].startsWith("#")) {
							this.color_health = Integer.parseInt(string[1].replace("#", ""), 16);
						} else {
							this.color_health = getColor(this.color_health);
						}
					}
					if (string[0].equals("color_experience")) {
						if(string[1].startsWith("#")) {
							this.color_health = Integer.parseInt(string[1].replace("#", ""), 16);
						} else {
							this.color_health = getColor(this.color_health);
						}
					}
					if (string[0].equals("color_jumpbar")) {
						if(string[1].startsWith("#")) {
							this.color_health = Integer.parseInt(string[1].replace("#", ""), 16);
						} else {
							this.color_health = getColor(this.color_health);
						}
					}
					if (string[0].equals("color_poison")) {
						this.color_poison = Integer.parseInt(string[1].replace("#", ""), 16);
					}
					if (string[0].equals("color_hunger")) {
						this.color_hunger = Integer.parseInt(string[1].replace("#", ""), 16);
					}
					if (string[0].equals("color_jumpbar")) {
						if(string[1].startsWith("#")) {
							this.color_health = Integer.parseInt(string[1].replace("#", ""), 16);
						} else {
							this.color_health = getColor(this.color_health);
						}
					}
					if (string[0].equals("clock_time_format")) {
						this.clock_time_format = Integer.parseInt(string[1]);
					}
					if (string[0].equals("hud_type")) {
						this.hud_type = string[1];
					}
					if (string[0].equals("show_armor")) {
						this.show_armor = string[1].equals("true");
					}
					if (string[0].equals("show_blockcount")) {
						this.show_blockcount = string[1].equals("true");
					}
					if (string[0].equals("show_arrowcount")) {
						this.show_arrowcount = string[1].equals("true");
					}
					if (string[0].equals("show_itemdurability")) {
						this.show_itemdurability = string[1].equals("true");
					}
					if (string[0].equals("enable_clock")) {
						this.enable_clock = string[1].equals("true");
					}
					if (string[0].equals("enable_clock_color")) {
						this.enable_clock_color = string[1].equals("true");
					}
					if (string[0].equals("enable_immersive_clock")) {
						this.enable_immersive_clock = string[1].equals("true");
					}
					if (string[0].equals("enable_compass")) {
						this.enable_compass = string[1].equals("true");
					}
					if (string[0].equals("enable_compass_color")) {
						this.enable_compass_color = string[1].equals("true");
					}
					if (string[0].equals("enable_immersive_compass")) {
						this.enable_immersive_compass = string[1].equals("true");
					}
					if (string[0].equals("render_player_face")) {
						this.render_player_face = string[1].equals("true");
					}
					if (string[0].equals("show_numbers_health")) {
						this.show_numbers_health = string[1].equals("true");
					}
					if (string[0].equals("show_numbers_stamina")) {
						this.show_numbers_stamina = string[1].equals("true");
					}
					if (string[0].equals("show_numbers_experience")) {
						this.show_numbers_experience = string[1].equals("true");
					}
					if (string[0].equals("show_hunger_preview")) {
						this.show_hunger_preview = string[1].equals("true");
					}
					if (string[0].equals("reduce_size")) {
						this.reduce_size = string[1].equals("true");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the String of the Button for the specified setting
	 * 
	 * @param option
	 *            the option whose button text will be returned
	 * @return the String
	 */
	public String getKeyBinding(EnumOptionsMod par1EnumOptions) {
		String s = I18n.format(par1EnumOptions.getName(), new Object[0]) + ": ";
		if (par1EnumOptions.isBoolean()) {
			boolean flag = this.getOptionOrdinalValue(par1EnumOptions);
			return flag ? s + I18n.format("options.on", new Object[0]) : s + I18n.format("options.off", new Object[0]);
		}
		switch (par1EnumOptions) {
		case HUD_TYPE:
			return s + getHudName(this.hud_type);
		case COLOR_JUMPBAR:
			return s + intToHexString(this.color_jumpbar);
		case COLOR_EXPERIENCE:
			return s + intToHexString(this.color_experience);
		case COLOR_STAMINA:
			return s + intToHexString(this.color_stamina);
		case COLOR_POISON:
			return s + intToHexString(this.color_poison);
		case COLOR_HUNGER:
			return s + intToHexString(this.color_hunger);
		case COLOR_HEALTH:
			return s + intToHexString(this.color_health);
		case COLOR_AIR:
			return s + intToHexString(this.color_air);
		case CLOCK_TIME_FORMAT:
			return s + getTranslation(ModSettings.TIME_FORMAT, this.clock_time_format);
		default:
			return s;
		}
	}

	/**
	 * Returns the localized name of the specified HUD
	 * 
	 * @param hudtype
	 *            the key of the HUD whose name should be returned
	 */
	private static String getHudName(String hudtype) {
		Hud hud = ModRPGHud.instance.huds.get(hudtype);
		return hud.getHudName();
	}

	/**
	 * Returns the localized value of a setting
	 * 
	 * @param strings
	 *            the String array to pick the translation from
	 * @param value
	 *            the setting's value
	 * @return the translated value
	 */
	private static String getTranslation(String[] strings, int value) {
		if ((value < 0) || (value >= strings.length)) {
			value = 0;
		}
		return I18n.format(strings[value], new Object[0]);
	}

	/** Saves the settings to the file */
	public void saveOptions() {
		if (!FMLClientHandler.instance().isLoading()) {
			try {
				PrintWriter exception = new PrintWriter(new FileWriter(this.optionsFile));
				exception.println("button_tooltip_enabled:" + this.button_tooltip_enabled);
				exception.println("color_health:" + "#" + Integer.toHexString(this.color_health));
				exception.println("color_air:" + "#" + Integer.toHexString(this.color_air));
				exception.println("color_stamina:" + "#" + Integer.toHexString(this.color_stamina));
				exception.println("color_experience:" + "#" + Integer.toHexString(this.color_experience));
				exception.println("color_jumpbar:" + "#" + Integer.toHexString(this.color_jumpbar));
				exception.println("color_poison:" + "#" + Integer.toHexString(this.color_poison));
				exception.println("color_hunger:" + "#" + Integer.toHexString(this.color_hunger));
				exception.println("clock_time_format:" + this.clock_time_format);
				exception.println("hud_type:" + this.hud_type);
				exception.println("show_armor:" + this.show_armor);
				exception.println("show_blockcount:" + this.show_blockcount);
				exception.println("show_arrowcount:" + this.show_arrowcount);
				exception.println("show_itemdurability:" + this.show_itemdurability);
				exception.println("show_numbers_health:" + this.show_numbers_health);
				exception.println("show_numbers_stamina:" + this.show_numbers_stamina);
				exception.println("show_numbers_experience:" + this.show_numbers_experience);
				exception.println("enable_clock:" + this.enable_clock);
				exception.println("enable_clock_color:" + this.enable_clock_color);
				exception.println("enable_immersive_clock:" + this.enable_immersive_clock);
				exception.println("enable_compass:" + this.enable_compass);
				exception.println("enable_compass_color:" + this.enable_compass_color);
				exception.println("enable_immersive_compass:" + this.enable_immersive_compass);
				exception.println("render_player_face:" + this.render_player_face);
				exception.println("show_hunger_preview:" + this.show_hunger_preview);
				exception.println("reduce_size:" + this.reduce_size);
				exception.close();
			} catch (Exception var2) {
				var2.printStackTrace();
			}
		}
	}
	
	/**
	 * Returns the color for the parameter
	 * 
	 * @param setting
	 *            should the value of a ModSettings color setting
	 * @return the color
	 */
	@Deprecated
	public static int getColor(int setting) {
		switch (setting) {
		case 0:
			return HudElementBarred.COLOR_RED;
		case 1:
			return HudElementBarred.COLOR_BLUE;
		case 2:
			return HudElementBarred.COLOR_GREEN;
		case 3:
			return HudElementBarred.COLOR_YELLOW;
		case 4:
			return HudElementBarred.COLOR_WHITE;
		case 5:
			return HudElementBarred.COLOR_GREY;
		}
		return 0xFFFFFF;
	}
	
	public static String intToHexString(int hex) {
		String s = Integer.toHexString(hex).toUpperCase();
		if(hex <= 0xFFFFF) {
			s = "0" + s;
			if(hex <= 0xFFFF) {
				s = "0" + s;
				if(hex <= 0xFFF) {
					s = "0" + s;
					if(hex <= 0xFF) {
						s = "0" + s;
						if(hex <= 0xF) {
							s = "0" + s;
						}
					}
				}
			}
		}
		return "#" + s;
	}
}
