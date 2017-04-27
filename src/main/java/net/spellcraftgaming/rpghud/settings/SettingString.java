package net.spellcraftgaming.rpghud.settings;

import java.util.List;

import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class SettingString extends Setting {

	public final int defaultValueId;
	public int valueId;
	public final List<String> possibleValues;

	public SettingString(String ID, int defaultValueId, List<String> possibleValues) {
		super(ID);
		this.possibleValues = possibleValues;
		this.defaultValueId = defaultValueId;
		this.valueId = defaultValueId;
	}
	
	public SettingString(String ID, HudElementType type, int defaultValueId, List<String> possibleValues) {
		super(ID, type);
		this.possibleValues = possibleValues;
		this.defaultValueId = defaultValueId;
		this.valueId = defaultValueId;
	}

	@Override
	public void increment() {
		if (this.valueId < (this.possibleValues.size() - 1)) {
			this.valueId++;
		} else {
			this.valueId = 0;
		}
	}

	@Override
	public Object getValue() {
		return this.possibleValues.get(this.valueId);
	}

	@Override
	public void resetValue() {
		this.valueId = this.defaultValueId;
	}

	@Override
	public void setValue(Object o) {
		if (o instanceof String) {
			int id = this.possibleValues.indexOf(o);
			this.valueId = id != 0 ? id : 0;
		}
	}
	
	@Override
	public Object getDefaultValue() {
		return this.possibleValues.get(this.defaultValueId);
	}
}
