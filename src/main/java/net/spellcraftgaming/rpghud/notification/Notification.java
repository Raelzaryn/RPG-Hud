package net.spellcraftgaming.rpghud.notification;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;

public class Notification{

	protected static final ResourceLocation NOTIFICATION = new ResourceLocation("rpghud:textures/notification.png");
	
	public boolean shouldDestroy = false;
	
	List<NotificationButton> buttons = new ArrayList<NotificationButton>();
	public Notification() {
	}
	
	public void drawOnScreen(GuiScreen screen){
		draw(screen);
		drawButtons(screen);
	}
	
	public void draw(GuiScreen screen){
		HudElement.drawRect(0, 0, screen.width, screen.height, 0x98000000);
		Minecraft.getMinecraft().getTextureManager().bindTexture(NOTIFICATION);
		screen.drawTexturedModalRect((screen.width - 250) / 2, (screen.height - 150) / 2, 0, 0, 250, 150);
	}
	
	public void drawButtons(GuiScreen screen){
		for(NotificationButton button : this.buttons){
			button.drawButton(screen);
		}
	}
	
	public void handleMouse(){
		for(NotificationButton button : this.buttons){
			if(button.mouseOver()) performAction(button);
		}
	}
	
	public void performAction(NotificationButton button){
		
	}
}
