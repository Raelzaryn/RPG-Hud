package net.spellcraftgaming.rpghud.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

import java.util.function.Supplier;

@Environment(value=EnvType.CLIENT)
public abstract class GuiButtonLib extends Button.Plain {

	public GuiButtonLib(int x, int y, int widthIn, int heightIn, Component buttonText, OnPress ip) {
		super(x, y, widthIn, heightIn, buttonText, ip, Supplier::get);
	}
	
	public GuiButtonLib(int x, int y, Component buttonText, OnPress ip) {
		super(x, y, 200, 20, buttonText, ip, Supplier::get);
	}
}
