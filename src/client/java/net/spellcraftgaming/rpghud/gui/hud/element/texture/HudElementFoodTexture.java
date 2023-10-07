package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.item.ItemStack;
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
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		HungerManager stats = this.mc.player.getHungerManager();
		int stamina = stats.getFoodLevel();
		int staminaMax = 20;
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hunger_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 22 : 18) + this.settings.getPositionValue(Settings.hunger_position)[1];
		ItemStack itemMain = this.mc.player.getMainHandStack();
		ItemStack itemSec = this.mc.player.getOffHandStack();

		if (stats.isNotFull() && this.settings.getBoolValue(Settings.show_hunger_preview)) {
			float value = 0;
			if (itemMain != ItemStack.EMPTY && itemMain.getItem().getFoodComponent() != null) {
				value = itemMain.getItem().getFoodComponent().getHunger();
			} else if (itemSec != ItemStack.EMPTY && itemMain.getItem().getFoodComponent() != null) {
				value = itemSec.getItem().getFoodComponent().getHunger();
			}
			if (value > 0) {
				int bonusHunger = (int) (value + stamina);
				if (bonusHunger > staminaMax)
					bonusHunger = staminaMax;
				dc.drawTexture(INTERFACE, posX, posY, 141, 148, (int) (110.0D * (bonusHunger / (double) staminaMax)), 12);
			}
		}

		if (this.mc.player.hasStatusEffect(StatusEffects.HUNGER)) {
			dc.drawTexture(INTERFACE, posX, posY, 141, 136, (int) (110.0D * (stamina / (double) staminaMax)), 12);
		} else {
			dc.drawTexture(INTERFACE, posX, posY, 110, 100, (int) (110.0D * (stamina / (double) staminaMax)), 12);
		}
		
		String staminaString = this.settings.getBoolValue(Settings.hunger_percentage) ? (int) Math.floor((double) stamina / (double) staminaMax * 100) + "%" : stamina + "/" + staminaMax;
		if (this.settings.getBoolValue(Settings.show_numbers_food))
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, staminaString, posX + 55, posY + 2, -1);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
	}

}
