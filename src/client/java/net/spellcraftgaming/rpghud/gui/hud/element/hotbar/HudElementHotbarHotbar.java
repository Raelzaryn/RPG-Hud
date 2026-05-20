package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
	public boolean checkConditions() {
		return !this.mc.gameMode.isSpectator();
	}

	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
        if (this.mc.getCameraEntity() instanceof Player) {
			Player entityplayer = (Player) this.mc.getCameraEntity();
			ItemStack itemstack = this.mc.player.getOffhandItem();
			int i = scaledWidth / 2;
			int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hotbar_position)[0];
			int posY = this.settings.getPositionValue(Settings.hotbar_position)[1];
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_TEXTURE, posX, scaledHeight - 47 + posY, 182, 22);
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_SELECTION_TEXTURE, posX + entityplayer.getInventory().getSelectedSlot() * 20, scaledHeight - 47 - 1 + posY, 24, 22);

			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_OFFHAND_RIGHT_TEXTURE, posX + 181, scaledHeight - 47 + posY, 22, 22);

	        int s = 1;
	        
			for (int l = 0; l < 9; ++l) {
				int i1 = posX + 1 + l * 20 + 2;
				int j1 = scaledHeight - 16 - 19 - 9 + posY;
				this.renderHotbarItem(graphics, i1, j1, deltaTracker, this.mc.player, this.mc.player.getInventory().getItem(s), s++);
			}

			int l1 = scaledHeight - 47 + 3 + posY;
			this.renderHotbarItem(graphics, posX + 184, l1, deltaTracker, this.mc.player, itemstack, s++);

			if(this.mc.options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
				float f1 = this.mc.player.getAttackAnim(0.0f);

				if (f1 < 1.0F) {
					int i2 = scaledHeight - 36 + posY;
					int j2 = i + 40 + this.settings.getPositionValue(Settings.hotbar_position)[0];

					int k1 = (int) (f1 * 19.0F);
					graphics.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_ATTACK_INDICATOR_BACKGROUND_TEXTURE, j2, i2 - 9, 18, 18);
					graphics.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_ATTACK_INDICATOR_PROGRESS_TEXTURE, j2, i2 - 9 + 18 - k1, 18, k1);
				}
			}

		}
	}
}
