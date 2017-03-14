package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementTexture;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementFoodTexture extends HudElementTexture {

	public HudElementFoodTexture() {
		super(HudElementType.FOOD, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return GameData.shouldDrawHUD();
	}
	
	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		bind(INTERFACE);
		GlStateManager.color(1f, 1f, 1f);
		int stamina = GameData.getPlayerFood();
		int foodMax = GameData.getPlayerMaxFood();
		int posX = this.settings.render_player_face ? 49 : 25;
		int posY = this.settings.render_player_face ? 22 : 18;
		ItemStack itemMain = GameData.getMainhand();
		ItemStack itemSec = GameData.getOffhand();
		if ((itemMain != GameData.nullStack() && itemMain.getItem() instanceof ItemFood) || (itemSec != GameData.nullStack() && itemSec.getItem() instanceof ItemFood) && GameData.doesPlayerNeedFood() && this.settings.show_hunger_preview) {
			float value;
			if(itemMain.getItem() instanceof ItemFood) 
				value = ((ItemFood) itemMain.getItem()).getHealAmount(itemMain);
			else value = ((ItemFood) itemSec.getItem()).getHealAmount(itemSec);
			int bonusHunger = (int) (value + stamina);
			if (bonusHunger > foodMax)
				bonusHunger = foodMax;
			gui.drawTexturedModalRect(posX, posY, 141, 148, (int) (110.0D * (bonusHunger / (double)foodMax)), 12);
		}
		if (GameData.isPlayerHungered()) {
			gui.drawTexturedModalRect(posX, posY, 141, 136, (int) (110.0D * (stamina / (double)foodMax)), 12);
		} else {
			gui.drawTexturedModalRect(posX, posY, 110, 100, (int) (110.0D * (stamina / (double)foodMax)), 12);
		}
		String staminaString = stamina + "/" + foodMax;
		if (this.settings.show_numbers_stamina)
			gui.drawCenteredString(this.mc.fontRendererObj, staminaString, posX + 55, posY + 2, -1);
		GlStateManager.color(1f, 1f, 1f);
		GameData.bindIcons();
	}

}
