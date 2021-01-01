package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

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
public class HudElementFoodTexture extends HudElement {

	public HudElementFoodTexture() {
		super(HudElementType.FOOD, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(AbstractGui gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		bind(INTERFACE);
		RenderSystem.color3f(1f, 1f, 1f);
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
				gui.func_238474_b_(ms, posX, posY, 141, 148, (int) (110.0D * (bonusHunger / (double) staminaMax)), 12);
			}
		}

		if (this.mc.player.isPotionActive(Effects.HUNGER)) {
			gui.func_238474_b_(ms, posX, posY, 141, 136, (int) (110.0D * (stamina / (double) staminaMax)), 12);
		} else {
			gui.func_238474_b_(ms, posX, posY, 110, 100, (int) (110.0D * (stamina / (double) staminaMax)), 12);
		}
		
		String staminaString = this.settings.getBoolValue(Settings.hunger_percentage) ? (int) Math.floor((double) stamina / (double) staminaMax * 100) + "%" : stamina + "/" + staminaMax;
		if (this.settings.getBoolValue(Settings.show_numbers_food))
			AbstractGui.func_238471_a_(ms, this.mc.fontRenderer, staminaString, posX + 55, posY + 2, -1);
		RenderSystem.color3f(1f, 1f, 1f);
		this.mc.getTextureManager().bindTexture(AbstractGui.field_230664_g_);
	}

}
