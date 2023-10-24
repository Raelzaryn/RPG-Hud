package net.spellcraftgaming.rpghud.client.gui.hud;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.resource.language.I18n;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElement;

public abstract class Hud {
	
	/** Hud key for registering */
	private final String hudKey;
	
	public Map<OverlayElement.Type, OverlayElement> elements;
	
	public Hud(String hudKey) {
		this.hudKey = hudKey;
		initialize();
	}
	
	/** initialize the overlay elements */
	public void initialize() {
		elements = new HashMap<OverlayElement.Type, OverlayElement>();
	}
	
	/** get the key (String) of this HUD */
	public String getHudKey() {
		return this.hudKey;
	}

	/** get the name of this HUD */
	public String getHudName() {
		return I18n.translate("name." + this.hudKey);
	}
	
	public void renderElement(OverlayElement.Type type, DrawContext dc, float tickDelta) {
		elements.get(type).render(dc, tickDelta);
	}
}
