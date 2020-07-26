package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.AbstractGui;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class HudElementArmorDefault extends HudElement {

	public HudElementArmorDefault() {
		super(HudElementType.ARMOR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(AbstractGui gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int left = scaledWidth / 2 - 91 + this.settings.getPositionValue(Settings.armor_position)[0];
		int top = scaledHeight - 40 + this.settings.getPositionValue(Settings.armor_position)[1];
		int level = this.mc.player.getTotalArmorValue();
		for (int i = 1; level > 0 && i < 20; i += 2) {
			if (i < level) {
				gui.func_238474_b_(ms, left + 48, top - 2, 34, 9, 9, 9);
			} else if (i == level) {
				gui.func_238474_b_(ms, left + 48, top - 2, 25, 9, 9, 9);
			} else if (i > level) {
				gui.func_238474_b_(ms, left + 48, top - 2, 16, 9, 9, 9);
			}
			left += 8;
		}
	}

}
