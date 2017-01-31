package net.spellcraftgaming.rpghud.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.spellcraftgaming.rpghud.gui.GuiIngameRPGHud;

public class ClientTickHandler {

	/**
	 * Event to change the ingameHud.
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (!(Minecraft.getMinecraft().ingameGUI instanceof GuiIngameRPGHud))
			Minecraft.getMinecraft().ingameGUI = new GuiIngameRPGHud(Minecraft.getMinecraft());
	}
}
