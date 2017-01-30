package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.block.material.Material;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementAirDefault extends HudElementBarred{

	public HudElementAirDefault() {
		super(HudElementType.AIR, 0, 0, 0, 0, true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(mc);
		int width = res.getScaledWidth();
		int height = res.getScaledHeight();
		int adjustedWidth = width / 2 - 91;
		int[] airColor = this.getColor(this.settings.color_air);
		int airAmount = this.mc.player.getAir();
		GlStateManager.disableLighting();
		drawCustomBar(adjustedWidth + 21, height - 80, 141, 10, airAmount / 300.0D * 100.0D, airColor[0], airColor[1]);
	}
	
	@Override
	public boolean checkConditions() {
		return this.mc.player.isInsideOfMaterial(Material.WATER);
	}

}
