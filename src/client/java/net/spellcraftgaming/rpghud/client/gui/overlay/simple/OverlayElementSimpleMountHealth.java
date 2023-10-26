package net.spellcraftgaming.rpghud.client.gui.overlay.simple;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElement;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElementType;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayParentAnchor;
import net.spellcraftgaming.rpghud.client.helper.DrawHelper;

public class OverlayElementSimpleMountHealth extends OverlayElement {

	public OverlayElementSimpleMountHealth() {
		super(OverlayElementType.MOUNT_HEALTH, 84, 10);
	}
	
	public boolean show_numbers_mount_health;
	public boolean mount_health_percentage;
	public int color_mount_health;
	
	@Override
	public void initialize() {
		this.anchoredX = 0; //TODO: SETTING
		this.anchoredY = 0; //TODO: SETTING
		this.scale = 2; //TODO: SETTING
		this.isGuiScale = true; //TODO: SETTING
			
		this.yAnchor = Y_ANCHOR_BOTTOM; // TODO: SETTING
		this.xAnchor = X_ANCHOR_RIGHT; // TODO: SETTING
		this.parentAnchor = new OverlayParentAnchor(OverlayElementType.HUNGER, X_ANCHOR_RIGHT, Y_ANCHOR_TOP, 0, -1);
		
		this.show_numbers_mount_health = true; // TODO: SETTING
		this.mount_health_percentage = false; // TODO: SETTING
	}
	
	@Override
	public boolean shouldRender() {
		MinecraftClient instance = MinecraftClient.getInstance();
		return instance.player.getVehicle() instanceof LivingEntity && instance.interactionManager.hasStatusBars();
	}
	
	@Override
	public void render(DrawContext dc, float tickDelta) {
		MinecraftClient instance = MinecraftClient.getInstance();
		
		LivingEntity mount = (LivingEntity) instance.player.getVehicle();
		int health = MathHelper.ceil(mount.getHealth());
		int healthMax = MathHelper.ceil(mount.getMaxHealth());
		if(health > healthMax) health = healthMax;
		
		int width = 84;
		
		int posX = getPosX();
		int posY = getPosY();
		
		DrawHelper.drawCustomBarBackdrop(dc, posX, posY, width, height, (double) health / (double) healthMax * 100.0D, color_mount_health, DrawHelper.offsetColorPercent(color_mount_health, 25));
		
		String stringHealth = mount_health_percentage ? (int) Math.floor((double) health / (double) healthMax * 100) + "%" : health + "/" + healthMax;

		if (show_numbers_mount_health) {
			dc.drawCenteredTextWithShadow(instance.textRenderer, stringHealth, posX + (width/2), posY + 1, -1);
		}
	}
}
