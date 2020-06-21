package net.spellcraftgaming.rpghud.settings;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

public class Settings {

    private Map<String, Setting> settings = new LinkedHashMap<String, Setting>();
    private Configuration config;

    private Configuration[] hudConfig;

    public static final String hud_type = "hud_type";
    public static final String enable_button_tooltip = "enable_button_tooltip";
    public static final String show_update_notification = "show_update_notification";
    public static final String show_convert_notification = "show_convert_notification";
    public static final String hotbar_position = "hotbar_position";
    public static final String widget_position = "widget_position";
    public static final String chat_position = "chat_position";
    public static final String armor_position = "armor_position";

    public static final String reduce_size = "reduce_size";
    public static final String show_armor = "show_armor";
    public static final String show_arrow_count = "show_arrow_count";
    public static final String show_item_durability = "show_item_durability";
    public static final String show_block_count = "show_block_count";
    public static final String show_durability_bar = "show_durability_bar";
    public static final String armor_det_position = "armor_det_position";
    public static final String arrow_det_position = "arrow_det_position";
    public static final String item_det_position = "item_det_position";

    public static final String show_numbers_health = "show_numbers_health";
    public static final String health_percentage = "health_percentage";
    public static final String color_health = "color_health";
    public static final String color_absorption = "color_absorption";
    public static final String color_poison = "color_poison";
    public static final String color_wither = "color_wither";
    public static final String health_position = "health_position";
    public static final String mount_health_position = "mount_health_position";
    public static final String mount_health_percentage = "mount_health_percentage";

    public static final String show_numbers_food = "show_numbers_food";
    public static final String hunger_percentage = "hunger_percentage";
    public static final String show_hunger_preview = "show_hunger_preview";
    public static final String color_food = "color_food";
    public static final String color_hunger = "color_hunger";
    public static final String hunger_position = "hunger_position";

    public static final String show_numbers_experience = "show_numbers_experience";
    public static final String experience_percentage = "experience_percentage";
    public static final String color_experience = "color_experience";
    public static final String experience_position = "experience_position";
    public static final String level_position = "level_position";

    public static final String enable_clock = "enable_clock";
    public static final String enable_clock_color = "enable_clock_color";
    public static final String enable_immersive_clock = "enable_immersive_clock";
    public static final String clock_time_format = "clock_time_format";
    public static final String clock_position = "clock_position";

    public static final String enable_compass = "enable_compass";
    public static final String enable_compass_color = "enable_compass_color";
    public static final String enable_immersive_compass = "enable_immersive_compass";
    public static final String enable_compass_coordinates = "enable_compass_coordinates";
    public static final String invert_compass = "invert_compass";
    public static final String compass_position = "compass_position";

    public static final String render_player_face = "render_player_face";
    public static final String face_position = "face_position";

    public static final String limit_jump_bar = "limit_jump_bar";
    public static final String color_jump_bar = "color_jump_bar";
    public static final String jump_bar_position = "jump_bar_position";

    public static final String enable_entity_inspect = "enable_entity_inspect";
    public static final String inspector_position = "inspector_position";

    public static final String color_air = "color_air";
    public static final String air_position = "air_position";

    public static final String force_render = "force_render";
    public static final String render_vanilla = "render_vanilla";
    public static final String prevent_event = "prevent_event";
    public static final String prevent_element_render = "prevent_element_render";

    private File rpgHudDir() {
        return (new File(Minecraft.getMinecraft().mcDataDir.getPath(), "config" + File.separator + "RPG-HUD"));
    }

    public Settings() {
        this.config = new Configuration(new File(rpgHudDir(), "settings.cfg"));
        this.config.load();
        init();
        this.config.save();

    }

    public void initHudConfig() {
        hudConfig = new Configuration[ModRPGHud.instance.huds.size()];
        Set<String> huds = ModRPGHud.instance.huds.keySet();
        String[] keys = huds.toArray(new String[huds.size()]);
        int size = keys.length;
        for(int n = 0; n < size; n++) {
            hudConfig[n] = new Configuration(new File(rpgHudDir() + File.separator + "hud", keys[n] + ".cfg"));
            Setting setting = new SettingBoolean("test", true);
            hudConfig[n].get("test", "test", (Boolean) setting.getDefaultValue(), setting.getFormatedTooltip()).set((Boolean) setting.getValue());
            hudConfig[n].save();
        }
    }

