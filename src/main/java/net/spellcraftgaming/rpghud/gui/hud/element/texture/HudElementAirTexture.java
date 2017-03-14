package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementTexture;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementAirTexture extends HudElementTexture {

	public HudElementAirTexture() {
		super(HudElementType.AIR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return GameData.isPlayerUnderwater();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		bind(INTERFACE);
		GlStateManager.color(1f, 1f, 1f);
		ScaledResolution res = new ScaledResolution(this.mc);
		int height = res.getScaledHeight();
		int adjustedWidth = res.getScaledWidth() / 2;
		int airAmount = GameData.getPlayerAir();
		gui.drawTexturedModalRect(adjustedWidth - 70, height - 80, 0, 160, 141, 10);
		gui.drawTexturedModalRect(adjustedWidth - 70, height - 80, 0, 140, (int) (141.0D * (airAmount / 300.0D)), 10);
		GlStateManager.color(1f, 1f, 1f);
		GameData.bindIcons();
	}

}
