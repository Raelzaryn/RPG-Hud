package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(EnvType.CLIENT)
public class HudElementWidgetHotbar extends HudElement {

	public HudElementWidgetHotbar() {
		super(HudElementType.WIDGET, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawableHelper gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		bind(INTERFACE);
		int posX = this.settings.getPositionValue(Settings.widget_position)[0];
		int posY = scaledHeight + this.settings.getPositionValue(Settings.widget_position)[1];
		gui.drawTexture(ms, posX + (this.settings.getBoolValue(Settings.render_player_face) ? 50 : 26), posY - 16 - 52 + 9, 0, 172, 251, 48);

		int facePosX = this.settings.getPositionValue(Settings.face_position)[0];
		int facePosY = this.settings.getPositionValue(Settings.face_position)[1];
		if (ModRPGHud.instance.settings.getBoolValue(Settings.render_player_face)) {
			gui.drawTexture(ms, posX + facePosX, posY - 16 - 52 + 7 + facePosY, 164, 20, 50, 52);
			bind(getPlayerSkin(this.mc.player));
			ms.scale(0.5f, 0.5f, 0.5f);
			gui.drawTexture(ms, posX * 2 + 34 + facePosX * 2, posY * 2 - 88 + facePosY * 2, 32, 32, 32, 32);
			gui.drawTexture(ms, posX * 2 + 34 + facePosX * 2, posY * 2 - 88 + facePosY * 2, 160, 32, 32, 32);
			ms.scale(2f, 2f, 2f);
		} else {
			gui.drawTexture(ms, posX, posY - 12 - 52 + 7, 214, 58, 26, 42);
		}
		bind(AbstractParentElement.GUI_ICONS_TEXTURE);
	}
}
