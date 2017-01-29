package net.spellcraftgaming.rpghud.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.spellcraftgaming.rpghud.settings.EnumOptionsDebugMod;
import net.spellcraftgaming.rpghud.settings.EnumOptionsMod;

@SideOnly(Side.CLIENT)
public class GuiButtonSettings extends GuiButton{
	private final EnumOptionsMod enumOptions;
	private final EnumOptionsDebugMod enumOptionsDebug;

	private String[] tooltip;
	
	public GuiButtonSettings(int buttonId, int x, int y, String buttonText) {
		super(buttonId, x, y, buttonText);
		this.enumOptions = null;
		this.enumOptionsDebug = null;
	}

	public GuiButtonSettings(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.enumOptions = null;
		this.enumOptionsDebug = null;
	}

	public GuiButtonSettings(int buttonId, int x, int y, EnumOptionsMod setting, String buttonText) {
		super(buttonId, x, y, 150, 20, buttonText);
		this.enumOptions = setting;
		this.enumOptionsDebug = null;
	}
	
	public GuiButtonSettings(int buttonId, int x, int y, EnumOptionsDebugMod setting, String buttonText) {
		super(buttonId, x, y, 150, 20, buttonText);
		this.enumOptions = null;
		this.enumOptionsDebug = setting;
	}

	public GuiButtonSettings setTooltip(String s) {
		System.out.println(s);
		this.tooltip = s.split("/n");
		return this;
	}
	
	public GuiButtonSettings setTooltip() {
		if(enumOptions != null) return setTooltip(enumOptions.getTooltip());
		if(enumOptionsDebug != null) return setTooltip(enumOptionsDebug.getTooltip());
		return this;
	}
	
	public <Type> Type returnOptions(Class<Type> c){
		if(c == EnumOptionsMod.class) return (Type) this.enumOptions;
		if(c == EnumOptionsDebugMod.class) return (Type) this.enumOptionsDebug;
		return null;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		super.drawButton(mc, mouseX, mouseY);
	}

	public String[] getTooltip() {
		return tooltip;
	}
}
