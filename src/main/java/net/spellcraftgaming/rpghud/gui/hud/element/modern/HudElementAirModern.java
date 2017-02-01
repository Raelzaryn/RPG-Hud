package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.minecraft.block.material.Material;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementAirModern extends HudElementBarred {

	public HudElementAirModern() {
		super(HudElementType.AIR, 0, 0, 0, 0, true);
	}


	@Override
	public boolean checkConditions() {
		return this.mc.player.isInsideOfMaterial(Material.WATER) && this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int width = res.getScaledWidth();
		int height = res.getScaledHeight();
		int[] airColor = this.getColor(this.settings.color_air);
		int airAmount = this.mc.player.getAir();
		GlStateManager.disableLighting();
		drawRect(width / 2 - 72, height - 78, 144, 2, 0xA0000000);
		drawRect(width / 2 - 72, height - 70, 144, 2, 0xA0000000);
		drawRect(width / 2 - 72, height - 76, 2, 6, 0xA0000000);
		drawRect(width / 2 + 70, height - 76, 2, 6, 0xA0000000);
		drawRect(width / 2 - 70, height - 76, 140, 6, 0x20FFFFFF);
		drawRect(width / 2 - 70, height - 76, (int) (140 * (airAmount / 300.0D)), 6, airColor[0]);
	}
}
