package net.spellcraftgaming.rpghud.client.main;

import net.fabricmc.api.ClientModInitializer;
import net.spellcraftgaming.rpghud.client.gui.hud.Hud;
import net.spellcraftgaming.rpghud.client.gui.hud.HudSimple;

public class RPGHudClient implements ClientModInitializer {
	
	public static Hud currentHud;
	@Override
	public void onInitializeClient() {
		currentHud = new HudSimple("simple");
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}