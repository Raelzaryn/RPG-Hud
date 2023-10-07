package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementJumpBarTexture extends HudElement {

	public HudElementJumpBarTexture() {
		super(HudElementType.JUMP_BAR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.player.getVehicle() instanceof LivingEntity && (this.settings.getBoolValue(Settings.limit_jump_bar) ? this.mc.player.getMountJumpStrength() > 0F : true);
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int height = scaledHeight + this.settings.getPositionValue(Settings.jump_bar_position)[1];
		int adjustedWidth = (scaledWidth / 2) + this.settings.getPositionValue(Settings.jump_bar_position)[0];
		float var14 = this.mc.player.getMountJumpStrength();
		int color = (int) (var14 * 100.0F);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		dc.drawTexture(INTERFACE, adjustedWidth - 71, height - 80, 0, 160, 141, 10);
		dc.drawTexture(INTERFACE, adjustedWidth - 71, height - 80, 0, 150, (int) (141.0D * (color / 100.0D)), 10);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
	}

}
