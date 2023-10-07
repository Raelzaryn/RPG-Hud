package net.spellcraftgaming.rpghud.gui;

import java.util.function.Supplier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

@Environment(value=EnvType.CLIENT)
public abstract class GuiButtonLib extends ButtonWidget{

	public GuiButtonLib(int x, int y, int widthIn, int heightIn, Text buttonText, ButtonWidget.PressAction ip) {
		super(x, y, widthIn, heightIn, buttonText, ip, Supplier::get);
	}
	
	public GuiButtonLib(int x, int y, Text buttonText, ButtonWidget.PressAction ip) {
		super(x, y, 200, 20, buttonText, ip, Supplier::get);
	}

	@Override
	public void renderButton(DrawContext dc, int mouseX, int mouseY, float partial) {
		super.renderButton(dc, mouseX, mouseY, partial);
		this.drawButton(mouseX, mouseY);
	}
	
	public void drawButton(int mouseX, int mouseY){
	}
}
