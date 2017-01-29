package net.spellcraftgaming.rpghud.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.fml.client.FMLClientHandler;

public class ModSettings {

	protected Minecraft mc;
	private File optionsFile;

	private static final String[] COLOR = { "Red", "Blue", "Green", "Yellow", "White", "Grey" };
	private static final String[] HUD_TYPE = {"Default", "Extended Widget", "Full Texture", "Hotbar Widget", "New Style"};
	private static final String[] TIME_FORMAT = {"24 Hours", "12 Hours"};

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
	public boolean render_player_face = true;
	public boolean show_hunger_preview = true;
	public boolean reduce_size = false;
	public int hudtype = 0;
	public int color_health = 0;
	public int color_stamina = 2;
	public int color_air = 1;
	public int color_experience = 3;
	public int color_jumpbar = 5;
	public int clock_time_format = 0;

	public ModSettings(File file) {
		this.mc = Minecraft.getMinecraft();
		this.optionsFile = new File(file, "RPGHud_settings.txt");
		this.loadOptions();
	}

	public void setOptionValue(EnumOptionsMod options, int value) {
		if (options == EnumOptionsMod.BUTTON_TOOLTIP_ENABLED) {
			this.button_tooltip_enabled = (!this.button_tooltip_enabled);
			System.out.println(options + ":" + this.button_tooltip_enabled);
		}	
		if (options == EnumOptionsMod.SHOW_ARMOR) {
			this.show_armor = (!this.show_armor);
			System.out.println(options + ":" + this.show_armor);
		}
		if (options == EnumOptionsMod.SHOW_ARROWCOUNT) {
			this.show_arrowcount= (!this.show_arrowcount);
			System.out.println(options + ":" + this.show_arrowcount);
		}
		if (options == EnumOptionsMod.SHOW_ITEMDURABILITY) {
			this.show_itemdurability = (!this.show_itemdurability);
			System.out.println(options + ":" + this.show_itemdurability);
		}
		if (options == EnumOptionsMod.SHOW_ITEMCOUNT) {
			this.show_blockcount = (!this.show_blockcount);
			System.out.println(options + ":" + this.show_blockcount);
		}
		if (options == EnumOptionsMod.SHOW_NUMBERS_HEALTH) {
			this.show_numbers_health = (!this.show_numbers_health);
			System.out.println(options + ":" + this.show_numbers_health);
		}
		if (options == EnumOptionsMod.SHOW_NUMBERS_STAMINA) {
			this.show_numbers_stamina = (!this.show_numbers_stamina);
			System.out.println(options + ":" + this.show_numbers_stamina);
		}
		if (options == EnumOptionsMod.SHOW_NUMBERS_EXPERIENCE) {
			this.show_numbers_experience = (!this.show_numbers_experience);
			System.out.println(options + ":" + this.show_numbers_experience);
		}
		if (options == EnumOptionsMod.ENABLE_CLOCK) {
			this.enable_clock = (!this.enable_clock);
			System.out.println(options + ":" + this.enable_clock);
		}
		if (options == EnumOptionsMod.ENABLE_TIMECOLOR) {
			this.enable_clock_color = (!this.enable_clock_color);
			System.out.println(options + ":" + this.enable_clock_color);
		}
		if (options == EnumOptionsMod.ENABLE_IMMERSIVE_CLOCK) {
			this.enable_immersive_clock = (!this.enable_immersive_clock);
			System.out.println(options + ":" + this.enable_immersive_clock);
		}
		if (options == EnumOptionsMod.RENDER_PLAYER_FACE) {
			this.render_player_face = (!this.render_player_face);
			System.out.println(options + ":" + this.render_player_face);
		}
		if (options == EnumOptionsMod.SHOW_HUNGERPREVIEW) {
			this.show_hunger_preview = (!this.show_hunger_preview);
			System.out.println(options + ":" + this.show_hunger_preview);
		}
		if (options == EnumOptionsMod.REDUCE_SIZE) {
			this.reduce_size = (!this.reduce_size);
			System.out.println(options + ":" + this.reduce_size);
		}
		if (options == EnumOptionsMod.COLOR_HEALTH) {
			if (this.color_health >= 5) {
				this.color_health = 0;
			} else {
				this.color_health += value;
			}
			System.out.println(options + ":" + this.color_health);
		}
		if (options == EnumOptionsMod.COLOR_STAMINA) {
			if (this.color_stamina >= 5) {
				this.color_stamina = 0;
			} else {
				this.color_stamina += value;
			}
			System.out.println(options + ":" + this.color_stamina);
		}
		if (options == EnumOptionsMod.COLOR_AIR) {
			if (this.color_air >= 5) {
				this.color_air = 0;
			} else {
				this.color_air += value;
			}
			System.out.println(options + ":" + this.color_air);
		}
		if (options == EnumOptionsMod.COLOR_EXPERIENCE) {
			if (this.color_experience >= 5) {
				this.color_experience = 0;
			} else {
				this.color_experience += value;
			}
			System.out.println(options + ":" + this.color_experience);
		}
		if (options == EnumOptionsMod.COLOR_JUMPBAR) {
			if (this.color_jumpbar >= 5) {
				this.color_jumpbar = 0;
			} else {
				this.color_jumpbar += value;
			}
			System.out.println(options + ":" + this.color_jumpbar);
		}
		if (options == EnumOptionsMod.CLOCK_TIME_FORMAT) {
			if (this.clock_time_format >= 1) {
				this.clock_time_format = 0;
			} else {
				this.clock_time_format += value;
			}
			System.out.println(options + ":" + this.clock_time_format);
		}
		if (options == EnumOptionsMod.HUD_TYPE) {
			if (this.hudtype >= 4) {
				this.hudtype = 0;
			} else {
				this.hudtype += value;
			}
			System.out.println(options + ":" + this.hudtype);
		}
		saveOptions();
	}

