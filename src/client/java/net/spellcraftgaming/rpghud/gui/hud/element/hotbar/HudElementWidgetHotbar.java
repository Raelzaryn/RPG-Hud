package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementWidgetHotbar extends HudElement {

	public HudElementWidgetHotbar() {
		super(HudElementType.WIDGET, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int posX = this.settings.getPositionValue(Settings.widget_position)[0];
		int posY = scaledHeight + this.settings.getPositionValue(Settings.widget_position)[1];
		dc.drawTexture(INTERFACE, posX + (this.settings.getBoolValue(Settings.render_player_face) ? 50 : 26), posY - 16 - 52 + 9, 0, 172, 251, 48);

		int facePosX = this.settings.getPositionValue(Settings.face_position)[0];
		int facePosY = this.settings.getPositionValue(Settings.face_position)[1];
		if (ModRPGHud.instance.settings.getBoolValue(Settings.render_player_face)) {
			dc.drawTexture(INTERFACE, posX + facePosX, posY - 16 - 52 + 7 + facePosY, 164, 20, 50, 52);
			Identifier skin = getPlayerSkin(this.mc.player);
			dc.getMatrices().scale(0.5f, 0.5f, 0.5f);
			dc.drawTexture(skin, posX * 2 + 34 + facePosX * 2, posY * 2 - 88 + facePosY * 2, 32, 32, 32, 32);
			dc.drawTexture(skin, posX * 2 + 34 + facePosX * 2, posY * 2 - 88 + facePosY * 2, 160, 32, 32, 32);
			dc.getMatrices().scale(2f, 2f, 2f);
		} else {
			dc.drawTexture(INTERFACE, posX, posY - 12 - 52 + 7, 214, 58, 26, 42);
		}
	}
}
