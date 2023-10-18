package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
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
	public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if(this.mc.gameMode.getPlayerMode() == GameType.SPECTATOR) {
            this.mc.gui.getSpectatorGui().renderHotbar(ms);
		} else if (this.mc.getCameraEntity() instanceof Player) {
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			bind(WIDGETS_TEX_PATH);
			Player entityplayer = (Player) this.mc.getCameraEntity();
			ItemStack itemstack = this.mc.player.getOffhandItem();
			int i = scaledWidth / 2;
			float f = zLevel;
			zLevel = -90.0F;
			int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hotbar_position)[0];
			int posY = this.settings.getPositionValue(Settings.hotbar_position)[1];
			GuiComponent.blit(ms, posX, scaledHeight - 47 + posY, 0, 0, 182, 22);
			GuiComponent.blit(ms, posX + entityplayer.getInventory().selected * 20, scaledHeight - 47 - 1 + posY, 0, 22, 24, 22);

			GuiComponent.blit(ms, posX + 181, scaledHeight - 47 + posY, 60, 23, 22, 22);

			zLevel = f;
			RenderSystem.enableBlend();
	        RenderSystem.defaultBlendFunc();

			for (int l = 0; l < 9; ++l) {
				int i1 = posX + 1 + l * 20 + 2;
				int j1 = scaledHeight - 16 - 19 - 9 + posY;
				this.renderHotbarItem(ms, i1, j1, partialTicks, entityplayer, this.mc.player.getInventory().items.get(l));
			}

			int l1 = scaledHeight - 47 + 3 + posY;
			this.renderHotbarItem(ms, posX + 184, l1, partialTicks, entityplayer, itemstack);

            if(this.mc.options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
                float f1 = this.mc.player.getAttackAnim(0.0F);

				if (f1 < 1.0F) {
					int i2 = scaledHeight - 36 + posY;
					int j2 = i + 40 + this.settings.getPositionValue(Settings.hotbar_position)[0];

                    bind(Gui.GUI_ICONS_LOCATION);
					int k1 = (int) (f1 * 19.0F);
					RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
					GuiComponent.blit(ms, j2, i2 - 9, 0, 94, 18, 18);
					GuiComponent.blit(ms, j2, i2 - 9 + 18 - k1, 18, 112 - k1, 18, k1);
				}
			}

			RenderSystem.disableBlend();
		}
	}
}
