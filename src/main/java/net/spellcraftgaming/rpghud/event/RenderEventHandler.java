package net.spellcraftgaming.rpghud.event;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.notification.Notification;
import net.spellcraftgaming.rpghud.notification.NotificationOldSettings;
import net.spellcraftgaming.rpghud.notification.NotificationUpdate;
import net.spellcraftgaming.rpghud.settings.Settings;

public class RenderEventHandler {

	public Notification notification = null;
	
	public static boolean updateCheck = true;
	public static boolean oldSettingCheck = true;
	
	@SubscribeEvent
	public void onRenderTick(GuiScreenEvent event){
		if(event.getGui() instanceof GuiMainMenu){
			if(updateCheck && this.notification == null && ModRPGHud.instance.settings.getBoolValue(Settings.show_update_string) && ModRPGHud.outdated){
				updateCheck = false;
				this.notification = new NotificationUpdate();
			}
			if(oldSettingCheck && this.notification == null && (new File(Minecraft.getMinecraft().mcDataDir, "RPGHud_settings.txt").exists() || new File(Minecraft.getMinecraft().mcDataDir, "RPGHud_settings_debug.txt").exists())){
				oldSettingCheck = false;
				this.notification = new NotificationOldSettings(ModRPGHud.instance.settings);
				System.out.println("X");
			}
		}
	}
	
	@SubscribeEvent
	public void onActionPerformed(ActionPerformedEvent event){
		if(event.getGui() instanceof GuiMainMenu && this.notification != null){
			event.setCanceled(true);
		}
	}
}
