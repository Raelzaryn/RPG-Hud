package net.spellcraftgaming.rpghud.client.gui.overlay.simple;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElement;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElementType;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayParentAnchor;
import net.spellcraftgaming.rpghud.client.helper.DrawHelper;

public class OverlayElementSimpleLevel extends OverlayElement{

	public OverlayElementSimpleLevel() {
		super(OverlayElementType.LEVEL, 12, 10);
	}

	@Override
	public void initialize() {
		this.anchoredX = 0; //TODO: SETTING
		this.anchoredY = 0; //TODO: SETTING
		this.scale = 2; //TODO: SETTING
		this.isGuiScale = true; //TODO: SETTING
			
		this.yAnchor = Y_ANCHOR_BOTTOM; // TODO: SETTING
		this.xAnchor = X_ANCHOR_CENTER; // TODO: SETTING
		this.parentAnchor = new OverlayParentAnchor(OverlayElementType.EXPERIENCE, X_ANCHOR_CENTER, Y_ANCHOR_TOP, 0, -1);
	}
	
	@Override
	public boolean shouldRender() {
		MinecraftClient instance = MinecraftClient.getInstance();
		return instance.interactionManager.hasStatusBars();
	}
	
	@Override
	public void render(DrawContext dc, float tickDelta) {
		MinecraftClient instance = MinecraftClient.getInstance();
		String level = String.valueOf(instance.player.experienceLevel);
		int posX = getPosX();
		int posY = getPosY();
		DrawHelper.drawRect(dc, posX, posY, width, height, 0xA0000000);
		dc.drawCenteredTextWithShadow(instance.textRenderer, level, posX + (width/2), posY, 0x80FF20);
	}

}
