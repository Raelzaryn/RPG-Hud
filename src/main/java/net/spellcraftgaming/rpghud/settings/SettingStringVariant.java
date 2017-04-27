package net.spellcraftgaming.rpghud.settings;

import java.util.List;

import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class SettingStringVariant extends Setting {

	public final int defaultValueId;
	public int valueId;
	public String value;
	public List<String> possibleValues;

	public SettingStringVariant(String ID, int defaultValueId, List<String> possibleValues) {
		super(ID);
		this.possibleValues = possibleValues;
		this.defaultValueId = defaultValueId;
		this.valueId = defaultValueId;
		this.value = possibleValues.get(this.valueId);
	}
	
	public SettingStringVariant(String ID, HudElementType type, int defaultValueId, List<String> possibleValues) {
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
		this.value = this.possibleValues.get(this.valueId);
	}

	@Override
	public Object getValue() {
		return this.possibleValues.get(this.valueId);
	}

	@Override
	public void resetValue() {
		this.valueId = this.defaultValueId;
	}
	
	public void updatePossibleValues(List<String> possibleValues){
		this.possibleValues = possibleValues;
		if(!possibleValues.get(this.valueId).contentEquals(this.value)){
			boolean finished = false;
			for(int i = 0; i < possibleValues.size(); i++){
				if(possibleValues.get(i).contentEquals(this.value)){
					this.valueId = i;
					finished = true;
					i = possibleValues.size();
				}
			}
			if(!finished){
				if(this.defaultValueId < this.possibleValues.size()){
					this.valueId = this.defaultValueId;
					this.value = possibleValues.get(this.valueId);
				}
				else {
					this.valueId = 0;
					this.value = possibleValues.get(this.valueId);
				}
				
			}
		}
	}

	@Override
	public void setValue(Object o) {
		if (o instanceof String) {
			this.value = (String) o;
		}
	}
	
	@Override
	public Object getDefaultValue() {
		return this.possibleValues.get(this.defaultValueId);
	}
}
