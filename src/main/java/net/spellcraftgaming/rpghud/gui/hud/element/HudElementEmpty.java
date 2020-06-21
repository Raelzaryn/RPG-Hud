package net.spellcraftgaming.rpghud.gui.hud.element;

import net.minecraft.client.gui.AbstractGui;

public class HudElementEmpty extends HudElement {

	public HudElementEmpty() {
		super(HudElementType.VOID, 0, 0, 0, 0, false);
	}

	@Override
	public void drawElement(AbstractGui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
	}

}
