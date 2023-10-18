package net.spellcraftgaming.rpghud.gui.hud.element;

import net.minecraft.client.gui.GuiGraphics;

public class HudElementEmpty extends HudElement {

	public HudElementEmpty() {
		super(HudElementType.DEBUG, 0, 0, 0, 0, false);
	}

	@Override
	public void drawElement(GuiGraphics gg, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
	}

}
