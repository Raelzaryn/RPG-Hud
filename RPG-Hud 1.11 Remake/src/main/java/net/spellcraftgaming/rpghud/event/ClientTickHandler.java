package net.spellcraftgaming.rpghud.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.spellcraftgaming.rpghud.gui.GuiIngameRPGHud;

public class ClientTickHandler {
	
	private Minecraft mc;
	
	public ClientTickHandler() {
		this.mc = Minecraft.getMinecraft();
	}
	
	/**Event to change the ingameHud.
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event){
		if(!(this.mc.ingameGUI instanceof GuiIngameRPGHud)) this.mc.ingameGUI = new GuiIngameRPGHud(Minecraft.getMinecraft());
	}
}
