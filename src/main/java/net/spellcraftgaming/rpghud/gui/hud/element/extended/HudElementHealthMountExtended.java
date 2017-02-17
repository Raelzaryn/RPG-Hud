package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementHealthMountExtended extends HudElementBarred {

	public HudElementHealthMountExtended() {
		super(HudElementType.HEALTH_MOUNT, 0, 0, 0, 0, false);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.thePlayer.ridingEntity instanceof EntityLivingBase && this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		EntityLivingBase mount = (EntityLivingBase) this.mc.thePlayer.ridingEntity;
		int health = (int) Math.ceil(mount.getHealth());
		int healthMax = (int) mount.getMaxHealth();
		int posX = this.settings.render_player_face ? 53 : 25;
		int posY = this.settings.render_player_face ? 54 : 49;
		drawCustomBar(posX, posY, 88, 8, (double) health / (double) healthMax * 100.0D, -1, -1, this.settings.color_health, offsetColorPercent(this.settings.color_health, OFFSET_PERCENT));
		String stringHealth = health + "/" + healthMax;

		if (this.settings.show_numbers_health) {
			GlStateManager.scale(0.5, 0.5, 0.5);
			gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, posX * 2 + 82, posY * 2 + 4, -1);
			GlStateManager.scale(2.0, 2.0, 2.0);
		}
	}

}
