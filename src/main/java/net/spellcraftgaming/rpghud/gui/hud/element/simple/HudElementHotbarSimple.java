package net.spellcraftgaming.rpghud.gui.hud.element.simple;

import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.spellcraftgaming.rpghud.gui.hud.element.defaulthud.HudElementHotbarDefault;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementHotbarSimple extends HudElementHotbarDefault {

	public HudElementHotbarSimple() {
        super();
    }

	@Override
	public void drawElement(GuiGraphicsExtractor gg, float zLevel, DeltaTracker partialTicks, int scaledWidth, int scaledHeight) {
        if(this.mc.gameMode.getPlayerMode() == GameType.SPECTATOR) {
                this.mc.gui.hud.getSpectatorGui().extractHotbar(gg);
		} else if (this.mc.getCameraEntity() instanceof Player entityplayer) {
	        ItemStack itemstack = this.mc.player.getOffhandItem();
			int posX = this.settings.getPositionValue(Settings.hotbar_position)[0];
			int posY = 5 + this.settings.getPositionValue(Settings.hotbar_position)[1];
			HumanoidArm enumhandside = this.mc.player.getMainArm().getOpposite();
	        int height = scaledHeight + posY;
			int i = (scaledWidth / 2) + posX;
			float f = zLevel;
			zLevel = -90.0F;

	        drawRect(gg, scaledWidth / 2 - 91 + posX, height - 22 - 5, 182, 2, 0xA0000000);
	        drawRect(gg, scaledWidth / 2 - 91 + posX, height - 7, 182, 2, 0xA0000000);
			for (int x = 0; x < 10; x++) {
				drawRect(gg, scaledWidth / 2 - 91 + (x * 20) + posX, height - 22 - 3, 2, 18, 0xA0000000);
				if (x < 9) {
					drawRect(gg, scaledWidth / 2 - 91 + 2 + (x * 20) + posX, height - 22 - 3, 18, 18, 0x60000000);
				}
			}
			drawRect(gg, scaledWidth / 2 - 91 + 2 + (entityplayer.getInventory().getSelectedSlot() * 20) + posX, height - 22 - 3, 18, 18, 0x40FFFFFF);
			if (itemstack != ItemStack.EMPTY) {
				if (enumhandside == HumanoidArm .LEFT) {
					drawRect(gg, scaledWidth / 2 - 91 - 24 + posX, height - 22 - 5, 22, 2, 0xA0000000);
					drawRect(gg, scaledWidth / 2 - 91 - 24 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(gg, scaledWidth / 2 - 91 - 4 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(gg, scaledWidth / 2 - 91 + 2 - 24 + posX, height - 22 - 3, 18, 18, 0x60000000);
					drawRect(gg, scaledWidth / 2 - 91 - 24 + posX, height - 7, 22, 2, 0xA0000000);
				} else {
					drawRect(gg, scaledWidth / 2 - 91 - 24 + 209 + posX, height - 22 - 5, 22, 2, 0xA0000000);
					drawRect(gg, scaledWidth / 2 - 91 - 24 + 209 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(gg, scaledWidth / 2 - 91 - 4 + 209 + posX, height - 22 - 3, 2, 18, 0xA0000000);
					drawRect(gg, scaledWidth / 2 - 91 + 2 - 24 + 209 + posX, height - 22 - 3, 18, 18, 0x60000000);
					drawRect(gg, scaledWidth / 2 - 91 - 24 + 209 + posX, height - 7, 22, 2, 0xA0000000);
				}
			}

			zLevel = f;

			for (int l = 0; l < 9; ++l) {
				int i1 = i - 90 + l * 20 + 2;
				int j1 = scaledHeight - 16 - 3 - 9 + 4 + posY;
				this.renderHotbarItem(gg, i1, j1, partialTicks, entityplayer, this.mc.player.getInventory().getItem(l));
			}

			if (itemstack != ItemStack.EMPTY) {
				int l1 = scaledHeight - 16 - 3 - 9 + posY;

				if (enumhandside == HumanoidArm .LEFT) {
					this.renderHotbarItem(gg, i - 91 - 26 + 5, l1 + 4, partialTicks, entityplayer, itemstack);
				} else {
					this.renderHotbarItem(gg, i + 91 + 10 - 4, l1 + 4, partialTicks, entityplayer, itemstack);
				}
			}

            if(this.mc.options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
	            int j2 = i + 91 + 6;

	            if (enumhandside == HumanoidArm .RIGHT) {
		            j2 = i - 91 - 22;
	            }
				renderAttackIndicator(gg, j2, scaledHeight - 25 + posY);
			}
		}
	}
}
