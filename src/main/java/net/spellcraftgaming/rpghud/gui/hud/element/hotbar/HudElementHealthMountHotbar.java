package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.LivingEntity;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHealthMountHotbar extends HudElement {

	public HudElementHealthMountHotbar() {
		super(HudElementType.HEALTH_MOUNT, 0, 0, 0, 0, false);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return this.mc.player.getRidingEntity() instanceof LivingEntity && this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(AbstractGui gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int height = scaledHeight + this.settings.getPositionValue(Settings.mount_health_position)[1];
		LivingEntity mount = (LivingEntity) this.mc.player.getRidingEntity();
		int health = (int) Math.ceil(mount.getHealth());
		int healthMax = (int) mount.getMaxHealth();
		if(health > healthMax) health = healthMax;
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.mount_health_position)[0];
		drawCustomBar(posX, height - 56, 200, 10, (double) health / (double) healthMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_health), offsetColorPercent(this.settings.getIntValue(Settings.color_health), OFFSET_PERCENT));
		
		String stringHealth = this.settings.getBoolValue(Settings.mount_health_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%" : health + "/" + healthMax;

		if (this.settings.getBoolValue(Settings.show_numbers_health))
			AbstractGui.drawCenteredString(ms, this.mc.fontRenderer, stringHealth, posX + 100, height - 55, -1);
	}

}
