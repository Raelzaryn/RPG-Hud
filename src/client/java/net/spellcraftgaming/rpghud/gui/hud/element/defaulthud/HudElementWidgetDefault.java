package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementWidgetDefault extends HudElement {

	public HudElementWidgetDefault() {
		super(HudElementType.WIDGET, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int posX = this.settings.getPositionValue(Settings.widget_position)[0];
		int posY = this.settings.getPositionValue(Settings.widget_position)[1];
		dc.drawTexture(INTERFACE,posX + (this.settings.getBoolValue(Settings.render_player_face) ? 50 : 25), posY + (this.settings.getBoolValue(Settings.render_player_face) ? 8 : 0), 0, 0, 114, 35);
		if (this.mc.player.getVehicle() instanceof LivingEntity) {
			dc.drawTexture(INTERFACE,posX + (this.settings.getBoolValue(Settings.render_player_face) ? 51 : 31), posY + (this.settings.getBoolValue(Settings.render_player_face) ? 39 : 30), 164, 0, 92, 20);
		}

		int facePosX = this.settings.getPositionValue(Settings.face_position)[0];
		int facePosY = this.settings.getPositionValue(Settings.face_position)[1];
		if (this.settings.getBoolValue(Settings.render_player_face)) {
			dc.drawTexture(INTERFACE,posX + facePosX, posY + facePosY, 114, 0, 50, 50);
			Identifier skin = getPlayerSkin(this.mc.player);
			dc.getMatrices().scale(0.5f, 0.5f, 0.5f);
			dc.drawTexture(skin ,posX * 2 + 34 + facePosX * 2, posY * 2 + 34 + facePosY * 2, 32, 32, 32, 32);
			dc.drawTexture(skin,posX * 2 + 34 + facePosX * 2, posY * 2 + 34 + facePosY * 2, 160, 32, 32, 32);
			dc.getMatrices().scale(2f, 2f, 2f);
		} else {
			dc.drawTexture(INTERFACE,posX, posY + (this.settings.getBoolValue(Settings.render_player_face) ? 11 : 3), 114, 50, 25, 29);
		}
	}

}
