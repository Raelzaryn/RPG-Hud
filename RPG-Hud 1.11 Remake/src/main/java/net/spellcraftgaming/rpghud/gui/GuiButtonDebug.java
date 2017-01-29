package net.spellcraftgaming.rpghud.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.spellcraftgaming.rpghud.settings.EnumOptionsDebugMod;

@SideOnly(Side.CLIENT)
public class GuiButtonDebug extends GuiButton{
	private final EnumOptionsDebugMod enumOptions;

	public GuiButtonDebug(int par1, int par2, int par3, String par4Str) {
		this(par1, par2, par3, (EnumOptionsDebugMod) null, par4Str);
	}

	public GuiButtonDebug(int par1, int par2, int par3, int par4, int par5, String par6Str) {
		super(par1, par2, par3, par4, par5, par6Str);
		this.enumOptions = null;
	}

	public GuiButtonDebug(int par1, int par2, int par3, EnumOptionsDebugMod par4EnumOptions, String par5Str) {
		super(par1, par2, par3, 150, 20, par5Str);
		this.enumOptions = par4EnumOptions;
	}

	public EnumOptionsDebugMod returnEnumOptions() {
		return this.enumOptions;
	}
	
	@Override
	public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {
		super.drawButton(p_146112_1_, p_146112_2_, p_146112_3_);
        this.hovered = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition && p_146112_2_ < this.xPosition + this.width && p_146112_3_ < this.yPosition + this.height;
        int k = this.getHoverState(this.hovered);
		if(k == 2)GuiButtonTooltip.setTooltip(Minecraft.getMinecraft().currentScreen, this);
	}
}
