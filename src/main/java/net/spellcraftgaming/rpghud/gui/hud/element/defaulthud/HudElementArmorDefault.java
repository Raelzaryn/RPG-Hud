package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.ForgeHooks;
import net.spellcraftgaming.rpghud.gui.GuiIngameRPGHud;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementArmorDefault extends HudElement {

	public HudElementArmorDefault() {
		super(HudElementType.ARMOR, 0, 0, 0, 0, true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int width = res.getScaledWidth();
		int height = res.getScaledHeight();
		int left = width / 2 - 91;
		int top = height - GuiIngameRPGHud.left_height;

		int level = ForgeHooks.getTotalArmorValue(this.mc.player);
		for (int i = 1; level > 0 && i < 20; i += 2) {
			if (i < level) {
				gui.drawTexturedModalRect(left + 48, top - 2, 34, 9, 9, 9);
			} else if (i == level) {
				gui.drawTexturedModalRect(left + 48, top - 2, 25, 9, 9, 9);
			} else if (i > level) {
				gui.drawTexturedModalRect(left + 48, top - 2, 16, 9, 9, 9);
			}
			left += 8;
		}
		GuiIngameRPGHud.left_height += 10;
	}

}
