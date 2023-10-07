package net.spellcraftgaming.rpghud.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

@Environment(value=EnvType.CLIENT)
public class SettingPosition extends Setting{

	public final int defaultX, defaultY;
	public int x, y;

	public SettingPosition(String ID, int x, int y) {
		super(ID);
		this.defaultX = x;
		this.x = x;
		this.defaultY = y;
		this.y = y;
	}

	public SettingPosition(String ID, HudElementType type, int x, int y) {
		super(ID, type);
		this.defaultX = x;
		this.x = x;
		this.defaultY = y;
		this.y = y;
	}

	@Override
	public void increment() {
	}

	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}

	@Override
	public Object getValue() {
		return x + "_" + y;
	}

	@Override
	public void resetValue() {
		this.x = this.defaultX;
		this.y = this.defaultY;
	}

	@Override
	public Setting setValue(Object o) {
		String[] positions = ((String) o).split("_");
		if(positions.length > 1 && !positions[0].equals("") && !positions[1].equals("")) {
			try {
				this.x = Integer.parseInt(positions[0]);
				this.y = Integer.parseInt(positions[1]);
			}
			catch(NumberFormatException e)
			{
				this.x = 0;
				this.y = 0;
			}
		}
		return this;
	}

	@Override
	public Object getDefaultValue() {
		return this.defaultX + "_" + this.defaultY;
	}
}
