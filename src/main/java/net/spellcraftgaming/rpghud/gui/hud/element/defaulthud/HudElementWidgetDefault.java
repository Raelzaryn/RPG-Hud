package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementWidgetDefault extends HudElement {

	public HudElementWidgetDefault() {
		super(HudElementType.WIDGET, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return GameData.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks, double scale) {
		bind(INTERFACE);
		drawTexturedModalRect(gui, this.settings.getBoolValue(Settings.render_player_face) ? 50 : 25, this.settings.getBoolValue(Settings.render_player_face) ? 8 : 0, 0, 0, 114, 35, scale);
		if (GameData.isRidingLivingMount()) {
			drawTexturedModalRect(gui, this.settings.getBoolValue(Settings.render_player_face) ? 51 : 31, this.settings.getBoolValue(Settings.render_player_face) ? 39 : 30, 164, 0, 92, 20, scale);
		}

		if (this.settings.getBoolValue(Settings.render_player_face)) {
			drawTexturedModalRect(gui, 0, 0, 114, 0, 50, 50, scale);
			bind(getPlayerSkin(GameData.getPlayer()));
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			drawTexturedModalRect(gui, 34, 34, 32, 32, 32, 32, scale);
			drawTexturedModalRect(gui, 34, 34, 160, 32, 32, 32, scale);
			GL11.glScaled(2.0D, 2.0D, 2.0D);
			GameData.bindIcons();
		} else {
			drawTexturedModalRect(gui, 0, this.settings.getBoolValue(Settings.render_player_face) ? 11 : 3, 114, 50, 25, 29, scale);
		}
	}

}
