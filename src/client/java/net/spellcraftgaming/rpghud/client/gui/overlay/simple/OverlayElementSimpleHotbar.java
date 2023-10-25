package net.spellcraftgaming.rpghud.client.gui.overlay.simple;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameMode;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElement;
import net.spellcraftgaming.rpghud.client.helper.DrawHelper;

public class OverlayElementSimpleHotbar extends OverlayElement{
    	
	public OverlayElementSimpleHotbar() {
		super(Type.HOTBAR, 182, 22);		
	}
	
	@Override
	public void initialize() {
		this.anchoredX = 500; //TODO: SETTING
		this.anchoredY = 1000; //TODO: SETTING
		this.scale = 2; //TODO: SETTING
		this.isGuiScale = true; //TODO: SETTING
		this.xAnchor = X_ANCHOR_CENTER; //TODO: SETTING
		this.yAnchor = Y_ANCHOR_BOTTOM; //TODO: SETTING
	}

	@Override
	public void render(DrawContext dc, float tickDelta) {
		//initialize();
		MinecraftClient instance = MinecraftClient.getInstance();
		int posX = getPosX();
		int posY = getPosY();
		// render
		if(instance.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR) {
            instance.inGameHud.getSpectatorHud().render(dc);
		} else if (instance.getCameraEntity() instanceof PlayerEntity) {
			
			
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
		}
	}
}
