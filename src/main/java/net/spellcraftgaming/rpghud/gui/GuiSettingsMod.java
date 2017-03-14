package net.spellcraftgaming.rpghud.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.ModDebugSettings;
import net.spellcraftgaming.rpghud.settings.ModSettings;

public class GuiSettingsMod extends GuiScreenTooltip {

	/** The ModSettings instance*/
	private ModSettings settings;
	
	/** The ModDebugSettings instance*/
	private ModDebugSettings debug;
	
	/** The GuiScreen which lead to this GUI*/
	private GuiScreen parent;

	public GuiSettingsMod(GuiScreen parent) {
		this.parent = parent;
		this.settings = ModRPGHud.instance.settings;
		this.debug = ModRPGHud.instance.settingsDebug;
	}

	@Override
	public void initGui() {
		this.buttonList.add(new GuiButtonTooltip(1, this.width / 2 - 100, this.height / 6 - 12, I18n.format("gui.rpg.general", new Object[0])).setTooltip(I18n.format("tooltip.general", new Object[0])));
		this.buttonList.add(new GuiButtonTooltip(2, this.width / 2 - 100, this.height / 6 + 12, I18n.format("gui.rpg.hud", new Object[0])).setTooltip(I18n.format("tooltip.hud", new Object[0])));
		this.buttonList.add(new GuiButtonTooltip(3, this.width / 2 - 100, this.height / 6 + 36, I18n.format("gui.rpg.color", new Object[0])).setTooltip(I18n.format("tooltip.color", new Object[0])));
		this.buttonList.add(new GuiButtonTooltip(4, this.width / 2 - 100, this.height / 6 + 60, I18n.format("gui.rpg.detail", new Object[0])).setTooltip(I18n.format("tooltip.detail", new Object[0])));
		this.buttonList.add(new GuiButtonTooltip(5, this.width / 2 - 100, this.height / 6 + 96, I18n.format("gui.rpg.debug", new Object[0])).setTooltip(I18n.format("tooltip.debug", new Object[0])));

		this.buttonList.add(new GuiButtonTooltip(0, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])).setTooltip(I18n.format("tooltip.done", new Object[0])));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button.id == 1) {
				this.mc.displayGuiScreen(new GuiSettingsModSub(this.parent, GuiSettingsModSub.GENERAL));
			} else if (button.id == 2) {
				this.mc.displayGuiScreen(new GuiSettingsModSub(this.parent, GuiSettingsModSub.HUD));
			} else if (button.id == 3) {
				this.mc.displayGuiScreen(new GuiSettingsModSub(this.parent, GuiSettingsModSub.COLORS));
			} else if (button.id == 4) {
				this.mc.displayGuiScreen(new GuiSettingsModSub(this.parent, GuiSettingsModSub.DETAILS));
			} else if (button.id == 5) {
				this.mc.displayGuiScreen(new GuiSettingsModSub(this.parent, GuiSettingsModSub.MAIN_DEBUG));
			} else if (button.id == 0) {
				this.settings.saveOptions();
				this.debug.saveOptions();
				this.mc.displayGuiScreen(this.parent);
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, I18n.format("gui.rpg.settings", new Object[0]), this.width / 2, 12, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
