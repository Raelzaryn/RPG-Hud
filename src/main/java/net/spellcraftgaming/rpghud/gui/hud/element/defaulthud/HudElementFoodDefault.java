package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Gui;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementFoodDefault extends HudElement {

	public HudElementFoodDefault() {
		super(HudElementType.FOOD, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.options.hideGui;
	}

	@Override
	public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		FoodData stats = this.mc.player.getFoodData();
		int stamina = stats.getFoodLevel();
		int staminaMax = 20;
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 24) + this.settings.getPositionValue(Settings.hunger_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 26 : 18) + this.settings.getPositionValue(Settings.hunger_position)[1];
		ItemStack itemMain = this.mc.player.getMainHandItem();
		ItemStack itemSec = this.mc.player.getOffhandItem();
		if (stats.needsFood() && this.settings.getBoolValue(Settings.show_hunger_preview)) {
			float value = 0;
			if (itemMain != ItemStack.EMPTY && itemMain.getItem().isEdible()) {
				value = itemMain.getItem().getFoodProperties().getNutrition();
			} else if (itemSec != ItemStack.EMPTY && itemMain.getItem().isEdible()) {
				value = itemSec.getItem().getFoodProperties().getNutrition();
			}
			if (value > 0) {
				int bonusHunger = (int) (value + stamina);
				if (bonusHunger > staminaMax)
					bonusHunger = staminaMax;
				int colorPreview = offsetColor(this.settings.getIntValue(Settings.color_food), OFFSET_PREVIEW);
				drawCustomBar(ms, posX, posY, 110, 12, bonusHunger / (double) staminaMax * 100.0D, -1, -1, colorPreview, offsetColorPercent(colorPreview, OFFSET_PERCENT));
			}
		}

		if (this.mc.player.hasEffect(MobEffects.HUNGER)) {
			drawCustomBar(ms, posX, posY, 110, 12, stamina / (double) staminaMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_hunger), offsetColorPercent(this.settings.getIntValue(Settings.color_hunger), OFFSET_PERCENT));
		} else {
			drawCustomBar(ms, posX, posY, 110, 12, stamina / (double) staminaMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_food), offsetColorPercent(this.settings.getIntValue(Settings.color_food), OFFSET_PERCENT));
		}
		String staminaString = this.settings.getBoolValue(Settings.hunger_percentage) ? (int) Math.floor((double) stamina / (double) staminaMax * 100) + "%" : stamina + "/" + staminaMax;
		if (this.settings.getBoolValue(Settings.show_numbers_food))
			Gui.drawCenteredString(ms,this.mc.font, staminaString, posX + 55, posY + 2, -1);
	}

}
