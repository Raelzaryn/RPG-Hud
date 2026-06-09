package net.spellcraftgaming.rpghud.gui.hud.element;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class HudElementEmpty extends HudElement {

	public HudElementEmpty() {
		super(HudElementType.DEBUG, 0, 0, 0, 0, false);
	}

	@Override
	public void drawElement(GuiGraphicsExtractor gg, float zLevel, DeltaTracker partialTicks, int scaledWidth, int scaledHeight) {
	}

}
