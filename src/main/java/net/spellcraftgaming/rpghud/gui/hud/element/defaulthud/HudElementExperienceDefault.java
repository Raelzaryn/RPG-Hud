package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.Mth;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementExperienceDefault extends HudElement {

	public HudElementExperienceDefault() {
		super(HudElementType.EXPERIENCE, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.options.hideGui;
	}

	@Override
	public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int exp = Mth.ceil(this.mc.player.getXpNeededForNextLevel() * this.mc.player.experienceProgress);
		int expCap = this.mc.player.getXpNeededForNextLevel();
		double full = 100D / expCap;
		int posX = this.settings.getPositionValue(Settings.experience_position)[0];
		int posY = this.settings.getPositionValue(Settings.experience_position)[1];
		drawCustomBar(ms, posX, scaledHeight - 10 + posY, scaledWidth, 10, exp * full, this.settings.getIntValue(Settings.color_experience), offsetColorPercent(this.settings.getIntValue(Settings.color_experience), 25));

		String stringExp =  this.settings.getBoolValue(Settings.experience_percentage) ? (int) Math.floor((double) exp / (double) expCap * 100) + "%" : exp + "/" + expCap;

		int var7 = scaledWidth / 2;
		if (this.settings.getBoolValue(Settings.show_numbers_experience))
			Gui.drawCenteredString(ms,this.mc.font, stringExp, var7 + posX, scaledHeight - 9 + posY, -1);
	}

}
