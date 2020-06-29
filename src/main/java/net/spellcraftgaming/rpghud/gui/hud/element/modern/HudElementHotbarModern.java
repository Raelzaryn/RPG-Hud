package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.settings.AttackIndicatorStatus;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameType;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHotbarModern extends HudElement {

	public HudElementHotbarModern() {
        super(HudElementType.HOTBAR, 0, 0, 0, 0, true);
    }

    protected static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");

	@Override
	public void drawElement(AbstractGui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if (this.mc.playerController.getCurrentGameType() == GameType.SPECTATOR) {
            mc.ingameGUI.getSpectatorGui().renderTooltip(partialTicks);
		} else if (this.mc.getRenderViewEntity() instanceof PlayerEntity) {
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.getTextureManager().bindTexture(WIDGETS_TEX_PATH);
			PlayerEntity entityplayer = (PlayerEntity) this.mc.getRenderViewEntity();
			ItemStack itemstack = this.mc.player.getHeldItemOffhand();
			int posX = this.settings.getPositionValue(Settings.hotbar_position)[0];
			int posY = this.settings.getPositionValue(Settings.hotbar_position)[1];
			HandSide enumhandside = this.mc.player.getPrimaryHand().opposite();
			int width = scaledWidth;
			int height = scaledHeight + posY;
			int i = (width / 2) + posX;
			float f = zLevel;
			zLevel = -90.0F;
			drawRect(width / 2 - 91 + posX, height - 22 - 5, 182, 2, 0xA0000000);
			if(this.mc.player.isCreative()) drawRect(width / 2 - 91 + posX, height - 7, 182, 2, 0xA0000000);
			for (int x = 0; x < 10; x++) {
				drawRect(width / 2 - 91 + (x * 20) + posX, height - 22 - 3, 2, 18, 0xA0000000);
				if (x < 9) {
					drawRect(width / 2 - 91 + 2 + (x * 20) + posX, height - 22 - 3, 18, 18, 0x60000000);
				}
			}
			drawRect(width / 2 - 91 + 2 + (entityplayer.inventory.currentItem * 20) + posX, height - 22 - 3, 18, 18, 0x40FFFFFF);
			RenderSystem.enableAlphaTest();
			if (itemstack != ItemStack.EMPTY) {
				if (enumhandside == HandSide.LEFT) {
					drawRect(width / 2 - 91 - 24 + posX, height - 22 - 5, 22, 2, 0xA0000000);
					drawRect(width / 2 - 91 - 24 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(width / 2 - 91 - 4 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(width / 2 - 91 + 2 - 24 + posX, height - 22 - 3, 18, 18, 0x60000000);
					if(this.mc.player.isCreative()) drawRect(width / 2 - 91 - 24 + posX, height - 7, 22, 2, 0xA0000000);
				} else {
					drawRect(width / 2 - 91 - 24 + 209 + posX, height - 22 - 5, 22, 2, 0xA0000000);
					drawRect(width / 2 - 91 - 24 + 209 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(width / 2 - 91 - 4 + 209 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(width / 2 - 91 + 2 - 24 + 209 + posX, height - 22 - 3, 18, 18, 0x60000000);
					if(this.mc.player.isCreative()) drawRect(width / 2 - 91 - 24 + 209 + posX, height - 7, 22, 2, 0xA0000000);
				}
			}

			zLevel = f;
			RenderSystem.enableRescaleNormal();
			RenderSystem.enableBlend();
			RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	        RenderHelper.enableStandardItemLighting();

			for (int l = 0; l < 9; ++l) {
				int i1 = i - 90 + l * 20 + 2;
				int j1 = scaledHeight - 16 - 3 - 9 + 4 + posY;
				this.renderHotbarItem(i1, j1, partialTicks, entityplayer, this.mc.player.inventory.mainInventory.get(l));
			}

			if (itemstack != ItemStack.EMPTY) {
				int l1 = scaledHeight - 16 - 3 - 9 + posY;

				if (enumhandside == HandSide.LEFT) {
					this.renderHotbarItem(i - 91 - 26 + 5, l1 + 4, partialTicks, entityplayer, itemstack);
				} else {
					this.renderHotbarItem(i + 91 + 10 - 4, l1 + 4, partialTicks, entityplayer, itemstack);
				}
			}

			if (this.mc.gameSettings.attackIndicator == AttackIndicatorStatus.HOTBAR) {
				float f1 = this.mc.player.getCooledAttackStrength(0.0F);

				if (f1 < 1.0F) {
					int i2 = scaledHeight - 17 + posY;
					int j2 = i + 91 + 6;

					if (enumhandside == HandSide.RIGHT) {
						j2 = i - 91 - 22;
					}

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
}
