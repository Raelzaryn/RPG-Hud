package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameMode;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(EnvType.CLIENT)
public class HudElementHotbarHotbar extends HudElement {

	protected static final Identifier WIDGETS_TEX_PATH = new Identifier("textures/gui/widgets.png");

	public HudElementHotbarHotbar() {
		super(HudElementType.HOTBAR, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public void drawElement(DrawableHelper gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if(this.mc.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR) {
            this.mc.inGameHud.getSpectatorHud().render(ms);
		} else if (this.mc.getCameraEntity() instanceof PlayerEntity) {
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			bind(WIDGETS_TEX_PATH);
			PlayerEntity entityplayer = (PlayerEntity) this.mc.getCameraEntity();
			ItemStack itemstack = this.mc.player.getOffHandStack();
			int i = scaledWidth / 2;
			float f = zLevel;
			zLevel = -90.0F;
			int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hotbar_position)[0];
			int posY = this.settings.getPositionValue(Settings.hotbar_position)[1];
			gui.drawTexture(ms, posX, scaledHeight - 47 + posY, 0, 0, 182, 22);
			gui.drawTexture(ms, posX + entityplayer.getInventory().selectedSlot * 20, scaledHeight - 47 - 1 + posY, 0, 22, 24, 22);

			gui.drawTexture(ms, posX + 181, scaledHeight - 47 + posY, 60, 23, 22, 22);

			zLevel = f;
			RenderSystem.enableBlend();
	        RenderSystem.defaultBlendFunc();

			for (int l = 0; l < 9; ++l) {
				int i1 = posX + 1 + l * 20 + 2;
				int j1 = scaledHeight - 16 - 19 - 9 + posY;
				this.renderHotbarItem(i1, j1, partialTicks, entityplayer, this.mc.player.getInventory().main.get(l));
			}

			int l1 = scaledHeight - 47 + 3 + posY;
			this.renderHotbarItem(posX + 184, l1, partialTicks, entityplayer, itemstack);

            if(this.mc.options.getAttackIndicator().getValue() == AttackIndicator.HOTBAR) {
                float f1 = this.mc.player.getAttackCooldownProgress(0.0F);

				if (f1 < 1.0F) {
					int i2 = scaledHeight - 36 + posY;
					int j2 = i + 40 + this.settings.getPositionValue(Settings.hotbar_position)[0];

                    bind(DrawableHelper.GUI_ICONS_TEXTURE);
					int k1 = (int) (f1 * 19.0F);
					RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
					gui.drawTexture(ms, j2, i2 - 9, 0, 94, 18, 18);
					gui.drawTexture(ms, j2, i2 - 9 + 18 - k1, 18, 112 - k1, 18, k1);
				}
			}

			RenderSystem.disableBlend();
		}
	}
}
