package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementWidgetDefault extends HudElement {

	public HudElementWidgetDefault() {
		super(HudElementType.WIDGET, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.options.hideGui;
	}

	@Override
	public void drawElement(GuiGraphics gg, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int posX = this.settings.getPositionValue(Settings.widget_position)[0];
		int posY = this.settings.getPositionValue(Settings.widget_position)[1];
		gg.blit(INTERFACE, posX + (this.settings.getBoolValue(Settings.render_player_face) ? 50 : 25), posY + (this.settings.getBoolValue(Settings.render_player_face) ? 8 : 0), 0, 0, 114, 35);
		if (this.mc.player.getVehicle() instanceof LivingEntity) {
			gg.blit(INTERFACE, posX + (this.settings.getBoolValue(Settings.render_player_face) ? 51 : 31), posY + (this.settings.getBoolValue(Settings.render_player_face) ? 39 : 30), 164, 0, 92, 20);
		}

		int facePosX = this.settings.getPositionValue(Settings.face_position)[0];
		int facePosY = this.settings.getPositionValue(Settings.face_position)[1];
		if (this.settings.getBoolValue(Settings.render_player_face)) {
			gg.blit(INTERFACE, posX + facePosX, posY + facePosY, 114, 0, 50, 50);
			ResourceLocation l = getPlayerSkin(this.mc.player);
			gg.pose().scale(0.5f, 0.5f, 0.5f);
			gg.blit(l, posX * 2 + 34 + facePosX * 2, posY * 2 + 34 + facePosY * 2, 32, 32, 32, 32);
			gg.blit(l, posX * 2 + 34 + facePosX * 2, posY * 2 + 34 + facePosY * 2, 160, 32, 32, 32);
			gg.pose().scale(2f, 2f, 2f);
		} else {
			gg.blit(INTERFACE, posX, posY + (this.settings.getBoolValue(Settings.render_player_face) ? 11 : 3), 114, 50, 25, 29);
		}
	}

}
