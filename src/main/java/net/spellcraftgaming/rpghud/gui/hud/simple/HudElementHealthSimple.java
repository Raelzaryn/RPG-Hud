package net.spellcraftgaming.rpghud.gui.hud.simple;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHealthSimple extends HudElement {

	public HudElementHealthSimple() {
		super(HudElementType.HEALTH, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawableHelper gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int health = MathHelper.ceil(this.mc.player.getHealth());
		int absorption = MathHelper.ceil(this.mc.player.getAbsorptionAmount());
		int healthMax = MathHelper.ceil(this.mc.player.getMaxHealth());
		int width = 84;
		
		int posX = ((scaledWidth) / 2) - width - 7 + this.settings.getPositionValue(Settings.health_position)[0];
		int posY = scaledHeight - 32 - 8 + this.settings.getPositionValue(Settings.health_position)[1];

		if (absorption > 1)
			drawCustomBar(ms, posX, posY, width, 8, (double) (health + absorption) / (double) (healthMax + absorption) * 100D, 0xA0000000, 0xA0000000, this.settings.getIntValue(Settings.color_absorption), offsetColorPercent(this.settings.getIntValue(Settings.color_absorption), OFFSET_PERCENT), 0xA0000000);
		if (this.mc.player.hasStatusEffect(StatusEffects.POISON)) {
			drawCustomBar(ms, posX, posY, width, 8, (double) health / (double) (healthMax + absorption) * 100D, 0xA0000000, 0xA0000000, this.settings.getIntValue(Settings.color_poison), offsetColorPercent(this.settings.getIntValue(Settings.color_poison), OFFSET_PERCENT), 0xA0000000);
		} else if (this.mc.player.hasStatusEffect(StatusEffects.WITHER)) {
			drawCustomBar(ms, posX, posY, width, 8, (double) health / (double) (healthMax + absorption) * 100D, 0xA0000000, 0xA0000000, this.settings.getIntValue(Settings.color_wither), offsetColorPercent(this.settings.getIntValue(Settings.color_wither), OFFSET_PERCENT), 0xA0000000);
		} else {
			drawCustomBar(ms, posX, posY, width, 8, (double) health / (double) (healthMax + absorption) * 100D, 0xA0000000, 0xA0000000, this.settings.getIntValue(Settings.color_health), offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT), 0xA0000000);
		}

		String stringHealth = this.settings.getBoolValue(Settings.health_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%" : (health + absorption) + "/" + healthMax;
		if (this.settings.getBoolValue(Settings.show_numbers_health)) {
			ms.scale(0.5f, 0.5f, 0.5f);
			DrawableHelper.drawCenteredText(ms, this.mc.textRenderer, stringHealth, posX * 2 + width, posY * 2 + 4, -1);
			ms.scale(2f, 2f, 2f);
		}
	}

}
