package net.spellcraftgaming.rpghud.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;

import net.minecraft.client.resources.language.I18n;


import net.minecraftforge.api.distmarker.Dist;


import net.minecraftforge.api.distmarker.OnlyIn;


import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.TranslatableComponent;

import net.spellcraftgaming.rpghud.gui.EditBoxMod.ValueType;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.SettingColor;
import net.spellcraftgaming.rpghud.settings.SettingDouble;
import net.spellcraftgaming.rpghud.settings.SettingPosition;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class GuiSettingsMod extends GuiScreenTooltip {

	/** The ModSettings instance */
	private Settings settings;

	/** The GuiScreen which lead to this GUI */
	private Screen parent;

	private String subSetting;

	private Map<String, List<EditBox>> textFields = new HashMap<String, List<EditBox>>();
	
	private GuiSettingsMod instance;
	
	public GuiSettingsMod(Screen parent, String subSetting, BaseComponent titleIn) {
		super(titleIn);
		this.parent = parent;
		this.settings = ModRPGHud.instance.settings;
		this.subSetting = subSetting;
		this.instance = this;
	}
	
	public GuiSettingsMod(Screen parent, BaseComponent titleIn) {
		super(titleIn);
		this.parent = parent;
		this.settings = ModRPGHud.instance.settings;
		this.subSetting = "";
		this.instance = this;
	}

	@Override
	public void init() {
		Font fontRenderer = minecraft.font;
		if(this.subSetting.equals("")){
			GuiButtonTooltip guismallbutton = new GuiButtonTooltip(this.width / 2 - 155 + 0 % 2 * 160, this.height / 6 - 14 + 20 * (0 >> 1), "general", new TranslatableComponent("gui.rpg.general"), button -> {
					GuiButtonTooltip b = (GuiButtonTooltip) button;
					if(b.enumOptions != null)
					    minecraft.setScreen(new GuiSettingsMod(instance, b.enumOptions, new TranslatableComponent("gui.settings.rpghud")));
			}).setTooltip(I18n.get("tooltip.general", new Object[0]));
			this.addRenderableWidget(guismallbutton);
			
			int count = 1;
			
			for(HudElementType type : HudElementType.values()){
				List<String> settings = this.settings.getSettingsOf(type);
				if(!settings.isEmpty()) {
					guismallbutton = new GuiButtonTooltip(this.width / 2 - 155 + count % 2 * 160, this.height / 6 - 14 + 20 * (count >> 1), type.name(), new TranslatableComponent(type.getDisplayName()), button -> {
							GuiButtonTooltip b = (GuiButtonTooltip) button;
							if(b.enumOptions != null){
								this.minecraft.setScreen(new GuiSettingsMod(instance, b.enumOptions, new TranslatableComponent("gui.settings.rpghud")));
							}
					}).setTooltip(I18n.get("tooltip.element", new Object[0]));
					this.addRenderableWidget(guismallbutton);
					count++;
				}
			}
		} else {
			List<String> settingList = this.settings.getSettingsOf(this.subSetting);
			for(int i = 0; i < settingList.size(); i++){
				if(this.settings.getSetting(settingList.get(i)) instanceof SettingPosition)
				{
					String[] values = ((String) this.settings.getSetting(settingList.get(i)).getValue()).split("_");
					List<EditBox> fields = new ArrayList<EditBox>();
					
					GuiTextLabel settingLabel = new GuiTextLabel(this.width / 2 - 152 + i % 2 * 160, this.height / 6 - 8 + 20 * (i >> 1), this.settings.getButtonString(settingList.get(i)));
					labelList.add(settingLabel);

					EditBox xPos = new EditBoxMod(fontRenderer, ValueType.POSITION, this.width / 2 - 100 + i % 2 * 160, this.height / 6 - 12 + 20 * (i >> 1), 45, 15, new TranslatableComponent(values[0]));
					xPos.setValue(values[0]);
					xPos.setMaxLength(6);
					this.addRenderableWidget(xPos);
					fields.add(xPos);

					EditBox yPos = new EditBoxMod(fontRenderer, ValueType.POSITION, this.width / 2 - 100 + i % 2 * 160 + 48, this.height / 6 - 12 + 20 * (i >> 1), 45, 15, new TranslatableComponent(values[1]));
					yPos.setValue(values[1]);
					yPos.setMaxLength(6);
					this.addRenderableWidget(yPos);
					fields.add(yPos);

					textFields.put(settingList.get(i), fields);
				} else if(this.settings.getSetting(settingList.get(i)) instanceof SettingDouble) {
                    List<EditBox> fields = new ArrayList<EditBox>();
                    GuiTextLabel scaleLabel = new GuiTextLabel(this.width / 2 - 151 + i % 2 * 160, this.height / 6 - 8 + 20 * (i >> 1),
                            this.settings.getButtonString(settingList.get(i)));
                    EditBox scale = new EditBoxMod(fontRenderer, ValueType.DOUBLE, this.width / 2 - 100 + i % 2 * 160 + 3,
                            this.height / 6 - 12 + 20 * (i >> 1), 90, 15, new TranslatableComponent(String.valueOf(this.settings.getDoubleValue(settingList.get(i)))));
                    scale.setValue(String.valueOf(this.settings.getDoubleValue(settingList.get(i))));
                    labelList.add(scaleLabel);
                    this.addRenderableWidget(scale);
                    fields.add(scale);
                    textFields.put(settingList.get(i), fields);
				} else
				{
					GuiButtonTooltip guismallbutton = new GuiButtonTooltip(this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 14 + 20 * (i >> 1), settingList.get(i), new TranslatableComponent(this.settings.getButtonString(settingList.get(i))), button -> {
							GuiButtonTooltip b = (GuiButtonTooltip) button;
							if(b.enumOptions != null){
								if(settings.getSetting(b.enumOptions) instanceof SettingColor){
								    minecraft.setScreen(new GuiSettingsModColor(instance, b.enumOptions, new TranslatableComponent("gui.settings.rpghud")));
								} else {
									settings.increment(b.enumOptions);
									button.setMessage(new TranslatableComponent(settings.getButtonString(b.enumOptions)));
								}
							}
					}).setTooltip(this.settings.getSetting(settingList.get(i)).getTooltip());
					this.addRenderableWidget(guismallbutton);
				}
			}
		}

		this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, new TranslatableComponent("gui.done"), button -> {
				Settings settings = ModRPGHud.instance.settings;
				for(String settingID : textFields.keySet()) {
				    for(EditBox t : textFields.get(settingID)) {
	                    if(t instanceof EditBoxMod) {
	                        ValueType type = ((EditBoxMod) t).getValueType();
	                        switch(type) {
	                            case DOUBLE:
	                                double value;
	                                try {
	                                    value = Double.valueOf(textFields.get(settingID).get(0).getValue());
	                                    this.settings.getSetting(settingID).setValue(value);
	                                } catch(NumberFormatException e) {
	                                }
	                                break;
	                            case POSITION:
	                                this.settings.getSetting(settingID)
	                                        .setValue(textFields.get(settingID).get(0).getValue() + "_" + textFields.get(settingID).get(1).getValue());
	                                break;
	                        }
	                    }
	                }
				}
				settings.saveSettings();
				minecraft.setScreen(parent);
		}));
	}

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		Gui.drawCenteredString(ms, minecraft.font, I18n.get("gui.rpg.settings", new Object[0]), this.width / 2, 12, 16777215);
		for(List<EditBox> positionPairs : textFields.values()) {
			for(EditBox t : positionPairs)
				t.render(ms, mouseX, mouseY, partialTicks);
		}
		super.render(ms, mouseX, mouseY, partialTicks);
	}

	@Override
	public void tick() {
		super.tick();
		for(String settingID : textFields.keySet()) {
			for(EditBox t : textFields.get(settingID)) {
                if(t instanceof EditBoxMod) {
                    ValueType type = ((EditBoxMod) t).getValueType();
                    switch(type) {
                        case DOUBLE:
                            double value;
                            try {
                                value = Double.valueOf(textFields.get(settingID).get(0).getValue());
                                this.settings.getSetting(settingID).setValue(value);
                            } catch(NumberFormatException e) {
                            }
                            break;
                        case POSITION:
                            this.settings.getSetting(settingID)
                                    .setValue(textFields.get(settingID).get(0).getValue() + "_" + textFields.get(settingID).get(1).getValue());
                            break;
                    }
                }
				t.tick();
			}
		}
	}
}
