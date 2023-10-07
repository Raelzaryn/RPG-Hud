package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementDetailsVanilla;

@Environment(value=EnvType.CLIENT)
public class HudElementDetailsExtended extends HudElementDetailsVanilla {

	public HudElementDetailsExtended() {
		super();
		this.typeOffset = 10;
	}
}
