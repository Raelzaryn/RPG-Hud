package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.client.renderer.RenderPipelines;
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
public class HudElementFoodTexture extends HudElement {

	public HudElementFoodTexture() {
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
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hunger_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 22 : 18) + this.settings.getPositionValue(Settings.hunger_position)[1];
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
				graphics.blit(RenderPipelines.GUI_TEXTURED, INTERFACE, posX, posY, 141, 148, (int) (110.0D * (bonusHunger / (double) staminaMax)), 12, 256, 256);
			}
		}

		if (this.mc.player.hasEffect(MobEffects.HUNGER)) {
			graphics.blit(RenderPipelines.GUI_TEXTURED, INTERFACE, posX, posY, 141, 136, (int) (110.0D * (stamina / (double) staminaMax)), 12, 256, 256);
		} else {
			graphics.blit(RenderPipelines.GUI_TEXTURED, INTERFACE, posX, posY, 110, 100, (int) (110.0D * (stamina / (double) staminaMax)), 12, 256, 256);
		}
		
		String staminaString = this.settings.getBoolValue(Settings.hunger_percentage) ? Mth.floor((double) stamina / (double) staminaMax * 100) + "%" : stamina + "/" + staminaMax;
		if (this.settings.getBoolValue(Settings.show_numbers_food))
			graphics.centeredText(this.mc.font, staminaString, posX + 55, posY + 2, -1);
	}

}
