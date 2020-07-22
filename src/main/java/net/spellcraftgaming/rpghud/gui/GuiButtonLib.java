package net.spellcraftgaming.rpghud.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.ButtonWidget;

@Environment(EnvType.CLIENT)
public abstract class GuiButtonLib extends ButtonWidget{

	public GuiButtonLib(int x, int y, int widthIn, int heightIn, String buttonText, ButtonWidget.PressAction ip) {
		super(x, y, widthIn, heightIn, buttonText, ip);
	}
	
	public GuiButtonLib(int x, int y, String buttonText, ButtonWidget.PressAction ip) {
		super(x, y, 200, 20, buttonText, ip);
	}

	@Override
	public void renderButton(int mouseX, int mouseY, float partial) {
		super.renderButton(mouseX, mouseY, partial);
		this.drawButton(mouseX, mouseY);
	}
	
	public void drawButton(int mouseX, int mouseY){
	}
}
