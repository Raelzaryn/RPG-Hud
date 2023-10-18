package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
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
	public void drawElement(GuiGraphics gg, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int posX = this.settings.getPositionValue(Settings.face_position)[0];
		int posY = this.settings.getPositionValue(Settings.face_position)[1];
		drawRect(gg, posX + 2, posY + 2, 20, 20, 0xA0000000);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.enableBlend();
		ResourceLocation l = getPlayerSkin(this.mc.player);
		RenderSystem.disableDepthTest();
		gg.pose().scale(0.5f, 0.5f, 0.5f);
		gg.blit(l, posX * 2 + 8, posY * 2 + 8, 32, 32, 32, 32);
		gg.blit(l, posX * 2 + 8, posY * 2 + 8, 160, 32, 32, 32);
		gg.pose().scale(2f, 2f, 2f);
	}
}
