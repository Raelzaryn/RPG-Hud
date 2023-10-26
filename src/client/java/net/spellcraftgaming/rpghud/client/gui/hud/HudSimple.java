package net.spellcraftgaming.rpghud.client.gui.hud;

import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElementType;
import net.spellcraftgaming.rpghud.client.gui.overlay.simple.OverlayElementSimpleArmor;
import net.spellcraftgaming.rpghud.client.gui.overlay.simple.OverlayElementSimpleExperience;
import net.spellcraftgaming.rpghud.client.gui.overlay.simple.OverlayElementSimpleHealth;
import net.spellcraftgaming.rpghud.client.gui.overlay.simple.OverlayElementSimpleHotbar;
import net.spellcraftgaming.rpghud.client.gui.overlay.simple.OverlayElementSimpleHotbarAttackIndicator;
import net.spellcraftgaming.rpghud.client.gui.overlay.simple.OverlayElementSimpleHotbarOffhand;
import net.spellcraftgaming.rpghud.client.gui.overlay.simple.OverlayElementSimpleHunger;
import net.spellcraftgaming.rpghud.client.gui.overlay.simple.OverlayElementSimpleLevel;

public class HudSimple extends Hud {

	public HudSimple(String hudKey) {
		super(hudKey);
	}

	@Override
	public void initialize() {
		super.initialize();
		this.elements.put(OverlayElementType.HOTBAR, new OverlayElementSimpleHotbar());
		this.elements.put(OverlayElementType.HOTBAR_OFFHAND, new OverlayElementSimpleHotbarOffhand());
		this.elements.put(OverlayElementType.ATTACK_INDICATOR, new OverlayElementSimpleHotbarAttackIndicator());
		this.elements.put(OverlayElementType.EXPERIENCE, new OverlayElementSimpleExperience());
		this.elements.put(OverlayElementType.HEALTH, new OverlayElementSimpleHealth());
		this.elements.put(OverlayElementType.HUNGER, new OverlayElementSimpleHunger());
		this.elements.put(OverlayElementType.ARMOR, new OverlayElementSimpleArmor());
		this.elements.put(OverlayElementType.LEVEL, new OverlayElementSimpleLevel());
	}
}
