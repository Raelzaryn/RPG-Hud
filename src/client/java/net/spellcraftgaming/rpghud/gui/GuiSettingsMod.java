package net.spellcraftgaming.rpghud.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.spellcraftgaming.rpghud.gui.TextFieldWidgetMod.ValueType;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.SettingColor;
import net.spellcraftgaming.rpghud.settings.SettingDouble;
import net.spellcraftgaming.rpghud.settings.SettingPosition;
import net.spellcraftgaming.rpghud.settings.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Environment(value=EnvType.CLIENT)
public class GuiSettingsMod extends GuiScreenTooltip {

	/** The ModSettings instance */
	private Settings settings;

	/** The GuiScreen which lead to this GUI */
	private Screen parent;

	private String subSetting;

	private Map<String, List<TextFieldWidgetMod>> textFields = new HashMap<>();

    private GuiSettingsMod instance;
	
	public GuiSettingsMod(Screen parent, String subSetting, Component titleIn) {
		super(titleIn);
		this.parent = parent;
		this.settings = ModRPGHud.instance.settings;
		this.subSetting = subSetting;
		this.instance = this;
	}
	
	public GuiSettingsMod(Screen parent, Component titleIn) {
		super(titleIn);
		this.parent = parent;
		this.settings = ModRPGHud.instance.settings;
		this.subSetting = "";
		this.instance = this;
	}

	@Override
	public void init() {
        Font font = Minecraft.getInstance().font;
		if(this.subSetting.equals("")){
			GuiButtonTooltip guismallbutton = new GuiButtonTooltip(this.width / 2 - 155 + 0 % 2 * 160, this.height / 6 - 14 + 20 * (0 >> 1), "general", Component.translatable("gui.rpg.general"), button -> {
					GuiButtonTooltip b = (GuiButtonTooltip) button;
					if(b.enumOptions != null)
					    Minecraft.getInstance().setScreen(new GuiSettingsMod(instance, b.enumOptions, Component.translatable("gui.settings.rpghud")));
			}).setTooltip(Component.translatable("tooltip.general").getString());
            this.addRenderableWidget(guismallbutton);
			
			int count = 1;
			
			for(HudElementType type : HudElementType.values()){
				List<String> settings = this.settings.getSettingsOf(type);
				if(!settings.isEmpty()) {
					guismallbutton = new GuiButtonTooltip(this.width / 2 - 155 + count % 2 * 160, this.height / 6 - 14 + 20 * (count >> 1), type.name(), Component.translatable(type.getDisplayName()), button -> {
							GuiButtonTooltip b = (GuiButtonTooltip) button;
							if(b.enumOptions != null){
								Minecraft.getInstance().setScreen(new GuiSettingsMod(instance, b.enumOptions, Component.translatable("gui.settings.rpghud")));
							}
					}).setTooltip(Component.translatable("tooltip." + type.name()).getString());
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
					List<TextFieldWidgetMod> fields = new ArrayList<>();
					
					GuiTextLabel settingLabel = new GuiTextLabel(this.width / 2 - 152 + i % 2 * 160, this.height / 6 - 8 + 20 * (i >> 1), this.settings.getButtonString(settingList.get(i)));
					labelList.add(settingLabel);

                    TextFieldWidgetMod xPos = new TextFieldWidgetMod(font, ValueType.POSITION, this.width / 2 - 100 + i % 2 * 160, this.height / 6 - 12 + 20 * (i >> 1), 45, 15, Component.literal(values[0]));
					xPos.setValue(values[0]);
					xPos.setMaxLength(6);
					this.addRenderableWidget(xPos);
					fields.add(xPos);

                    TextFieldWidgetMod yPos = new TextFieldWidgetMod(font, ValueType.POSITION, this.width / 2 - 100 + i % 2 * 160 + 48, this.height / 6 - 12 + 20 * (i >> 1), 45, 15, Component.literal(values[1]));
					yPos.setValue(values[1]);
					yPos.setMaxLength(6);
					this.addRenderableWidget(yPos);
					fields.add(yPos);

					textFields.put(settingList.get(i), fields);
				} else if(this.settings.getSetting(settingList.get(i)) instanceof SettingDouble) {
                    List<TextFieldWidgetMod> fields = new ArrayList<>();
                    GuiTextLabel scaleLabel = new GuiTextLabel(this.width / 2 - 151 + i % 2 * 160, this.height / 6 - 8 + 20 * (i >> 1),
                            this.settings.getButtonString(settingList.get(i)));
                    TextFieldWidgetMod scale = new TextFieldWidgetMod(font, ValueType.DOUBLE, this.width / 2 - 100 + i % 2 * 160 + 3,
                            this.height / 6 - 12 + 20 * (i >> 1), 90, 15, Component.translatable(String.valueOf(this.settings.getDoubleValue(settingList.get(i)))));
                    scale.setMessage(Component.literal(String.valueOf(this.settings.getDoubleValue(settingList.get(i)))));
                    labelList.add(scaleLabel);
                    this.addRenderableWidget(scale);
                    fields.add(scale);
                    textFields.put(settingList.get(i), fields);
				} else
				{
					GuiButtonTooltip guismallbutton = new GuiButtonTooltip(this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 14 + 20 * (i >> 1), settingList.get(i), Component.translatable(this.settings.getButtonString(settingList.get(i))), button -> {
							GuiButtonTooltip b = (GuiButtonTooltip) button;
							if(b.enumOptions != null){
								if(settings.getSetting(b.enumOptions) instanceof SettingColor){
								    Minecraft.getInstance().setScreen(new GuiSettingsModColor(instance, b.enumOptions, Component.translatable("gui.settings.rpghud")));
								} else {
									settings.increment(b.enumOptions);
									button.setMessage(Component.translatable(settings.getButtonString(b.enumOptions)));
								}
							}
					}).setTooltip(this.settings.getSetting(settingList.get(i)).getTooltip());
					this.addRenderableWidget(guismallbutton);
				}
			}
		}

		this.addRenderableWidget(Button.builder(Component.translatable("gui.done"), button -> {
				Settings settings = ModRPGHud.instance.settings;
				for(String settingID : textFields.keySet()) {
				    for(TextFieldWidgetMod t : textFields.get(settingID)) {
	                    if(t instanceof TextFieldWidgetMod) {
	                        ValueType type = t.getValueType();
	                        switch(type) {
	                            case DOUBLE:
	                                double value;
	                                try {
	                                    value = Double.parseDouble(textFields.get(settingID).get(0).getValue());
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
				Minecraft.getInstance().setScreen(parent);
		}).bounds(this.width / 2 - 100, this.height / 6 + 168, 200, 20).build());
	}

	@Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		graphics.centeredText(this.font, Component.translatable("gui.rpg.settings"), this.width / 2, 12, 16777215);

		for(List<TextFieldWidgetMod> positionPairs : textFields.values()) {
			for(TextFieldWidgetMod t : positionPairs)
				t.extractRenderState(graphics, mouseX, mouseY, a);
		}
		super.extractRenderState(graphics, mouseX, mouseY, a);
	}

	@Override
	public void tick() {
		super.tick();
		for(String settingID : textFields.keySet()) {
			for(TextFieldWidgetMod t : textFields.get(settingID)) {
                if(t instanceof TextFieldWidgetMod) {
                    ValueType type = t.getValueType();
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
	}
}
