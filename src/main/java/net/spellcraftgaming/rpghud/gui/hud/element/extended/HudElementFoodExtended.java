package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.FoodStats;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class HudElementFoodExtended extends HudElement {

	public HudElementFoodExtended() {
		super(HudElementType.FOOD, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.gameSettings.hideGUI && this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(AbstractGui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		FoodStats stats = this.mc.player.getFoodStats();
		int stamina = stats.getFoodLevel();
		int staminaMax = 20;
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hunger_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 22 : 18) + this.settings.getPositionValue(Settings.hunger_position)[1];
		ItemStack itemMain = this.mc.player.getHeldItemMainhand();
		ItemStack itemSec = this.mc.player.getHeldItemOffhand();

		if (stats.needFood() && this.settings.getBoolValue(Settings.show_hunger_preview)) {
			float value = 0;
			if (itemMain != ItemStack.EMPTY && itemMain.getItem().getFood() != null) {
				value = itemMain.getItem().getFood().getHealing();
			} else if (itemSec != ItemStack.EMPTY && itemMain.getItem().getFood() != null) {
				value = itemSec.getItem().getFood().getHealing();
			}
			if (value > 0) {
				int bonusHunger = (int) (value + stamina);
				if (bonusHunger > staminaMax)
					bonusHunger = staminaMax;
				int colorPreview = offsetColor(this.settings.getIntValue(Settings.color_food), OFFSET_PREVIEW);
				drawCustomBar(posX, posY, 110, 12, bonusHunger / (double) staminaMax * 100.0D, -1, -1, colorPreview, offsetColorPercent(colorPreview, OFFSET_PERCENT));
			}
		}

		if (this.mc.player.isPotionActive(Effects.HUNGER)) {
			drawCustomBar(posX, posY, 110, 12, stamina / (double) staminaMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_hunger), offsetColorPercent(this.settings.getIntValue(Settings.color_hunger), OFFSET_PERCENT));
		} else {
			drawCustomBar(posX, posY, 110, 12, stamina / (double) staminaMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_food), offsetColorPercent(this.settings.getIntValue(Settings.color_food), OFFSET_PERCENT));
		}
		
		String staminaString = this.settings.getBoolValue(Settings.hunger_percentage) ? (int) Math.floor((double) stamina / (double) staminaMax * 100) + "%" : stamina + "/" + staminaMax;
		if (this.settings.getBoolValue(Settings.show_numbers_food))
			gui.drawCenteredString(this.mc.fontRenderer, staminaString, posX + 55, posY + 2, -1);
	}

}
