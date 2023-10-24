package net.spellcraftgaming.rpghud.client.gui.hud;

import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElement.Type;
import net.spellcraftgaming.rpghud.client.gui.overlay.simple.OverlayElementSimpleHotbar;

public class HudSimple extends Hud {

	public HudSimple(String hudKey) {
		super(hudKey);
	}

	@Override
	public void initialize() {
		super.initialize();
		this.elements.put(Type.HOTBAR, new OverlayElementSimpleHotbar());
	}
}
