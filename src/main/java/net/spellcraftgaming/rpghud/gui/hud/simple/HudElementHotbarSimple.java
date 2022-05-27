package net.spellcraftgaming.rpghud.gui.hud.simple;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHotbarSimple extends HudElement{

	public HudElementHotbarSimple() {
        super(HudElementType.HOTBAR, 0, 0, 0, 0, true);
    }

    protected static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");

	@Override
	public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if(this.mc.gameMode.getPlayerMode() == GameType.SPECTATOR) {
                this.mc.gui.getSpectatorGui().renderHotbar(ms, partialTicks);
		} else if (this.mc.getCameraEntity() instanceof Player) {
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.setShaderTexture(0, WIDGETS_TEX_PATH);
			Player entityplayer = (Player) this.mc.getCameraEntity();
			ItemStack itemstack = this.mc.player.getOffhandItem();
			int posX = this.settings.getPositionValue(Settings.hotbar_position)[0];
			int posY = 5 + this.settings.getPositionValue(Settings.hotbar_position)[1];
			HumanoidArm enumhandside = this.mc.player.getMainArm().getOpposite();
			int width = scaledWidth;
			int height = scaledHeight + posY;
			int i = (width / 2) + posX;
			float f = zLevel;
			zLevel = -90.0F;
			drawRect(ms, width / 2 - 91 + posX, height - 22 - 5, 182, 2, 0xA0000000);
			drawRect(ms, width / 2 - 91 + posX, height - 22 - 5 + 20, 182, 2, 0xA0000000);
			if(this.mc.player.isCreative()) drawRect(ms, width / 2 - 91 + posX, height - 7, 182, 2, 0xA0000000);
			for (int x = 0; x < 10; x++) {
				drawRect(ms, width / 2 - 91 + (x * 20) + posX, height - 22 - 3, 2, 18, 0xA0000000);
				if (x < 9) {
					drawRect(ms, width / 2 - 91 + 2 + (x * 20) + posX, height - 22 - 3, 18, 18, 0x60000000);
				}
			}
			drawRect(ms, width / 2 - 91 + 2 + (entityplayer.getInventory().selected * 20) + posX, height - 22 - 3, 18, 18, 0x40FFFFFF);
			if (itemstack != ItemStack.EMPTY) {
				if (enumhandside == HumanoidArm .LEFT) {
					drawRect(ms, width / 2 - 91 - 24 + posX, height - 22 - 5, 22, 2, 0xA0000000);
					drawRect(ms, width / 2 - 91 - 24 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(ms, width / 2 - 91 - 4 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(ms, width / 2 - 91 + 2 - 24 + posX, height - 22 - 3, 18, 18, 0x60000000);
					drawRect(ms, width / 2 - 91 - 24 + posX, height - 7, 22, 2, 0xA0000000);
				} else {
					drawRect(ms, width / 2 - 91 - 24 + 209 + posX, height - 22 - 5, 22, 2, 0xA0000000);
					drawRect(ms, width / 2 - 91 - 24 + 209 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(ms, width / 2 - 91 - 4 + 209 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(ms, width / 2 - 91 + 2 - 24 + 209 + posX, height - 22 - 3, 18, 18, 0x60000000);
					drawRect(ms, width / 2 - 91 - 24 + 209 + posX, height - 7, 22, 2, 0xA0000000);
				}
			}

			zLevel = f;
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();

			for (int l = 0; l < 9; ++l) {
				int i1 = i - 90 + l * 20 + 2;
				int j1 = scaledHeight - 16 - 3 - 9 + 4 + posY;
				this.renderHotbarItem(i1, j1, partialTicks, entityplayer, this.mc.player.getInventory().items.get(l));
			}

			if (itemstack != ItemStack.EMPTY) {
				int l1 = scaledHeight - 16 - 3 - 9 + posY;

				if (enumhandside == HumanoidArm .LEFT) {
					this.renderHotbarItem(i - 91 - 26 + 5, l1 + 4, partialTicks, entityplayer, itemstack);
				} else {
					this.renderHotbarItem(i + 91 + 10 - 4, l1 + 4, partialTicks, entityplayer, itemstack);
				}
			}

            if(this.mc.options.attackIndicator == AttackIndicatorStatus.HOTBAR) {
                float f1 = this.mc.player.getAttackAnim(0.0F);

				if (f1 < 1.0F) {
					int i2 = scaledHeight - 17 + posY;
					int j2 = i + 91 + 6;

					if (enumhandside == HumanoidArm .RIGHT) {
						j2 = i - 91 - 22;
					}

					bind(Gui.GUI_ICONS_LOCATION);
					int k1 = (int) (f1 * 19.0F);
					RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
					gui.blit(ms, j2, i2 - 9, 0, 94, 18, 18);
					gui.blit(ms, j2, i2 - 9 + 18 - k1, 18, 112 - k1, 18, k1);
				}
			}

			RenderSystem.disableBlend();
		}
	}
}
