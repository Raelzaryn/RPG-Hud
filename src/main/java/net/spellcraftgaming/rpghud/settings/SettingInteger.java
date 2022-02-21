package net.spellcraftgaming.rpghud.settings;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

@SideOnly(Side.CLIENT)
public class SettingInteger extends Setting {

    public final int defaultValue;
    public int value;
    public final int minValue;
    public final int maxValue;

    public SettingInteger(String ID, int defaultValue, int minValue, int maxValue) {
        super(ID);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public SettingInteger(String ID, HudElementType type, int defaultValue, int minValue, int maxValue) {
        super(ID, type);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public void increment() {
        if (this.value < this.maxValue)
            this.value++;
        else
            this.value = this.minValue;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public void resetValue() {
        this.value = this.defaultValue;
    }

    @Override
    public Setting setValue(Object o) {
        if (o instanceof Integer) {
            this.value = (Integer) o;
        }
        return this;
    }

    @Override
    public Object getDefaultValue() {
        return this.defaultValue;
    }


}
