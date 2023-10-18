package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
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
	public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledHeight, int scaledWidth) {
		bind(INTERFACE);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		int health = Mth.ceil(this.mc.player.getHealth());
		int absorption = Mth.ceil(this.mc.player.getAbsorptionAmount());
		int healthMax = Mth.ceil(this.mc.player.getMaxHealth());
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.health_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 9 : 5) + this.settings.getPositionValue(Settings.health_position)[1];
		if (absorption > 1)
			GuiComponent.blit(ms, posX, posY, 0, 88, (int) (110.0D * ((double) (health + absorption) / (double) (healthMax + absorption))), 12);
		if (this.mc.player.hasEffect(MobEffects.POISON)) {
			GuiComponent.blit(ms, posX, posY, 141, 160, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12);
		} else if (this.mc.player.hasEffect(MobEffects.WITHER)) {
			GuiComponent.blit(ms, posX, posY, 34, 244, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12);
		} else {
			GuiComponent.blit(ms, posX, posY, 0, 100, (int) (110.0D * ((double) health / (double) (healthMax + absorption))), 12);
		}

		String stringHealth = this.settings.getBoolValue(Settings.health_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%" : (health + absorption) + "/" + healthMax;
		if (this.settings.getBoolValue(Settings.show_numbers_health))
			Gui.drawCenteredString(ms, this.mc.font, stringHealth, posX + 55, posY + 2, -1);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		bind(Gui.GUI_ICONS_LOCATION);
	}

}
