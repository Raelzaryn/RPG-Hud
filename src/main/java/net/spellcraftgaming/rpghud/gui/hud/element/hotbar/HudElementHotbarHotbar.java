package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.gui.hud.element.defaulthud.HudElementHotbarDefault;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHotbarHotbar extends HudElementHotbarDefault {

	protected static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");

	public HudElementHotbarHotbar() {
		super();
		parent = HudElementType.WIDGET;
	}

	@Override
	public void drawElement(GuiGraphics gg, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if(this.mc.gameMode.getPlayerMode() == GameType.SPECTATOR) {
            this.mc.gui.getSpectatorGui().renderHotbar(gg);
		} else if (this.mc.getCameraEntity() instanceof Player entityplayer) {
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	        ItemStack itemstack = this.mc.player.getOffhandItem();
			int i = scaledWidth / 2;
			float f = zLevel;
			zLevel = -90.0F;
			int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hotbar_position)[0];
			int posY = this.settings.getPositionValue(Settings.hotbar_position)[1];
			gg.blitSprite(HOTBAR_SPRITE, posX, scaledHeight - 47 + posY, 182, 22);
			gg.blitSprite(HOTBAR_SELECTION_SPRITE, posX + entityplayer.getInventory().selected * 20, scaledHeight - 47 - 1 + posY, 24, 22);

			gg.blitSprite(HOTBAR_OFFHAND_RIGHT_SPRITE, posX + 174, scaledHeight - 48 + posY, 29, 24);

			zLevel = f;
			RenderSystem.enableBlend();
	        RenderSystem.defaultBlendFunc();

			for (int l = 0; l < 9; ++l) {
				int i1 = posX + 1 + l * 20 + 2;
				int j1 = scaledHeight - 16 - 19 - 9 + posY;
				this.renderHotbarItem(gg, i1, j1, partialTicks, entityplayer, this.mc.player.getInventory().items.get(l));
			}

			int l1 = scaledHeight - 47 + 3 + posY;
			this.renderHotbarItem(gg, posX + 184, l1, partialTicks, entityplayer, itemstack);

            if(this.mc.options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
				renderAttackIndicator(gg,posX + 210 + this.settings.getPositionValue(Settings.hotbar_position)[0], scaledHeight - 44 + posY);
			}

			RenderSystem.disableBlend();
		}
	}
}
