package net.spellcraftgaming.rpghud.event;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

public class PlayerEventHandler {

	/**Event to set stack sizes of the HUD display to be recalculated again
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public void onPickupItem(PlayerEvent.ItemPickupEvent event) {
		ModRPGHud.renderDetailsAgain[0] = true;
		ModRPGHud.renderDetailsAgain[1] = true;
		ModRPGHud.renderDetailsAgain[2] = true;
	}
}
