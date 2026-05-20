package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.spellcraftgaming.rpghud.RPGHudUtils;
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
		return RPGHudUtils.isSurvival();
	}

	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
		int health = Mth.ceil(this.mc.player.getHealth());
		int absorption = Mth.ceil(this.mc.player.getAbsorptionAmount());
		int healthMax = Mth.ceil(this.mc.player.getMaxHealth());
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.health_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 9 : 5) + this.settings.getPositionValue(Settings.health_position)[1];
		if (absorption > 1)
			graphics.blit(RenderPipelines.GUI_TEXTURED,INTERFACE, posX, posY, 0, 88, (int) (110.0D * ((double) (health + absorption) / (double) (healthMax + absorption))), 12, 256, 256);
		if (this.mc.player.hasEffect(MobEffects.POISON)) {
			graphics.blit(RenderPipelines.GUI_TEXTURED,INTERFACE, posX, posY, 141, 160, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12, 256, 256);
		} else if (this.mc.player.hasEffect(MobEffects.WITHER)) {
			graphics.blit(RenderPipelines.GUI_TEXTURED,INTERFACE, posX, posY, 34, 244, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12, 256, 256);
		} else {
			graphics.blit(RenderPipelines.GUI_TEXTURED,INTERFACE, posX, posY, 0, 100, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12, 256, 256);
		}

		String stringHealth = this.settings.getBoolValue(Settings.health_percentage) ? Mth.floor((double) health / (double) healthMax * 100) + "%" : (health + absorption) + "/" + healthMax;
		if (this.settings.getBoolValue(Settings.show_numbers_health))
			graphics.centeredText(this.mc.font, stringHealth, posX + 55, posY + 2, -1);
	}

}
