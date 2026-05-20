package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.spellcraftgaming.rpghud.RPGHudUtils;
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
		return RPGHudUtils.isSurvival();
	}

	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
		int left = scaledWidth / 2 - 91 + this.settings.getPositionValue(Settings.armor_position)[0];
		int top = scaledHeight - 39 - this.settings.getPositionValue(Settings.armor_position)[1];
		int level = this.mc.player.getArmorValue();
		int x;
		for (int w = 0; w < 10; ++w) {
            if (level <= 0) continue;
            x = left + w * 8;
            if (w * 2 + 1 < level) {
                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ARMOR_FULL_TEXTURE, x + 48, top - 2, 9, 9);
            }
            if (w * 2 + 1 == level) {
                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ARMOR_HALF_TEXTURE, x + 48, top - 2, 9, 9);
            }
            if (w * 2 + 1 <= level) continue;
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ARMOR_EMPTY_TEXTURE, x + 48, top - 2, 9, 9);
        }
	}

}
