package net.spellcraftgaming.rpghud.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.ButtonWidget;

import java.util.function.Supplier;

@Environment(value=EnvType.CLIENT)
public abstract class GuiButtonLib extends ButtonWidget.Text {

	public GuiButtonLib(int x, int y, int widthIn, int heightIn, net.minecraft.text.Text buttonText, ButtonWidget.PressAction ip) {
		super(x, y, widthIn, heightIn, buttonText, ip, Supplier::get);
	}
	
	public GuiButtonLib(int x, int y, net.minecraft.text.Text buttonText, ButtonWidget.PressAction ip) {
		super(x, y, 200, 20, buttonText, ip, Supplier::get);
	}
}
