package net.spellcraftgaming.rpghud.event;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

public class PlayerTickHandler {

	/**
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public void onWorldTick(PlayerTickEvent event) {
		ModRPGHud.instance.pickupHandler.onUpdate();
	}
}
