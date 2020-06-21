package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.gui.AbstractGui;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
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
	public void drawElement(AbstractGui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int left = scaledWidth / 2 - 91 + this.settings.getPositionValue(Settings.armor_position)[0];
		int top = scaledHeight - ForgeIngameGui.left_height + this.settings.getPositionValue(Settings.armor_position)[1];

		int level = this.mc.player.getTotalArmorValue();
		for (int i = 1; level > 0 && i < 20; i += 2) {
			if (i < level) {
				gui.blit(left, top, 34, 9, 9, 9);
			} else if (i == level) {
				gui.blit(left, top, 25, 9, 9, 9);
			} else if (i > level) {
				gui.blit(left, top, 16, 9, 9, 9);
			}
			left += 8;
		}
		ForgeIngameGui.left_height += 10;
	}

}
