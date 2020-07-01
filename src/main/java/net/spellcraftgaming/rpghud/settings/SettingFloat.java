package net.spellcraftgaming.rpghud.settings;

import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

@OnlyIn(Dist.CLIENT)
public class SettingFloat extends Setting {

	public final float defaultValue;
	public float value;
	public final float minValue;
	public final float maxValue;
	public final float step;

	public SettingFloat(String ID, float defaultValue, float minValue, float maxValue, float step) {
		super(ID);
		this.defaultValue = defaultValue;
		this.value = defaultValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.step = step;
	}
	
	public SettingFloat(String ID, HudElementType type, float defaultValue, float minValue, float maxValue, float step) {
		super(ID, type);
		this.defaultValue = defaultValue;
		this.value = defaultValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.step = step;
	}

	@Override
	public void increment() {
		if (this.value < this.maxValue)
			this.value += this.step;
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
		if (o instanceof Float) {
			this.value = (Float) o;
		}
		return this;
	}

	@Override
	public Object getDefaultValue() {
		return this.defaultValue;
	}
	
	public static float normalizeValue(SettingFloat setting, float value) {
		return MathHelper.clamp((snapToStepClamp(setting, value) - setting.minValue) / (setting.maxValue - setting.minValue), 0.0F, 1.0F);
	}

	public static float denormalizeValue(SettingFloat setting, float value) {
		return snapToStepClamp(setting, setting.minValue + (setting.maxValue - setting.minValue) * MathHelper.clamp(value, 0.0F, 1.0F));
	}

	public static float snapToStepClamp(SettingFloat setting, float value) {
		value = snapToStep(setting, value);
		return MathHelper.clamp(value, setting.minValue, setting.maxValue);
	}

	public static float snapToStep(SettingFloat setting, float value) {
		if (setting.step > 0.0F) {
			value = setting.step * Math.round(value / setting.step);
		}

		return value;
	}
}
