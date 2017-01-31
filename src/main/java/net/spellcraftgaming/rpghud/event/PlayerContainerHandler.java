package net.spellcraftgaming.rpghud.event;

import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

public class PlayerContainerHandler {

	/**
	 * Event to set stack sizes of the HUD display to be recalculated again
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public void onPlayerCloseContainer(PlayerContainerEvent.Close event) {
		ModRPGHud.renderDetailsAgain[0] = true;
		ModRPGHud.renderDetailsAgain[1] = true;
		ModRPGHud.renderDetailsAgain[2] = true;
	}
}
