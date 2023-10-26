package net.spellcraftgaming.rpghud.client.gui.overlay.simple;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.registry.tag.FluidTags;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElement;
import net.spellcraftgaming.rpghud.client.gui.overlay.OverlayElementType;
import net.spellcraftgaming.rpghud.client.helper.DrawHelper;

public class OverlayElementSimpleBreath extends OverlayElement {

	public int color_air;
	
	public OverlayElementSimpleBreath() {
		super(OverlayElementType.BREATH, 144, 10);
	}

	@Override
	public void initialize() {
		this.anchoredX = 500; //TODO: SETTING
		this.anchoredY = 700; //TODO: SETTING
		this.scale = 2; //TODO: SETTING
		this.isGuiScale = true; //TODO: SETTING
		this.yAnchor = Y_ANCHOR_TOP; // TODO: SETTING
		this.xAnchor = X_ANCHOR_CENTER; // TODO: SETTING
		
		this.color_air = DrawHelper.COLOR_BLUE;
	}

	
	@Override
	public boolean shouldRender() {
		MinecraftClient instance = MinecraftClient.getInstance();
		return (instance.player.isSubmergedIn(FluidTags.WATER) || instance.player.getAir() < instance.player.getMaxAir()) && instance.interactionManager.hasStatusBars();
	
	}
	@Override
	public void render(DrawContext dc, float tickDelta) {
		MinecraftClient instance = MinecraftClient.getInstance();
		int posX = getPosX();
		int posY = getPosY();
		int airAmount = instance.player.getAir();
        double maxAir = instance.player.getMaxAir();
        
        if(airAmount < 0) airAmount = 0;
        
        DrawHelper.drawCustomBarBackdropBright(dc, posX, posY, width, height, (airAmount / maxAir), color_air);

	}


}
