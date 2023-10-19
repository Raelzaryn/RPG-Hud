package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Gui;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementFoodTexture extends HudElement {

	public HudElementFoodTexture() {
		super(HudElementType.FOOD, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.options.hideGui;
	}

	@Override
	public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		bind(INTERFACE);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		FoodData stats = this.mc.player.getFoodData();
		int stamina = stats.getFoodLevel();
		int staminaMax = 20;
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hunger_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 22 : 18) + this.settings.getPositionValue(Settings.hunger_position)[1];
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
				gui.blit(ms, posX, posY, 141, 148, (int) (110.0D * (bonusHunger / (double) staminaMax)), 12);
			}
		}

		if (this.mc.player.hasEffect(MobEffects.HUNGER)) {
			gui.blit(ms, posX, posY, 141, 136, (int) (110.0D * (stamina / (double) staminaMax)), 12);
		} else {
			gui.blit(ms, posX, posY, 110, 100, (int) (110.0D * (stamina / (double) staminaMax)), 12);
		}
		
		String staminaString = this.settings.getBoolValue(Settings.hunger_percentage) ? (int) Math.floor((double) stamina / (double) staminaMax * 100) + "%" : stamina + "/" + staminaMax;
		if (this.settings.getBoolValue(Settings.show_numbers_food))
			Gui.drawCenteredString(ms, this.mc.font, staminaString, posX + 55, posY + 2, -1);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		bind(Gui.GUI_ICONS_LOCATION);
	}

}
