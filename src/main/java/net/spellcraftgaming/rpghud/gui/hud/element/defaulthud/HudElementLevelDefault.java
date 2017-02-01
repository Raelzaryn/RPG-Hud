package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementLevelDefault extends HudElement {

	public HudElementLevelDefault() {
		super(HudElementType.LEVEL, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		GlStateManager.disableBlend();
		String level = String.valueOf(this.mc.thePlayer.experienceLevel);
		this.mc.fontRendererObj.drawStringWithShadow(level, 38 - this.mc.fontRendererObj.getStringWidth(level) / 2, 38, 0x80FF20);
		GlStateManager.enableBlend();
	}

}
