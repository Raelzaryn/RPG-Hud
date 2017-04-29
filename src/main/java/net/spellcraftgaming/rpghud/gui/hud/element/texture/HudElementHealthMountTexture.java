package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHealthMountTexture extends HudElement {

	public HudElementHealthMountTexture() {
		super(HudElementType.HEALTH_MOUNT, 0, 0, 0, 0, false);
		this.parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return GameData.isRidingLivingMount() && GameData.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks, double scale) {
		bind(INTERFACE);
		GlStateManager.color(1f, 1f, 1f);
		EntityLivingBase mount = (EntityLivingBase) GameData.getMount();
		int health = (int) Math.ceil(mount.getHealth());
		int healthMax = (int) mount.getMaxHealth();
		int posX = this.settings.getBoolValue(Settings.render_player_face) ? 53 : 25;
		int posY = this.settings.getBoolValue(Settings.render_player_face) ? 54 : 49;

		gui.drawTexturedModalRect(posX, posY, 0, 124, (int) (88.0D * ((double) health / (double) healthMax)), 8);

		String stringHealth = health + "/" + healthMax;
		if (this.settings.getBoolValue(Settings.show_numbers_health)) {
			GlStateManager.scale(0.5, 0.5, 0.5);
			gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, posX * 2 + 82, posY * 2 + 4, -1);
			GlStateManager.scale(2.0, 2.0, 2.0);
		}
		GlStateManager.color(1f, 1f, 1f);
		GameData.bindIcons();
	}

}
