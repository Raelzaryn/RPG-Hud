package net.spellcraftgaming.rpghud.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.resources.I18n;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.SettingColor;
import net.spellcraftgaming.rpghud.settings.SettingPosition;
import net.spellcraftgaming.rpghud.settings.Settings;

public class GuiSettingsMod extends GuiScreenTooltip {

	/** The ModSettings instance */
	private Settings settings;

	/** The GuiScreen which lead to this GUI */
	private GuiScreen parent;

	private String subSetting;

	private Map<String, List<GuiTextField>> textFields = new HashMap<String, List<GuiTextField>>();
	
	public GuiSettingsMod(GuiScreen parent, String subSetting) {
		this.parent = parent;
		this.settings = ModRPGHud.instance.settings;
		this.subSetting = subSetting;
	}
	
	public GuiSettingsMod(Minecraft minecraft, GuiScreen parent) {
		this.parent = parent;
		this.settings = ModRPGHud.instance.settings;
		this.subSetting = "";
	}

	@Override
	public void initGui() {
		FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
		if(this.subSetting.equals("")){
			GuiButtonTooltip guismallbutton = new GuiButtonTooltip(0, this.width / 2 - 155 + 0 % 2 * 160, this.height / 6 - 14 + 20 * (0 >> 1), "general", I18n.format("gui.rpg.general", new Object[0])).setTooltip(I18n.format("tooltip.general", new Object[0]));
			this.buttons.add(guismallbutton);
			
			int count = 1;
			
			for(HudElementType type : HudElementType.values()){
				List<String> settings = this.settings.getSettingsOf(type);
				if(!settings.isEmpty()) {
					guismallbutton = new GuiButtonTooltip(count, this.width / 2 - 155 + count % 2 * 160, this.height / 6 - 14 + 20 * (count >> 1), type.name(), type.getDisplayName()).setTooltip(I18n.format("tooltip.element", new Object[0]));
					this.buttons.add(guismallbutton);
					count++;
				}
			}
		} else {
			List<String> settings = this.settings.getSettingsOf(this.subSetting);
			int id = 0;
			for(int i = 0; i < settings.size(); i++){
				if(this.settings.getSetting(settings.get(i)) instanceof SettingPosition)
				{
					String[] values = ((String) this.settings.getSetting(settings.get(i)).getValue()).split("_");
					List<GuiTextField> fields = new ArrayList<GuiTextField>();

					/*GuiLabel settingLabel = new GuiLabel(Lists.<String>newArrayList(), id, 30, 15, Color.white.getRGB(), fontRenderer);
					settingLabel.x = this.width / 2 - 154 + i % 2 * 160;
					settingLabel.y = this.height / 6 - 11 + 20 * (i >> 1);
					settingLabel.addLine(this.settings.getButtonString(settings.get(i)));
					labels.add(settingLabel);*/

					GuiTextField xPos = new GuiTextField(id + 1, fontRenderer, this.width / 2 - 100 + i % 2 * 160, this.height / 6 - 12 + 20 * (i >> 1), 45, 15);
					xPos.setText(values[0]);
					fields.add(xPos);

					GuiTextField yPos = new GuiTextField(id + 2, fontRenderer, this.width / 2 - 100 + i % 2 * 160 + 50, this.height / 6 - 12 + 20 * (i >> 1), 45, 15);
					yPos.setText(values[1]);
					fields.add(yPos);

					textFields.put(settings.get(i), fields);
					id += 2;
				}
				else
				{
					GuiButtonTooltip guismallbutton = new GuiButtonTooltip(id, this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 14 + 20 * (i >> 1), settings.get(i), this.settings.getButtonString(settings.get(i))).setTooltip(this.settings.getSetting(settings.get(i)).getTooltip());
					this.buttons.add(guismallbutton);
					id++;
				}
			}
		}

		this.buttons.add(new GuiButtonTooltip(100, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])).setTooltip(I18n.format("tooltip.done", new Object[0])));
	}

	public void setWorldAndResolution(Minecraft mc, int width, int height)
	{
		this.labels.clear();
		this.textFields.clear();
		super.setWorldAndResolution(mc, width, height);
	}
	
	@Override
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
	      for(IGuiEventListener iguieventlistener : this.getChildren()) {
	    	  if(iguieventlistener instanceof GuiButton) {
	    		  GuiButton button = (GuiButton) iguieventlistener;
	    		  this.actionPerformed(button);
	    		  return true;
	    	  }
	          boolean flag = iguieventlistener.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
	          if (flag) {
	             this.focusOn(iguieventlistener);
	             if (p_mouseClicked_5_ == 0) {
	                this.setDragging(true);
	             }

	             return true;
	          }
	       }

	       return false;
	}
	
	protected void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button.id == 100) {
				for(String settingID : textFields.keySet()) {
					this.settings.setSetting(settingID, textFields.get(settingID).get(0).getText() + "_" + textFields.get(settingID).get(1).getText());
				}
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
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(fontRenderer, I18n.format("gui.rpg.settings", new Object[0]), this.width / 2, 12, 16777215);
		for(List<GuiTextField> positionPairs : textFields.values()) {
			for(GuiTextField t : positionPairs)
				t.drawTextField(mouseX, mouseY, partialTicks);
		}
		super.render(mouseX, mouseY, partialTicks);
	}

	@Override
	public void tick() {
		super.tick();
		for(String settingID : textFields.keySet()) {
			for(GuiTextField t : textFields.get(settingID)) {
				if (t.isFocused()) this.settings.getSetting(settingID).setValue(textFields.get(settingID).get(0).getText() + "_" + textFields.get(settingID).get(1).getText());
				t.tick();
			}
		}
	}
}
