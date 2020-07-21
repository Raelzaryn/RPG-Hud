package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import org.lwjgl.opengl.GL11;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(EnvType.CLIENT)
public class HudElementWidgetDefault extends HudElement {

	public HudElementWidgetDefault() {
		super(HudElementType.WIDGET, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.options.hudHidden;
	}

	@Override
	public void drawElement(DrawableHelper gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		bind(INTERFACE);
		int posX = this.settings.getPositionValue(Settings.widget_position)[0];
		int posY = this.settings.getPositionValue(Settings.widget_position)[1];
		gui.drawTexture(ms,posX + (this.settings.getBoolValue(Settings.render_player_face) ? 50 : 25), posY + (this.settings.getBoolValue(Settings.render_player_face) ? 8 : 0), 0, 0, 114, 35);
		if (this.mc.player.getVehicle() instanceof LivingEntity) {
			gui.drawTexture(ms,posX + (this.settings.getBoolValue(Settings.render_player_face) ? 51 : 31), posY + (this.settings.getBoolValue(Settings.render_player_face) ? 39 : 30), 164, 0, 92, 20);
		}

		int facePosX = this.settings.getPositionValue(Settings.face_position)[0];
		int facePosY = this.settings.getPositionValue(Settings.face_position)[1];
		if (this.settings.getBoolValue(Settings.render_player_face)) {
			gui.drawTexture(ms,posX + facePosX, posY + facePosY, 114, 0, 50, 50);
			bind(getPlayerSkin(this.mc.player));
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			gui.drawTexture(ms,posX * 2 + 34 + facePosX * 2, posY * 2 + 34 + facePosY * 2, 32, 32, 32, 32);
			gui.drawTexture(ms,posX * 2 + 34 + facePosX * 2, posY * 2 + 34 + facePosY * 2, 160, 32, 32, 32);
			GL11.glScaled(2.0D, 2.0D, 2.0D);
			this.mc.getTextureManager().bindTexture(AbstractParentElement.GUI_ICONS_TEXTURE);
		} else {
			gui.drawTexture(ms,posX, posY + (this.settings.getBoolValue(Settings.render_player_face) ? 11 : 3), 114, 50, 25, 29);
			this.mc.getTextureManager().bindTexture(AbstractParentElement.GUI_ICONS_TEXTURE);
		}
	}

}
