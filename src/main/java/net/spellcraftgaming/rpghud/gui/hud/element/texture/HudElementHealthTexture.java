package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
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
		return !this.mc.options.hideGui;
	}

	@Override
	public void drawElement(GuiGraphics gg, float zLevel, DeltaTracker partialTicks, int scaledHeight, int scaledWidth) {
		int health = Mth.ceil(this.mc.player.getHealth());
		int absorption = Mth.ceil(this.mc.player.getAbsorptionAmount());
		int healthMax = Mth.ceil(this.mc.player.getMaxHealth());
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.health_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 9 : 5) + this.settings.getPositionValue(Settings.health_position)[1];
		if (absorption > 1)
			gg.blit(RenderPipelines.GUI_TEXTURED, INTERFACE, posX, posY, 0, 88, (int) (110.0D * ((double) (health + absorption) / (double) (healthMax + absorption))), 12, 256, 256);
		if (this.mc.player.hasEffect(MobEffects.POISON)) {
			gg.blit(RenderPipelines.GUI_TEXTURED, INTERFACE, posX, posY, 141, 160, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12, 256, 256);
		} else if (this.mc.player.hasEffect(MobEffects.WITHER)) {
			gg.blit(RenderPipelines.GUI_TEXTURED, INTERFACE, posX, posY, 34, 244, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12, 256, 256);
		} else {
			gg.blit(RenderPipelines.GUI_TEXTURED, INTERFACE, posX, posY, 0, 100, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12, 256, 256);
		}

		String stringHealth = this.settings.getBoolValue(Settings.health_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%" : (health + absorption) + "/" + healthMax;
		if (this.settings.getBoolValue(Settings.show_numbers_health))
			gg.drawCenteredString( this.mc.font, stringHealth, posX + 55, posY + 2, -1);
	}

}
