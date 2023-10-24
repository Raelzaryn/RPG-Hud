package net.spellcraftgaming.rpghud.client.gui.overlay.simple;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameMode;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElement;
import net.spellcraftgaming.rpghud.client.helper.DrawHelper;

public class OverlayElementSimpleHotbar extends OverlayElement{
    
	boolean scaleIsGui = false; //TODO: SETTING
	
	public OverlayElementSimpleHotbar() {
		super(Type.HOTBAR, 182, 2);
		this.xAnchor = X_ANCHOR_RIGHT;
		this.yAnchor = Y_ANCHOR_TOP;
		
	}

	@Override
	public void render(DrawContext dc, float tickDelta) {
		MinecraftClient instance = MinecraftClient.getInstance();

		int posX = getPosX();
		int posY = getPosY();
		float scaleFloat = getScale();
		float invertedScale = getInvertedScale();
		// render
		if(instance.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR) {
            instance.inGameHud.getSpectatorHud().render(dc);
		} else if (instance.getCameraEntity() instanceof PlayerEntity) {
			if(!scaleIsGui) dc.getMatrices().scale(scaleFloat, scaleFloat, scaleFloat);
			
			PlayerEntity entityplayer = (PlayerEntity) instance.getCameraEntity();

			DrawHelper.drawRect(dc, posX, posY, width, 2, DrawHelper.COLOR_BACKDROP);
			DrawHelper.drawRect(dc, posX, posY + 20, width, 2, DrawHelper.COLOR_BACKDROP);

			for (int i = 0; i < 10; i++) {
				DrawHelper.drawRect(dc, posX + (i * 20), posY + 2, 2, 18, DrawHelper.COLOR_BACKDROP);
				if (i < 9) {
					DrawHelper.drawRect(dc, posX + 2 + (i * 20), posY + 2, 18, 18, 0x60000000);
				}
			}
			DrawHelper.drawRect(dc, posX + 2 + (entityplayer.getInventory().selectedSlot * 20), posY + 2, 18, 18, 0x40FFFFFF);
			
			for (int i = 0; i < 9; ++i) {
				DrawHelper.renderHotbarItem(dc, posX + i * 20 + 3, posY + 3, tickDelta, entityplayer, instance.player.getInventory().main.get(i));
			}
			
			if(!scaleIsGui) dc.getMatrices().scale(invertedScale, invertedScale, invertedScale);
		}
	}

	@Override
	public int getPosX() {
		MinecraftClient instance = MinecraftClient.getInstance();
		int scaledWidth = instance.getWindow().getScaledWidth();

		final int anchoredX = 1000; //TODO: SETTING
		
		int posX = (int) ((scaledWidth / 1000D) * anchoredX * getInvertedScale());
		
		if(this.xAnchor == X_ANCHOR_CENTER) posX -= (width / 2);
		if(this.xAnchor == X_ANCHOR_RIGHT) posX -= width;
		
		return posX;
	}

	@Override
	public int getPosY() {
		MinecraftClient instance = MinecraftClient.getInstance();
		int scaledHeight = instance.getWindow().getScaledHeight();
		
		final int anchoredY = 0; //TODO: SETTING
		
		int posY = (int) ((scaledHeight / 1000D) * anchoredY * getInvertedScale());
		
		if(this.yAnchor == Y_ANCHOR_CENTER) posY -= (height / 2);
		if(this.yAnchor == Y_ANCHOR_BOTTOM) posY -= height;
		
		return posY;
	}

	@Override
	public float getScale() {
		MinecraftClient instance = MinecraftClient.getInstance();
		double guiScale = instance.getWindow().getScaleFactor();
		
		final int scale = 2; //TODO SETTING

		float scaleFloat = (float) (scale / guiScale);
		// TODO Auto-generated method stub
		if(scaleIsGui) {
			scaleFloat = (float) guiScale;
		}
		return scaleFloat;
	}

}
