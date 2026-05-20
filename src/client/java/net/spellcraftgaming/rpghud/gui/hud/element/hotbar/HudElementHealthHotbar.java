package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.spellcraftgaming.rpghud.RPGHudUtils;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementHealthHotbar extends HudElement {

	public HudElementHealthHotbar() {
		super(HudElementType.HEALTH, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return RPGHudUtils.isSurvival() && !(this.mc.player.getVehicle() instanceof LivingEntity);
	}

	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
		
		int height = scaledHeight + this.settings.getPositionValue(Settings.health_position)[1];
		int health = Mth.ceil(this.mc.player.getHealth());
		int absorption = Mth.ceil(this.mc.player.getAbsorptionAmount());
		int healthMax = Mth.ceil(this.mc.player.getMaxHealth());
		if(health > healthMax) health = healthMax;
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.health_position)[0];

		if (absorption > 1)
			drawCustomBar(graphics, posX, height - 56, 200, 10, (double) (health + absorption) / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_absorption), offsetColorPercent(this.settings.getIntValue(Settings.color_absorption), OFFSET_PERCENT));

		if (this.mc.player.hasEffect(MobEffects.POISON)) {
			drawCustomBar(graphics, posX, height - 56, 200, 10, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_poison), offsetColorPercent(this.settings.getIntValue(Settings.color_poison), OFFSET_PERCENT));
		} else if (this.mc.player.hasEffect(MobEffects.WITHER)) {
			drawCustomBar(graphics, posX, height - 56, 200, 10, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_wither), offsetColorPercent(this.settings.getIntValue(Settings.color_wither), OFFSET_PERCENT));
		} else {
			drawCustomBar(graphics, posX, height - 56, 200, 10, (double) health / (double) (healthMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_health), offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT));
		}
		
		String stringHealth = this.settings.getBoolValue(Settings.health_percentage) ? Mth.floor((double) health / (double) healthMax * 100) + "%" : (health + absorption) + "/" + healthMax;
		if (this.settings.getBoolValue(Settings.show_numbers_health))
			graphics.centeredText(this.mc.font, stringHealth, posX + 100, height - 55, -1);
	}
}
