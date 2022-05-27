package net.spellcraftgaming.rpghud.settings;


import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class SettingColor extends Setting{

	public final int defaultColor;
	public int color;
	
	public SettingColor(String ID, int color) {
		super(ID);
		this.defaultColor = color;
		this.color = color;
	}
	
	public SettingColor(String ID, HudElementType type, int color) {
		super(ID, type);
		this.defaultColor = color;
		this.color = color;
	}

	@Override
	public void increment() {
	}
	
	public void setColor(int color){
		this.color = color;
	}

	@Override
	public Object getValue() {
		return this.color;
	}

	@Override
	public void resetValue() {
		this.color = this.defaultColor;
	}

	@Override
	public Setting setValue(Object o) {
		if (o instanceof String) {
			this.color = Integer.parseInt((String) o, 16);
		} else if(o instanceof Integer) {
			this.color = (Integer) o;
		}
		return this;
	}

	@Override
	public Object getDefaultValue() {
		return this.defaultColor;
	}
}
