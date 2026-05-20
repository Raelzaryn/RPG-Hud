package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.spellcraftgaming.rpghud.RPGHudUtils;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementWidgetModern extends HudElement {

	public HudElementWidgetModern() {
		super(HudElementType.WIDGET, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return RPGHudUtils.isSurvival() && ModRPGHud.instance.settings.getBoolValue(Settings.render_player_face);
	}

	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
		int posX = this.settings.getPositionValue(Settings.face_position)[0];
		int posY = this.settings.getPositionValue(Settings.face_position)[1];
		drawRect(graphics, posX + 2, posY + 2, 20, 20, 0xA0000000);
		graphics.pose().scale(0.5f, 0.5f);
		
		graphics.blit(RenderPipelines.GUI_TEXTURED, this.playerSkinId, posX * 2 + 8, posY * 2 + 8, 32, 32, 32, 32, 256, 256);
		graphics.blit(RenderPipelines.GUI_TEXTURED, this.playerSkinId, posX * 2 + 8, posY * 2 + 8, 160, 32, 32, 32, 256, 256);
		
		graphics.pose().scale(2f, 2f);
	}
}