    public void init() {
        addSetting(hud_type, new SettingHudType(hud_type, "vanilla"));
        addSetting(enable_button_tooltip, new SettingBoolean(enable_button_tooltip, true));
        addSetting(show_update_notification, new SettingBoolean(show_update_notification, true));
        addSetting(show_convert_notification, new SettingBoolean(show_convert_notification, true));

        addSetting(reduce_size, new SettingBoolean(reduce_size, HudElementType.DETAILS, false));
        addSetting(show_armor, new SettingBoolean(show_armor, HudElementType.DETAILS, true));
        addSetting(show_arrow_count, new SettingBoolean(show_arrow_count, HudElementType.DETAILS, true));
        addSetting(show_item_durability, new SettingBoolean(show_item_durability, HudElementType.DETAILS, true));
        addSetting(show_block_count, new SettingBoolean(show_block_count, HudElementType.DETAILS, true));
        addSetting(show_durability_bar, new SettingBoolean(show_durability_bar, HudElementType.DETAILS, true));
        addSetting(armor_det_position, new SettingPosition(armor_det_position, HudElementType.DETAILS, 0, 0));
        addSetting(arrow_det_position, new SettingPosition(arrow_det_position, HudElementType.DETAILS, 0, 0));
        addSetting(item_det_position, new SettingPosition(item_det_position, HudElementType.DETAILS, 0, 0));

        addSetting(show_numbers_health, new SettingBoolean(show_numbers_health, HudElementType.HEALTH, true));
        addSetting(health_percentage, new SettingBoolean(health_percentage, HudElementType.HEALTH, false));
        addSetting(color_health, new SettingColor(color_health, HudElementType.HEALTH, HudElement.COLOR_RED));
        addSetting(color_absorption, new SettingColor(color_absorption, HudElementType.HEALTH, HudElement.COLOR_ORANGE));
        addSetting(color_poison, new SettingColor(color_poison, HudElementType.HEALTH, HudElement.COLOR_PURPLE));
        addSetting(color_wither, new SettingColor(color_wither, HudElementType.HEALTH, HudElement.COLOR_BLACK));
        addSetting(health_position, new SettingPosition(health_position, HudElementType.HEALTH, 0, 0));

        addSetting(show_numbers_food, new SettingBoolean(show_numbers_food, HudElementType.FOOD, true));
        addSetting(hunger_percentage, new SettingBoolean(hunger_percentage, HudElementType.FOOD, false));
        addSetting(show_hunger_preview, new SettingBoolean(show_hunger_preview, HudElementType.FOOD, true));
        addSetting(color_food, new SettingColor(color_food, HudElementType.FOOD, HudElement.COLOR_GREEN));
        addSetting(color_hunger, new SettingColor(color_hunger, HudElementType.FOOD, 0x9ba067));
        addSetting(hunger_position, new SettingPosition(hunger_position, HudElementType.FOOD, 0, 0));

        addSetting(show_numbers_experience, new SettingBoolean(show_numbers_experience, HudElementType.EXPERIENCE, true));
        addSetting(experience_percentage, new SettingBoolean(experience_percentage, HudElementType.EXPERIENCE, false));
        addSetting(color_experience, new SettingColor(color_experience, HudElementType.EXPERIENCE, HudElement.COLOR_YELLOW));
        addSetting(experience_position, new SettingPosition(experience_position, HudElementType.EXPERIENCE, 0, 0));

        addSetting(mount_health_percentage, new SettingBoolean(mount_health_percentage, HudElementType.HEALTH_MOUNT, false));

        addSetting(enable_clock, new SettingBoolean(enable_clock, HudElementType.CLOCK, true));
        addSetting(enable_clock_color, new SettingBoolean(enable_clock_color, HudElementType.CLOCK, true));
        addSetting(enable_immersive_clock, new SettingBoolean(enable_immersive_clock, HudElementType.CLOCK, false));
        addSetting(clock_time_format, new SettingString(clock_time_format, HudElementType.CLOCK, 0, new String[] { "time.24", "time.12" }));
        addSetting(clock_position, new SettingPosition(clock_position, HudElementType.CLOCK, 0, 0));

        addSetting(enable_compass, new SettingBoolean(enable_compass, HudElementType.COMPASS, true));
        addSetting(enable_compass_color, new SettingBoolean(enable_compass_color, HudElementType.COMPASS, true));
        addSetting(enable_immersive_compass, new SettingBoolean(enable_immersive_compass, HudElementType.COMPASS, false));
        addSetting(enable_compass_coordinates, new SettingBoolean(enable_compass_coordinates, HudElementType.COMPASS, true));
        addSetting(invert_compass, new SettingBoolean(invert_compass, HudElementType.COMPASS, false));
        addSetting(compass_position, new SettingPosition(compass_position, HudElementType.COMPASS, 0, 0));

        addSetting(render_player_face, new SettingBoolean(render_player_face, HudElementType.WIDGET, true));
        addSetting(widget_position, new SettingPosition(widget_position, HudElementType.WIDGET, 0, 0));
        addSetting(face_position, new SettingPosition(face_position, HudElementType.WIDGET, 0, 0));

        addSetting(limit_jump_bar, new SettingBoolean(limit_jump_bar, HudElementType.JUMP_BAR, true));
        addSetting(color_jump_bar, new SettingColor(color_jump_bar, HudElementType.JUMP_BAR, HudElement.COLOR_GREY));
        addSetting(jump_bar_position, new SettingPosition(jump_bar_position, HudElementType.JUMP_BAR, 0, 0));

        addSetting(enable_entity_inspect, new SettingBoolean(enable_entity_inspect, HudElementType.ENTITY_INSPECT, true));
        addSetting(inspector_position, new SettingPosition(inspector_position, HudElementType.ENTITY_INSPECT, 0, 0));

        addSetting(color_air, new SettingColor(color_air, HudElementType.AIR, HudElement.COLOR_BLUE));
        addSetting(air_position, new SettingPosition(air_position, HudElementType.AIR, 0, 0));

        addSetting(mount_health_position, new SettingPosition(mount_health_position, HudElementType.HEALTH_MOUNT, 0, 0));
        addSetting(hotbar_position, new SettingPosition(hotbar_position, HudElementType.HOTBAR, 0, 0));
        addSetting(level_position, new SettingPosition(level_position, HudElementType.LEVEL, 0, 0));
        addSetting(armor_position, new SettingPosition(armor_position, HudElementType.ARMOR, 0, 0));

        addDebugSettings(HudElementType.CROSSHAIR);
        addDebugSettings(HudElementType.ARMOR);
        addDebugSettings(HudElementType.HOTBAR);
        addDebugSettings(HudElementType.AIR);
        addDebugSettings(HudElementType.HEALTH);
        addDebugSettings(HudElementType.FOOD);
        addDebugSettings(HudElementType.EXPERIENCE);
        addDebugSettings(HudElementType.LEVEL);
        addDebugSettings(HudElementType.HEALTH_MOUNT);
        addDebugSettings(HudElementType.JUMP_BAR);
    }

