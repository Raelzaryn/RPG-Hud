package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

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
public class HudElementFoodDefault extends HudElement {

	public HudElementFoodDefault() {
		super(HudElementType.FOOD, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return RPGHudUtils.isSurvival();
	}

	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
		FoodData stats = this.mc.player.getFoodData();
		int stamina = stats.getFoodLevel();
		int staminaMax = 20;
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 24) + this.settings.getPositionValue(Settings.hunger_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 26 : 18) + this.settings.getPositionValue(Settings.hunger_position)[1];
		ItemStack itemMain = this.mc.player.getMainHandItem();
		ItemStack itemSec = this.mc.player.getOffhandItem();
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
				drawCustomBar(graphics, posX, posY, 110, 12, bonusHunger / (double) staminaMax * 100.0D, -1, -1, colorPreview, offsetColorPercent(colorPreview, OFFSET_PERCENT));
			}
		}

		if (this.mc.player.hasEffect(MobEffects.HUNGER)) {
			drawCustomBar(graphics, posX, posY, 110, 12, stamina / (double) staminaMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_hunger), offsetColorPercent(this.settings.getIntValue(Settings.color_hunger), OFFSET_PERCENT));
		} else {
			drawCustomBar(graphics, posX, posY, 110, 12, stamina / (double) staminaMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_food), offsetColorPercent(this.settings.getIntValue(Settings.color_food), OFFSET_PERCENT));
		}
		String staminaString = this.settings.getBoolValue(Settings.hunger_percentage) ? Mth.floor((double) stamina / (double) staminaMax * 100) + "%" : stamina + "/" + staminaMax;
		if (this.settings.getBoolValue(Settings.show_numbers_food))
			graphics.centeredText(this.mc.font, staminaString, posX + 55, posY + 2, -1);
	}

}
