package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.LivingEntity;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementJumpBarTexture extends HudElement {

	public HudElementJumpBarTexture() {
		super(HudElementType.JUMP_BAR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.player.getVehicle() instanceof LivingEntity && (this.settings.getBoolValue(Settings.limit_jump_bar) ? this.mc.player.getJumpRidingScale() > 0F : true);
	}

	@Override
	public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		bind(INTERFACE);
		int height = scaledHeight + this.settings.getPositionValue(Settings.jump_bar_position)[1];
		int adjustedWidth = (scaledWidth / 2) + this.settings.getPositionValue(Settings.jump_bar_position)[0];
		float var14 = this.mc.player.getJumpRidingScale();
		int color = (int) (var14 * 100.0F);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		gui.blit(ms, adjustedWidth - 71, height - 80, 0, 160, 141, 10);
		gui.blit(ms, adjustedWidth - 71, height - 80, 0, 150, (int) (141.0D * (color / 100.0D)), 10);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		bind(Gui.GUI_ICONS_LOCATION);
	}

}
