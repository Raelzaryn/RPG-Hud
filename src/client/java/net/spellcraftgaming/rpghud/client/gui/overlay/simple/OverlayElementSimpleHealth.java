package net.spellcraftgaming.rpghud.client.gui.overlay.simple;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElement;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElementType;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayParentAnchor;
import net.spellcraftgaming.rpghud.client.helper.DrawHelper;

public class OverlayElementSimpleHealth extends OverlayElement {

	public boolean show_numbers_health;
	public boolean health_percentage;
	public int color_health;
	public int color_absorption;
	public int color_poison;
	public int color_wither;
	
	public OverlayElementSimpleHealth() {
		super(OverlayElementType.HEALTH, 84, 10);
	}
	
	@Override
	public void initialize() {
		this.anchoredX = 0; //TODO: SETTING
		this.anchoredY = 0; //TODO: SETTING
		this.scale = 2; //TODO: SETTING
		this.isGuiScale = true; //TODO: SETTING
			
		this.yAnchor = Y_ANCHOR_BOTTOM; // TODO: SETTING
		this.xAnchor = X_ANCHOR_LEFT; // TODO: SETTING
		this.parentAnchor = new OverlayParentAnchor(OverlayElementType.EXPERIENCE, X_ANCHOR_LEFT, Y_ANCHOR_TOP, 0, -1);
		
		this.show_numbers_health = true; // TODO: SETTING
		this.health_percentage = false; // TODO: SETTING
		this.color_health = DrawHelper.COLOR_RED; // TODO: SETTING
		this.color_absorption = DrawHelper.COLOR_ORANGE; // TODO: SETTING
		this.color_poison = DrawHelper.COLOR_PURPLE; // TODO: SETTING
		this.color_wither = DrawHelper.COLOR_BLACK; // TODO: SETTING
	}
	
	@Override
	public boolean shouldRender() {
		MinecraftClient instance = MinecraftClient.getInstance();
		return instance.interactionManager.hasStatusBars();
	}
	
	@Override
	public void render(DrawContext dc, float tickDelta) {
		MinecraftClient instance = MinecraftClient.getInstance();
		int health = MathHelper.ceil(instance.player.getHealth());
		int absorption = MathHelper.ceil(instance.player.getAbsorptionAmount());
		int healthMax = MathHelper.ceil(instance.player.getMaxHealth());
		
		int posX = getPosX();
		int posY = getPosY();
		if (absorption > 1)
			DrawHelper.drawCustomBarBackdrop(dc, posX, posY, width, height, (double) (health + absorption) / (double) (healthMax + absorption) * 100D, color_absorption, DrawHelper.offsetColorPercent(color_absorption, 25));
		if (instance.player.hasStatusEffect(StatusEffects.POISON)) {
			DrawHelper.drawCustomBarBackdrop(dc, posX, posY, width, height, (double) health / (double) (healthMax + absorption) * 100D, color_poison, DrawHelper.offsetColorPercent(color_poison, 25));
		} else if (instance.player.hasStatusEffect(StatusEffects.WITHER)) {
			DrawHelper.drawCustomBarBackdrop(dc, posX, posY, width, height, (double) health / (double) (healthMax + absorption) * 100D, color_wither, DrawHelper.offsetColorPercent(color_wither, 25));
		} else {
			DrawHelper.drawCustomBarBackdrop(dc, posX, posY, width, height, (double) health / (double) (healthMax + absorption) * 100D, color_health, DrawHelper.offsetColorPercent(color_health, 25));
		}

		String stringHealth = health_percentage ? (int) Math.floor((double) health / (double) healthMax * 100) + "%" : (health + absorption) + "/" + healthMax;
		if (show_numbers_health) {
			dc.drawCenteredTextWithShadow(instance.textRenderer, stringHealth, posX + (width/2), posY + 1, -1);
		}

	}


}
