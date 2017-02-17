package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.spellcraftgaming.rpghud.gui.hud.HudModern;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementFoodModern extends HudElementBarred{

	public HudElementFoodModern() {
		super(HudElementType.FOOD, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}
	
	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int stamina = this.mc.player.getFoodStats().getFoodLevel();
		
		int xOffset = ((HudModern) this.rpgHud.huds.get("modern")).getPosX();
		
		int width = this.mc.fontRendererObj.getStringWidth("20/20") / 2 + 4;
		
		String staminaString = stamina + "/" + "20";
		if(this.settings.show_numbers_health && this.settings.show_numbers_stamina){
			drawRect(this.settings.render_player_face ? 23 : 2, 12, width, 8, 0xA0000000);
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			gui.drawCenteredString(this.mc.fontRendererObj, staminaString, this.settings.render_player_face ? (xOffset * 2) + 28: 24, 28, -1);
			GL11.glScaled(2.0D, 2.0D, 2.0D);
		}
		int posX = (this.settings.render_player_face ? 24 : 2) + ((this.settings.show_numbers_health && this.settings.show_numbers_stamina) ? xOffset + 1: 0);
		
		drawTetragon(posX, posX, 13, 13, 70, 58, 8, 8, 0xA0000000);
		drawTetragon(posX + 2, posX + 2, 13, 13, 64, 54, 6, 6, 0x20FFFFFF);
		
		ItemStack itemMain = this.mc.player.getHeldItemMainhand();
		ItemStack itemSec = this.mc.player.getHeldItemOffhand();
		if ((itemMain != null && itemMain.getItem() instanceof ItemFood) || (itemSec != null && itemSec.getItem() instanceof ItemFood) && this.mc.player.getFoodStats().needFood() && this.settings.show_hunger_preview) {
			float value;
			if(itemMain.getItem() instanceof ItemFood) 
				value = ((ItemFood) itemMain.getItem()).getHealAmount(itemMain);
			else value = ((ItemFood) itemSec.getItem()).getHealAmount(itemSec);
			int bonusHunger = (int) (value + stamina);
			if (bonusHunger > 20)
				bonusHunger = 20;
			drawTetragon(posX + 2, posX + 2, 13, 13, (int) (64 * ((double)bonusHunger / (double) 20)), (int) (63 * ((double)bonusHunger / (double) 20)) - 10, 6, 6, offsetColor(this.settings.color_stamina, OFFSET_PREVIEW));
		}
		if (this.mc.player.isPotionActive(MobEffects.HUNGER)) {
			drawTetragon(posX + 2, posX + 2, 13, 13, (int) (64 * ((double)stamina / (double) 20)), (int) (64 * ((double)stamina / (double) 20)) - 10, 6, 6, this.settings.color_hunger);
		} else {
			drawTetragon(posX + 2, posX + 2, 13, 13, (int) (64 * ((double)stamina / (double) 20)), (int) (64 * ((double)stamina / (double) 20)) - 10, 6, 6, this.settings.color_stamina);
		}
	}

}
