package net.spellcraftgaming.rpghud.gui;

import net.minecraft.client.gui.GuiButton;

public class GuiButtonLib extends GuiButton{

	public GuiButtonLib(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}
	
	public GuiButtonLib(int buttonId, int x, int y, String buttonText) {
		super(buttonId, x, y, buttonText);
	}

	@Override
	public void render(int mouseX, int mouseY, float partial) {
		super.render(mouseX, mouseY, partial);
		this.drawButton(mouseX, mouseY);
	}
	
	public void drawButton(int mouseX, int mouseY){
	}
}
