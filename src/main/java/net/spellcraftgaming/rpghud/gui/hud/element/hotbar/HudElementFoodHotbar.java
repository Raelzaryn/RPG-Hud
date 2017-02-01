package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementFoodHotbar extends HudElementBarred {

	public HudElementFoodHotbar() {
		super(HudElementType.FOOD, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}
	
	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int[] staminaColor = getColor(this.settings.color_stamina);
		int stamina = this.mc.thePlayer.getFoodStats().getFoodLevel();
		ScaledResolution res = new ScaledResolution(this.mc);
		int height = res.getScaledHeight();
		ItemStack currentItem = this.mc.thePlayer.getHeldItemMainhand();
		if (currentItem != null && currentItem.getItem() instanceof ItemFood && this.mc.thePlayer.getFoodStats().needFood() && this.settings.show_hunger_preview) {
			float value = ((ItemFood) this.mc.thePlayer.getHeldItemMainhand().getItem()).getHealAmount(this.mc.thePlayer.getHeldItemMainhand());
			int bonusHunger = (int) (value + stamina);
			if (bonusHunger > 20)
				bonusHunger = 20;
			drawCustomBar(49, height - 26, 200, 10, bonusHunger / 20.0D * 100.0D, 0, 0, staminaColor[4], staminaColor[5]);
		}
		if (this.mc.thePlayer.isPotionActive(MobEffects.HUNGER)) {
			drawCustomBar(49, height - 26, 200, 10, stamina / 20.0D * 100.0D, 0, 0, staminaColor[2], staminaColor[3]);
		} else {
			drawCustomBar(49, height - 26, 200, 10, stamina / 20.0D * 100.0D, 0, 0, staminaColor[0], staminaColor[1]);
		}
		String staminaString = stamina + "/" + "20";
		if (this.settings.show_numbers_stamina)
			gui.drawCenteredString(this.mc.fontRendererObj, staminaString, 49 + 100, height - 25, -1);
	}

	@Override
	public int[] getColor(int setting) {
		int[] color = new int[6];
		switch (this.settings.color_stamina) {
		case 0:
			color[0] = HudElementBarred.COLOR_RED[0];
			color[1] = HudElementBarred.COLOR_RED[1];
			color[2] = HudElementBarred.COLOR_RED[0] + 0x4400;
			color[3] = HudElementBarred.COLOR_RED[1] + 0x4400;
			color[4] = HudElementBarred.COLOR_RED[0] + 0x330000;
			color[5] = HudElementBarred.COLOR_RED[1] + 0x2a0000;
			break;
		case 1:
			color[0] = HudElementBarred.COLOR_BLUE[0];
			color[1] = HudElementBarred.COLOR_BLUE[1];
			color[2] = HudElementBarred.COLOR_BLUE[0] + 0x4400;
			color[3] = HudElementBarred.COLOR_BLUE[1] + 0x4400;
			color[4] = HudElementBarred.COLOR_BLUE[0] + 0x1D3D;
			color[5] = HudElementBarred.COLOR_BLUE[1] + 0x1D3D;
			break;
		case 2:
			color[0] = HudElementBarred.COLOR_GREEN[0];
			color[1] = HudElementBarred.COLOR_GREEN[1];
			color[2] = HudElementBarred.COLOR_GREEN[0] + 0x440000;
			color[3] = HudElementBarred.COLOR_GREEN[1] + 0x440000;
			color[4] = HudElementBarred.COLOR_GREEN[0] + 0x663333;
			color[5] = HudElementBarred.COLOR_GREEN[1] + 0x663333;
			break;
		case 3:
			color[0] = HudElementBarred.COLOR_YELLOW[0];
			color[1] = HudElementBarred.COLOR_YELLOW[1];
			color[2] = HudElementBarred.COLOR_YELLOW[0] + 0x1100;
			color[3] = HudElementBarred.COLOR_YELLOW[1] + 0x2200;
			color[4] = HudElementBarred.COLOR_YELLOW[0] - 0x333300;
			color[5] = HudElementBarred.COLOR_YELLOW[1] - 0x444400;
			break;
		case 4:
			color[0] = HudElementBarred.COLOR_WHITE[0];
			color[1] = HudElementBarred.COLOR_WHITE[1];
			color[2] = HudElementBarred.COLOR_WHITE[0] - 0x222222;
			color[3] = HudElementBarred.COLOR_WHITE[1] - 0x222222;
			color[4] = HudElementBarred.COLOR_WHITE[0] - 0x333333;
			color[5] = HudElementBarred.COLOR_WHITE[1] - 0x555555;
			break;
		case 5:
			color[0] = HudElementBarred.COLOR_GREY[0];
			color[1] = HudElementBarred.COLOR_GREY[1];
			color[2] = HudElementBarred.COLOR_GREY[0] - 0x222222;
			color[3] = HudElementBarred.COLOR_GREY[1] - 0x222222;
			color[4] = HudElementBarred.COLOR_GREY[0] + 0x222222;
			color[5] = HudElementBarred.COLOR_GREY[1] + 0x222222;
		}
		return color;
	}

}
