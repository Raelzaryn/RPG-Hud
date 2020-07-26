package net.spellcraftgaming.rpghud.settings;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

@SideOnly(Side.CLIENT)
public class SettingString extends Setting {

	public final int defaultValueId;
	public int valueId;
	public final String[] possibleValues;

	public SettingString(String ID, int defaultValueId, String[] possibleValues) {
		super(ID);
		this.possibleValues = possibleValues;
		this.defaultValueId = defaultValueId;
		this.valueId = defaultValueId;
	}
	
	public SettingString(String ID, HudElementType type, int defaultValueId, String[] possibleValues) {
		super(ID, type);
		this.possibleValues = possibleValues;
		this.defaultValueId = defaultValueId;
		this.valueId = defaultValueId;
	}

	@Override
	public void increment() {
		if (this.valueId < (this.possibleValues.length - 1)) {
			this.valueId++;
		} else {
			this.valueId = 0;
		}
	}

	@Override
	public Object getValue() {
		return this.possibleValues[this.valueId];
	}

	@Override
	public void resetValue() {
		this.valueId = this.defaultValueId;
	}

	@Override
	public Setting setValue(Object o) {
		if (o instanceof String) {
			boolean set = false;
			for(int i = 0; i < this.possibleValues.length; i++)
			if(((String) o).equals(this.possibleValues[i])){
				this.valueId = i;
				set = true;
			}
			if(!set) this.valueId = this.defaultValueId;

		}
		return this;
	}
	
	@Override
	public Object getDefaultValue() {
		return this.possibleValues[this.defaultValueId];
	}
}
