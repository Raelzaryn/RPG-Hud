package net.spellcraftgaming.rpghud.settings;

import net.minecraft.client.resources.I18n;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class SettingBooleanDebug extends SettingBoolean {

    public static final String force_render = "force_render";
    public static final String render_vanilla = "render_vanilla";
    public static final String prevent_event = "prevent_event";
    public static final String prevent_element_render = "prevent_element_render";

    public SettingBooleanDebug(String ID, HudElementType type, boolean defaultValue) {
        super(ID, type, defaultValue);
    }

    @Override
    public String getName() {
        if(this.ID.contains(prevent_event))
            return I18n.format("name." + prevent_event, new Object[0]);
        else if(this.ID.contains(prevent_element_render))
            return I18n.format("name." + prevent_element_render, new Object[0]);
        else if(this.ID.contains(render_vanilla))
            return I18n.format("name." + render_vanilla, new Object[0]);
        else if(this.ID.contains(force_render))
            return I18n.format("name." + force_render, new Object[0]);
        else
            return I18n.format("name." + this.ID + "error", new Object[0]);
    }

    @Override
    public String getTooltip() {
        if(this.ID.contains(prevent_event))
            return I18n.format("tooltip." + prevent_event, new Object[0]);
        else if(this.ID.contains(prevent_element_render))
            return I18n.format("tooltip." + prevent_element_render, new Object[0]);
        else if(this.ID.contains(render_vanilla))
            return I18n.format("tooltip." + render_vanilla, new Object[0]);
        else if(this.ID.contains(force_render))
            return I18n.format("tooltip." + force_render, new Object[0]);
        else
            return I18n.format("tooltip." + this.ID + "error", new Object[0]);
    }

    @Override
    public String getFormatedTooltip() {
        if(this.ID.contains(prevent_event))
            return I18n.format("tooltip." + prevent_event, new Object[0]).replaceAll("/n", " ");
        else if(this.ID.contains(prevent_element_render))
            return I18n.format("tooltip." + prevent_element_render, new Object[0]).replaceAll("/n", " ");
        else if(this.ID.contains(render_vanilla))
            return I18n.format("tooltip." + render_vanilla, new Object[0]).replaceAll("/n", " ");
        else if(this.ID.contains(force_render))
            return I18n.format("tooltip." + force_render, new Object[0]).replaceAll("/n", " ");
        else
            return I18n.format("tooltip." + this.ID + "error", new Object[0]).replaceAll("/n", " ");
    }
}
