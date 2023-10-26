package net.spellcraftgaming.rpghud.client.gui.overlay.simple;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElement;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElementType;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayParentAnchor;
import net.spellcraftgaming.rpghud.client.helper.DrawHelper;

public class OverlayElementSimpleArmor extends OverlayElement{

	public OverlayElementSimpleArmor() {
		super(OverlayElementType.ARMOR, 14, 10);
	}

	@Override
	public void initialize() {
		this.anchoredX = 0; //TODO: SETTING
		this.anchoredY = 0; //TODO: SETTING
		this.scale = 2; //TODO: SETTING
		this.isGuiScale = true; //TODO: SETTING
			
		this.yAnchor = Y_ANCHOR_BOTTOM; // TODO: SETTING
		this.xAnchor = X_ANCHOR_LEFT; // TODO: SETTING
		this.parentAnchor = new OverlayParentAnchor(OverlayElementType.HEALTH, X_ANCHOR_LEFT, Y_ANCHOR_TOP, 0, -1);
	}
	
	@Override
	public boolean shouldRender() {
		MinecraftClient instance = MinecraftClient.getInstance();
		return instance.interactionManager.hasStatusBars();
	}
	
	@Override
	public void render(DrawContext dc, float tickDelta) {
		MinecraftClient instance = MinecraftClient.getInstance();
		int posX = getPosX();
		int posY = getPosY();
		
		int level = instance.player.getArmor();
		if (level > 0) {
			width = 14 + instance.textRenderer.getWidth(String.valueOf(level));
			DrawHelper.drawRect(dc, posX, posY, width, height, DrawHelper.COLOR_BACKDROP);
			dc.drawText(instance.textRenderer, String.valueOf(level), posX + 12, posY + 2, -1, false);
			dc.drawGuiTexture(DrawHelper.ARMOR_FULL_TEXTURE, posX + 1, posY + 1, 9, 9);
		}
		
	}

}