    public void addDebugSettings(HudElementType type) {
        addSetting(force_render + "_" + type.name().toLowerCase(), new SettingBoolean(force_render, type, false));
        addSetting(render_vanilla + "_" + type.name().toLowerCase(), new SettingBoolean(render_vanilla, type, false));
        addSetting(prevent_event + "_" + type.name().toLowerCase(), new SettingBoolean(prevent_event, type, false));
        addSetting(prevent_element_render + "_" + type.name().toLowerCase(), new SettingBoolean(prevent_element_render, type, false));
    }

    public Setting getSetting(String id) {
        return this.settings.get(id);
    }

    public int[] getPositionValue(String i) {
        String[] postions = this.settings.get(i).getValue().toString().split("_");
        int[] values = { Integer.valueOf(postions[0]), Integer.valueOf(postions[1]) };
        return values;
    }

    public Object getValue(String i) {
        return this.settings.get(i).getValue();
    }

    public double getDoubleValue(String i) {
        return this.settings.get(i).getDoubleValue();
    }

    public Integer getIntValue(String i) {
        return this.settings.get(i).getIntValue();
    }

    public Boolean getBoolValue(String i) {
        return this.settings.get(i).getBoolValue();
    }

    public Float getFloatValue(String i) {
        return this.settings.get(i).getFloatValue();
    }

    public String getStringValue(String i) {
        return this.settings.get(i).getStringValue();
    }

    public void resetSetting(String i) {
        Setting setting = this.settings.get(i);
        setting.resetValue();
        this.settings.put(i, setting);
        saveSetting(i);
    }

    public void increment(String i) {
        Setting setting = this.settings.get(i);
        setting.increment();
        this.settings.put(i, setting);
        saveSetting(i);
    }

    public void setSetting(String i, Object o) {
        Setting setting = this.settings.get(i);
        setting.setValue(o);
        this.settings.put(i, setting);
        saveSetting(i);
    }

