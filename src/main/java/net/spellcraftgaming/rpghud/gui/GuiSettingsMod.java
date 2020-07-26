package net.spellcraftgaming.rpghud.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.TextFieldWidgetMod.ValueType;
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

    private Map<String, List<TextFieldWidget>> textFields = new HashMap<String, List<TextFieldWidget>>();

    private GuiSettingsMod instance;

    public GuiSettingsMod(Screen parent, String subSetting, ITextComponent titleIn) {
        super(titleIn);
        this.parent = parent;
        this.settings = ModRPGHud.instance.settings;
        this.subSetting = subSetting;
        this.instance = this;
    }

    public GuiSettingsMod(Screen parent, ITextComponent titleIn) {
        super(titleIn);
        this.parent = parent;
        this.settings = ModRPGHud.instance.settings;
        this.subSetting = "";
        this.instance = this;
    }

    @Override
    public void func_231160_c_() {
        FontRenderer fontRenderer = field_230706_i_.fontRenderer;
        if(this.subSetting.equals("")) {
            GuiButtonTooltip guismallbutton = new GuiButtonTooltip(this.field_230708_k_ / 2 - 155 + 0 % 2 * 160, this.field_230709_l_ / 6 - 14 + 20 * (0 >> 1),
                    "general", new TranslationTextComponent("gui.rpg.general"), button -> {
                        GuiButtonTooltip b = (GuiButtonTooltip) button;
                        if(b.enumOptions != null)
                            field_230706_i_.displayGuiScreen(new GuiSettingsMod(instance, b.enumOptions, new TranslationTextComponent("gui.settings.rpghud")));
                    }).setTooltip(I18n.format("tooltip.general", new Object[0]));
            this.func_230480_a_(guismallbutton);

            int count = 1;

            for(HudElementType type : HudElementType.values()) {
                List<String> settings = this.settings.getSettingsOf(type);
                if(!settings.isEmpty()) {
                    guismallbutton = new GuiButtonTooltip(this.field_230708_k_ / 2 - 155 + count % 2 * 160, this.field_230709_l_ / 6 - 14 + 20 * (count >> 1),
                            type.name(), new TranslationTextComponent(type.getDisplayName()), button -> {
                                GuiButtonTooltip b = (GuiButtonTooltip) button;
                                if(b.enumOptions != null) {
                                    this.field_230706_i_
                                            .displayGuiScreen(new GuiSettingsMod(instance, b.enumOptions, new TranslationTextComponent("gui.settings.rpghud")));
                                }
                            }).setTooltip(I18n.format("tooltip.element", new Object[0]));
                    this.func_230480_a_(guismallbutton);
                    count++;
                }
            }
        } else {
            List<String> settingList = this.settings.getSettingsOf(this.subSetting);
            for(int i = 0; i < settingList.size(); i++) {
                if(this.settings.getSetting(settingList.get(i)) instanceof SettingPosition) {
                    String[] values = ((String) this.settings.getSetting(settingList.get(i)).getValue()).split("_");
                    List<TextFieldWidget> fields = new ArrayList<TextFieldWidget>();

                    GuiTextLabel settingLabel = new GuiTextLabel(this.field_230708_k_ / 2 - 151 + i % 2 * 160, this.field_230709_l_ / 6 - 8 + 20 * (i >> 1),
                            this.settings.getButtonString(settingList.get(i)));
                    labelList.add(settingLabel);

                    TextFieldWidget xPos = new TextFieldWidgetMod(fontRenderer, ValueType.POSITION, this.field_230708_k_ / 2 - 100 + i % 2 * 160,
                            this.field_230709_l_ / 6 - 12 + 20 * (i >> 1), 45, 15, new TranslationTextComponent(values[0]));
                    xPos.setText(values[0]);
                    xPos.setMaxStringLength(6);
                    this.field_230705_e_.add(xPos);
                    fields.add(xPos);

                    TextFieldWidget yPos = new TextFieldWidgetMod(fontRenderer, ValueType.POSITION, this.field_230708_k_ / 2 - 100 + i % 2 * 160 + 48,
                            this.field_230709_l_ / 6 - 12 + 20 * (i >> 1), 45, 15, new TranslationTextComponent(values[1]));
                    yPos.setText(values[1]);
                    yPos.setMaxStringLength(6);
                    this.field_230705_e_.add(yPos);
                    fields.add(yPos);

                    textFields.put(settingList.get(i), fields);
                } else if(this.settings.getSetting(settingList.get(i)) instanceof SettingDouble) {
                    List<TextFieldWidget> fields = new ArrayList<TextFieldWidget>();
                    GuiTextLabel scaleLabel = new GuiTextLabel(this.field_230708_k_ / 2 - 151 + i % 2 * 160, this.field_230709_l_ / 6 - 8 + 20 * (i >> 1),
                            this.settings.getButtonString(settingList.get(i)));
                    TextFieldWidget scale = new TextFieldWidgetMod(fontRenderer, ValueType.DOUBLE, this.field_230708_k_ / 2 - 100 + i % 2 * 160 +3,
                            this.field_230709_l_ / 6 - 12 + 20 * (i >> 1), 90, 15,
                            new TranslationTextComponent(String.valueOf(this.settings.getDoubleValue(settingList.get(i)))));
                    scale.setText(String.valueOf(this.settings.getDoubleValue(settingList.get(i))));
                    labelList.add(scaleLabel);
                    this.field_230705_e_.add(scale);
                    fields.add(scale);
                    textFields.put(settingList.get(i), fields);
                } else {
                    GuiButtonTooltip guismallbutton = new GuiButtonTooltip(this.field_230708_k_ / 2 - 155 + i % 2 * 160,
                            this.field_230709_l_ / 6 - 14 + 20 * (i >> 1), settingList.get(i),
                            new TranslationTextComponent(this.settings.getButtonString(settingList.get(i))), button -> {
                                GuiButtonTooltip b = (GuiButtonTooltip) button;
                                if(b.enumOptions != null) {
                                    if(settings.getSetting(b.enumOptions) instanceof SettingColor) {
                                        field_230706_i_.displayGuiScreen(
                                                new GuiSettingsModColor(instance, b.enumOptions, new TranslationTextComponent("gui.settings.rpghud")));
                                    } else {
                                        settings.increment(b.enumOptions);
                                        button.func_238482_a_(new TranslationTextComponent(settings.getButtonString(b.enumOptions)));
                                    }
                                }
                            }).setTooltip(this.settings.getSetting(settingList.get(i)).getTooltip());
                    this.func_230480_a_(guismallbutton);
                }
            }
        }

        this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 6 + 168, 200, 20, new TranslationTextComponent("gui.done"), button -> {
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
            this.settings.saveSettings();
            this.field_230706_i_.displayGuiScreen(parent);
        }));
    }

    @Override
    public void func_230430_a_(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
        this.func_230446_a_(ms);
        this.func_238471_a_(ms, field_230706_i_.fontRenderer, I18n.format("gui.rpg.settings", new Object[0]), this.field_230708_k_ / 2, 12, 16777215);
        for(List<TextFieldWidget> positionPairs : textFields.values()) {
            for(TextFieldWidget t : positionPairs)
                t.func_230430_a_(ms, mouseX, mouseY, partialTicks);
        }
        super.func_230430_a_(ms, mouseX, mouseY, partialTicks);
    }

    @Override
    public void func_231023_e_() {
        super.func_231023_e_();
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
                t.tick();

            }
        }
    }
}
