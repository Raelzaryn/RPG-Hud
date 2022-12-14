package net.spellcraftgaming.rpghud.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public abstract class GuiButtonLib extends ButtonWidget{

	public GuiButtonLib(int x, int y, int widthIn, int heightIn, Text buttonText, ButtonWidget.PressAction ip) {
		super(x, y, widthIn, heightIn, buttonText, ip, Supplier::get);
	}
	
	public GuiButtonLib(int x, int y, Text buttonText, ButtonWidget.PressAction ip) {
		super(x, y, 200, 20, buttonText, ip, Supplier::get);
	}

	@Override
	public void renderButton(MatrixStack ms, int mouseX, int mouseY, float partial) {
		super.renderButton(ms, mouseX, mouseY, partial);
		this.drawButton(mouseX, mouseY);
	}
	
	public void drawButton(int mouseX, int mouseY){
	}
}
