package net.spellcraftgaming.rpghud.client.gui.overlay.simple;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElement;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElementType;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayParentAnchor;
import net.spellcraftgaming.rpghud.client.helper.DrawHelper;

public class OverlayElementSimpleHotbarOffhand extends OverlayElement {

	public OverlayElementSimpleHotbarOffhand() {
		super(OverlayElementType.HOTBAR_OFFHAND, 22, 22);
	}

	@Override
	public void initialize() {
		this.anchoredX = 0; //TODO: SETTING
		this.anchoredY = 0; //TODO: SETTING
		this.scale = 2; //TODO: SETTING
		this.isGuiScale = true; //TODO: SETTING
			
		this.yAnchor = Y_ANCHOR_CENTER; // TODO: SETTING
		this.xAnchor = X_ANCHOR_RIGHT;
		this.parentAnchor = new OverlayParentAnchor(OverlayElementType.HOTBAR, X_ANCHOR_LEFT, Y_ANCHOR_CENTER, -2, 0);
	}
	
	@Override
	public void render(DrawContext dc, float tickDelta) {
		MinecraftClient instance = MinecraftClient.getInstance();
		
		PlayerEntity entityplayer = (PlayerEntity) instance.getCameraEntity();
		
		if(entityplayer.getMainArm() == Arm.RIGHT) {
			this.xAnchor = X_ANCHOR_RIGHT; //TODO: SETTING
			parentAnchor.parentAnchorX = X_ANCHOR_LEFT;
			parentAnchor.parentOffsetX = -2;
		} else {
			this.xAnchor = X_ANCHOR_LEFT; //TODO: SETTING
			parentAnchor.parentAnchorX = X_ANCHOR_RIGHT;
			parentAnchor.parentOffsetX = 2;
		}
		
		int posX = getPosX();
		int posY = getPosY();
		

		ItemStack itemstack = instance.player.getOffHandStack();
		
		if (itemstack != ItemStack.EMPTY) {
			DrawHelper.drawRect(dc, posX, posY, width, 2, 0xA0000000);
			DrawHelper.drawRect(dc, posX, posY + 2, 2, height - 4, 0xA0000000);
			DrawHelper.drawRect(dc, posX + width - 2, posY + 2, 2, height - 4, 0xA0000000);
			DrawHelper.drawRect(dc, posX, posY + height -2, width, 2, 0xA0000000);
			DrawHelper.drawRect(dc, posX + 2, posY +2, width - 4, height -4, 0x60000000);
			DrawHelper.renderHotbarItem(dc, posX + 3, posY + 3, tickDelta, entityplayer, itemstack);
		}
	}
}
