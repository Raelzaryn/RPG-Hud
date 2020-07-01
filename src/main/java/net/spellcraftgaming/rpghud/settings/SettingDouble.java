package net.spellcraftgaming.rpghud.settings;

import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

@OnlyIn(Dist.CLIENT)
public class SettingDouble extends Setting {

	public final double defaultValue;
	public double value;
	public final double minValue;
	public final double maxValue;
	public final double step;

	public SettingDouble(String ID, double defaultValue, double minValue, double maxValue, double step) {
		super(ID);
		this.defaultValue = defaultValue;
		this.value = defaultValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.step = step;
	}
	
	public SettingDouble(String ID, HudElementType type, double defaultValue, double minValue, double maxValue, double step) {
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
		if (o instanceof Double) {
			this.value = (Double) o;
		}
		return this;
	}

	@Override
	public Object getDefaultValue() {
		return this.defaultValue;
	}
	
	public static double normalizeValue(SettingDouble setting, double value) {
		return MathHelper.clamp((snapToStepClamp(setting, value) - setting.minValue) / (setting.maxValue - setting.minValue), 0.0F, 1.0F);
	}

	public static double denormalizeValue(SettingDouble setting, double value) {
		return snapToStepClamp(setting, setting.minValue + (setting.maxValue - setting.minValue) * MathHelper.clamp(value, 0.0F, 1.0F));
	}

	public static double snapToStepClamp(SettingDouble setting, double value) {
		value = snapToStep(setting, value);
		return MathHelper.clamp(value, setting.minValue, setting.maxValue);
	}

	public static double snapToStep(SettingDouble setting, double value) {
		if (setting.step > 0.0F) {
			value = setting.step * Math.round(value / setting.step);
		}

		return value;
	}
}
