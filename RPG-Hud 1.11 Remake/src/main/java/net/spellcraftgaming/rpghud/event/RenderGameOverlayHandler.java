package net.spellcraftgaming.rpghud.event;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderGameOverlayHandler {

	@SubscribeEvent
	public void onRenderOverlay(RenderGameOverlayEvent.Pre event){
		if(event.getType() == ElementType.HEALTH){
			//event.setCanceled(true);
		}
	}
}
