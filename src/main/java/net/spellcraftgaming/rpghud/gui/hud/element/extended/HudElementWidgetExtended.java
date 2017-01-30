package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.spellcraftgaming.rpghud.gui.GuiIngameRPGHud;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementWidgetExtended extends HudElement{

	protected static final ResourceLocation INTERFACE = new ResourceLocation("rpghud:textures/interface.png");
	
	public HudElementWidgetExtended() {
		super(HudElementType.WIDGET, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.gameSettings.showDebugInfo;
	}
	
	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		((GuiIngameRPGHud) gui).bind(INTERFACE);
		gui.drawTexturedModalRect(0, 0, 0, 50, 162, 50);
		if (this.mc.player.getRidingEntity() instanceof EntityLivingBase) {
			gui.drawTexturedModalRect(51, 44, 163, 0, 92, 20);
		}
		((GuiIngameRPGHud) gui).bind(Gui.ICONS);
		
		if (!this.mc.gameSettings.showDebugInfo) {

			((GuiIngameRPGHud) gui).bind(getPlayerSkin(this.mc.player));
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			gui.drawTexturedModalRect(34, 34, 32, 32, 32, 32);
			gui.drawTexturedModalRect(34, 34, 160, 32, 32, 32);
			GL11.glScaled(2.0D, 2.0D, 2.0D);
			((GuiIngameRPGHud) gui).bind(Gui.ICONS);
		}
	}
	
	protected static ResourceLocation getPlayerSkin(AbstractClientPlayer player) {
		return player.getLocationSkin();
	}

}
