package net.spellcraftgaming.rpghud.event;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

public class PlayerEventHandler {

	@SubscribeEvent
	public void onPickupItem(@SuppressWarnings("unused") PlayerEvent.ItemPickupEvent event) {
		ModRPGHud.renderDetailsAgain[0] = true;
		ModRPGHud.renderDetailsAgain[1] = true;
		ModRPGHud.renderDetailsAgain[2] = true;
	}
}
