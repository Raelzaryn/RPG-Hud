package net.spellcraftgaming.rpghud.gui.hud.element.texture;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.world.entity.LivingEntity;
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
		return this.mc.player.getVehicle() instanceof LivingEntity && (this.settings.getBoolValue(Settings.limit_jump_bar) ? this.mc.player.getJumpRidingScale() > 0F : true);
	}

	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
		int height = scaledHeight + this.settings.getPositionValue(Settings.jump_bar_position)[1];
		int adjustedWidth = (scaledWidth / 2) + this.settings.getPositionValue(Settings.jump_bar_position)[0];
		float var14 = this.mc.player.getJumpRidingScale();
		int color = (int) (var14 * 100.0F);
		graphics.blit(RenderPipelines.GUI_TEXTURED,INTERFACE, adjustedWidth - 71, height - 80, 0, 160, 141, 10, 256, 256);
		graphics.blit(RenderPipelines.GUI_TEXTURED,INTERFACE, adjustedWidth - 71, height - 80, 0, 150, (int) (141.0D * (color / 100.0D)), 10, 256, 256);
	}

}
