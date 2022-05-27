package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.LivingEntity;
import net.spellcraftgaming.rpghud.gui.hud.HudModern;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHealthMountModern extends HudElement {

	public HudElementHealthMountModern() {
		super(HudElementType.HEALTH_MOUNT, 0, 0, 0, 0, false);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return this.mc.player.getVehicle() instanceof LivingEntity && !this.mc.options.hideGui;
	}

	@Override
	public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		LivingEntity mount = (LivingEntity) this.mc.player.getVehicle();
		int health = (int) Math.ceil(mount.getHealth());
		int healthMax = (int) mount.getMaxHealth();
		if (health > healthMax) health = healthMax;
		int xOffset = ((HudModern) this.rpgHud.huds.get("modern")).getPosX();
		
		String stringHealth = this.settings.getBoolValue(Settings.mount_health_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%" : health + "/" + healthMax;

		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 26 : 4) + (this.settings.getBoolValue(Settings.show_numbers_health) ? xOffset -2 : -2) + this.settings.getPositionValue(Settings.mount_health_position)[0];
		int posY = this.settings.getPositionValue(Settings.mount_health_position)[1];
		if (this.settings.getBoolValue(Settings.show_numbers_health)) {
			int width2 = this.mc.font.width(stringHealth) / 2;
			drawRect(ms, posX, 24 + posY, width2 + 4, 5, 0xA0000000);
			ms.scale(0.5f, 0.5f, 0.5f);
			Gui.drawString(ms, this.mc.font, stringHealth, posX * 2 + 4, 48 + posY * 2, -1);
			ms.scale(2f, 2f, 2f);
		}

		drawTetragon(posX, posX, 21 + posY, 21 + posY, 58, 54, 3, 3, 0xA0000000);
		drawTetragon(posX + 2, posX + 2, 21 + posY, 21 + posY, (int) (((double) health / (double) healthMax) * 53), (int) (((double) health / (double) healthMax) * 53 - 2), 1, 1, this.settings.getIntValue(Settings.color_health));

	}

}
