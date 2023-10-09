package net.spellcraftgaming.rpghud.gui.hud.simple;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementHealthSimple extends HudElement {

	public HudElementHealthSimple() {
		super(HudElementType.HEALTH, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {		
		
		int health = MathHelper.ceil(this.mc.player.getHealth());
		int absorption = MathHelper.ceil(this.mc.player.getAbsorptionAmount());
		int healthMax = MathHelper.ceil(this.mc.player.getMaxHealth());
		int width = 84;
		
		int posX = ((scaledWidth) / 2) - width - 7 + this.settings.getPositionValue(Settings.health_position)[0];
		int posY = scaledHeight - 32 - 8 + this.settings.getPositionValue(Settings.health_position)[1];

		drawRect(dc, posX, posY, width, 8, 0xA0000000);
		if (absorption > 1)
			drawCustomBar(dc, posX, posY, width, 8, (double) (health + absorption) / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_absorption), offsetColorPercent(this.settings.getIntValue(Settings.color_absorption), OFFSET_PERCENT), false);
		if (this.mc.player.hasStatusEffect(StatusEffects.POISON)) {
			drawCustomBar(dc, posX, posY, width, 8, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_poison), offsetColorPercent(this.settings.getIntValue(Settings.color_poison), OFFSET_PERCENT), false);
		} else if (this.mc.player.hasStatusEffect(StatusEffects.WITHER)) {
			drawCustomBar(dc, posX, posY, width, 8, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_wither), offsetColorPercent(this.settings.getIntValue(Settings.color_wither), OFFSET_PERCENT), false);
		} else {
			drawCustomBar(dc, posX, posY, width, 8, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_health), offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT), false);
		}

		String stringHealth = this.settings.getBoolValue(Settings.health_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%" : (health + absorption) + "/" + healthMax;
		if (this.settings.getBoolValue(Settings.show_numbers_health)) {
			float scale =0.5f;
			if(this.settings.getBoolValue(Settings.debug_number_size)) scale = 0.666666666f;
			float invertedScale = 1f/scale;
			dc.getMatrices().scale(scale, scale, scale);
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, stringHealth, Math.round((posX + (width/2))* invertedScale), (int) Math.round(((posY)* invertedScale) + Math.ceil(invertedScale*4-4)), -1); //+4 correct for 0.5 // +0 correct for 1 // +12 for 0.25 // -2 for 2
			dc.getMatrices().scale(invertedScale, invertedScale, invertedScale);
		}
	}

}
