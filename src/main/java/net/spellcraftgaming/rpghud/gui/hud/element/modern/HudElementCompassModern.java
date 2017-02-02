package net.spellcraftgaming.rpghud.gui.hud.element.modern;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementCompassModern extends HudElement{

	public HudElementCompassModern() {
		super(HudElementType.COMPASS, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.settings.enable_compass && !this.mc.gameSettings.showDebugInfo && (this.settings.enable_immersive_compass ? this.mc.thePlayer.inventory.hasItemStack(new ItemStack(Items.COMPASS)) : true);
		}
	
	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int width = res.getScaledWidth() / 2;
		
		int rotation = Math.round(((this.mc.thePlayer.rotationYaw % 360) / 360) * 200);
		if(rotation < 0) rotation = 200 + rotation;
		drawRect(width - 50, 4, 100, 6, 0xAA000000);
		
		if(rotation > 0 && rotation <= 100) {
			gui.drawCenteredString(this.mc.fontRendererObj, "W", width - 50 + rotation, 3, -1);
		}
		if(rotation > 25 && rotation <= 125) {
			gui.drawCenteredString(this.mc.fontRendererObj, ".", width - 75 + rotation, 1, -1);
		}
		
		if(rotation > 50 && rotation <= 150) {
			gui.drawCenteredString(this.mc.fontRendererObj, "N", width - 100 + rotation, 3, this.settings.enable_compass_color ? 0xE60909 : -1);
		}
		
		if(rotation > 75 && rotation <= 175) {
			gui.drawCenteredString(this.mc.fontRendererObj, ".", width - 125 + rotation, 1, -1);
		}
		
		if(rotation > 100 && rotation <= 200) {
			gui.drawCenteredString(this.mc.fontRendererObj, "E", width - 150 + rotation, 3, -1);
		}
		
		if(rotation >= 125) {
			gui.drawCenteredString(this.mc.fontRendererObj, ".", width - 175 + rotation, 1, -1);
		} else if(rotation <= 25) {
			gui.drawCenteredString(this.mc.fontRendererObj, ".", width + 25 + rotation, 1, -1);
		}
		
		if(rotation >= 150) {
			gui.drawCenteredString(this.mc.fontRendererObj, "S", width - 200 + rotation, 3, -1);
		} else if(rotation <= 50) {
			gui.drawCenteredString(this.mc.fontRendererObj, "S", width + rotation, 3, -1);
		}
		
		if(rotation >= 175) {
			gui.drawCenteredString(this.mc.fontRendererObj, ".", width - 225 + rotation, 1, -1);
		} else if(rotation <= 75) {
			gui.drawCenteredString(this.mc.fontRendererObj, ".", width - 25 +  rotation, 1, -1);
		}
		
		BlockPos pos = this.mc.thePlayer.getPosition();
		drawRect(width - 50, 11, this.mc.fontRendererObj.getStringWidth(String.valueOf(pos.getX())) / 2 + 4, 6, 0xA0000000);
		drawRect((int)(width - ((float) this.mc.fontRendererObj.getStringWidth(String.valueOf(pos.getY())) / 4) - 2), 11, this.mc.fontRendererObj.getStringWidth(String.valueOf(pos.getY())) / 2 + 4, 6, 0xA0000000);
		drawRect((width + 48) - (this.mc.fontRendererObj.getStringWidth(String.valueOf(pos.getZ()))/2) - 2, 11, this.mc.fontRendererObj.getStringWidth(String.valueOf(pos.getZ())) / 2 + 4, 6, 0xA0000000);
		
		GlStateManager.scale(0.5D, 0.5D, 0.5D);
		gui.drawString(this.mc.fontRendererObj, String.valueOf(pos.getX()), (width - 48) * 2, 12 * 2, -1);
		gui.drawCenteredString(this.mc.fontRendererObj, String.valueOf(pos.getY()), width * 2, 12 * 2, -1);
		gui.drawString(this.mc.fontRendererObj, String.valueOf(pos.getZ()), (width + 48) * 2 - this.mc.fontRendererObj.getStringWidth(String.valueOf(pos.getZ())), 12 * 2, -1);
		GlStateManager.scale(2D, 2D, 2D);
	}

}
