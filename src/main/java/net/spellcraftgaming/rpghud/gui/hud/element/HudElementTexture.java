package net.spellcraftgaming.rpghud.gui.hud.element;

import net.minecraft.util.ResourceLocation;

public abstract class HudElementTexture extends HudElement{

	protected static final ResourceLocation INTERFACE = new ResourceLocation("rpghud:textures/interface.png");
	
	public HudElementTexture(HudElementType type, int posX, int posY, int width, int height, boolean moveable) {
		super(type, posX, posY, width, height, moveable);
	}
	
	protected void bind(ResourceLocation res) {
		mc.getTextureManager().bindTexture(res);
	}
}
