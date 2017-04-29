package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementJumpBarModern extends HudElement {

	public HudElementJumpBarModern() {
		super(HudElementType.JUMP_BAR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return GameData.isRidingLivingMount() && (this.settings.getBoolValue(Settings.limit_jump_bar) ? GameData.getHorseJumpPower() > 0F : true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks, double scale) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int height = res.getScaledHeight();
		int width = res.getScaledWidth();
		float jumpPower = GameData.getHorseJumpPower();
		int value = (int) (jumpPower * 100.0F);
		drawRect(width / 2 - 72, height - 78, 144, 2, 0xA0000000);
		drawRect(width / 2 - 72, height - 70, 144, 2, 0xA0000000);
		drawRect(width / 2 - 72, height - 76, 2, 6, 0xA0000000);
		drawRect(width / 2 + 70, height - 76, 2, 6, 0xA0000000);
		drawRect(width / 2 - 70, height - 76, 140, 6, 0x20FFFFFF);
		drawRect(width / 2 - 70, height - 76, (int) (140 * (value / 100.0D)), 6, this.settings.getIntValue(Settings.color_jump_bar));
	}

}
