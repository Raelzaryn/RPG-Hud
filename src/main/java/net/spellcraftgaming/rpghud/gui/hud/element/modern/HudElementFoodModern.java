package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.HudModern;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementFoodModern extends HudElement {

	public HudElementFoodModern() {
		super(HudElementType.FOOD, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return GameData.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int stamina = GameData.getPlayerFood();
		int staminaMax = GameData.getPlayerMaxFood();
		int xOffset = ((HudModern) this.rpgHud.huds.get("modern")).getPosX();

		int width = this.mc.fontRendererObj.getStringWidth(staminaMax + "/" + staminaMax) / 2 + 4;

		String staminaString = stamina + "/" + staminaMax;
		if (this.settings.show_numbers_health && this.settings.show_numbers_stamina) {
			drawRect(this.settings.render_player_face ? 23 : 2, 12, width, 8, 0xA0000000);
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			gui.drawCenteredString(this.mc.fontRendererObj, staminaString, this.settings.render_player_face ? (xOffset * 2) + 28 : 24, 28, -1);
			GL11.glScaled(2.0D, 2.0D, 2.0D);
		}
		int posX = (this.settings.render_player_face ? 24 : 2) + ((this.settings.show_numbers_health && this.settings.show_numbers_stamina) ? xOffset + 1 : 0);

		drawTetragon(posX, posX, 13, 13, 70, 58, 8, 8, 0xA0000000);
		drawTetragon(posX + 2, posX + 2, 13, 13, 64, 54, 6, 6, 0x20FFFFFF);

		ItemStack itemMain = GameData.getMainhand();
		ItemStack itemSec = GameData.getOffhand();

		if (GameData.doesPlayerNeedFood() && this.settings.show_hunger_preview) {
			float value = 0;
			if (itemMain != GameData.nullStack() && itemMain.getItem() instanceof ItemFood) {
				value = ((ItemFood) itemMain.getItem()).getHealAmount(itemMain);
			} else if (itemSec != GameData.nullStack() && itemSec.getItem() instanceof ItemFood) {
				value = ((ItemFood) itemSec.getItem()).getHealAmount(itemSec);
			}
			if (value > 0) {
				int bonusHunger = (int) (value + stamina);
				if (bonusHunger > staminaMax)
					bonusHunger = staminaMax;
				drawTetragon(posX + 2, posX + 2, 13, 13, (int) (64 * ((double) bonusHunger / (double) staminaMax)), (int) (63 * ((double) bonusHunger / (double) 20)) - 10, 6, 6, offsetColor(this.settings.color_stamina, OFFSET_PREVIEW));
			}
		}

		if (GameData.isPlayerHungered()) {
			drawTetragon(posX + 2, posX + 2, 13, 13, (int) (64 * ((double) stamina / (double) staminaMax)), (int) (64 * ((double) stamina / (double) 20)) - 10, 6, 6, this.settings.color_hunger);
		} else {
			drawTetragon(posX + 2, posX + 2, 13, 13, (int) (64 * ((double) stamina / (double) staminaMax)), (int) (64 * ((double) stamina / (double) 20)) - 10, 6, 6, this.settings.color_stamina);
		}
	}

}
