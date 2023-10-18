package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.LivingEntity;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHealthMountTexture extends HudElement {

	public HudElementHealthMountTexture() {
		super(HudElementType.HEALTH_MOUNT, 0, 0, 0, 0, false);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return this.mc.player.getVehicle() instanceof LivingEntity && !this.mc.options.hideGui;
	}

	@Override
	public void drawElement(GuiGraphics gg, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		LivingEntity mount = (LivingEntity) this.mc.player.getVehicle();
		int health = (int) Math.ceil(mount.getHealth());
		int healthMax = (int) mount.getMaxHealth();
		if(health > healthMax) health = healthMax;
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 53 : 25) + this.settings.getPositionValue(Settings.mount_health_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 54 : 49) + this.settings.getPositionValue(Settings.mount_health_position)[1];

		gg.blit(INTERFACE, posX, posY, 0, 124, (int) (88.0D * ((double) health / (double) healthMax)), 8);

		String stringHealth = this.settings.getBoolValue(Settings.mount_health_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%" : health + "/" + healthMax;

		if (this.settings.getBoolValue(Settings.show_numbers_health)) {
			gg.pose().scale(0.5f, 0.5f, 0.5f);
			gg.drawCenteredString( this.mc.font, stringHealth, posX * 2 + 88, posY * 2 + 4, -1);
			gg.pose().scale(2f, 2f, 2f);
		}
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
	}

}
