package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementClockVanilla;

public class HudElementClockModern extends HudElementClockVanilla{

	public HudElementClockModern() {
		super();
		this.posX = 0;
		this.posY = 0;
		this.elementWidth = 0;
		this.elementHeight = 0;
		this.moveable = true;
	}

	@Override
	public boolean checkConditions() {
		return this.settings.enable_clock && !this.mc.gameSettings.showDebugInfo && (this.settings.enable_immersive_clock ? this.mc.thePlayer.inventory.hasItemStack(new ItemStack(Items.CLOCK)) : true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int clockColor = 0xFFFFFF;
		if (this.settings.enable_clock_color) {
			clockColor = getClockColor();
		}
		drawRect(2, 23,20, 6, 0xA0000000);
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		
		gui.drawCenteredString(this.mc.fontRendererObj, getTime(), 24, 48, clockColor);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		GL11.glScaled(2.0D, 2.0D, 2.0D);
	}

}
