package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameMode;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementHotbarModern extends HudElement {

	public HudElementHotbarModern() {
        super(HudElementType.HOTBAR, 0, 0, 0, 0, true);
    }

    protected static final Identifier WIDGETS_TEX_PATH = new Identifier("textures/gui/widgets.png");

	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if(this.mc.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR) {
            this.mc.inGameHud.getSpectatorHud().render(dc);
		} else if (this.mc.getCameraEntity() instanceof PlayerEntity) {
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			PlayerEntity entityplayer = (PlayerEntity) this.mc.getCameraEntity();
			ItemStack itemstack = this.mc.player.getOffHandStack();
			int posX = this.settings.getPositionValue(Settings.hotbar_position)[0];
			int posY = this.settings.getPositionValue(Settings.hotbar_position)[1];
			Arm enumhandside = this.mc.player.getMainArm().getOpposite();
			int width = scaledWidth;
			int height = scaledHeight + posY;
			int i = (width / 2) + posX;
			float f = zLevel;
			zLevel = -90.0F;
			drawRect(dc, width / 2 - 91 + posX, height - 22 - 5, 182, 2, 0xA0000000);
			if(this.mc.player.isCreative()) drawRect(dc, width / 2 - 91 + posX, height - 7, 182, 2, 0xA0000000);
			for (int x = 0; x < 10; x++) {
				drawRect(dc, width / 2 - 91 + (x * 20) + posX, height - 22 - 3, 2, 18, 0xA0000000);
				if (x < 9) {
					drawRect(dc, width / 2 - 91 + 2 + (x * 20) + posX, height - 22 - 3, 18, 18, 0x60000000);
				}
			}
			drawRect(dc, width / 2 - 91 + 2 + (entityplayer.getInventory().selectedSlot * 20) + posX, height - 22 - 3, 18, 18, 0x40FFFFFF);
			if (itemstack != ItemStack.EMPTY) {
				if (enumhandside == Arm.LEFT) {
					drawRect(dc, width / 2 - 91 - 24 + posX, height - 22 - 5, 22, 2, 0xA0000000);
					drawRect(dc, width / 2 - 91 - 24 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(dc, width / 2 - 91 - 4 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(dc, width / 2 - 91 + 2 - 24 + posX, height - 22 - 3, 18, 18, 0x60000000);
					if(this.mc.player.isCreative()) drawRect(dc, width / 2 - 91 - 24 + posX, height - 7, 22, 2, 0xA0000000);
				} else {
					drawRect(dc, width / 2 - 91 - 24 + 209 + posX, height - 22 - 5, 22, 2, 0xA0000000);
					drawRect(dc, width / 2 - 91 - 24 + 209 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(dc, width / 2 - 91 - 4 + 209 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(dc, width / 2 - 91 + 2 - 24 + 209 + posX, height - 22 - 3, 18, 18, 0x60000000);
					if(this.mc.player.isCreative()) drawRect(dc, width / 2 - 91 - 24 + 209 + posX, height - 7, 22, 2, 0xA0000000);
				}
			}

			zLevel = f;
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();

			for (int l = 0; l < 9; ++l) {
				int i1 = i - 90 + l * 20 + 2;
				int j1 = scaledHeight - 16 - 3 - 9 + 4 + posY;
				this.renderHotbarItem(dc, i1, j1, partialTicks, entityplayer, this.mc.player.getInventory().main.get(l));
			}

			if (itemstack != ItemStack.EMPTY) {
				int l1 = scaledHeight - 16 - 3 - 9 + posY;

				if (enumhandside == Arm.LEFT) {
					this.renderHotbarItem(dc, i - 91 - 26 + 5, l1 + 4, partialTicks, entityplayer, itemstack);
				} else {
					this.renderHotbarItem(dc, i + 91 + 10 - 4, l1 + 4, partialTicks, entityplayer, itemstack);
				}
			}

            if(this.mc.options.getAttackIndicator().getValue() == AttackIndicator.HOTBAR) {
                float f1 = this.mc.player.getAttackCooldownProgress(0.0F);

				if (f1 < 1.0F) {
					int i2 = scaledHeight - 17 + posY;
					int j2 = i + 91 + 6;

					if (enumhandside == Arm.RIGHT) {
						j2 = i - 91 - 22;
					}

					int k1 = (int) (f1 * 19.0F);
					RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
					dc.drawTexture(ICONS, j2, i2 - 9, 0, 94, 18, 18);
					dc.drawTexture(ICONS, j2, i2 - 9 + 18 - k1, 18, 112 - k1, 18, k1);
				}
			}

			RenderSystem.disableBlend();
		}
	}
}
