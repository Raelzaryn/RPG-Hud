package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.minecraft.client.gui.Gui;
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
		int experience = (int) (this.mc.player.xpBarCap() * this.mc.player.experience);
		String levelString = String.valueOf(this.mc.player.experienceLevel);
		gui.drawTexturedModalRect(49, 35, 0, 132, (int) (88.0D * (experience / (double) this.mc.player.xpBarCap())), 8);
		
		if (this.settings.show_numbers_experience) {
			this.mc.fontRendererObj.drawStringWithShadow(levelString, 38 - this.mc.fontRendererObj.getStringWidth(levelString) / 2, 38, 0x80FF20);
		}
		bind(Gui.ICONS);
	}

}
