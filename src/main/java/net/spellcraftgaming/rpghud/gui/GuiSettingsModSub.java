package net.spellcraftgaming.rpghud.gui;

import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.BUTTON_TOOLTIP_ENABLED;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.CLOCK_TIME_FORMAT;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.COLOR_ABSORPTION;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.COLOR_AIR;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.COLOR_EXPERIENCE;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.COLOR_HEALTH;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.COLOR_HUNGER;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.COLOR_JUMPBAR;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.COLOR_POISON;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.COLOR_STAMINA;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.COLOR_WITHER;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.ENABLE_CLOCK;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.ENABLE_COMPASS;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.ENABLE_COMPASS_COLOR;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.ENABLE_COMPASS_COORDINATES;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.ENABLE_ENTITY_INSPECT;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.ENABLE_IMMERSIVE_CLOCK;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.ENABLE_IMMERSIVE_COMPASS;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.ENABLE_PICKUP;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.ENABLE_TIMECOLOR;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.HUD_TYPE;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.INVERT_COMPASS;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.LIMIT_JUMPBAR;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.PICK_DURATION;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.REDUCE_SIZE;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.RENDER_PLAYER_FACE;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.SHOW_ARMOR;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.SHOW_ARROWCOUNT;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.SHOW_HUNGERPREVIEW;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.SHOW_ITEMCOUNT;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.SHOW_ITEMDURABILITY;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.SHOW_NUMBERS_EXPERIENCE;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.SHOW_NUMBERS_HEALTH;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.SHOW_NUMBERS_STAMINA;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.spellcraftgaming.rpghud.gui.GuiSettingsModColor.EnumColor;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.EnumOptionsDebugMod;
import net.spellcraftgaming.rpghud.settings.EnumOptionsMod;
import net.spellcraftgaming.rpghud.settings.ModSettings;

public class GuiSettingsModSub extends GuiScreenTooltip {

	/** The group of settings to be displayed in the "general" category */
	private static final EnumOptionsMod[] optionsGeneral = { BUTTON_TOOLTIP_ENABLED };

	/** The group of settings to be displayed in the "HUD" category */
	private static final EnumOptionsMod[] optionsHUD = { HUD_TYPE, RENDER_PLAYER_FACE, REDUCE_SIZE, SHOW_NUMBERS_HEALTH, SHOW_NUMBERS_STAMINA, SHOW_NUMBERS_EXPERIENCE, LIMIT_JUMPBAR};

	/** The group of settings to be displayed in the "colors" category */
	private static final EnumOptionsMod[] optionsColors = { COLOR_HEALTH, COLOR_ABSORPTION, COLOR_POISON, COLOR_WITHER, COLOR_STAMINA, COLOR_HUNGER, COLOR_AIR, COLOR_EXPERIENCE, COLOR_JUMPBAR };

	/** The group of settings to be displayed in the "details" category */
	private static final EnumOptionsMod[] optionsDetails = { SHOW_ARMOR, SHOW_ITEMDURABILITY, SHOW_ITEMCOUNT, SHOW_ARROWCOUNT, ENABLE_CLOCK, ENABLE_IMMERSIVE_CLOCK, CLOCK_TIME_FORMAT, ENABLE_TIMECOLOR, ENABLE_COMPASS, ENABLE_IMMERSIVE_COMPASS, ENABLE_COMPASS_COLOR, ENABLE_COMPASS_COORDINATES, SHOW_HUNGERPREVIEW, ENABLE_PICKUP, PICK_DURATION, INVERT_COMPASS, ENABLE_ENTITY_INSPECT};

	/** The ModSettings instance */
	private ModSettings settings;

	/** The GuiScreen which lead to this GUI */
	private GuiScreen parent;

	/** The current subgui type (category of this GUI */
	private int subgui;

	/** Constant to for the "general" category */
	public static final int GENERAL = 0;

	/** Constant to for the hud category */
	public static final int HUD = 1;

	/** Constant to for the colors category */
	public static final int COLORS = 2;

	/** Constant to for the details category */
	public static final int DETAILS = 3;

	/** Constant to for the debug category */
	public static final int MAIN_DEBUG = 4;

	public GuiSettingsModSub(GuiScreen parent, int subgui) {
		this.parent = parent;
		this.settings = ModRPGHud.instance.settings;
		this.subgui = subgui;
	}

