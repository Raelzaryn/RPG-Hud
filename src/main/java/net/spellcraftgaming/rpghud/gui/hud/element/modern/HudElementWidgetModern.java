package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.lib.GameData;
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
		return GameData.shouldDrawHUD() && ModRPGHud.instance.settings.getBoolValue(Settings.render_player_face);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks, double scale) {
		drawRect(2, 2, 20, 20, 0xA0000000);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		bind(getPlayerSkin(GameData.getPlayer()));
		GlStateManager.disableDepth();
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		gui.drawTexturedModalRect(8, 8, 32, 32, 32, 32);
		gui.drawTexturedModalRect(8, 8, 160, 32, 32, 32);
		GL11.glScaled(2.0D, 2.0D, 2.0D);
	}
}
