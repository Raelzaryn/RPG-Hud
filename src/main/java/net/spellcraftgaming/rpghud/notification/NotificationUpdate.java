package net.spellcraftgaming.rpghud.notification;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.ForgeVersion.CheckResult;
import net.minecraftforge.fml.common.Loader;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

public class NotificationUpdate extends Notification {

	public NotificationUpdate() {
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;
		this.buttons.add(new NotificationButton(0, screen.width / 2 - 105, screen.height / 2 + 70 - 24, "Close this"));
		this.buttons.add(new NotificationButton(1, screen.width / 2 + 5, screen.height / 2 + 70 - 24, "Don't show again"));
	}

	@Override
	public void draw(GuiScreen screen) {
		super.draw(screen);
		try{
			CheckResult verCheck = ForgeVersion.getResult(Loader.instance().getIndexedModList().get(ModRPGHud.MOD_ID));
			FontRenderer font = Minecraft.getMinecraft().fontRendererObj;
			screen.drawCenteredString(font, "New update available", screen.width / 2, screen.height / 2 - 68, 0xFFD700);
			screen.drawString(font, "RPG-HUD has found an outdated version!", screen.width / 2 - 115, screen.height / 2 - 70 + 20, -1);
			screen.drawString(font, "You are currently running version: " + ModRPGHud.VERSION, screen.width / 2 - 115, screen.height / 2 - 70 + 30, -1);
			screen.drawString(font, "The latest version is: " + verCheck.target.toString(), screen.width / 2 - 115, screen.height / 2 - 70 + 40, -1);
			screen.drawString(font, "Changelog: " + verCheck.target.toString(), screen.width / 2 - 115, screen.height / 2 - 70 + 55, -1);
			
			String[] changes = verCheck.changes.get(verCheck.target).split("\n");
			
			for(int i = 0; i < changes.length; i++){
				screen.drawString(font, changes[i], screen.width / 2 - 115, screen.height / 2 - 70 + 65 + (i * 10), -1);
			}
		} catch(Exception e){
			this.shouldDestroy = true;
		}
	}
	
	@Override
	public void performAction(NotificationButton button) {
		super.performAction(button);
		if (button.id == 0) {
			this.shouldDestroy = true;
		} else if (button.id == 1) {
			this.shouldDestroy = true;
			ModRPGHud.instance.settings.setSetting(Settings.show_update_notification, false);
		}
	}
}
