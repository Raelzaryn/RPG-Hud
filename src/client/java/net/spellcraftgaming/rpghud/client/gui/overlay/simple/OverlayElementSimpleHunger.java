package net.spellcraftgaming.rpghud.client.gui.overlay.simple;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.item.ItemStack;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElement;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElementType;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayParentAnchor;
import net.spellcraftgaming.rpghud.client.helper.DrawHelper;

public class OverlayElementSimpleHunger extends OverlayElement{

	public boolean show_numbers_hunger;
	public boolean hunger_percentage;
	public int color_hunger;
	public int color_hunger_debuff;
	public boolean show_hunger_preview;
	
	public OverlayElementSimpleHunger() {
		super(OverlayElementType.HUNGER, 84, 10);
	}
	
	@Override
	public void initialize() {
		this.anchoredX = 0; //TODO: SETTING
		this.anchoredY = 0; //TODO: SETTING
		this.scale = 2; //TODO: SETTING
		this.isGuiScale = true; //TODO: SETTING
			
		this.yAnchor = Y_ANCHOR_BOTTOM; // TODO: SETTING
		this.xAnchor = X_ANCHOR_RIGHT; // TODO: SETTING
		this.parentAnchor = new OverlayParentAnchor(OverlayElementType.EXPERIENCE, X_ANCHOR_RIGHT, Y_ANCHOR_TOP, 0, -1);
		
		this.show_numbers_hunger = true; // TODO: SETTING
		this.hunger_percentage = false; // TODO: SETTING
		this.color_hunger = DrawHelper.COLOR_GREEN; // TODO: SETTING
		this.color_hunger_debuff = 0x9ba067;
		this.show_hunger_preview = true; // TODO: SETTING
	}
	
	@Override
	public boolean shouldRender() {
		MinecraftClient instance = MinecraftClient.getInstance();
		return instance.interactionManager.hasStatusBars();
	}
	@Override
	public void render(DrawContext dc, float tickDelta) {
		MinecraftClient instance = MinecraftClient.getInstance();
		HungerManager stats = instance.player.getHungerManager();
		int stamina = stats.getFoodLevel();
		int staminaMax = 20;
		
		int posX = getPosX();
		int posY = getPosY();
		
		ItemStack itemMain = instance.player.getMainHandStack();
		ItemStack itemSec = instance.player.getOffHandStack();
		
		DrawHelper.drawRect(dc, posX, posY, width, height, DrawHelper.COLOR_BACKDROP);
		if (stats.isNotFull() && show_hunger_preview) {
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
				int colorPreview = DrawHelper.offsetColor(color_hunger, 0x5A5A5A);
				DrawHelper.drawCustomBar(dc, posX, posY, width, height, bonusHunger / (double) staminaMax * 100.0D, -1, -1, colorPreview, DrawHelper.offsetColorPercent(colorPreview, 25), false);
			}
		}
		
		if (instance.player.hasStatusEffect(StatusEffects.HUNGER)) {
			DrawHelper.drawCustomBar(dc, posX, posY, width, height, stamina / (double) staminaMax * 100.0D, -1, -1, color_hunger_debuff, DrawHelper.offsetColorPercent(color_hunger_debuff, 25), false);
		} else {
			DrawHelper.drawCustomBar(dc, posX, posY, width, height, stamina / (double) staminaMax * 100.0D, -1, -1, color_hunger, DrawHelper.offsetColorPercent(color_hunger, 25), false);
		}
		
		String hungerString = hunger_percentage ? (int) Math.floor((double) stamina / (double) staminaMax * 100) + "%" : stamina + "/" + staminaMax;
		if (show_numbers_hunger) {
			dc.drawCenteredTextWithShadow(instance.textRenderer, hungerString, posX + (width/2), posY, -1);
		}
	}

}
