package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementJumpBarTexture extends HudElement {

	public HudElementJumpBarTexture() {
		super(HudElementType.JUMP_BAR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return GameData.isRidingLivingMount() && (this.settings.getBoolValue(Settings.limit_jump_bar) ? GameData.getHorseJumpPower() > 0F : true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks, double scale) {
		bind(INTERFACE);
		ScaledResolution res = new ScaledResolution(this.mc);
		int height = res.getScaledHeight();
		int adjustedWidth = res.getScaledWidth() / 2;
		float var14 = GameData.getHorseJumpPower();
		int color = (int) (var14 * 100.0F);
		GlStateManager.color(1f, 1f, 1f);
		gui.drawTexturedModalRect(adjustedWidth - 71, height - 80, 0, 160, 141, 10);
		gui.drawTexturedModalRect(adjustedWidth - 71, height - 80, 0, 150, (int) (141.0D * (color / 100.0D)), 10);
		GlStateManager.color(1f, 1f, 1f);
		GameData.bindIcons();
	}

}