	@Override
	public void initGui() {
		if (this.subgui < MAIN_DEBUG) {
			initSettingsGui();
		} else if (this.subgui == MAIN_DEBUG) {
			HudElementType[] debugTypes = EnumOptionsDebugMod.getDebugTypes();
			int i = 0;
			int j = debugTypes.length;
			for (int k = 0; k < j; k++) {
				GuiButtonTooltip guismallbutton = new GuiButtonTooltip(200 + k, this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 22 * (i >> 1), 150, 20, debugTypes[k].getDisplayName()).setTooltip(I18n.format("tooltip.debug_option", new Object[0]));
				this.buttonList.add(guismallbutton);
				i++;
			}
		}
		this.buttonList.add(new GuiButtonTooltip(250, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])).setTooltip(I18n.format("tooltip.done", new Object[0])));
	}

	/** Function to initialize the settings GUI */
	private void initSettingsGui() {
		EnumOptionsMod[] options;
		options = this.getOptions();
		int i = 0;
		int j = options.length;
		for (int k = 0; k < j; k++) {
			EnumOptionsMod enumoptions = options[k];
			if(enumoptions.getType() == EnumOptionsMod.EnumOptionType.FLOAT){
				GuiSliderSetting guiSlider = new GuiSliderSetting(enumoptions.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 14 + 20 * (i >> 1), enumoptions);
				guiSlider.setTooltip();
				this.buttonList.add(guiSlider);
			} else {
				GuiButtonTooltip guismallbutton = new GuiButtonTooltip(enumoptions.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 14 + 20 * (i >> 1), enumoptions, this.settings.getKeyBinding(enumoptions)).setTooltip();
				this.buttonList.add(guismallbutton);
			}

			i++;
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button.id == EnumOptionsMod.COLOR_EXPERIENCE.ordinal()) {
				this.mc.displayGuiScreen(new GuiSettingsModColor(this.parent, this.subgui, EnumColor.EXPERIENCE));
			} else if (button.id == EnumOptionsMod.COLOR_AIR.ordinal()) {
				this.mc.displayGuiScreen(new GuiSettingsModColor(this.parent, this.subgui, EnumColor.BREATH));
			} else if (button.id == EnumOptionsMod.COLOR_HEALTH.ordinal()) {
				this.mc.displayGuiScreen(new GuiSettingsModColor(this.parent, this.subgui, EnumColor.HEALTH));
			} else if (button.id == EnumOptionsMod.COLOR_STAMINA.ordinal()) {
				this.mc.displayGuiScreen(new GuiSettingsModColor(this.parent, this.subgui, EnumColor.STAMINA));
			} else if (button.id == EnumOptionsMod.COLOR_JUMPBAR.ordinal()) {
				this.mc.displayGuiScreen(new GuiSettingsModColor(this.parent, this.subgui, EnumColor.JUMPBAR));
			} else if (button.id == EnumOptionsMod.COLOR_POISON.ordinal()) {
				this.mc.displayGuiScreen(new GuiSettingsModColor(this.parent, this.subgui, EnumColor.POISON));
			} else if (button.id == EnumOptionsMod.COLOR_HUNGER.ordinal()) {
				this.mc.displayGuiScreen(new GuiSettingsModColor(this.parent, this.subgui, EnumColor.HUNGER));
			} else if (button.id == EnumOptionsMod.COLOR_ABSORPTION.ordinal()) {
				this.mc.displayGuiScreen(new GuiSettingsModColor(this.parent, this.subgui, EnumColor.ABSORPTION));
			} else if (button.id == EnumOptionsMod.COLOR_WITHER.ordinal()) {
				this.mc.displayGuiScreen(new GuiSettingsModColor(this.parent, this.subgui, EnumColor.WITHER));
			} else if ((button.id < 100) && ((button instanceof GuiButtonTooltip))) {
				this.settings.setOptionValue(((GuiButtonTooltip) button).returnOptions());
				button.displayString = this.settings.getKeyBinding(EnumOptionsMod.getEnumOptions(button.id));
			} else if (button.id >= 200 && button.id < 250) {
				HudElementType[] types = EnumOptionsDebugMod.getDebugTypes();
				this.mc.displayGuiScreen(new GuiSettingsModDebug(this.parent, types[button.id - 200]));
			} else if (button.id == 250) {
				this.mc.displayGuiScreen(new GuiSettingsMod(this.parent));
				this.settings.saveOptions();
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		String title = "";
		switch (this.subgui) {
		case GENERAL:
			title = I18n.format("gui.rpg.general", new Object[0]);
			break;
		case HUD:
			title = I18n.format("gui.rpg.hud", new Object[0]);
			break;
		case COLORS:
			title = I18n.format("gui.rpg.color", new Object[0]);
			break;
		case DETAILS:
			title = I18n.format("gui.rpg.detail", new Object[0]);
			break;
		case MAIN_DEBUG:
			title = I18n.format("gui.rpg.debug", new Object[0]);
			break;
		}
		this.drawCenteredString(this.fontRendererObj, title, this.width / 2, 12, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	/** Function to get which category's settings should be displayed */
	public EnumOptionsMod[] getOptions() {
		switch (this.subgui) {
		case GENERAL:
			return optionsGeneral;
		case HUD:
			return optionsHUD;
		case COLORS:
			return optionsColors;
		case DETAILS:
			return optionsDetails;
		default:
			return null;
		}
	}
}
