package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.RPGHudUtils;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementWidgetExtended extends HudElement {

	public HudElementWidgetExtended() {
		super(HudElementType.WIDGET, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return RPGHudUtils.isSurvival();
	}

	@Override
	public void drawElement(GuiGraphicsExtractor gg, float zLevel, DeltaTracker partialTicks, int scaledWidth, int scaledHeight) {
		int posY = this.settings.getPositionValue(Settings.widget_position)[1];
		gg.blit(RenderPipelines.GUI_TEXTURED, INTERFACE, posX + (this.settings.getBoolValue(Settings.render_player_face) ? 50 : 26), posY + (this.settings.getBoolValue(Settings.render_player_face) ? 4 : 0), 0, 35, 114, 44, 256, 256);
		if (this.mc.player.getVehicle() instanceof LivingEntity) {
			gg.blit(RenderPipelines.GUI_TEXTURED, INTERFACE, posX + (this.settings.getBoolValue(Settings.render_player_face) ? 51 : 23), posY + (this.settings.getBoolValue(Settings.render_player_face) ? 44 : 39), 164, 0, 92, 20, 256, 256);
		}

		int facePosX = this.settings.getPositionValue(Settings.face_position)[0];
		int facePosY = this.settings.getPositionValue(Settings.face_position)[1];
		if (this.settings.getBoolValue(Settings.render_player_face)) {
			gg.blit(RenderPipelines.GUI_TEXTURED, INTERFACE, posX + facePosX, posY + facePosY, 114, 0, 50, 50, 256, 256);
			Identifier l = getPlayerSkin(this.mc.player);
			gg.pose().scale(0.5f, 0.5f);
			gg.blit(RenderPipelines.GUI_TEXTURED, l,posX * 2 + 34 + facePosX * 2, posY * 2 + 34 + facePosY * 2, 32, 32, 32, 32, 256, 256);
			gg.blit(RenderPipelines.GUI_TEXTURED, l,posX * 2 + 34 + facePosX * 2, posY * 2 + 34 + facePosY * 2, 160, 32, 32, 32, 256, 256);
			gg.pose().scale(2f, 2f);
		} else {
			gg.blit(RenderPipelines.GUI_TEXTURED, INTERFACE,posX, 3, 214, 20, 26, 38, 256, 256);
		}
	}

}
