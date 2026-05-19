package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
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
	public void drawElement(GuiGraphicsExtractor dc, float zLevel, DeltaTracker partialTicks, int scaledWidth, int scaledHeight) {
        if(this.mc.gameMode.getPlayerMode() == GameType.SPECTATOR) {
            this.mc.gui.getSpectatorGui().extractHotbar(dc);
		} else if (this.mc.getCameraEntity() instanceof Player) {
			Player entityplayer = (Player) this.mc.getCameraEntity();
			net.minecraft.world.item.ItemStack itemstack = this.mc.player.getOffhandItem();
			int i = scaledWidth / 2;
			float f = zLevel;
			zLevel = -90.0F;
			int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hotbar_position)[0];
			int posY = this.settings.getPositionValue(Settings.hotbar_position)[1];
			dc.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_TEXTURE, posX, scaledHeight - 47 + posY, 182, 22);
			dc.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_SELECTION_TEXTURE, posX + entityplayer.getInventory().getSelectedSlot() * 20, scaledHeight - 47 - 1 + posY, 24, 22);

			dc.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_OFFHAND_RIGHT_TEXTURE, posX + 181, scaledHeight - 47 + posY, 22, 22);

			zLevel = f;

	        int s = 1;
	        
			for (int l = 0; l < 9; ++l) {
				int i1 = posX + 1 + l * 20 + 2;
				int j1 = scaledHeight - 16 - 19 - 9 + posY;
				this.renderHotbarItem(dc, i1, j1, partialTicks, entityplayer, this.mc.player.getInventory().getItem(s), s++);
			}

			int l1 = scaledHeight - 47 + 3 + posY;
			this.renderHotbarItem(dc, posX + 184, l1, partialTicks, entityplayer, itemstack, s++);

            if(this.mc.options.attackIndicator().get()  == AttackIndicatorStatus.HOTBAR) {
                float f1 = this.mc.player.getAttackStrengthScale(0.0F);

				if (f1 < 1.0F) {
					int i2 = scaledHeight - 36 + posY;
					int j2 = i + 40 + this.settings.getPositionValue(Settings.hotbar_position)[0];

					int k1 = (int) (f1 * 19.0F);
					dc.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_ATTACK_INDICATOR_BACKGROUND_TEXTURE, j2, i2 - 9, 18, 18);
					dc.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_ATTACK_INDICATOR_PROGRESS_TEXTURE, j2, i2 - 9 + 18 - k1, 18, k1);
				}
			}

		}
	}
}
