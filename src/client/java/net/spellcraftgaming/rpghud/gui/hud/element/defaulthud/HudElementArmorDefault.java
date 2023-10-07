package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementArmorDefault extends HudElement {

	public HudElementArmorDefault() {
		super(HudElementType.ARMOR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int left = scaledWidth / 2 - 91 + this.settings.getPositionValue(Settings.armor_position)[0];
		int top = scaledHeight - 39 - this.settings.getPositionValue(Settings.armor_position)[1];
		int level = this.mc.player.getArmor();
		int x;
		for (int w = 0; w < 10; ++w) {
            if (level <= 0) continue;
            x = left + w * 8;
            if (w * 2 + 1 < level) {
                dc.drawGuiTexture(ARMOR_FULL_TEXTURE, x + 48, top - 2, 9, 9);
            }
            if (w * 2 + 1 == level) {
                dc.drawGuiTexture(ARMOR_HALF_TEXTURE, x + 48, top - 2, 9, 9);
            }
            if (w * 2 + 1 <= level) continue;
            dc.drawGuiTexture(ARMOR_EMPTY_TEXTURE, x + 48, top - 2, 9, 9);
        }
	}

}
