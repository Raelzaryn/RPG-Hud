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
import net.minecraft.client.resources.I18n;
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
    private GuiScreen parent;

    private String subSetting;

    private Map<String, List<GuiTextField>> textFields = new HashMap<>();

    private GuiSettingsMod instance;

    public GuiSettingsMod(GuiScreen parent, String subSetting) {
        this.parent = parent;
        this.settings = ModRPGHud.instance.settings;
        this.subSetting = subSetting;
        this.instance = this;
    }

    public GuiSettingsMod(GuiScreen parent) {
        this.parent = parent;
        this.settings = ModRPGHud.instance.settings;
        this.subSetting = "";
        this.instance = this;
    }

    @Override
    public void initGui() {
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        if(this.subSetting.equals("")) {
            GuiButtonTooltip guismallbutton = new GuiButtonTooltip(0, this.width / 2 - 155 + 0 % 2 * 160, this.height / 6 - 14 + 20 * (0 >> 1), "general",
                    I18n.format("gui.rpg.general", new Object[0])) {
                @Override
                public void onClick(double mouseX, double mouseY) {
                    GuiButtonTooltip b = this;
                    if(b.enumOptions != null)
                        GuiSettingsMod.this.mc.displayGuiScreen(new GuiSettingsMod(GuiSettingsMod.this.instance, b.enumOptions));
                }
            }.setTooltip(I18n.format("tooltip.general", new Object[0]));
            this.addButton(guismallbutton);

            int count = 1;
            for(HudElementType type : HudElementType.values()) {

                List<String> settings = this.settings.getSettingsOf(type);
                if(!settings.isEmpty()) {
                    guismallbutton = new GuiButtonTooltip(count, this.width / 2 - 155 + count % 2 * 160, this.height / 6 - 14 + 20 * (count >> 1), type.name(),
                            I18n.format(type.getDisplayName(), new Object[0])) {
                        @Override
                        public void onClick(double mouseX, double mouseY) {
                            GuiButtonTooltip b = this;
                            if(b.enumOptions != null)
                                GuiSettingsMod.this.mc.displayGuiScreen(new GuiSettingsMod(GuiSettingsMod.this.instance, b.enumOptions));

                        }
                    }.setTooltip(I18n.format("tooltip.element", new Object[0]));
                    this.addButton(guismallbutton);
                    count++;
                }
            }
        } else {
            List<String> settings = this.settings.getSettingsOf(this.subSetting);
            int id = 0;
            for(int i = 0; i < settings.size(); i++)
                if(this.settings.getSetting(settings.get(i)) instanceof SettingPosition) {
                    String[] values = ((String) this.settings.getSetting(settings.get(i)).getValue()).split("_");
                    List<GuiTextField> fields = new ArrayList<>();

                    GuiTextLabel settingLabel = new GuiTextLabel(this.width / 2 - 152 + i % 2 * 160, this.height / 6 - 8 + 20 * (i >> 1),
                            this.settings.getButtonString(settings.get(i)));
                    this.labelList.add(settingLabel);

                    GuiTextField xPos = new TextFieldWidgetMod(id + 1, fontRenderer, ValueType.POSITION, this.width / 2 - 100 + i % 2 * 160, this.height / 6 - 12 + 20 * (i >> 1), 45, 15);
                    xPos.setText(values[0]);
                    fields.add(xPos);

                    GuiTextField yPos = new TextFieldWidgetMod(id + 2, fontRenderer, ValueType.POSITION, this.width / 2 - 100 + i % 2 * 160 + 48, this.height / 6 - 12 + 20 * (i >> 1), 45,
                            15);
                    yPos.setText(values[1]);
                    fields.add(yPos);

                    this.textFields.put(settings.get(i), fields);
                    id += 2;
                } else if(this.settings.getSetting(settings.get(i)) instanceof SettingDouble) {
                    List<GuiTextField> fields = new ArrayList<>();
                    GuiTextLabel scaleLabel = new GuiTextLabel(this.width / 2 - 151 + i % 2 * 160, this.height / 6 - 8 + 20 * (i >> 1),
                            this.settings.getButtonString(settings.get(i)));
                    GuiTextField scale = new TextFieldWidgetMod(id + 1, fontRenderer, ValueType.DOUBLE, this.width / 2 - 100 + i % 2 * 160 + 3,
                            this.height / 6 - 12 + 20 * (i >> 1), 90, 15);
                    scale.setText(String.valueOf(this.settings.getDoubleValue(settings.get(i))));
                    this.labelList.add(scaleLabel);
                    this.children.add(scale);
                    fields.add(scale);
                    textFields.put(settings.get(i), fields);
                    id++;
                } else {
                    GuiButtonTooltip guismallbutton = new GuiButtonTooltip(id, this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 14 + 20 * (i >> 1),
                            settings.get(i), this.settings.getButtonString(settings.get(i))) {
                        @Override
                        public void onClick(double mouseX, double mouseY) {
                            Settings settings = ModRPGHud.instance.settings;
                            GuiButtonTooltip b = this;
                            if(b.enumOptions != null)
                                if(settings.getSetting(b.enumOptions) instanceof SettingColor)
                                    GuiSettingsMod.this.mc.displayGuiScreen(new GuiSettingsModColor(GuiSettingsMod.this.instance, b.enumOptions));
                                else {
                                    settings.increment(b.enumOptions);
                                    this.displayString = settings.getButtonString(b.enumOptions);
                                }
                        }
                    }.setTooltip(this.settings.getSetting(settings.get(i)).getTooltip());
                    this.addButton(guismallbutton);
                    id++;
                }
        }

        this.addButton(new GuiButton(100, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])) {
            @Override
            public void onClick(double mouseX, double mouseY) {
                Settings settings = ModRPGHud.instance.settings;
                for(String settingID : GuiSettingsMod.this.textFields.keySet())
                    for(GuiTextField t : textFields.get(settingID)) {
                        if(t instanceof TextFieldWidgetMod) {
                            ValueType type = ((TextFieldWidgetMod) t).getValueType();
                            switch(type) {
                                case DOUBLE:
                                    double value;
                                    try {
                                        value = Double.valueOf(textFields.get(settingID).get(0).getText());
                                        settings.getSetting(settingID).setValue(value);
                                    } catch(NumberFormatException e) {
                                    }
                                    break;
                                case POSITION:
                                    settings.getSetting(settingID)
                                            .setValue(textFields.get(settingID).get(0).getText() + "_" + textFields.get(settingID).get(1).getText());
                                    break;
                            }
                        }
                    }
                settings.saveSettings();
                GuiSettingsMod.this.mc.displayGuiScreen(GuiSettingsMod.this.parent);
            }
        });
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.format("gui.rpg.settings", new Object[0]), this.width / 2, 12, 16777215);
        for(List<GuiTextField> positionPairs : this.textFields.values())
            for(GuiTextField t : positionPairs)
                t.drawTextField(mouseX, mouseY, partialTicks);
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public void tick() {
        super.tick();
        for(String settingID : this.textFields.keySet())
            for(GuiTextField t : this.textFields.get(settingID)) {
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
