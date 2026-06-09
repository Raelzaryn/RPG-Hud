package net.spellcraftgaming.rpghud.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
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
	public void extractContents(GuiGraphicsExtractor gg, int mouseX, int mouseY, float partial) {
		this.drawButton(mouseX, mouseY);
		this.extractDefaultSprite(gg);
		this.extractDefaultLabel(gg.textRendererForWidget(this, GuiGraphicsExtractor.HoveredTextEffects.NONE));
	}

	public void drawButton(int mouseX, int mouseY){
	}
}
