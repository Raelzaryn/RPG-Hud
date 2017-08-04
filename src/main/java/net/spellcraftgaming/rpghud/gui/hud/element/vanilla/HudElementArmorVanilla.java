package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.lib.gui.override.GuiIngameRPGHud;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementArmorVanilla extends HudElement {

	public HudElementArmorVanilla() {
		super(HudElementType.ARMOR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return GameData.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int width = res.getScaledWidth();
		int height = res.getScaledHeight();
		int left = width / 2 - 91;
		int top = height - GuiIngameRPGHud.left_height;

		int level = GameData.getPlayerArmor();
		for (int i = 1; level > 0 && i < 20; i += 2) {
			if (i < level) {
				gui.drawTexturedModalRect(left, top, 34, 9, 9, 9);
			} else if (i == level) {
				gui.drawTexturedModalRect(left, top, 25, 9, 9, 9);
			} else if (i > level) {
				gui.drawTexturedModalRect(left, top, 16, 9, 9, 9);
			}
			left += 8;
		}
		GuiIngameRPGHud.left_height += 10;
	}

}
