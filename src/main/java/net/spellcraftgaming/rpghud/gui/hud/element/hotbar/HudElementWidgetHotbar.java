package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.AbstractGui;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementWidgetHotbar extends HudElement {

	public HudElementWidgetHotbar() {
		super(HudElementType.WIDGET, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(AbstractGui gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		bind(INTERFACE);
		int posX = this.settings.getPositionValue(Settings.widget_position)[0];
		int posY = scaledHeight + this.settings.getPositionValue(Settings.widget_position)[1];
		gui.blit(ms, posX + (this.settings.getBoolValue(Settings.render_player_face) ? 50 : 26), posY - 16 - 52 + 9, 0, 172, 251, 48);

		int facePosX = this.settings.getPositionValue(Settings.face_position)[0];
		int facePosY = this.settings.getPositionValue(Settings.face_position)[1];
		if (ModRPGHud.instance.settings.getBoolValue(Settings.render_player_face)) {
			gui.blit(ms, posX + facePosX, posY - 16 - 52 + 7 + facePosY, 164, 20, 50, 52);
			bind(getPlayerSkin(this.mc.player));
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			gui.blit(ms, posX * 2 + 34 + facePosX * 2, posY * 2 - 88 + facePosY * 2, 32, 32, 32, 32);
			gui.blit(ms, posX * 2 + 34 + facePosX * 2, posY * 2 - 88 + facePosY * 2, 160, 32, 32, 32);
			GL11.glScaled(2.0D, 2.0D, 2.0D);
			this.mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
		} else {
			gui.blit(ms, posX, posY - 12 - 52 + 7, 214, 58, 26, 42);
			this.mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
		}
	}
}
