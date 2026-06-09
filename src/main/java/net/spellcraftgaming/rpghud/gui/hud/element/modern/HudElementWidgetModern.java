package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementWidgetModern extends HudElement {

	public HudElementWidgetModern() {
		super(HudElementType.WIDGET, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.options.hideGui && ModRPGHud.instance.settings.getBoolValue(Settings.render_player_face);
	}

	@Override
	public void drawElement(GuiGraphics gg, float zLevel, DeltaTracker partialTicks, int scaledWidth, int scaledHeight) {
		int posX = this.settings.getPositionValue(Settings.face_position)[0];
		int posY = this.settings.getPositionValue(Settings.face_position)[1];
		drawRect(gg, posX + 2, posY + 2, 20, 20, 0xA0000000);
		ResourceLocation l = getPlayerSkin(this.mc.player);
		gg.pose().scale(0.5f, 0.5f);
		gg.blit(RenderPipelines.GUI_TEXTURED, l, posX * 2 + 8, posY * 2 + 8, 32, 32, 32, 32, 256, 256);
		gg.blit(l, posX * 2 + 8, posY * 2 + 8, 160, 32, 32, 32, 256, 256);
		gg.pose().scale(2f, 2f);
	}
}
