package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(EnvType.CLIENT)
public class HudElementArmorDefault extends HudElement {

	public HudElementArmorDefault() {
		super(HudElementType.ARMOR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawableHelper gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int left = scaledWidth / 2 - 91 + this.settings.getPositionValue(Settings.armor_position)[0];
		int top = scaledHeight - 39 - this.settings.getPositionValue(Settings.armor_position)[1];
		int level = this.mc.player.getArmor();
		for (int i = 1; level > 0 && i < 20; i += 2) {
			if (i < level) {
				gui.drawTexture(ms, left + 48, top - 2, 34, 9, 9, 9);
			} else if (i == level) {
				gui.drawTexture(ms, left + 48, top - 2, 25, 9, 9, 9);
			} else if (i > level) {
				gui.drawTexture(ms, left + 48, top - 2, 16, 9, 9, 9);
			}
			left += 8;
		}
	}

}
