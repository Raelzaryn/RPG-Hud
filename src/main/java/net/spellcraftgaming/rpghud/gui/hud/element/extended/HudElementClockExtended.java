package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementClockVanilla;

public class HudElementClockExtended extends HudElementClockVanilla {

	public HudElementClockExtended() {
		super();
	}

	@Override
	public boolean checkConditions() {
		return super.checkConditions() && this.settings.enable_clock && !this.mc.gameSettings.showDebugInfo && (this.settings.enable_immersive_clock ? this.mc.player.inventory.hasItemStack(new ItemStack(Items.CLOCK)) : true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int clockColor = 0xFFFFFF;
		if (this.settings.enable_clock_color) {
			clockColor = getClockColor();
		}
		if (this.settings.reduce_size)
			GL11.glScaled(0.5D, 0.5D, 0.5D);
		gui.drawString(this.mc.fontRendererObj, getTime(), this.settings.reduce_size ? 8 : 4, this.settings.reduce_size ? 124 : 62, clockColor);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if (this.settings.reduce_size)
			GL11.glScaled(2.0D, 2.0D, 2.0D);
	}

}
