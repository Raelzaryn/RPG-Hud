package net.spellcraftgaming.rpghud.gui.hud.element;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class HudElementEmpty extends HudElement {

	public HudElementEmpty() {
		super(HudElementType.VOID, 0, 0, 0, 0, false);
	}

	@Override
	public void drawElement(AbstractParentElement gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
	}

}
