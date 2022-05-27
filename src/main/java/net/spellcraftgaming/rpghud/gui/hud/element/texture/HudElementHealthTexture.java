package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHealthTexture extends HudElement {

	public HudElementHealthTexture() {
		super(HudElementType.HEALTH, 0, 0, 0, 0, false);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(AbstractGui gui, MatrixStack ms, float zLevel, float partialTicks, int scaledHeight, int scaledWidth) {
		bind(INTERFACE);
		RenderSystem.color3f(1f, 1f, 1f);
		int health = MathHelper.ceil(this.mc.player.getHealth());
		int absorption = MathHelper.ceil(this.mc.player.getAbsorptionAmount());
		int healthMax = MathHelper.ceil(this.mc.player.getMaxHealth());
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.health_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 9 : 5) + this.settings.getPositionValue(Settings.health_position)[1];
		if (absorption > 1)
			gui.blit(ms, posX, posY, 0, 88, (int) (110.0D * ((double) (health + absorption) / (double) (healthMax + absorption))), 12);
		if (this.mc.player.isPotionActive(Effects.POISON)) {
			gui.blit(ms, posX, posY, 141, 160, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12);
		} else if (this.mc.player.isPotionActive(Effects.WITHER)) {
			gui.blit(ms, posX, posY, 34, 244, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12);
		} else {
			gui.blit(ms, posX, posY, 0, 100, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12);
		}

		String stringHealth = this.settings.getBoolValue(Settings.health_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%" : (health + absorption) + "/" + healthMax;
		if (this.settings.getBoolValue(Settings.show_numbers_health))
			AbstractGui.drawCenteredString(ms, this.mc.fontRenderer, stringHealth, posX + 55, posY + 2, -1);
		RenderSystem.color3f(1f, 1f, 1f);
		this.mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
	}

}
