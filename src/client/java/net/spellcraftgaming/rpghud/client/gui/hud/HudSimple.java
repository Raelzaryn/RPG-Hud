package net.spellcraftgaming.rpghud.client.gui.hud;

import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElement.Type;
import net.spellcraftgaming.rpghud.client.gui.overlay.simple.OverlayElementSimpleHotbar;
import net.spellcraftgaming.rpghud.client.gui.overlay.simple.OverlayElementSimpleHotbarAttackIndicator;
import net.spellcraftgaming.rpghud.client.gui.overlay.simple.OverlayElementSimpleHotbarOffhand;

public class HudSimple extends Hud {

	public HudSimple(String hudKey) {
		super(hudKey);
	}

	@Override
	public void initialize() {
		super.initialize();
		this.elements.put(Type.HOTBAR, new OverlayElementSimpleHotbar());
		this.elements.put(Type.HOTBAR_OFFHAND, new OverlayElementSimpleHotbarOffhand());
		this.elements.put(Type.ATTACK_INDICATOR, new OverlayElementSimpleHotbarAttackIndicator());
	}
}
