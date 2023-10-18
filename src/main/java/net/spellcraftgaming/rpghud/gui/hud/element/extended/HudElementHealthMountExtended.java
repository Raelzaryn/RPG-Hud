package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.LivingEntity;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHealthMountExtended extends HudElement {

	public HudElementHealthMountExtended() {
		super(HudElementType.HEALTH_MOUNT, 0, 0, 0, 0, false);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return this.mc.player.getVehicle() instanceof LivingEntity && !this.mc.options.hideGui;
	}

	@Override
	public void drawElement(GuiGraphics gg, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		LivingEntity mount = (LivingEntity) this.mc.player.getVehicle();
		int health = (int) Math.ceil(mount.getHealth());
		int healthMax = (int) mount.getMaxHealth();
		if(health > healthMax) health = healthMax;
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 53 : 25) + this.settings.getPositionValue(Settings.mount_health_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 54 : 49) + this.settings.getPositionValue(Settings.mount_health_position)[1];

		drawCustomBar(gg, posX, posY, 88, 8, (double) health / (double) healthMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_health), offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT));

		String stringHealth = this.settings.getBoolValue(Settings.mount_health_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%" : health + "/" + healthMax;

		if (this.settings.getBoolValue(Settings.show_numbers_health)) {
			gg.pose().scale(0.5f, 0.5f, 0.5f);
			gg.drawCenteredString( this.mc.font, stringHealth, posX * 2 + 88, posY * 2 + 4, -1);
			gg.pose().scale(2f, 2f, 2f);
		}
	}

}
