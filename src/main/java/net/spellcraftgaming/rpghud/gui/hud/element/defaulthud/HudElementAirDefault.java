package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.block.material.Material;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementAirDefault extends HudElementBarred {

	public HudElementAirDefault() {
		super(HudElementType.AIR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.getRenderViewEntity().isInsideOfMaterial(Material.water) && this.mc.playerController.shouldDrawHUD();
	}
	
	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int width = res.getScaledWidth();
		int height = res.getScaledHeight();
		int adjustedWidth = width / 2 - 91;
		int airAmount = this.mc.thePlayer.getAir();
		GlStateManager.disableLighting();
		drawCustomBar(adjustedWidth + 21, height - 80, 141, 10, airAmount / 300.0D * 100.0D, this.settings.color_air, offsetColorPercent(this.settings.color_air, OFFSET_PERCENT));
	}

}
