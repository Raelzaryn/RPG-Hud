package net.spellcraftgaming.rpghud.gui.hud.element.simple;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementHotbarSimple extends HudElement{

	public HudElementHotbarSimple() {
        super(HudElementType.HOTBAR, 0, 0, 0, 0, true);
    }

	@Override
	public boolean checkConditions() {
		return !this.mc.gameMode.isSpectator();
	}

	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
		if (this.mc.getCameraEntity() instanceof Player) {
			Player entityplayer = (Player) this.mc.getCameraEntity();
			ItemStack itemstack = this.mc.player.getMainHandItem();
			int posX = this.settings.getPositionValue(Settings.hotbar_position)[0];
			int posY = 5 + this.settings.getPositionValue(Settings.hotbar_position)[1];
			HumanoidArm enumhandside = this.mc.player.getMainArm().getOpposite();
			int width = scaledWidth;
			int height = scaledHeight + posY;
			int i = (width / 2) + posX;
			drawRect(graphics, width / 2 - 91 + posX, height - 22 - 5, 182, 2, 0xA0000000);
			drawRect(graphics, width / 2 - 91 + posX, height - 22 - 5 + 20, 182, 2, 0xA0000000);
			if(this.mc.player.isCreative()) drawRect(graphics, width / 2 - 91 + posX, height - 7, 182, 2, 0xA0000000);
			for (int x = 0; x < 10; x++) {
				drawRect(graphics, width / 2 - 91 + (x * 20) + posX, height - 22 - 3, 2, 18, 0xA0000000);
				if (x < 9) {
					drawRect(graphics, width / 2 - 91 + 2 + (x * 20) + posX, height - 22 - 3, 18, 18, 0x60000000);
				}
			}
			drawRect(graphics, width / 2 - 91 + 2 + (entityplayer.getInventory().getSelectedSlot() * 20) + posX, height - 22 - 3, 18, 18, 0x40FFFFFF);
			if (itemstack != ItemStack.EMPTY) {
				if (enumhandside == HumanoidArm.LEFT) {
					drawRect(graphics, width / 2 - 91 - 24 + posX, height - 22 - 5, 22, 2, 0xA0000000);
					drawRect(graphics, width / 2 - 91 - 24 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(graphics, width / 2 - 91 - 4 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(graphics, width / 2 - 91 - 24 + posX, height - 22 - 5 + 20, 22, 2, 0xA0000000);
					drawRect(graphics, width / 2 - 91 + 2 - 24 + posX, height - 22 - 3, 18, 18, 0x60000000);
					if(this.mc.player.isCreative()) drawRect(graphics, width / 2 - 91 - 24 + posX, height - 7, 22, 2, 0xA0000000);
				} else {
					drawRect(graphics, width / 2 - 91 - 24 + 209 + posX, height - 22 - 5, 22, 2, 0xA0000000);
					drawRect(graphics, width / 2 - 91 - 24 + 209 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(graphics, width / 2 - 91 - 4 + 209 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(graphics, width / 2 - 91 - 24 + 209 + posX, height - 22 - 5 + 20, 22, 2, 0xA0000000);
					drawRect(graphics, width / 2 - 91 + 2 - 24 + 209 + posX, height - 22 - 3, 18, 18, 0x60000000);
					if(this.mc.player.isCreative()) drawRect(graphics, width / 2 - 91 - 24 + 209 + posX, height - 7, 22, 2, 0xA0000000);
				}
			}

			int s = 1;
			
			for (int l = 0; l < 9; ++l) {
				int i1 = i - 90 + l * 20 + 2;
				int j1 = scaledHeight - 16 - 3 - 9 + 4 + posY;
				this.renderHotbarItem(graphics, i1, j1, deltaTracker, this.mc.player, this.mc.player.getInventory().getItem(l), s++);
			}

			if (itemstack != ItemStack.EMPTY) {
				int l1 = scaledHeight - 16 - 3 - 9 + posY;

				if (enumhandside == HumanoidArm.LEFT) {
					this.renderHotbarItem(graphics, i - 91 - 26 + 5, l1 + 4, deltaTracker, this.mc.player, itemstack, s++);
				} else {
					this.renderHotbarItem(graphics, i + 91 + 10 - 4, l1 + 4, deltaTracker, this.mc.player, itemstack, s++);
				}
			}

            if(this.mc.options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
				float f1 = this.mc.player.getAttackAnim(0.0f);

				if (f1 < 1.0F) {
					int i2 = scaledHeight - 17 + posY;
					int j2 = i + 91 + 6;

					if (enumhandside == HumanoidArm.RIGHT) {
						j2 = i - 91 - 22;
					}

					int k1 = (int) (f1 * 19.0F);
					graphics.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_ATTACK_INDICATOR_BACKGROUND_TEXTURE, j2, i2 - 9, 18, 18);
					graphics.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_ATTACK_INDICATOR_PROGRESS_TEXTURE, j2, i2 - 9 + 18 - k1, 18, k1);
				}
			}
		}
	}
}
