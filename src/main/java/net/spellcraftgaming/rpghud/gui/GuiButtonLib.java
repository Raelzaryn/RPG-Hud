package net.spellcraftgaming.rpghud.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public abstract class GuiButtonLib extends Button {

	public GuiButtonLib(int x, int y, int widthIn, int heightIn, Component buttonText, Button.OnPress ip) {
		super(x, y, widthIn, heightIn, buttonText, ip, DEFAULT_NARRATION);
	}
	
	public GuiButtonLib(int x, int y, Component buttonText, Button.OnPress ip) {
		super(x, y, 200, 20, buttonText, ip, DEFAULT_NARRATION);
	}

	@Override
	public void renderWidget(GuiGraphics gg, int mouseX, int mouseY, float partial) {
		super.renderWidget(gg, mouseX, mouseY, partial);
		this.drawButton(mouseX, mouseY);
	}
	
	public void drawButton(int mouseX, int mouseY){
	}
}
