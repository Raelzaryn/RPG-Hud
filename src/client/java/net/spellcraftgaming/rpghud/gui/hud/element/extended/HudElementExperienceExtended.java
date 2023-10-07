package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementExperienceExtended extends HudElement {

	public HudElementExperienceExtended() {
		super(HudElementType.EXPERIENCE, 0, 0, 0, 0, false);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int exp = MathHelper.ceil(this.mc.player.getNextLevelExperience() * this.mc.player.experienceProgress);
		int expCap = this.mc.player.getNextLevelExperience();
		double full = 100D / expCap;
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.experience_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 35 : 31) + this.settings.getPositionValue(Settings.experience_position)[1];

		drawCustomBar(dc, posX, posY, 88, 8, exp * full, -1, -1, this.settings.getIntValue(Settings.color_experience), offsetColorPercent(this.settings.getIntValue(Settings.color_experience), 25));

		String stringExp =  this.settings.getBoolValue(Settings.experience_percentage) ? (int) Math.floor((double) exp / (double) expCap * 100) + "%" : exp + "/" + expCap;

		if (this.settings.getBoolValue(Settings.show_numbers_experience)) {
			dc.getMatrices().scale(0.5f, 0.5f, 0.5f);
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, stringExp, posX * 2 + 88, posY * 2 + 4, -1);
			dc.getMatrices().scale(2f, 2f, 2f);
		}
	}

}
