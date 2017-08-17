package net.spellcraftgaming.lib.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonLib extends GuiButton{

	public GuiButtonLib(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}
	
	public GuiButtonLib(int buttonId, int x, int y, String buttonText) {
		super(buttonId, x, y, buttonText);
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partial) {
		super.drawButton(mc, mouseX, mouseY, partial);
		this.drawButton(mc, mouseX, mouseY);
	}
	
	public void drawButton(Minecraft mc, int mouseX, int mouseY){
	}
}
