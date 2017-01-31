package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementTexture;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementExperienceTexture extends HudElementTexture {

	public HudElementExperienceTexture() {
		super(HudElementType.EXPERIENCE, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.gameSettings.showDebugInfo && this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		bind(INTERFACE);
		int exp = (int) (this.mc.player.xpBarCap() * this.mc.player.experience);
		gui.drawTexturedModalRect(49, 35, 0, 132, (int) (88.0D * (exp / (double) this.mc.player.xpBarCap())), 8);
		String stringExp = exp + "/" + this.mc.player.xpBarCap();
		if (this.settings.show_numbers_experience) {
			GlStateManager.scale(0.5D, 0.5D, 0.5D);
			gui.drawCenteredString(this.mc.fontRendererObj, stringExp, 180, 74, -1);
			GlStateManager.scale(2.0D, 2.0D, 2.0D);
		}
		bind(Gui.ICONS);
	}

}
