package net.spellcraftgaming.rpghud.gui.hud.element.simple;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementExperienceSimple extends HudElement {

	public HudElementExperienceSimple() {
		super(HudElementType.EXPERIENCE, 0, 0, 0, 0, true);
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
		int width = 182;
		int posX = ((scaledWidth - width) / 2)  + this.settings.getPositionValue(Settings.experience_position)[0];
		int posY = scaledHeight - 31 + this.settings.getPositionValue(Settings.experience_position)[1];
		drawCustomBar(dc, posX, posY, width, 8, exp * full, 0xA0000000, 0xA0000000, this.settings.getIntValue(Settings.color_experience), offsetColorPercent(this.settings.getIntValue(Settings.color_experience), 25), 0xA0000000);
		
		String stringExp =  this.settings.getBoolValue(Settings.experience_percentage) ? (int) Math.floor((double) exp / (double) expCap * 100) + "%" : exp + "/" + expCap;

		if (this.settings.getBoolValue(Settings.show_numbers_experience)) {
			float scale =0.5f;
			if(this.settings.getBoolValue(Settings.debug_number_size)) scale = 0.666666666f;
			float invertedScale = 1f/scale;
			dc.getMatrices().scale(scale, scale, scale);
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, stringExp, Math.round((posX + (width/2))* invertedScale), (int) Math.round(((posY)* invertedScale) + Math.ceil(invertedScale*4-4)), -1);
			dc.getMatrices().scale(invertedScale, invertedScale, invertedScale);
		}
	}

}
