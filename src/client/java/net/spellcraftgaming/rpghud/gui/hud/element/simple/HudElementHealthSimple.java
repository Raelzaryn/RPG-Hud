package net.spellcraftgaming.rpghud.gui.hud.element.simple;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.spellcraftgaming.rpghud.RPGHudUtils;
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
		return RPGHudUtils.isSurvival();
	}

	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {		
		
		int health = Mth.ceil(this.mc.player.getHealth());
		int absorption = Mth.ceil(this.mc.player.getAbsorptionAmount());
		int healthMax = Mth.ceil(this.mc.player.getMaxHealth());
		int width = 84;
		
		int posX = ((scaledWidth) / 2) - width - 7 + this.settings.getPositionValue(Settings.health_position)[0];
		int posY = scaledHeight - 32 - 8 + this.settings.getPositionValue(Settings.health_position)[1];

		drawRect(graphics, posX, posY, width, 8, 0xA0000000);
		if (absorption > 1)
			drawCustomBar(graphics, posX, posY, width, 8, (double) (health + absorption) / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_absorption), offsetColorPercent(this.settings.getIntValue(Settings.color_absorption), OFFSET_PERCENT), false);
		if (this.mc.player.hasEffect(MobEffects.POISON)) {
			drawCustomBar(graphics, posX, posY, width, 8, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_poison), offsetColorPercent(this.settings.getIntValue(Settings.color_poison), OFFSET_PERCENT), false);
		} else if (this.mc.player.hasEffect(MobEffects.WITHER)) {
			drawCustomBar(graphics, posX, posY, width, 8, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_wither), offsetColorPercent(this.settings.getIntValue(Settings.color_wither), OFFSET_PERCENT), false);
		} else {
			drawCustomBar(graphics, posX, posY, width, 8, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_health), offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT), false);
		}

		String stringHealth = this.settings.getBoolValue(Settings.health_percentage) ? Mth.floor((double) health / (double) healthMax * 100) + "%" : (health + absorption) + "/" + healthMax;
		if (this.settings.getBoolValue(Settings.show_numbers_health)) {
			float scale = 0.5f;
			if (this.settings.getBoolValue(Settings.debug_number_size)) scale = 0.666666666f;
			float invertedScale = 1f / scale;
			graphics.pose().scale(scale, scale);
			graphics.centeredText(this.mc.font, stringHealth, Math.round((posX + (width / 2)) * invertedScale), (int) Math.round(((posY) * invertedScale) + Math.ceil(invertedScale * 4 - 4)), -1);
			graphics.pose().scale(invertedScale, invertedScale);
		}
	}

}
