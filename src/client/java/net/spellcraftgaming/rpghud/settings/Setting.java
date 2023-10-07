package net.spellcraftgaming.rpghud.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.I18n;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

@Environment(value=EnvType.CLIENT)
public abstract class Setting {

    public final HudElementType associatedType;
    public final String ID;

    public Setting(String ID) {
        this.ID = ID;
        this.associatedType = null;
    }

    public Setting(String ID, HudElementType type) {
        this.ID = ID;
        this.associatedType = type;
    }

    public abstract void increment();

    public abstract Object getValue();

    public Integer getIntValue() {
        if(getValue() instanceof Integer)
            return (Integer) getValue();
        return null;
    }

    public Boolean getBoolValue() {
        if(getValue() instanceof Boolean)
            return (Boolean) getValue();
        return null;
    }

    public Float getFloatValue() {
        if(getValue() instanceof Float)
            return (Float) getValue();
        return null;
    }

    public Double getDoubleValue() {
        if(getValue() instanceof Double)
            return (Double) getValue();
        return null;
    }

    public String getStringValue() {
        if(getValue() instanceof String)
            return (String) getValue();
        return null;
    }

    public abstract Object getDefaultValue();

    public abstract void resetValue();

    public abstract Setting setValue(Object o);

    public String getName() {
        return I18n.translate("name." + this.ID, new Object[0]);
    }

    public String getTooltip() {
        return I18n.translate("tooltip." + this.ID, new Object[0]);
    }

    public String getFormatedTooltip() {
        return I18n.translate("tooltip." + this.ID, new Object[0]).replaceAll("/n", " ");
    }

}
