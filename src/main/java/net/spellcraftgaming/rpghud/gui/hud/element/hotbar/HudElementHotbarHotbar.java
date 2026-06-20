package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.gui.hud.element.defaulthud.HudElementHotbarDefault;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHotbarHotbar extends HudElementHotbarDefault {

	public HudElementHotbarHotbar() {
		super();
		parent = HudElementType.WIDGET;
	}

	@Override
	public void drawElement(GuiGraphicsExtractor gg, float zLevel, DeltaTracker partialTicks, int scaledWidth, int scaledHeight) {
        if(this.mc.gameMode.getPlayerMode() == GameType.SPECTATOR) {
            this.mc.gui.hud.getSpectatorGui().extractHotbar(gg);
		} else if (this.mc.getCameraEntity() instanceof Player entityplayer) {
	        ItemStack itemstack = this.mc.player.getOffhandItem();
			int i = scaledWidth / 2;
			float f = zLevel;
			zLevel = -90.0F;
			int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hotbar_position)[0];
			int posY = this.settings.getPositionValue(Settings.hotbar_position)[1];
			gg.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_SPRITE, posX, scaledHeight - 47 + posY, 182, 22);
			gg.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_SELECTION_SPRITE, posX + entityplayer.getInventory().getSelectedSlot() * 20, scaledHeight - 47 - 1 + posY, 24, 22);

			gg.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_OFFHAND_RIGHT_SPRITE, posX + 174, scaledHeight - 48 + posY, 29, 24);

			zLevel = f;

			for (int l = 0; l < 9; ++l) {
				int i1 = posX + 1 + l * 20 + 2;
				int j1 = scaledHeight - 16 - 19 - 9 + posY;
				this.renderHotbarItem(gg, i1, j1, partialTicks, entityplayer, this.mc.player.getInventory().getItem(l));
			}

			int l1 = scaledHeight - 47 + 3 + posY;
			this.renderHotbarItem(gg, posX + 184, l1, partialTicks, entityplayer, itemstack);

            if(this.mc.options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
				renderAttackIndicator(gg,posX + 210 + this.settings.getPositionValue(Settings.hotbar_position)[0], scaledHeight - 44 + posY);
			}
		}
	}
}
