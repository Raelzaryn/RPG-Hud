package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementTexture;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementHealthMountTexture extends HudElementTexture {

	public HudElementHealthMountTexture() {
		super(HudElementType.HEALTH_MOUNT, 0, 0, 0, 0, false);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.player.getRidingEntity() instanceof EntityLivingBase;
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		bind(INTERFACE);
		EntityLivingBase mount = (EntityLivingBase) this.mc.player.getRidingEntity();
		int health = (int) Math.ceil(mount.getHealth());
		int healthMax = (int) mount.getMaxHealth();
		gui.drawTexturedModalRect(53, 54, 0, 124, (int) (88.0D * ((double) health / (double) healthMax)), 8);

		String stringHealth = health + "/" + healthMax;
		if (this.settings.show_numbers_health) {
			GlStateManager.scale(0.5, 0.5, 0.5);
			gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, 194, 112, -1);
			GlStateManager.scale(2.0, 2.0, 2.0);
		}
		bind(Gui.ICONS);
	}

}
