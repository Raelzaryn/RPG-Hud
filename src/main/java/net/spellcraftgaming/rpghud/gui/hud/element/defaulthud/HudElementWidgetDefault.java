package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementTexture;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementWidgetDefault extends HudElementTexture {

	public HudElementWidgetDefault() {
		super(HudElementType.WIDGET, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		bind(INTERFACE);
		gui.drawTexturedModalRect(this.settings.render_player_face ? 50 : 25, this.settings.render_player_face ? 8 : 0, 0, 0, 114, 35);
		if (this.mc.thePlayer.getRidingEntity() instanceof EntityLivingBase) {
			gui.drawTexturedModalRect(this.settings.render_player_face ? 51 : 31, this.settings.render_player_face ? 39 : 30, 164, 0, 92, 20);
		}

		if(this.settings.render_player_face) {
			gui.drawTexturedModalRect(0, 0, 114, 0, 50, 50);
			bind(getPlayerSkin(this.mc.thePlayer));
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			gui.drawTexturedModalRect(34, 34, 32, 32, 32, 32);
			gui.drawTexturedModalRect(34, 34, 160, 32, 32, 32);
			GL11.glScaled(2.0D, 2.0D, 2.0D);
			bind(Gui.ICONS);
		} else {
			gui.drawTexturedModalRect(0, this.settings.render_player_face ? 11 : 3, 114, 50, 25, 29);
		}
	}

}
