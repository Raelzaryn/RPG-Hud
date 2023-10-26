package net.spellcraftgaming.rpghud.client.gui.overlay.simple;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Arm;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElement;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElementType;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayParentAnchor;
import net.spellcraftgaming.rpghud.client.helper.DrawHelper;

public class OverlayElementSimpleHotbarAttackIndicator extends OverlayElement {

	public OverlayElementSimpleHotbarAttackIndicator() {
		super(OverlayElementType.ATTACK_INDICATOR, 18, 18);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean shouldRender() {
		MinecraftClient instance = MinecraftClient.getInstance();
		return instance.options.getAttackIndicator().getValue() == AttackIndicator.HOTBAR;
	}
	@Override
	public void initialize() {
		this.anchoredX = 0; //TODO: SETTING
		this.anchoredY = 0; //TODO: SETTING
		this.scale = 2; //TODO: SETTING
		this.isGuiScale = true; //TODO: SETTING
			
		this.yAnchor = Y_ANCHOR_CENTER; // TODO: SETTING
		this.xAnchor = X_ANCHOR_LEFT;
		this.parentAnchor = new OverlayParentAnchor(OverlayElementType.HOTBAR, X_ANCHOR_RIGHT, Y_ANCHOR_CENTER, 2, 0);
	}
	
	@Override
	public void render(DrawContext dc, float tickDelta) {
		MinecraftClient instance = MinecraftClient.getInstance();
		int posX = getPosX();
		int posY = getPosY();
        float f1 = instance.player.getAttackCooldownProgress(0.0F);
        PlayerEntity entityplayer = (PlayerEntity) instance.getCameraEntity();
        
		if(entityplayer.getMainArm() == Arm.RIGHT) {
			this.xAnchor = X_ANCHOR_LEFT; //TODO: SETTING
			parentAnchor.parentAnchorX = X_ANCHOR_RIGHT;
			parentAnchor.parentOffsetX = -2;
		} else {
			this.xAnchor = X_ANCHOR_RIGHT; //TODO: SETTING
			parentAnchor.parentAnchorX = X_ANCHOR_LEFT;
			parentAnchor.parentOffsetX = 2;
		}
        
		if (f1 < 1.0F) {
			int animationY = (int) (f1 * 19.0F);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			dc.drawGuiTexture(DrawHelper.HOTBAR_ATTACK_INDICATOR_BACKGROUND_TEXTURE, posX, posY, width, height);
			dc.drawGuiTexture(DrawHelper.HOTBAR_ATTACK_INDICATOR_PROGRESS_TEXTURE, posX, posY - animationY + height, width, animationY);
		}
	}
}
