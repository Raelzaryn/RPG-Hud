package net.spellcraftgaming.rpghud.gui;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.SettingColor;
import net.spellcraftgaming.rpghud.settings.Settings;

public class GuiSettingsMod extends GuiScreenTooltip {

	/** The ModSettings instance */
	private Settings settings;

	/** The GuiScreen which lead to this GUI */
	private GuiScreen parent;

	
	private String subSetting;
	
	public GuiSettingsMod(GuiScreen parent, String subSetting) {
		this.parent = parent;
		this.settings = ModRPGHud.instance.settings;
		this.subSetting = subSetting;
	}
	
	public GuiSettingsMod(GuiScreen parent) {
		this.parent = parent;
		this.settings = ModRPGHud.instance.settings;
		this.subSetting = "";
	}

	@Override
	public void initGui() {
		
		if(this.subSetting.equals("")){
			GuiButtonTooltip guismallbutton = new GuiButtonTooltip(0, this.width / 2 - 155 + 0 % 2 * 160, this.height / 6 - 14 + 20 * (0 >> 1), "general", I18n.format("gui.rpg.general", new Object[0])).setTooltip(I18n.format("tooltip.general", new Object[0]));
			this.buttonList.add(guismallbutton);
			
			int count = 1;
			
			for(HudElementType type : HudElementType.values()){
				List<String> settings = this.settings.getSettingsOf(type);
				if(!settings.isEmpty()) {
					guismallbutton = new GuiButtonTooltip(count, this.width / 2 - 155 + count % 2 * 160, this.height / 6 - 14 + 20 * (count >> 1), type.name(), type.getDisplayName()).setTooltip(I18n.format("tooltip.element", new Object[0]));
					this.buttonList.add(guismallbutton);
					count++;
				}
			}
		} else {
			List<String> settings = this.settings.getSettingsOf(this.subSetting);
			for(int i = 0; i < settings.size(); i++){
				GuiButtonTooltip guismallbutton = new GuiButtonTooltip(i, this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 14 + 20 * (i >> 1), settings.get(i), this.settings.getButtonString(settings.get(i))).setTooltip(this.settings.getSetting(settings.get(i)).getTooltip());
				this.buttonList.add(guismallbutton);
			}
		}

		this.buttonList.add(new GuiButtonTooltip(100, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])).setTooltip(I18n.format("tooltip.done", new Object[0])));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button.id == 100) {
				this.settings.saveSettings();
				this.mc.displayGuiScreen(this.parent);
			} else if(this.subSetting.equals("")){
				GuiButtonTooltip b = (GuiButtonTooltip) button;
				if(b.enumOptions != null){
					this.mc.displayGuiScreen(new GuiSettingsMod(this, b.enumOptions));
				}
			} else {
				GuiButtonTooltip b = (GuiButtonTooltip) button;
				if(b.enumOptions != null){
					if(this.settings.getSetting(b.enumOptions) instanceof SettingColor){
						this.mc.displayGuiScreen(new GuiSettingsModColor(this, b.enumOptions));
					} else {
						this.settings.increment(b.enumOptions);
						button.displayString = this.settings.getButtonString(b.enumOptions);
					}
				}
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(GameData.getFontRenderer(), I18n.format("gui.rpg.settings", new Object[0]), this.width / 2, 12, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);

	}
}
