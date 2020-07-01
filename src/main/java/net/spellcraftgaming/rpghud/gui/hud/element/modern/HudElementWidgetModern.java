package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.gui.AbstractGui;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class HudElementWidgetModern extends HudElement {

	public HudElementWidgetModern() {
		super(HudElementType.WIDGET, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD() && ModRPGHud.instance.settings.getBoolValue(Settings.render_player_face);
	}

	@Override
	public void drawElement(AbstractGui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int posX = this.settings.getPositionValue(Settings.face_position)[0];
		int posY = this.settings.getPositionValue(Settings.face_position)[1];
		drawRect(posX + 2, posY + 2, 20, 20, 0xA0000000);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		bind(getPlayerSkin(this.mc.player));
		GlStateManager.disableDepthTest();
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		gui.blit(posX * 2 + 8, posY * 2 + 8, 32, 32, 32, 32);
		gui.blit(posX * 2 + 8, posY * 2 + 8, 160, 32, 32, 32);
		GL11.glScaled(2.0D, 2.0D, 2.0D);
	}
}
