package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

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
public class HudElementFoodHotbar extends HudElement {

	public HudElementFoodHotbar() {
		super(HudElementType.FOOD, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		HungerManager stats = this.mc.player.getHungerManager();
		int stamina = stats.getFoodLevel();
		int staminaMax = 20;
		int height = scaledHeight + this.settings.getPositionValue(Settings.hunger_position)[1];
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hunger_position)[0];
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
				int colorPreview = offsetColor(this.settings.getIntValue(Settings.color_food), OFFSET_PREVIEW);
				drawCustomBar(dc, posX, height - 26, 200, 10, bonusHunger / (double) staminaMax * 100.0D, -1, -1, colorPreview, offsetColorPercent(colorPreview, OFFSET_PERCENT));
			}
		}

		if (this.mc.player.hasStatusEffect(StatusEffects.HUNGER)) {
			drawCustomBar(dc, posX, height - 26, 200, 10, stamina / (double) staminaMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_hunger), offsetColorPercent(this.settings.getIntValue(Settings.color_hunger), OFFSET_PERCENT));
		} else {
			drawCustomBar(dc, posX, height - 26, 200, 10, stamina / (double) staminaMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_food), offsetColorPercent(this.settings.getIntValue(Settings.color_food), OFFSET_PERCENT));
		}
		
		String staminaString = this.settings.getBoolValue(Settings.hunger_percentage) == true ? (int) Math.floor((double) stamina / (double) staminaMax * 100) + "%" : stamina + "/" + staminaMax;
		if (this.settings.getBoolValue(Settings.show_numbers_food))
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, staminaString, posX + 100, height - 25, -1);
	}

}
