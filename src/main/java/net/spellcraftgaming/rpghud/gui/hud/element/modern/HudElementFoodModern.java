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
		int[] staminaColor = getColor(this.settings.color_stamina);
		int stamina = this.mc.player.getFoodStats().getFoodLevel();
		ItemStack currentItem = this.mc.player.getHeldItemMainhand();
		
		int xOffset = ((HudModern) this.rpgHud.huds.get("modern")).getPosX();
		
		int width = this.mc.fontRendererObj.getStringWidth("20/20") / 2 + 4;
		
		String staminaString = stamina + "/" + "20";
		if(this.settings.show_numbers_stamina){
			drawRect(this.settings.render_player_face ? 23 : 2, 12, width, 8, 0xA0000000);
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			gui.drawCenteredString(this.mc.fontRendererObj, staminaString, 66, 28, -1);
			GL11.glScaled(2.0D, 2.0D, 2.0D);
		}
		int posX = (this.settings.render_player_face ? 24 : 2) + (this.settings.show_numbers_health ? xOffset - 1: 0);
		
		if (currentItem != null && currentItem.getItem() instanceof ItemFood && this.mc.player.getFoodStats().needFood() && this.settings.show_hunger_preview) {
			float value = ((ItemFood) this.mc.player.getHeldItemMainhand().getItem()).getHealAmount(this.mc.player.getHeldItemMainhand());
			int bonusHunger = (int) (value + stamina);
			if (bonusHunger > 20)
				bonusHunger = 20;
			drawTetragon(posX + 2, posX + 2, 13, 13, (int) (64 * ((double)bonusHunger / (double) 20)), (int) (63 * ((double)bonusHunger / (double) 20)) - 10, 6, 6, staminaColor[2]);
		}
		drawTetragon(posX, posX, 13, 13, 70, 58, 8, 8, 0xA0000000);
		drawTetragon(posX + 2, posX + 2, 13, 13, 64, 54, 6, 6, 0x20FFFFFF);
		if (this.mc.player.isPotionActive(MobEffects.HUNGER)) {
			drawTetragon(posX + 2, posX + 2, 13, 13, (int) (64 * ((double)stamina / (double) 20)), (int) (64 * ((double)stamina / (double) 20)) - 10, 6, 6, staminaColor[1]);
		} else {
			drawTetragon(posX + 2, posX + 2, 13, 13, (int) (64 * ((double)stamina / (double) 20)), (int) (64 * ((double)stamina / (double) 20)) - 10, 6, 6, staminaColor[0]);
		}
	}

	@Override
	public int[] getColor(int setting) {
		int[] color = new int[3];
		switch (this.settings.color_stamina) {
		case 0:
			color[0] = HudElementBarred.COLOR_RED[0];
			color[1] = HudElementBarred.COLOR_RED[0] + 0x4400;
			color[2] = HudElementBarred.COLOR_RED[0] + 0x330000;
			break;
		case 1:
			color[0] = HudElementBarred.COLOR_BLUE[0];
			color[1] = HudElementBarred.COLOR_BLUE[0] + 0x4400;
			color[2] = HudElementBarred.COLOR_BLUE[0] + 0x1D3D;
			break;
		case 2:
			color[0] = HudElementBarred.COLOR_GREEN[0];
			color[1] = HudElementBarred.COLOR_GREEN[0] + 0x440000;
			color[2] = HudElementBarred.COLOR_GREEN[0] + 0x663333;
			break;
		case 3:
			color[0] = HudElementBarred.COLOR_YELLOW[0];
			color[1] = HudElementBarred.COLOR_YELLOW[0] + 0x1100;
			color[2] = HudElementBarred.COLOR_YELLOW[0] - 0x333300;
			break;
		case 4:
			color[0] = HudElementBarred.COLOR_WHITE[0];
			color[1] = HudElementBarred.COLOR_WHITE[0] - 0x222222;
			color[2] = HudElementBarred.COLOR_WHITE[0] - 0x333333;
			break;
		case 5:
			color[0] = HudElementBarred.COLOR_GREY[0];
			color[1] = HudElementBarred.COLOR_GREY[0] - 0x222222;
			color[2] = HudElementBarred.COLOR_GREY[0] + 0x222222;
		}
		return color;
	}

}
