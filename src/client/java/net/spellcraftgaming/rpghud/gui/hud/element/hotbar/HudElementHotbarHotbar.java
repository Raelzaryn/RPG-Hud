package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GameMode;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementHotbarHotbar extends HudElement {

	public HudElementHotbarHotbar() {
		super(HudElementType.HOTBAR, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public void drawElement(DrawContext dc, float zLevel, RenderTickCounter partialTicks, int scaledWidth, int scaledHeight) {
		if(this.mc.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR) {
			this.mc.inGameHud.getSpectatorHud().render(dc);
		} else if (this.mc.getCameraEntity() instanceof PlayerEntity entityplayer) {
			ItemStack itemstack = this.mc.player.getOffHandStack();
			int i = scaledWidth / 2;
			int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hotbar_position)[0];
			int posY = this.settings.getPositionValue(Settings.hotbar_position)[1];
			dc.drawGuiTexture(RenderPipelines.GUI_TEXTURED, HOTBAR_TEXTURE, posX, scaledHeight - 47 + posY, 182, 22);
			dc.drawGuiTexture(RenderPipelines.GUI_TEXTURED, HOTBAR_SELECTION_TEXTURE, posX + entityplayer.getInventory().getSelectedSlot() * 20 - 1, scaledHeight - 47 - 1 + posY, 24, 23);
			drawRect(dc,posX + entityplayer.getInventory().getSelectedSlot() * 20 - 1, scaledHeight - 25 + posY,24,1,0xFF000000 );
			dc.drawGuiTexture(RenderPipelines.GUI_TEXTURED, HOTBAR_OFFHAND_RIGHT_TEXTURE, posX + 174, scaledHeight - 48 + posY, 29, 24);

			int s = 1;

			for (int l = 0; l < 9; ++l) {
				int i1 = posX + 1 + l * 20 + 2;
				int j1 = scaledHeight - 16 - 19 - 9 + posY;
				this.renderHotbarItem(dc, i1, j1, partialTicks, this.mc.player, this.mc.player.getInventory().getStack(s), s++);
			}

			int l1 = scaledHeight - 47 + 3 + posY;
			this.renderHotbarItem(dc, posX + 184, l1, partialTicks, this.mc.player, itemstack, s);

			if(this.mc.options.getAttackIndicator().getValue() == AttackIndicator.HOTBAR) {
				float f1 = this.mc.player.getAttackCooldownProgress(0.0F);

				if (f1 < 1.0F) {
					int i2 = scaledHeight - 36 + posY;
					int j2 = i + 40 + this.settings.getPositionValue(Settings.hotbar_position)[0];

					int k1 = (int) (f1 * 19.0F);
					dc.drawGuiTexture(RenderPipelines.GUI_TEXTURED, HOTBAR_ATTACK_INDICATOR_BACKGROUND_TEXTURE, j2, i2 - 9, 18, 18);
					dc.drawGuiTexture(RenderPipelines.GUI_TEXTURED, HOTBAR_ATTACK_INDICATOR_PROGRESS_TEXTURE, j2, i2 - 9 + 18 - k1, 18, k1);
				}
			}

		}
	}
}
