package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.ForgeHooks;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementArmorHotbar extends HudElement {

	public HudElementArmorHotbar() {
		super(HudElementType.ARMOR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}
	
	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int height = res.getScaledHeight();
		int left = this.settings.render_player_face ? 46 : 22;
		int top = height - 64;

		int level = ForgeHooks.getTotalArmorValue(this.mc.thePlayer);
		for (int i = 1; level > 0 && i < 20; i += 2) {
			if (i < level) {
				gui.drawTexturedModalRect(left + 62, top - 2, 34, 9, 9, 9);
			} else if (i == level) {
				gui.drawTexturedModalRect(left + 62, top - 2, 25, 9, 9, 9);
			} else if (i > level) {
				gui.drawTexturedModalRect(left + 62, top - 2, 16, 9, 9, 9);
			}
			left += 8;
		}
	}

}
