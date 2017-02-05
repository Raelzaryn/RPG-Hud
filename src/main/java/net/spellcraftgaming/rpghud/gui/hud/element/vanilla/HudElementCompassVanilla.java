package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementTexture;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementCompassVanilla extends HudElementTexture{

	public HudElementCompassVanilla() {
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
		
		bind(INTERFACE);
		gui.drawTexturedModalRect(width - 56, 0, 34, 234, 112, 9);
		
		if(rotation > 0 && rotation <= 100) {
			gui.drawCenteredString(this.mc.fontRendererObj, "W", width - 50 + rotation, 1, -1);
		}
		
		if(rotation > 25 && rotation <= 125) {
			gui.drawCenteredString(this.mc.fontRendererObj, ".", width - 75 + rotation, -2, -1);
		}
		
		if(rotation > 50 && rotation <= 150) {
			gui.drawCenteredString(this.mc.fontRendererObj, "N", width - 100 + rotation, 1, this.settings.enable_compass_color ? 0xE60909 : -1);
		}
		
		if(rotation > 75 && rotation <= 175) {
			gui.drawCenteredString(this.mc.fontRendererObj, ".", width - 125 + rotation, -2, -1);
		}
		
		if(rotation > 100 && rotation <= 200) {
			gui.drawCenteredString(this.mc.fontRendererObj, "E", width - 150 + rotation, 1, -1);
		}
		
		if(rotation >= 125) {
			gui.drawCenteredString(this.mc.fontRendererObj, ".", width - 175 + rotation, -2, -1);
		} else if(rotation <= 25) {
			gui.drawCenteredString(this.mc.fontRendererObj, ".", width + 25 + rotation, -2, -1);
		}
		
		if(rotation >= 150) {
			gui.drawCenteredString(this.mc.fontRendererObj, "S", width - 200 + rotation, 1, -1);
		} else if(rotation <= 50) {
			gui.drawCenteredString(this.mc.fontRendererObj, "S", width + rotation, 1, -1);
		}
		
		if(rotation >= 175) {
			gui.drawCenteredString(this.mc.fontRendererObj, ".", width - 225 + rotation, -2, -1);
		} else if(rotation <= 75) {
			gui.drawCenteredString(this.mc.fontRendererObj, ".", width - 25 +  rotation, -2, -1);
		}
		
		if(this.settings.reduce_size) GlStateManager.scale(0.5D, 0.5D, 0.5D);
		BlockPos pos = this.mc.thePlayer.getPosition();
		gui.drawString(this.mc.fontRendererObj, String.valueOf(pos.getX()), (width - 50) * (this.settings.reduce_size ? 2 : 1), 11 * (this.settings.reduce_size ? 2 : 1), -1);
		gui.drawCenteredString(this.mc.fontRendererObj, String.valueOf(pos.getY()), width * (this.settings.reduce_size ? 2 : 1), 11 * (this.settings.reduce_size ? 2 : 1), -1);
		gui.drawString(this.mc.fontRendererObj, String.valueOf(pos.getZ()), (width + 50) * (this.settings.reduce_size ? 2 : 1) - this.mc.fontRendererObj.getStringWidth(String.valueOf(pos.getZ())), 11 * (this.settings.reduce_size ? 2 : 1), -1);
		if(this.settings.reduce_size) GlStateManager.scale(2D, 2D, 2D);
	}

}
