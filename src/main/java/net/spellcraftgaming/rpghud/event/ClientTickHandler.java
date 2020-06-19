package net.spellcraftgaming.rpghud.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.spellcraftgaming.rpghud.gui.override.GuiIngameRPGHud;

@OnlyIn(Dist.CLIENT)
public class ClientTickHandler {

	public static void init() {
		MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
	}
	
	/**
	 * Event to change the ingameGui.
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (!(Minecraft.getInstance().ingameGUI instanceof GuiIngameRPGHud))
			Minecraft.getInstance().ingameGUI = new GuiIngameRPGHud(Minecraft.getInstance());
	}
}
