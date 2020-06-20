package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.gui.Gui;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.gui.override.GuiIngameRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementArmorVanilla extends HudElement {

	public HudElementArmorVanilla() {
		super(HudElementType.ARMOR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.gameSettings.hideGUI && this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int left = scaledWidth / 2 - 91 + this.settings.getPositionValue(Settings.armor_position)[0];
		int top = scaledHeight - GuiIngameRPGHud.left_height + this.settings.getPositionValue(Settings.armor_position)[1];

		int level = this.mc.player.getTotalArmorValue();
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
