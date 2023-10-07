package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.MathHelper;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementHealthTexture extends HudElement {

	public HudElementHealthTexture() {
		super(HudElementType.HEALTH, 0, 0, 0, 0, false);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledHeight, int scaledWidth) {
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		int health = MathHelper.ceil(this.mc.player.getHealth());
		int absorption = MathHelper.ceil(this.mc.player.getAbsorptionAmount());
		int healthMax = MathHelper.ceil(this.mc.player.getMaxHealth());
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.health_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 9 : 5) + this.settings.getPositionValue(Settings.health_position)[1];
		if (absorption > 1)
			dc.drawTexture(INTERFACE, posX, posY, 0, 88, (int) (110.0D * ((double) (health + absorption) / (double) (healthMax + absorption))), 12);
		if (this.mc.player.hasStatusEffect(StatusEffects.POISON)) {
			dc.drawTexture(INTERFACE, posX, posY, 141, 160, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12);
		} else if (this.mc.player.hasStatusEffect(StatusEffects.WITHER)) {
			dc.drawTexture(INTERFACE, posX, posY, 34, 244, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12);
		} else {
			dc.drawTexture(INTERFACE, posX, posY, 0, 100, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12);
		}

		String stringHealth = this.settings.getBoolValue(Settings.health_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%" : (health + absorption) + "/" + healthMax;
		if (this.settings.getBoolValue(Settings.show_numbers_health))
			dc.drawCenteredTextWithShadow( this.mc.textRenderer, stringHealth, posX + 55, posY + 2, -1);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
	}

}
