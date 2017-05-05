package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLiving;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementEntityInspectVanilla;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementEntityInspectModern extends HudElementEntityInspectVanilla {

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks, double scale) {
		EntityLiving focused = GameData.getFocusedEntity(GameData.getPlayer());
		if (focused != null) {
			ScaledResolution res = new ScaledResolution(this.mc);
			int width = res.getScaledWidth() / 2;

			drawRect(width - 62, 20, 32, 32, 0xA0000000);
			drawRect(width - 60, 22, 28, 28, 0x20FFFFFF);
			drawRect(width - 30, 20, 90, 12, 0xA0000000);
			drawTetragon(width - 30, width - 30, 32, 32, 90, 76, 10, 10, 0xA0000000);
			drawTetragon(width - 30, width - 30, 33, 33, 84, 74, 6, 6, 0x20FFFFFF);

			drawTetragon(width - 30, width - 30, 33, 33, (int) (84 * ((double) focused.getHealth() / (double) focused.getMaxHealth())), (int) (84 * ((double) focused.getHealth() / (double) focused.getMaxHealth())) - 10, 6, 6, this.settings.getIntValue(Settings.color_health));

			String stringHealth = ((double) Math.round(focused.getHealth() * 10)) / 10 + "/" + ((double) Math.round(focused.getMaxHealth() * 10)) / 10;

			GlStateManager.scale(0.5, 0.5, 0.5);
			gui.drawCenteredString(this.mc.fontRendererObj, stringHealth, (width - 29 + 44) * 2, 34 * 2, -1);
			GlStateManager.scale(2.0, 2.0, 2.0);

			int x = (width - 29 + 44 - this.mc.fontRendererObj.getStringWidth(focused.getName()) / 2);
			int y = 23;
			this.mc.fontRendererObj.drawString(focused.getName(), x, y, -1);

			drawEntityOnScreen(width - 60 + 14, 22 + 25, focused, scale);
		}
	}

}
