package net.spellcraftgaming.rpghud.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

public class RenderEventHandler {

	
	@SubscribeEvent
	public void onRenderTick(GuiScreenEvent event){
		if(event.gui instanceof GuiMainMenu && ModRPGHud.instance.settings.show_update_string && ModRPGHud.outdated){
			Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Update available for RPG-HUD", 0, 0, 0xFFD700);
		}
	}
}
