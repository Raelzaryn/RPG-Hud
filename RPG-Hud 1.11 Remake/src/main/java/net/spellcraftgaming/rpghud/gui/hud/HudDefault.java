package net.spellcraftgaming.rpghud.gui.hud;

import net.minecraft.client.Minecraft;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.defaulthud.HudElementAirDefault;
import net.spellcraftgaming.rpghud.gui.hud.element.defaulthud.HudElementArmorDefault;

public class HudDefault extends HudVanilla{

	public HudDefault(Minecraft mc, String hudKey, String hudName) {
		super(mc, hudKey, hudName);
	}

	@Override
	public HudElement setElementAir() {
		return new HudElementAirDefault();
	}
	
	@Override
	public HudElement setElementArmor() {
		return new HudElementArmorDefault();
	}
}
