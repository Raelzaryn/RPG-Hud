package net.spellcraftgaming.rpghud.gui;

import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.BUTTON_TOOLTIP_ENABLED;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.CLOCK_TIME_FORMAT;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.COLOR_AIR;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.COLOR_EXPERIENCE;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.COLOR_HEALTH;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.COLOR_JUMPBAR;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.COLOR_STAMINA;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.ENABLE_CLOCK;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.ENABLE_IMMERSIVE_CLOCK;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.ENABLE_TIMECOLOR;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.HUD_TYPE;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.REDUCE_SIZE;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.RENDER_PLAYER_FACE;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.SHOW_ARMOR;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.SHOW_ARROWCOUNT;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.SHOW_HUNGERPREVIEW;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.SHOW_ITEMCOUNT;
import static net.spellcraftgaming.rpghud.settings.EnumOptionsMod.SHOW_ITEMDURABILITY;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.EnumOptionsDebugMod;
import net.spellcraftgaming.rpghud.settings.EnumOptionsMod;
import net.spellcraftgaming.rpghud.settings.ModDebugSettings;
import net.spellcraftgaming.rpghud.settings.ModSettings;

public class GuiSettingsModSub extends GuiScreen {
	private static final EnumOptionsMod[] optionsGeneral = {BUTTON_TOOLTIP_ENABLED};
	private static final EnumOptionsMod[] optionsHUD = {HUD_TYPE, RENDER_PLAYER_FACE, REDUCE_SIZE};
	private static final EnumOptionsMod[] optionsColors = {COLOR_HEALTH, COLOR_STAMINA, COLOR_AIR, COLOR_EXPERIENCE, COLOR_JUMPBAR};
	private static final EnumOptionsMod[] optionsDetails = {SHOW_ARMOR, SHOW_ITEMDURABILITY, SHOW_ITEMCOUNT, SHOW_ARROWCOUNT, ENABLE_CLOCK, ENABLE_IMMERSIVE_CLOCK, CLOCK_TIME_FORMAT, ENABLE_TIMECOLOR, SHOW_HUNGERPREVIEW};
	
	private ModSettings settings;
	private ModDebugSettings debug;
	private GuiScreen parent;
	private int subgui;
	public static final int GENERAL = 0;
	public static final int HUD = 1;
	public static final int COLORS = 2;
	public static final int DETAILS = 3;
	public static final int MAIN_DEBUG = 4;
	
	public GuiSettingsModSub(GuiScreen parent, int subgui) {
		this.parent = parent;
		this.settings = ModRPGHud.instance.settings;
		this.debug = ModRPGHud.instance.settingsDebug;
		this.subgui = subgui;
	}
	
	@Override
	public void initGui() {
		if(this.subgui < MAIN_DEBUG) {
			initSettingsGui();
		}
		this.buttonList.add(new GuiButton(150, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])));
	}
	
	private void initSettingsGui() {
		EnumOptionsMod[] options;
		options = this.getOptions();
		int i = 0;
		int j = options.length;
		for (int k = 0; k < j; k++) {
			EnumOptionsMod enumoptions = options[k];
			GuiButtonSettings guismallbutton = new GuiButtonSettings(enumoptions.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160,
					this.height / 6 - 12 + 24 * (i >> 1), enumoptions, this.settings.getKeyBinding(enumoptions));
			this.buttonList.add(guismallbutton);
			i++;
		}
		this.buttonList.add(new GuiButton(250, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if ((button.id < 100) && ((button instanceof GuiButtonSettings))) {
				this.settings.setOptionValue(((GuiButtonSettings) button).returnEnumOptions(), 1);
				button.displayString = this.settings.getKeyBinding(EnumOptionsMod.getEnumOptions(button.id));
			} else if ((button.id < 200) && ((button instanceof GuiButtonSettings))) {
				this.debug.setOptionValue(((GuiButtonDebug) button).returnEnumOptions());
				button.displayString = this.debug.getKeyBinding(EnumOptionsDebugMod.getEnumOptions(button.id - 100));
			} else if (button.id == 250) {
				if(this.subgui >= GENERAL && this.subgui <= MAIN_DEBUG) {
					this.mc.displayGuiScreen(new GuiSettingsMod(this.parent));
					this.settings.saveOptions();
				} else if(this.subgui > MAIN_DEBUG) {
					this.mc.displayGuiScreen(new GuiSettingsMod(this.parent));
					this.debug.saveOptions();
				}
			}
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, I18n.format("RPG-Hud Settings", new Object[0]), this.width / 2, 12, 16777215);
        GuiButtonTooltip.drawTooltip(this, (ArrayList<GuiButton>) this.buttonList);
	}
	
	public EnumOptionsMod[] getOptions() {
		switch(this.subgui) {
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
