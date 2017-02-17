package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementLevelHotbar extends HudElement {

	public HudElementLevelHotbar() {
		super(HudElementType.LEVEL, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int height = res.getScaledHeight();
		GlStateManager.disableBlend();
		String level = String.valueOf(this.mc.thePlayer.experienceLevel);
		this.mc.fontRendererObj.drawStringWithShadow(level, (this.settings.render_player_face ? 25 : 13)- this.mc.fontRendererObj.getStringWidth(level) / 2, height - (this.settings.render_player_face ? 22 : 40), 0x80FF20);
		GlStateManager.enableBlend();
	}

}
