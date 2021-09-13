package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(EnvType.CLIENT)
public class HudElementExperienceModern extends HudElement {

	public HudElementExperienceModern() {
		super(HudElementType.EXPERIENCE, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawableHelper gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int exp = MathHelper.ceil(this.mc.player.getNextLevelExperience() * this.mc.player.experienceProgress);
		int expCap = this.mc.player.getNextLevelExperience();
		double full = ((double) (scaledWidth - 2)) / expCap;
		int posX = this.settings.getPositionValue(Settings.experience_position)[0];
		int posY = this.settings.getPositionValue(Settings.experience_position)[1];

		drawRect(ms, posX, scaledHeight - 7 + posY, scaledWidth, 7, 0xA0000000);
		drawRect(ms, 1 + posX, scaledHeight - 6 + posY, (int) (exp * full), 4, this.settings.getIntValue(Settings.color_experience));

		String stringExp =  this.settings.getBoolValue(Settings.experience_percentage) ? (int) Math.floor((double) exp / (double) expCap * 100) + "%" : exp + "/" + expCap;

		if (this.settings.getBoolValue(Settings.show_numbers_experience)) {
			int width2 = this.mc.textRenderer.getWidth(stringExp) / 2;
			drawRect(ms, 1 + posX, scaledHeight - 15 + posY, width2 + 4, 8, 0xA0000000);
			ms.scale(0.5f, 0.5f, 0.5f);
			DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, stringExp, 6 + width2 + posX * 2, (scaledHeight - 12) * 2 - 1 + posY * 2, -1);
			ms.scale(2f, 2f, 2f);
		}
	}

}
