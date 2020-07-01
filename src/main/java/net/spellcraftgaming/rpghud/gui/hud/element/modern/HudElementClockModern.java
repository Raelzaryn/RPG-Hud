package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.vanilla.HudElementClockVanilla;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class HudElementClockModern extends HudElementClockVanilla {

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
		return this.settings.getBoolValue(Settings.enable_clock) && !this.mc.gameSettings.showDebugInfo && (this.settings.getBoolValue(Settings.enable_immersive_clock) ? this.mc.player.inventory.hasItemStack(new ItemStack(Items.CLOCK)) : true);
	}

	@Override
	public void drawElement(AbstractGui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		int yOffset = (this.settings.getBoolValue(Settings.render_player_face) ? 0 : 8) + ((this.settings.getBoolValue(Settings.show_numbers_health) && this.settings.getBoolValue(Settings.show_numbers_food)) ? 0 : 4) + this.settings.getPositionValue(Settings.clock_position)[1];
		int xOffset = this.settings.getPositionValue(Settings.clock_position)[0];
		int clockColor = 0xFFFFFF;
		if (this.settings.getBoolValue(Settings.enable_clock_color)) {
			clockColor = getClockColor();
		}
		if (this.settings.getStringValue(Settings.clock_time_format) == "time.24") {
			drawRect(xOffset + 2, 23 + yOffset, 20, 6, 0xA0000000);
		} else {
			drawRect(xOffset + 2, 23 + yOffset, 23, 6, 0xA0000000);
		}
		GL11.glScaled(0.5D, 0.5D, 0.5D);

		if (this.settings.getStringValue(Settings.clock_time_format) == "time.24") {
			gui.drawCenteredString(this.mc.fontRenderer, getTime(), xOffset * 2 + 24, 48 + 2 * yOffset, clockColor);
		} else {
			gui.drawCenteredString(this.mc.fontRenderer, getTime(), xOffset * 2 + 28, 48 + 2 * yOffset, clockColor);
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glScaled(2.0D, 2.0D, 2.0D);
	}

}
