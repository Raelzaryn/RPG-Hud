package net.spellcraftgaming.rpghud.event;

import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

public class PlayerContainerHandler {

	
	@SubscribeEvent
	public void onPlayerCloseContainer(@SuppressWarnings("unused") PlayerContainerEvent.Close event) {
		ModRPGHud.renderDetailsAgain[0] = true;
		ModRPGHud.renderDetailsAgain[1] = true;
		ModRPGHud.renderDetailsAgain[2] = true;
	}
}
