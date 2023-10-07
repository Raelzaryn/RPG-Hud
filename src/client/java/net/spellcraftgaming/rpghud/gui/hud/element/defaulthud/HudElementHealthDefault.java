package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementHealthDefault extends HudElement {

	public HudElementHealthDefault() {
		super(HudElementType.HEALTH, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledHeight, int scaledWidth) {
		int health = MathHelper.ceil(this.mc.player.getHealth());
		int absorption = MathHelper.ceil(this.mc.player.getAbsorptionAmount());
		int healthMax = MathHelper.ceil(this.mc.player.getMaxHealth());
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 24) + this.settings.getPositionValue(Settings.health_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 13 : 5) + this.settings.getPositionValue(Settings.health_position)[1];

		if (absorption > 1)
			drawCustomBar(dc, posX, posY, 110, 12, (double) (health + absorption) / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_absorption), offsetColorPercent(this.settings.getIntValue(Settings.color_absorption), OFFSET_PERCENT));

		if (this.mc.player.hasStatusEffect(StatusEffects.POISON)) {
			drawCustomBar(dc, posX, posY, 110, 12, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_poison), offsetColorPercent(this.settings.getIntValue(Settings.color_poison), OFFSET_PERCENT));
		} else if (this.mc.player.hasStatusEffect(StatusEffects.WITHER)) {
			drawCustomBar(dc, posX, posY, 110, 12, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_wither), offsetColorPercent(this.settings.getIntValue(Settings.color_wither), OFFSET_PERCENT));
		} else {
			drawCustomBar(dc, posX, posY, 110, 12, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_health), offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT));
		}

		String stringHealth = this.settings.getBoolValue(Settings.health_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%" : (health + absorption) + "/" + healthMax;
		if (this.settings.getBoolValue(Settings.show_numbers_health))
			dc.drawCenteredTextWithShadow(this.mc.textRenderer, stringHealth, posX + 55, posY + 2, -1);
	}
}
