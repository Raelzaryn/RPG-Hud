package net.spellcraftgaming.rpghud.gui.hud.element;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Gui;

public class HudElementEmpty extends HudElement {

	public HudElementEmpty() {
		super(HudElementType.DEBUG, 0, 0, 0, 0, false);
	}

	@Override
	public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
	}

}