    public void saveSetting(String id) {
        Setting setting = this.settings.get(id);
        String category = "0:general";
        if(setting.associatedType != null)
            category = "1:" + setting.associatedType.name().toLowerCase();
        if(setting instanceof SettingInteger)
            this.config.get(category, id, (Integer) setting.getDefaultValue(), setting.getFormatedTooltip()).set((Integer) setting.getValue());
        else if(setting instanceof SettingColor)
            this.config.get(category, id, Integer.toHexString((Integer) setting.getDefaultValue()), setting.getFormatedTooltip())
                    .set(Integer.toHexString((Integer) setting.getValue()));
        else if(setting instanceof SettingBoolean)
            this.config.get(category, id, (Boolean) setting.getDefaultValue(), setting.getFormatedTooltip()).set((Boolean) setting.getValue());
        else if(setting instanceof SettingFloat)
            this.config.get(category, id, (Float) setting.getDefaultValue(), setting.getFormatedTooltip()).set((Float) setting.getValue());
        else if(setting instanceof SettingString || setting instanceof SettingHudType || setting instanceof SettingPosition)
            this.config.get(category, id, (String) setting.getDefaultValue(), setting.getFormatedTooltip()).set((String) setting.getValue());
        this.config.save();
    }

    public void addSetting(String id, Setting setting) {
        String category = "0:general";
        if(setting.associatedType != null)
            category = "1:" + setting.associatedType.name().toLowerCase();
        if(setting instanceof SettingInteger)
            setting.setValue(this.config.get(category, id, (Integer) setting.getDefaultValue(), setting.getFormatedTooltip()).getInt());
        else if(setting instanceof SettingColor)
            setting.setValue(this.config.get(category, id, Integer.toHexString((Integer) setting.getDefaultValue()), setting.getFormatedTooltip()).getString());
        else if(setting instanceof SettingBoolean)
            setting.setValue(this.config.get(category, id, (Boolean) setting.getDefaultValue(), setting.getFormatedTooltip()).getBoolean());
        else if(setting instanceof SettingFloat)
            setting.setValue(this.config.get(category, id, (Float) setting.getDefaultValue(), setting.getFormatedTooltip()).getDouble());
        else if(setting instanceof SettingString || setting instanceof SettingHudType || setting instanceof SettingPosition)
            setting.setValue(this.config.get(category, id, (String) setting.getDefaultValue(), setting.getFormatedTooltip()).getString());
        this.settings.put(id, setting);
    }

    public boolean doesSettingExist(String i) {
        return this.settings.containsKey(i);
    }

    public String getButtonString(String id) {
        Setting setting = this.settings.get(id);
        String s = I18n.format(setting.getName(), new Object[0]) + ": ";
        if(setting instanceof SettingBoolean) {
            return s + (setting.getBoolValue() ? I18n.format("options.on", new Object[0]) : I18n.format("options.off", new Object[0]));
        } else if(setting instanceof SettingString || setting instanceof SettingHudType) {
            return s + I18n.format(setting.getStringValue(), new Object[0]);
        } else if(setting instanceof SettingColor) {
            return s + intToHexString(setting.getIntValue());
        } else if(setting instanceof SettingInteger) {
            return s + setting.getIntValue();
        } else if(setting instanceof SettingFloat) {
            SettingFloat sf = (SettingFloat) setting;
            return s + String.valueOf(SettingFloat.snapToStepClamp(sf, sf.getFloatValue()));
        } else if(setting instanceof SettingPosition) {
            return s;
        } else {
            return s + "error";
        }
    }

    public static String intToHexString(int hex) {
        String s = Integer.toHexString(hex).toUpperCase();
        if(hex <= 0xFFFFF) {
            s = "0" + s;
            if(hex <= 0xFFFF) {
                s = "0" + s;
                if(hex <= 0xFFF) {
                    s = "0" + s;
                    if(hex <= 0xFF) {
                        s = "0" + s;
                        if(hex <= 0xF) {
                            s = "0" + s;
                        }
                    }
                }
            }
        }
        return "#" + s;
    }

    public void saveSettings() {
        this.config.save();
    }

    public List<String> getSettingsOf(HudElementType type) {
        List<String> settings = new ArrayList<String>();
        for(String key : this.settings.keySet()) {
            if(this.settings.get(key).associatedType == type)
                settings.add(key);
        }
        return settings;
    }

    public List<String> getSettingsOf(String type) {
        List<String> settings = new ArrayList<String>();
        for(String key : this.settings.keySet()) {
            if(this.settings.get(key).associatedType != null && this.settings.get(key).associatedType.name() == type)
                settings.add(key);
            else if(type == "general" && this.settings.get(key).associatedType == null)
                settings.add(key);
        }
        return settings;
    }
}
