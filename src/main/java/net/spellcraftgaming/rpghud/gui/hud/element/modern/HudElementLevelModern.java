package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementLevelModern extends HudElement{

	public HudElementLevelModern(){
		super(HudElementType.LEVEL, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		String level = String.valueOf(this.mc.player.experienceLevel);
		int width = 18;
		if(this.mc.fontRendererObj.getStringWidth(level) > (width + 2)) width = this.mc.fontRendererObj.getStringWidth(level) + 2;
		GlStateManager.enableAlpha();
		GlStateManager.disableBlend();
		int posX = this.settings.render_player_face ? 23 : 2;
		int posY = (this.settings.show_numbers_health && this.settings.show_numbers_stamina) ? 22: 26;
		if(this.settings.clock_time_format == 0 || !this.settings.render_player_face) {
			drawRect(posX, posY, 18, 7, 0xA0000000);
		} else {
			drawRect(26, posY, 18, 7, 0xA0000000);
		}
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		
		if(this.settings.clock_time_format == 0 || !this.settings.render_player_face){
			gui.drawCenteredString(this.mc.fontRendererObj, level, (posX * 2) + 18, posY * 2 + 3, 0x80FF20);
		} else {
			gui.drawCenteredString(this.mc.fontRendererObj, level, 70, posY * 2 + 3, 0x80FF20);
		}
		GL11.glScaled(2.0D, 2.0D, 2.0D);
		GlStateManager.enableBlend();
	}

}
