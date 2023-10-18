package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.minecraft.client.gui.GuiGraphics;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementArmorHotbar extends HudElement {

	public HudElementArmorHotbar() {
		super(HudElementType.ARMOR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.options.hideGui;
	}

	@Override
	public void drawElement(GuiGraphics gg, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int left = (this.settings.getBoolValue(Settings.render_player_face) ? 46 : 22) + this.settings.getPositionValue(Settings.armor_position)[0];
		int top = scaledHeight - 64 + this.settings.getPositionValue(Settings.armor_position)[1];
		int level = this.mc.player.getArmorValue();
		for (int i = 1; level > 0 && i < 20; i += 2) {
			if (i < level) {
				gg.blit(ICONS,left + 62, top - 2, 34, 9, 9, 9);
			} else if (i == level) {
				gg.blit(ICONS,left + 62, top - 2, 25, 9, 9, 9);
			} else if (i > level) {
				gg.blit(ICONS,left + 62, top - 2, 16, 9, 9, 9);
			}
			left += 8;
		}
	}

}
