package net.spellcraftgaming.rpghud.gui.hud.element.simple;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementFoodSimple extends HudElement {

	public HudElementFoodSimple() {
		super(HudElementType.FOOD, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.options.hideGui;
	}
	
	@Override
	public void drawElement(GuiGraphics gg, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		FoodData stats = this.mc.player.getFoodData();
		int stamina = stats.getFoodLevel();
		int staminaMax = 20;
		int width = 84;
		
		int posX = ((scaledWidth) / 2) + 7 + this.settings.getPositionValue(Settings.hunger_position)[0];
		int posY = scaledHeight - 32 - 8 + this.settings.getPositionValue(Settings.hunger_position)[1];
		
		
		ItemStack itemMain = this.mc.player.getMainHandItem();
		ItemStack itemSec = this.mc.player.getOffhandItem();
		if (stats.needsFood() && this.settings.getBoolValue(Settings.show_hunger_preview)) {
			float value = 0;
			if (itemMain != ItemStack.EMPTY && itemMain.getItem().isEdible()) {
				value = itemMain.getItem().getFoodProperties(itemMain, null).getNutrition();
			} else if (itemSec != ItemStack.EMPTY && itemMain.getItem().isEdible()) {
				value = itemSec.getItem().getFoodProperties(itemMain, null).getNutrition();
			}
			if (value > 0) {
				int bonusHunger = (int) (value + stamina);
				if (bonusHunger > staminaMax)
					bonusHunger = staminaMax;
				int colorPreview = offsetColor(this.settings.getIntValue(Settings.color_food), OFFSET_PREVIEW);
				drawCustomBar(gg, posX, posY, width, 8, bonusHunger / (double) staminaMax * 100.0D, -1, -1, colorPreview, offsetColorPercent(colorPreview, OFFSET_PERCENT), -1);
			}
		}

		if (this.mc.player.hasEffect(MobEffects.HUNGER)) {
			drawCustomBar(gg, posX, posY, width, 8, stamina / (double) staminaMax * 100.0D, 0xA0000000, 0xA0000000, this.settings.getIntValue(Settings.color_hunger), offsetColorPercent(this.settings.getIntValue(Settings.color_hunger), OFFSET_PERCENT), 0xA0000000);
		} else {
			drawCustomBar(gg, posX, posY, width, 8, stamina / (double) staminaMax * 100.0D, 0xA0000000, 0xA0000000, this.settings.getIntValue(Settings.color_food), offsetColorPercent(this.settings.getIntValue(Settings.color_food), OFFSET_PERCENT), 0xA0000000);
		}
		String staminaString = this.settings.getBoolValue(Settings.hunger_percentage) ? (int) Math.floor((double) stamina / (double) staminaMax * 100) + "%" : stamina + "/" + staminaMax;
		if (this.settings.getBoolValue(Settings.show_numbers_food)) {
			gg.pose().scale(0.5f, 0.5f, 0.5f);
			gg.drawCenteredString( this.mc.font, staminaString, posX * 2 + width, posY * 2 + 4, -1);
			gg.pose().scale(2f, 2f, 2f);
		}
	}

}
