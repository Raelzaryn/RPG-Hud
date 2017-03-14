package net.spellcraftgaming.rpghud.gui.hud.element;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;

public abstract class HudElementTexture extends HudElement {

	/** ResourceLocation of the interface texture for the RPG-HUD */
	protected static final ResourceLocation INTERFACE = new ResourceLocation("rpghud:textures/interface.png");

	public HudElementTexture(HudElementType type, int posX, int posY, int width, int height, boolean moveable) {
		super(type, posX, posY, width, height, moveable);
	}

	/**
	 * Binds a texture to the TextureManager
	 * 
	 * @param res
	 *            The ResourceLocation of the texture that should be bind
	 */
	protected void bind(ResourceLocation res) {
		this.mc.getTextureManager().bindTexture(res);
	}
	
	/**
	 * Returns the ResourceLocation for the skin of the player
	 * 
	 * @param player
	 *            the player whose skin should be returned
	 * @return the ResourceLocation
	 */
	protected static ResourceLocation getPlayerSkin(AbstractClientPlayer player) {
		return player.getLocationSkin();
	}
}
