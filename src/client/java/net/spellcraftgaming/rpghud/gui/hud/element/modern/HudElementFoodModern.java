package net.spellcraftgaming.rpghud.gui.hud.element.modern;

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
import net.spellcraftgaming.rpghud.gui.hud.HudModern;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementFoodModern extends HudElement {

	public HudElementFoodModern() {
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
		int xOffset = ((HudModern) this.rpgHud.huds.get("modern")).getPosX();
		String staminaString = this.settings.getBoolValue(Settings.hunger_percentage) ? Mth.floor((double) stamina / (double) staminaMax * 100) + "%" : stamina + "/" + staminaMax;
		int width = this.mc.font.width(staminaString) / 2 + 4;
		if(width < xOffset) width = xOffset;
		else ((HudModern) this.rpgHud.huds.get("modern")).setPosX(width);

		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 24 : 2) + ((this.settings.getBoolValue(Settings.show_numbers_health) && this.settings.getBoolValue(Settings.show_numbers_food)) ? xOffset : 0) + this.settings.getPositionValue(Settings.hunger_position)[0];
		int textPosX = this.settings.getPositionValue(Settings.hunger_position)[0];
		int posY = this.settings.getPositionValue(Settings.hunger_position)[1];

		if (this.settings.getBoolValue(Settings.show_numbers_health) && this.settings.getBoolValue(Settings.show_numbers_food)) {
			drawRect(graphics, textPosX + (this.settings.getBoolValue(Settings.render_player_face) ? 23 : 2), posY + 12, width, 8, 0xA0000000);
			graphics.pose().scale(0.5f, 0.5f);
			graphics.centeredText(this.mc.font, staminaString, textPosX * 2 + (this.settings.getBoolValue(Settings.render_player_face) ? 42 : 0) + width + 4, posY * 2 + 28, -1);
			graphics.pose().scale(2f, 2f);
		}

		drawTetragon(graphics, posX, posX, 13 + posY, 13 + posY, 70, 58, 8, 8, 0xA0000000);
		drawTetragon(graphics, posX + 2, posX + 2, 13 + posY, 13 + posY, 64, 54, 6, 6, 0x20FFFFFF);

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
				drawTetragon(graphics, posX + 2, posX + 2, 13 + posY, 13 + posY, (int) (64 * ((double) bonusHunger / (double) staminaMax)), (int) (63 * ((double) bonusHunger / (double) 20)) - 10, 6, 6, offsetColor(this.settings.getIntValue(Settings.color_food), OFFSET_PREVIEW));
			}
		}

		if (this.mc.player.hasEffect(MobEffects.HUNGER)) {
			drawTetragon(graphics, posX + 2, posX + 2, 13 + posY, 13 + posY, (int) (64 * ((double) stamina / (double) staminaMax)), (int) (64 * ((double) stamina / (double) 20)) - 10, 6, 6, this.settings.getIntValue(Settings.color_hunger));
		} else {
			drawTetragon(graphics, posX + 2, posX + 2, 13 + posY, 13 + posY, (int) (64 * ((double) stamina / (double) staminaMax)), (int) (64 * ((double) stamina / (double) 20)) - 10, 6, 6, this.settings.getIntValue(Settings.color_food));
		}
	}

}
