package net.spellcraftgaming.rpghud.gui.hud.element.simple;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.spellcraftgaming.rpghud.RPGHudUtils;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementFoodSimple extends HudElement {

	public HudElementFoodSimple() {
		super(HudElementType.FOOD, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return RPGHudUtils.isSurvival();
	}
	
	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
		this.mc.player.getFoodData();
		FoodData stats = this.mc.player.getFoodData();
		int stamina = stats.getFoodLevel();
		int staminaMax = 20;
		int width = 84;
		
		int posX = ((scaledWidth) / 2) + 7 + this.settings.getPositionValue(Settings.hunger_position)[0];
		int posY = scaledHeight - 32 - 8 + this.settings.getPositionValue(Settings.hunger_position)[1];
		
		
		ItemStack itemMain = this.mc.player.getMainHandItem();
		ItemStack itemSec = this.mc.player.getOffhandItem();
		
		drawRect(graphics, posX, posY, width, 8, 0xA0000000);
		if (stats.needsFood() && this.settings.getBoolValue(Settings.show_hunger_preview)) {
			float value = 0;
			if (itemMain != ItemStack.EMPTY && itemMain.has(DataComponents.FOOD)) {
				value = itemMain.get(DataComponents.FOOD).nutrition();
			} else if (itemSec != ItemStack.EMPTY && itemMain.has(DataComponents.FOOD)) {
				value = itemSec.get(DataComponents.FOOD).nutrition();
			}
			if (value > 0) {
				int bonusHunger = (int) (value + stamina);
				if (bonusHunger > staminaMax)
					bonusHunger = staminaMax;
				int colorPreview = offsetColor(this.settings.getIntValue(Settings.color_food), OFFSET_PREVIEW);
				drawCustomBar(graphics, posX, posY, width, 8, bonusHunger / (double) staminaMax * 100.0D, -1, -1, colorPreview, offsetColorPercent(colorPreview, OFFSET_PERCENT), false);
			}
		}

		if (this.mc.player.hasEffect(MobEffects.HUNGER)) {
			drawCustomBar(graphics, posX, posY, width, 8, stamina / (double) staminaMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_hunger), offsetColorPercent(this.settings.getIntValue(Settings.color_hunger), OFFSET_PERCENT), false);
		} else {
			drawCustomBar(graphics, posX, posY, width, 8, stamina / (double) staminaMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_food), offsetColorPercent(this.settings.getIntValue(Settings.color_food), OFFSET_PERCENT), false);
		}
		String staminaString = this.settings.getBoolValue(Settings.hunger_percentage) ? Mth.floor((double) stamina / (double) staminaMax * 100) + "%" : stamina + "/" + staminaMax;
		if (this.settings.getBoolValue(Settings.show_numbers_food)) {
			float scale = 0.5f;
			if (this.settings.getBoolValue(Settings.debug_number_size)) scale = 0.666666666f;
			float invertedScale = 1f / scale;
			graphics.pose().scale(scale, scale);
			graphics.centeredText(this.mc.font, staminaString, Math.round((posX + (width / 2)) * invertedScale), (int) Math.round(((posY) * invertedScale) + Math.ceil(invertedScale * 4 - 4)), -1);
			graphics.pose().scale(invertedScale, invertedScale);
		}
	}

}
