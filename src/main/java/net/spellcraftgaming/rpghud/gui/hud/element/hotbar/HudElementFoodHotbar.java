package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementFoodHotbar extends HudElementBarred{

	public HudElementFoodHotbar() {
		super(HudElementType.FOOD, 0, 0, 0, 0, true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int[] staminaColor = getColor(this.settings.color_stamina);
		int stamina = this.mc.player.getFoodStats().getFoodLevel();
		ScaledResolution res = new ScaledResolution(this.mc);
		int height = res.getScaledHeight();
		ItemStack currentItem = this.mc.player.getHeldItemMainhand();
			if(currentItem != null && currentItem.getItem() instanceof ItemFood && this.mc.player.getFoodStats().needFood() && this.settings.show_hunger_preview) {
				float value = ((ItemFood)this.mc.player.getHeldItemMainhand().getItem()).getHealAmount(this.mc.player.getHeldItemMainhand());
				int bonusHunger = (int) (value + stamina);
				if(bonusHunger > 20) bonusHunger = 20;
				drawCustomBar(49, height - 26, 200, 10, bonusHunger / 20.0D * 100.0D, 0, 0, staminaColor[4], staminaColor[5]);
			}
		if (this.mc.player.isPotionActive(MobEffects.HUNGER)) {
			drawCustomBar(49, height - 26, 200, 10, stamina / 20.0D * 100.0D, 0, 0, staminaColor[2], staminaColor[3]);
		} else {
			drawCustomBar(49, height - 26, 200, 10, stamina / 20.0D * 100.0D, 0, 0, staminaColor[0], staminaColor[1]);
		}
		String staminaString = stamina + "/" + "20";
		if(this.settings.show_numbers_stamina) gui.drawCenteredString(this.mc.fontRendererObj, staminaString, 49 + 100, height - 25, -1);
	}
	
	@Override
	public int[] getColor(int setting) {
		int[] color = new int[6];
		switch (this.settings.color_stamina)
		{
		case 0:
			color[0] = this.colorRed[0];
			color[1] = this.colorRed[1];
			color[2] = this.colorRed[0] + 0x4400;
			color[3] = this.colorRed[1] + 0x4400;
			color[4] = this.colorRed[0] + 0x330000;
			color[5] = this.colorRed[1] + 0x2a0000;
			break;
		case 1:
			color[0] = this.colorBlue[0];
			color[1] = this.colorBlue[1];
			color[2] = this.colorBlue[0] + 0x4400;
			color[3] = this.colorBlue[1] + 0x4400;
			color[4] = this.colorBlue[0] + 0x1D3D;
			color[5] = this.colorBlue[1] + 0x1D3D;
			break;
		case 2:
			color[0] = this.colorGreen[0];
			color[1] = this.colorGreen[1];
			color[2] = this.colorGreen[0] + 0x440000;
			color[3] = this.colorGreen[1] + 0x440000;
			color[4] = this.colorGreen[0] + 0x663333;
			color[5] = this.colorGreen[1] + 0x663333;
			break;
		case 3:
			color[0] = this.colorYellow[0];
			color[1] = this.colorYellow[1];
			color[2] = this.colorYellow[0] + 0x1100;
			color[3] = this.colorYellow[1] + 0x2200;
			color[4] = this.colorYellow[0] - 0x333300;
			color[5] = this.colorYellow[1] - 0x444400;
			break;
		case 4:
			color[0] = this.colorWhite[0];
			color[1] = this.colorWhite[1];
			color[2] = this.colorWhite[0] - 0x222222;
			color[3] = this.colorWhite[1] - 0x222222;
			color[4] = this.colorWhite[0] - 0x333333;
			color[5] = this.colorWhite[1] - 0x555555;
			break;
		case 5:
			color[0] = this.colorGrey[0];
			color[1] = this.colorGrey[1];
			color[2] = this.colorGrey[0] - 0x222222;
			color[3] = this.colorGrey[1] - 0x222222;
			color[4] = this.colorGrey[0] + 0x222222;
			color[5] = this.colorGrey[1] + 0x222222;
		}
		return color;
	}

}
