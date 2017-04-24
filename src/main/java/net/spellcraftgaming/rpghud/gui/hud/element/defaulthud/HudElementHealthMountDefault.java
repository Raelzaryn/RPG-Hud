package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementHealthMountDefault extends HudElement {

	public HudElementHealthMountDefault() {
		super(HudElementType.HEALTH_MOUNT, 0, 0, 0, 0, false);
	}

	@Override
	public boolean checkConditions() {
		return GameData.isRidingLivingMount() && GameData.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		EntityLivingBase mount = (EntityLivingBase) GameData.getMount();
		int health = GameData.ceil(mount.getHealth());
		int healthMax = GameData.ceil(mount.getMaxHealth());
		int posX = this.settings.render_player_face ? 53 : 33;
		int posY = this.settings.render_player_face ? 49 : 40;
		drawCustomBar(posX, posY, 88, 8, (double) health / (double) healthMax * 100.0D, -1, -1, this.settings.color_health, offsetColorPercent(this.settings.color_health, OFFSET_PERCENT));
		String stringHealth = health + "/" + healthMax;

		if (this.settings.show_numbers_health) {
			GlStateManager.scale(0.5, 0.5, 0.5);
			gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, posX * 2 + 88, posY * 2 + 4, -1);
			GlStateManager.scale(2.0, 2.0, 2.0);
		}
	}

}
