package net.spellcraftgaming.rpghud.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.spellcraftgaming.rpghud.gui.TextFieldWidgetMod.ValueType;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.SettingColor;
import net.spellcraftgaming.rpghud.settings.SettingDouble;
import net.spellcraftgaming.rpghud.settings.SettingPosition;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class GuiSettingsMod extends GuiScreenTooltip {

	/** The ModSettings instance */
	private Settings settings;

	/** The GuiScreen which lead to this GUI */
	private Screen parent;

	private String subSetting;

	private Map<String, List<TextFieldWidget>> textFields = new HashMap<String, List<TextFieldWidget>>();
	
	private GuiSettingsMod instance;
	
	public GuiSettingsMod(Screen parent, String subSetting, Text titleIn) {
		super(titleIn);
		this.parent = parent;
		this.settings = ModRPGHud.instance.settings;
		this.subSetting = subSetting;
		this.instance = this;
	}
	
	public GuiSettingsMod(Screen parent, Text titleIn) {
		super(titleIn);
		this.parent = parent;
		this.settings = ModRPGHud.instance.settings;
		this.subSetting = "";
		this.instance = this;
	}

	@Override
	public void init() {
		TextRenderer fontRenderer = client.textRenderer;
		if(this.subSetting.equals("")){
			GuiButtonTooltip guismallbutton = new GuiButtonTooltip(this.width / 2 - 155 + 0 % 2 * 160, this.height / 6 - 14 + 20 * (0 >> 1), "general", Text.translatable("gui.rpg.general"), button -> {
					GuiButtonTooltip b = (GuiButtonTooltip) button;
					if(b.enumOptions != null)
					    client.setScreen(new GuiSettingsMod(instance, b.enumOptions, Text.translatable("gui.settings.rpghud")));
			}).setTooltip(Text.translatable("tooltip.general").getString());
			this.addDrawableChild(guismallbutton);
			
			int count = 1;
			
			for(HudElementType type : HudElementType.values()){
				List<String> settings = this.settings.getSettingsOf(type);
				if(!settings.isEmpty()) {
					guismallbutton = new GuiButtonTooltip(this.width / 2 - 155 + count % 2 * 160, this.height / 6 - 14 + 20 * (count >> 1), type.name(), Text.translatable(type.getDisplayName()), button -> {
							GuiButtonTooltip b = (GuiButtonTooltip) button;
							if(b.enumOptions != null){
								this.client.setScreen(new GuiSettingsMod(instance, b.enumOptions, Text.translatable("gui.settings.rpghud")));
							}
					}).setTooltip(Text.translatable("tooltip.general").getString());
					this.addDrawableChild(guismallbutton);
					count++;
				}
			}
		} else {
			List<String> settingList = this.settings.getSettingsOf(this.subSetting);
			for(int i = 0; i < settingList.size(); i++){
				if(this.settings.getSetting(settingList.get(i)) instanceof SettingPosition)
				{
					String[] values = ((String) this.settings.getSetting(settingList.get(i)).getValue()).split("_");
					List<TextFieldWidget> fields = new ArrayList<TextFieldWidget>();
					
					GuiTextLabel settingLabel = new GuiTextLabel(this.width / 2 - 152 + i % 2 * 160, this.height / 6 - 8 + 20 * (i >> 1), this.settings.getButtonString(settingList.get(i)));
					labelList.add(settingLabel);

					TextFieldWidget xPos = new TextFieldWidgetMod(fontRenderer, ValueType.POSITION, this.width / 2 - 100 + i % 2 * 160, this.height / 6 - 12 + 20 * (i >> 1), 45, 15, Text.translatable(values[0]));
					xPos.setText(values[0]);
					xPos.setMaxLength(6);
					this.addDrawableChild(xPos);
					fields.add(xPos);

					TextFieldWidget yPos = new TextFieldWidgetMod(fontRenderer, ValueType.POSITION, this.width / 2 - 100 + i % 2 * 160 + 48, this.height / 6 - 12 + 20 * (i >> 1), 45, 15, Text.translatable(values[1]));
					yPos.setText(values[1]);
					yPos.setMaxLength(6);
					this.addDrawableChild(yPos);
					fields.add(yPos);

					textFields.put(settingList.get(i), fields);
				} else if(this.settings.getSetting(settingList.get(i)) instanceof SettingDouble) {
                    List<TextFieldWidget> fields = new ArrayList<TextFieldWidget>();
                    GuiTextLabel scaleLabel = new GuiTextLabel(this.width / 2 - 151 + i % 2 * 160, this.height / 6 - 8 + 20 * (i >> 1),
                            this.settings.getButtonString(settingList.get(i)));
                    TextFieldWidget scale = new TextFieldWidgetMod(fontRenderer, ValueType.DOUBLE, this.width / 2 - 100 + i % 2 * 160 + 3,
                            this.height / 6 - 12 + 20 * (i >> 1), 90, 15, Text.translatable(String.valueOf(this.settings.getDoubleValue(settingList.get(i)))));
                    scale.setText(String.valueOf(this.settings.getDoubleValue(settingList.get(i))));
                    labelList.add(scaleLabel);
                    this.addDrawableChild(scale);
                    fields.add(scale);
                    textFields.put(settingList.get(i), fields);
				} else
				{
					GuiButtonTooltip guismallbutton = new GuiButtonTooltip(this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 14 + 20 * (i >> 1), settingList.get(i), Text.translatable(this.settings.getButtonString(settingList.get(i))), button -> {
							GuiButtonTooltip b = (GuiButtonTooltip) button;
							if(b.enumOptions != null){
								if(settings.getSetting(b.enumOptions) instanceof SettingColor){
								    client.setScreen(new GuiSettingsModColor(instance, b.enumOptions, Text.translatable("gui.settings.rpghud")));
								} else {
									settings.increment(b.enumOptions);
									button.setMessage(Text.translatable(settings.getButtonString(b.enumOptions)));
								}
							}
					}).setTooltip(this.settings.getSetting(settingList.get(i)).getTooltip());
					this.addDrawableChild(guismallbutton);
				}
			}
		}

		this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.done"), button -> {
				Settings settings = ModRPGHud.instance.settings;
				for(String settingID : textFields.keySet()) {
				    for(TextFieldWidget t : textFields.get(settingID)) {
	                    if(t instanceof TextFieldWidgetMod) {
	                        ValueType type = ((TextFieldWidgetMod) t).getValueType();
	                        switch(type) {
	                            case DOUBLE:
	                                double value;
	                                try {
	                                    value = Double.valueOf(textFields.get(settingID).get(0).getText());
	                                    this.settings.getSetting(settingID).setValue(value);
	                                } catch(NumberFormatException e) {
	                                }
	                                break;
	                            case POSITION:
	                                this.settings.getSetting(settingID)
	                                        .setValue(textFields.get(settingID).get(0).getText() + "_" + textFields.get(settingID).get(1).getText());
	                                break;
	                        }
	                    }
	                }
				}
				settings.saveSettings();
				client.setScreen(parent);
		}).dimensions(this.width / 2 - 100, this.height / 6 + 168, 200, 20).build());
	}

	@Override
	public void render(DrawContext dc, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(dc);
		dc.drawCenteredTextWithShadow(client.textRenderer, I18n.translate("gui.rpg.settings", new Object[0]), this.width / 2, 12, 16777215);
		for(List<TextFieldWidget> positionPairs : textFields.values()) {
			for(TextFieldWidget t : positionPairs)
				t.render(dc, mouseX, mouseY, partialTicks);
		}
		super.render(dc, mouseX, mouseY, partialTicks);
	}

	@Override
	public void tick() {
		super.tick();
		for(String settingID : textFields.keySet()) {
			for(TextFieldWidget t : textFields.get(settingID)) {
                if(t instanceof TextFieldWidgetMod) {
                    ValueType type = ((TextFieldWidgetMod) t).getValueType();
                    switch(type) {
                        case DOUBLE:
                            double value;
                            try {
                                value = Double.valueOf(textFields.get(settingID).get(0).getText());
                                this.settings.getSetting(settingID).setValue(value);
                            } catch(NumberFormatException e) {
                            }
                            break;
                        case POSITION:
                            this.settings.getSetting(settingID)
                                    .setValue(textFields.get(settingID).get(0).getText() + "_" + textFields.get(settingID).get(1).getText());
                            break;
                    }
                }
			}
		}
	}
}
