package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.settings.AttackIndicatorStatus;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameType;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHotbarHotbar extends HudElement {

	protected static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");

	public HudElementHotbarHotbar() {
		super(HudElementType.HOTBAR, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public void drawElement(AbstractGui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if (this.mc.playerController.getCurrentGameType() == GameType.SPECTATOR) {
            this.mc.ingameGUI.getSpectatorGui().renderTooltip(partialTicks);
		} else if (this.mc.getRenderViewEntity() instanceof PlayerEntity) {
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.getTextureManager().bindTexture(WIDGETS_TEX_PATH);
			PlayerEntity entityplayer = (PlayerEntity) this.mc.getRenderViewEntity();
			ItemStack itemstack = this.mc.player.getHeldItemOffhand();
			int i = scaledWidth / 2;
			float f = zLevel;
			zLevel = -90.0F;
			int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hotbar_position)[0];
			int posY = this.settings.getPositionValue(Settings.hotbar_position)[1];
			gui.blit(posX, scaledHeight - 47 + posY, 0, 0, 182, 22);
			gui.blit(posX + entityplayer.inventory.currentItem * 20, scaledHeight - 47 - 1 + posY, 0, 22, 24, 22);

			gui.blit(posX + 181, scaledHeight - 47 + posY, 60, 23, 22, 22);

			zLevel = f;
			RenderSystem.enableRescaleNormal();
			RenderSystem.enableBlend();
	        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			RenderHelper.enableStandardItemLighting();

			for (int l = 0; l < 9; ++l) {
				int i1 = posX + 1 + l * 20 + 2;
				int j1 = scaledHeight - 16 - 19 - 9 + posY;
				this.renderHotbarItem(i1, j1, partialTicks, entityplayer, this.mc.player.inventory.mainInventory.get(l));
			}

			int l1 = scaledHeight - 47 + 3 + posY;
			this.renderHotbarItem(posX + 184, l1, partialTicks, entityplayer, itemstack);

			if (this.mc.gameSettings.attackIndicator == AttackIndicatorStatus.HOTBAR) {
				float f1 = this.mc.player.getCooledAttackStrength(0.0F);

				if (f1 < 1.0F) {
					int i2 = scaledHeight - 36 + posY;
					int j2 = i + 40 + this.settings.getPositionValue(Settings.hotbar_position)[0];

					this.mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
					int k1 = (int) (f1 * 19.0F);
					RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
					gui.blit(j2, i2 - 9, 0, 94, 18, 18);
					gui.blit(j2, i2 - 9 + 18 - k1, 18, 112 - k1, 18, k1);
				}
			}

			RenderHelper.disableStandardItemLighting();
			RenderSystem.disableRescaleNormal();
			RenderSystem.disableBlend();
		}
	}

	/**
	 * Renders an item on the screen
	 * 
	 * @param xPos
	 *            the x position on the screen
	 * @param yPos
	 *            the y position on the screen
	 * @param partialTicks
	 *            the partial ticks (used for animation)
	 * @param player
	 *            the player who should get the item rendered
	 * @param item
	 *            the item (via ItemStack)
	 */
	protected void renderHotbarItem(int x, int y, float partialTicks, PlayerEntity player, ItemStack item) {
		if (!item.isEmpty()) {
			float f = (float)item.getAnimationsToGo() - partialTicks;

			if (f > 0.0F) {
				RenderSystem.pushMatrix();
				float f1 = 1.0F + f / 5.0F;
				RenderSystem.translatef(x + 8, y + 12, 0.0F);
				RenderSystem.scalef(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
				RenderSystem.translatef((-(x + 8)), (-(y + 12)), 0.0F);
			}

	        this.mc.getItemRenderer().renderItemAndEffectIntoGUI(player, item, x, y);

			if (f > 0.0F) {
				RenderSystem.popMatrix();
			}

			this.mc.getItemRenderer().renderItemOverlays(this.mc.fontRenderer, item, x, y);
		}
	}

}
