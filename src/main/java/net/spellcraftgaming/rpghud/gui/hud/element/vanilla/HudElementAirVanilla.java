package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.block.material.Material;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.GuiIngameRPGHud;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementAirVanilla extends HudElement {

	public HudElementAirVanilla() {
		super(HudElementType.AIR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.getRenderViewEntity().isInsideOfMaterial(Material.WATER) && this.mc.playerController.shouldDrawHUD();
	}
	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int width = res.getScaledWidth();
		int height = res.getScaledHeight();
		EntityPlayer player = (EntityPlayer) this.mc.getRenderViewEntity();
		GlStateManager.enableBlend();
		int left = width / 2 + 91;
		int top = height - GuiIngameRPGHud.right_height;

		int air = player.getAir();
		int full = MathHelper.ceil((air - 2) * 10.0D / 300.0D);
		int partial = MathHelper.ceil(air * 10.0D / 300.0D) - full;

		for (int i = 0; i < full + partial; ++i) {
			gui.drawTexturedModalRect(left - i * 8 - 9, top, (i < full ? 16 : 25), 18, 9, 9);
		}
		GuiIngameRPGHud.right_height += 10;
	}

}
