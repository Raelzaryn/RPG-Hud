package net.spellcraftgaming.rpghud.client.gui.hud;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.resource.language.I18n;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElement;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElementType;

public abstract class Hud {
	
	/** Hud key for registering */
	private final String hudKey;
	
	public Map<OverlayElementType, OverlayElement> elements;
	
	public Hud(String hudKey) {
		this.hudKey = hudKey;
		initialize();
	}
	
	/** initialize the overlay elements */
	public void initialize() {
		elements = new HashMap<OverlayElementType, OverlayElement>();
	}
	
	/** get the key (String) of this HUD */
	public String getHudKey() {
		return this.hudKey;
	}

	/** get the name of this HUD */
	public String getHudName() {
		return I18n.translate("name." + this.hudKey);
	}
	
	public void renderElement(OverlayElementType type, DrawContext dc, float tickDelta) {
		OverlayElement element = elements.get(type);
		element.initialize();
		if(element.shouldRender()) {
			boolean isGuiNotScale = !element.isGuiScale;
			if(isGuiNotScale) {
				float scale = element.getScale();
				dc.getMatrices().scale(scale, scale, scale);
			}

			element.render(dc, tickDelta);
			
			if(isGuiNotScale) {
				float invertedScale = element.getInvertedScale();
				dc.getMatrices().scale(invertedScale, invertedScale, invertedScale);
			}
		}

	}
}
