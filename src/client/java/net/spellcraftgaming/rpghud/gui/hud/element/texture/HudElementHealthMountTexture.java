package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.spellcraftgaming.rpghud.RPGHudUtils;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementHealthMountTexture extends HudElement {

	public HudElementHealthMountTexture() {
		super(HudElementType.HEALTH_MOUNT, 0, 0, 0, 0, false);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return this.mc.player.getVehicle() instanceof LivingEntity && RPGHudUtils.isSurvival();
	}

	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
		LivingEntity mount = (LivingEntity) this.mc.player.getVehicle();
		int health = Mth.ceil(mount.getHealth());
		int healthMax = (int) mount.getMaxHealth();
		if(health > healthMax) health = healthMax;
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 53 : 25) + this.settings.getPositionValue(Settings.mount_health_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 54 : 49) + this.settings.getPositionValue(Settings.mount_health_position)[1];

		graphics.blit(RenderPipelines.GUI_TEXTURED, INTERFACE, posX, posY, 0, 124, (int) (88.0D * ((double) health / (double) healthMax)), 8, 256, 256);

		String stringHealth = this.settings.getBoolValue(Settings.mount_health_percentage) ? Mth.floor((double) health / (double) healthMax * 100) + "%" : health + "/" + healthMax;

		if (this.settings.getBoolValue(Settings.show_numbers_health)) {
			graphics.pose().scale(0.5f, 0.5f);
			graphics.centeredText(this.mc.font, stringHealth, posX * 2 + 88, posY * 2 + 4, -1);
			graphics.pose().scale(2f, 2f);
		}
	}

}
