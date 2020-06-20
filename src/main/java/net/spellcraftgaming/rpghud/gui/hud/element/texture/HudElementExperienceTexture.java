package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementExperienceTexture extends HudElement {

	public HudElementExperienceTexture() {
		super(HudElementType.EXPERIENCE, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		bind(INTERFACE);
		GlStateManager.color3f(1f, 1f, 1f);
		int exp = MathHelper.ceil(this.mc.player.xpBarCap() * this.mc.player.experience);
		int expCap = this.mc.player.xpBarCap();
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.experience_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 35 : 31) + this.settings.getPositionValue(Settings.experience_position)[1];
	
		gui.drawTexturedModalRect(posX, posY, 0, 132, (int) (88.0D * (exp / (double) expCap)), 8);

		String stringExp =  this.settings.getBoolValue(Settings.experience_percentage) ? (int) Math.floor((double) exp / (double) expCap * 100) + "%" : exp + "/" + expCap;
	
		if (this.settings.getBoolValue(Settings.show_numbers_experience)) {
			GlStateManager.scaled(0.5D, 0.5D, 0.5D);
			gui.drawCenteredString(this.mc.fontRenderer, stringExp, posX * 2 + 88, posY * 2 + 4, -1);
			GlStateManager.scaled(2.0D, 2.0D, 2.0D);
		}
		GlStateManager.color3f(1f, 1f, 1f);
		this.mc.getTextureManager().bindTexture(Gui.ICONS);
	}

}
