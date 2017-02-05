package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementHealthMountDefault extends HudElementBarred {

	public HudElementHealthMountDefault() {
		super(HudElementType.HEALTH_MOUNT, 0, 0, 0, 0, false);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.player.getRidingEntity() instanceof EntityLivingBase && this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		EntityLivingBase mount = (EntityLivingBase) this.mc.player.getRidingEntity();
		int health = (int) Math.ceil(mount.getHealth());
		int healthMax = (int) mount.getMaxHealth();
		drawCustomBar(53, 49, 88, 8, (double) health / (double) healthMax * 100.0D, -1, -1, this.settings.color_health, offsetColorPercent(this.settings.color_health, OFFSET_PERCENT));
		String stringHealth = health + "/" + healthMax;

		if (this.settings.show_numbers_health) {
			GlStateManager.scale(0.5, 0.5, 0.5);
			gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, 194, 102, -1);
			GlStateManager.scale(2.0, 2.0, 2.0);
		}
	}

}
