package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementTexture;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

public class HudElementWidgetHotbar extends HudElementTexture {

	public HudElementWidgetHotbar() {
		super(HudElementType.WIDGET, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return GameData.shouldDrawHUD();
	}
	
	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		bind(INTERFACE);
		ScaledResolution res = new ScaledResolution(this.mc);
		int height = res.getScaledHeight();
		gui.drawTexturedModalRect(this.settings.render_player_face ? 50 : 26, height - 16 - 52 + 7, 0, 170, 251, 52);
		
		if(ModRPGHud.instance.settings.render_player_face) {
			gui.drawTexturedModalRect(0, height - 16 - 52 + 7, 164, 20, 50, 52);
			bind(getPlayerSkin(GameData.getPlayer()));
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			gui.drawTexturedModalRect(34, height * 2 - 88, 32, 32, 32, 32);
			gui.drawTexturedModalRect(34, height * 2 - 88, 160, 32, 32, 32);
			GL11.glScaled(2.0D, 2.0D, 2.0D);
			GameData.bindIcons();
		} else {
			gui.drawTexturedModalRect(0, height - 12 - 52 + 7, 214, 58, 26, 42);
		}
	}
}
