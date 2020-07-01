package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.AbstractGui;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementArmorModern extends HudElement {

	public HudElementArmorModern() {
		super(HudElementType.ARMOR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(AbstractGui gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int left = scaledWidth / 2 - 91 + this.settings.getPositionValue(Settings.armor_position)[0];
		int top = scaledHeight - ForgeIngameGui.left_height + 2 + this.settings.getPositionValue(Settings.armor_position)[1];

		int level = this.mc.player.getTotalArmorValue();
		if (level > 0) {
			int width2 = 1 + 9 + 2 + this.mc.fontRenderer.getStringWidth(String.valueOf(level)) + 2;
			drawRect(left, top, width2, 10, 0xA0000000);
			this.mc.fontRenderer.func_238421_b_(ms,String.valueOf(level), left + 12, top + 2, -1);
			this.mc.getTextureManager().bindTexture(AbstractGui.field_230665_h_);
			gui.func_238474_b_(ms, left + 1, top + 1, 34, 9, 9, 9);
		}
		ForgeIngameGui.left_height += 10;
	}

}
