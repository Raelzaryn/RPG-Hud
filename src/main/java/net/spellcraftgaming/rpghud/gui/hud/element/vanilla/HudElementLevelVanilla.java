package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementLevelVanilla extends HudElement {

	public HudElementLevelVanilla() {
		super(HudElementType.LEVEL, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD() && mc.player.experienceLevel > 0;
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		GlStateManager.disableBlend();
        String s = "" + this.mc.player.experienceLevel;
        int i1 = (scaledWidth - mc.fontRenderer.getStringWidth(s)) / 2;
        int j1 = scaledHeight - 31 - 4;
        mc.fontRenderer.drawString(s, (float)(i1 + 1), (float)j1, 0);
        mc.fontRenderer.drawString(s, (float)(i1 - 1), (float)j1, 0);
        mc.fontRenderer.drawString(s, (float)i1, (float)(j1 + 1), 0);
        mc.fontRenderer.drawString(s, (float)i1, (float)(j1 - 1), 0);
        mc.fontRenderer.drawString(s, (float)i1, (float)j1, 8453920);
		GlStateManager.enableBlend();
	}

}