	public boolean getOptionOrdinalValue(EnumOptionsMod options) {
		switch (ModSettings.SwitchOptions.optionIds[options.ordinal()]) {
		case 1:
			return this.show_armor;
		case 2:
			return this.show_numbers_health;
		case 3:
			return this.show_numbers_stamina;
		case 4:
			return this.show_numbers_experience;
		case 5:
			return this.render_player_face;
		case 6:
			return this.show_hunger_preview;
		case 7:
			return this.enable_clock;
		case 8:
			return this.enable_clock_color;
		case 9:
			return this.enable_immersive_clock;
		case 10:
			return this.button_tooltip_enabled;
		case 11:
			return this.show_itemdurability;
		case 12:
			return this.show_arrowcount;
		case 13:
			return this.show_blockcount;
		case 14:
			return this.reduce_size;
		default:
			return false;
		}
	}

	@SuppressWarnings("unused")
	static final class SwitchOptions {
		static final int[] optionIds = new int[GameSettings.Options.values().length];

		static {
			try {
				optionIds[EnumOptionsMod.SHOW_ARMOR.ordinal()] = 1;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsMod.SHOW_NUMBERS_HEALTH.ordinal()] = 2;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsMod.SHOW_NUMBERS_STAMINA.ordinal()] = 3;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsMod.SHOW_NUMBERS_EXPERIENCE.ordinal()] = 4;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsMod.RENDER_PLAYER_FACE.ordinal()] = 5;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsMod.SHOW_HUNGERPREVIEW.ordinal()] = 6;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsMod.ENABLE_CLOCK.ordinal()] = 7;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsMod.ENABLE_TIMECOLOR.ordinal()] = 8;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsMod.ENABLE_IMMERSIVE_CLOCK.ordinal()] = 9;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsMod.BUTTON_TOOLTIP_ENABLED.ordinal()] = 10;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsMod.SHOW_ITEMDURABILITY.ordinal()] = 11;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsMod.SHOW_ARROWCOUNT.ordinal()] = 12;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsMod.SHOW_ITEMCOUNT.ordinal()] = 13;
			} catch (NoSuchFieldError e) {
				;
			}
			try {
				optionIds[EnumOptionsMod.REDUCE_SIZE.ordinal()] = 14;
			} catch (NoSuchFieldError e) {
				;
			}
		}
	}

	public void loadOptions() {
		try {
			if (!this.optionsFile.exists()) {
				return;
			}
			BufferedReader reader = new BufferedReader(new FileReader(
					this.optionsFile));
			String s = "";
			while ((s = reader.readLine()) != null) {
				try {
					String[] string = s.split(":");
					if (string[0].equals("button_tooltip_enabled")) {
						this.button_tooltip_enabled = string[1].equals("true");
					}
					if (string[0].equals("color_health")) {
						this.color_health = Integer.parseInt(string[1]);
					}
					if (string[0].equals("color_stamina")) {
						this.color_stamina = Integer.parseInt(string[1]);
					}
					if (string[0].equals("color_air")) {
						this.color_air = Integer.parseInt(string[1]);
					}
					if (string[0].equals("color_experience")) {
						this.color_experience = Integer.parseInt(string[1]);
					}
					if (string[0].equals("color_jumpbar")) {
						this.color_jumpbar = Integer.parseInt(string[1]);
					}
					if (string[0].equals("clock_time_format")) {
						this.clock_time_format = Integer.parseInt(string[1]);
					}
					if (string[0].equals("hudtype")) {
						this.hudtype = Integer.parseInt(string[1]);
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

	public String getKeyBinding(EnumOptionsMod par1EnumOptions) {
		String s = I18n.format(par1EnumOptions.getEnumString(), new Object[0])
				+ ": ";
		if (par1EnumOptions.getEnumBoolean()) {
			boolean flag = this.getOptionOrdinalValue(par1EnumOptions);
			return flag ? s + I18n.format("options.on", new Object[0]) : s + I18n.format("options.off", new Object[0]);
		}
		switch(par1EnumOptions) {
		case HUD_TYPE:
			return s + getTranslation(HUD_TYPE, this.hudtype);
		case COLOR_JUMPBAR:
			return s + getTranslation(COLOR, this.color_jumpbar);
		case COLOR_EXPERIENCE:
			return s + getTranslation(COLOR, this.color_experience);
		case COLOR_STAMINA:
			return s + getTranslation(COLOR, this.color_stamina);
		case COLOR_HEALTH:
			return s + getTranslation(COLOR, this.color_health);
		case COLOR_AIR:
			return s + getTranslation(COLOR, this.color_air);
		case CLOCK_TIME_FORMAT:
			return s + getTranslation(ModSettings.TIME_FORMAT, this.clock_time_format);
		default: 
			return s;
		}
	}

	private static String getTranslation(String[] par0ArrayOfStr, int par1) {
		if ((par1 < 0) || (par1 >= par0ArrayOfStr.length)) {
			par1 = 0;
		}
		return I18n.format(par0ArrayOfStr[par1], new Object[0]);
	}

	public void saveOptions() {
		if (!FMLClientHandler.instance().isLoading()) {
			try {
				PrintWriter exception = new PrintWriter(new FileWriter(
						this.optionsFile));
				exception.println("button_tooltip_enabled:" + this.button_tooltip_enabled);
				exception.println("color_health:" + this.color_health);
				exception.println("color_air:" + this.color_air);
				exception.println("color_stamina:" + this.color_stamina);
				exception.println("color_experience:" + this.color_experience);
				exception.println("color_jumpbar:" + this.color_jumpbar);
				exception.println("clock_time_format:" + this.clock_time_format);
				exception.println("hudtype:" + this.hudtype);
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
				exception.println("render_player_face:" + this.render_player_face);
				exception.println("show_hunger_preview:" + this.show_hunger_preview);
				exception.println("reduce_size:" + this.reduce_size);
				exception.close();
			} catch (Exception var2) {
				var2.printStackTrace();
			}
		}
	}
}
