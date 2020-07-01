package net.spellcraftgaming.rpghud.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.SettingColor;
import net.spellcraftgaming.rpghud.settings.SettingPosition;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class GuiSettingsMod extends GuiScreenTooltip {

    /** The ModSettings instance */
    private Settings settings;

    /** The GuiScreen which lead to this GUI */
    private Screen parent;

    private String subSetting;

    private Map<String, List<TextFieldWidget>> textFields = new HashMap<>();

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
    public void init() {
        FontRenderer fontRenderer = this.minecraft.fontRenderer;
        if(this.subSetting.equals("")) {
            GuiButtonTooltip guismallbutton = new GuiButtonTooltip(this.width / 2 - 155 + 0 % 2 * 160, this.height / 6 - 14 + 20 * (0 >> 1), "general",
                    I18n.format("gui.rpg.general"), button -> {
                        GuiButtonTooltip b = (GuiButtonTooltip) button;
                        if(b.enumOptions != null)
                            this.minecraft.displayGuiScreen(new GuiSettingsMod(this.instance, b.enumOptions, new TranslationTextComponent("gui.settings.rpghud")));
                    }).setTooltip(I18n.format("tooltip.general", new Object[0]));
            this.addButton(guismallbutton);

            int count = 1;

            for(HudElementType type : HudElementType.values()) {
                List<String> settings = this.settings.getSettingsOf(type);
                if(!settings.isEmpty()) {
                    guismallbutton = new GuiButtonTooltip(this.width / 2 - 155 + count % 2 * 160, this.height / 6 - 14 + 20 * (count >> 1), type.name(),
                            I18n.format(type.getDisplayName()), button -> {
                                GuiButtonTooltip b = (GuiButtonTooltip) button;
                                if(b.enumOptions != null)
                                    this.minecraft
                                            .displayGuiScreen(new GuiSettingsMod(this.instance, b.enumOptions, new TranslationTextComponent("gui.settings.rpghud")));
                            }).setTooltip(I18n.format("tooltip.element", new Object[0]));
                    this.addButton(guismallbutton);
                    count++;
                }
            }
        } else {
            List<String> settingList = this.settings.getSettingsOf(this.subSetting);
            for(int i = 0; i < settingList.size(); i++)
                if(this.settings.getSetting(settingList.get(i)) instanceof SettingPosition) {
                    String[] values = ((String) this.settings.getSetting(settingList.get(i)).getValue()).split("_");
                    List<TextFieldWidget> fields = new ArrayList<>();

                    GuiTextLabel settingLabel = new GuiTextLabel(this.width / 2 - 152 + i % 2 * 160, this.height / 6 - 8 + 20 * (i >> 1),
                            this.settings.getButtonString(settingList.get(i)));
                    this.labelList.add(settingLabel);

                    TextFieldWidget xPos = new TextFieldWidget(fontRenderer, this.width / 2 - 100 + i % 2 * 160, this.height / 6 - 12 + 20 * (i >> 1), 45, 15,
                            values[0]);
                    xPos.setText(values[0]);
                    xPos.setMaxStringLength(6);
                    this.children.add(xPos);
                    fields.add(xPos);

                    TextFieldWidget yPos = new TextFieldWidget(fontRenderer, this.width / 2 - 100 + i % 2 * 160 + 48, this.height / 6 - 12 + 20 * (i >> 1), 45, 15,
                            values[1]);
                    yPos.setText(values[1]);
                    yPos.setMaxStringLength(6);
                    this.children.add(yPos);
                    fields.add(yPos);

                    this.textFields.put(settingList.get(i), fields);
                } else {
                    GuiButtonTooltip guismallbutton = new GuiButtonTooltip(this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 14 + 20 * (i >> 1),
                            settingList.get(i), this.settings.getButtonString(settingList.get(i)), button -> {
                                GuiButtonTooltip b = (GuiButtonTooltip) button;
                                if(b.enumOptions != null)
                                    if(this.settings.getSetting(b.enumOptions) instanceof SettingColor)
                                        this.minecraft.displayGuiScreen(
                                                new GuiSettingsModColor(this.instance, b.enumOptions, new TranslationTextComponent("gui.settings.rpghud")));
                                    else {
                                        this.settings.increment(b.enumOptions);
                                        button.setMessage(this.settings.getButtonString(b.enumOptions));
                                    }
                            }).setTooltip(this.settings.getSetting(settingList.get(i)).getTooltip());
                    this.addButton(guismallbutton);
                }
        }

        this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, I18n.format("gui.done", new Object[0]), button -> {
            Settings settings = ModRPGHud.instance.settings;
            for(String settingID : this.textFields.keySet())
                settings.setSetting(settingID, this.textFields.get(settingID).get(0).getText() + "_" + this.textFields.get(settingID).get(1).getText());
            settings.saveSettings();
            this.minecraft.displayGuiScreen(this.parent);
        }));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        this.drawCenteredString(this.minecraft.fontRenderer, I18n.format("gui.rpg.settings", new Object[0]), this.width / 2, 12, 16777215);
        for(List<TextFieldWidget> positionPairs : this.textFields.values())
            for(TextFieldWidget t : positionPairs)
                t.render(mouseX, mouseY, partialTicks);
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public void tick() {
        super.tick();
        for(String settingID : this.textFields.keySet())
            for(TextFieldWidget t : this.textFields.get(settingID)) {
                if(t.isFocused())
                    this.settings.getSetting(settingID)
                            .setValue(this.textFields.get(settingID).get(0).getText() + "_" + this.textFields.get(settingID).get(1).getText());
                t.tick();
            }
    }
}
