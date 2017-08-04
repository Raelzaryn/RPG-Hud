package net.spellcraftgaming.rpghud.notification;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.spellcraftgaming.lib.GameData;

public class NotificationButton {

	protected static final ResourceLocation NOTIFICATION = new ResourceLocation("rpghud:textures/notification.png");
	public int id;
	private int x;
	private int y;
	private String text;
	
	public NotificationButton(int id, int x, int y, String text) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.text = text;
	}
	
	public boolean mouseOver(){
		GuiScreen gui = Minecraft.getMinecraft().currentScreen;
		int mouseX = Mouse.getEventX() * gui.width / Minecraft.getMinecraft().displayWidth;
		int mouseY = gui.height - Mouse.getEventY() * gui.height / Minecraft.getMinecraft().displayHeight - 1;
		return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + 100 && mouseY < this.y + 20;
	}
	
	public void drawButton(GuiScreen screen){
		int color = 0xFFFFFF;
		if(mouseOver()) color = 0xFFD700;
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(NOTIFICATION);
		screen.drawTexturedModalRect(this.x, this.y, 0, 150, 100, 24);
		screen.drawCenteredString(GameData.getFontRenderer(), this.text, this.x + 50, this.y + 8, color);
	}
	
	
}
