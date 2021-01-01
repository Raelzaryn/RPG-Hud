package net.spellcraftgaming.rpghud.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class GuiButtonLib extends Button{

	public GuiButtonLib(int x, int y, int widthIn, int heightIn, ITextComponent buttonText, IPressable ip) {
		super(x, y, widthIn, heightIn, buttonText, ip);
	}
	
	public GuiButtonLib(int x, int y, ITextComponent buttonText, IPressable ip) {
		super(x, y, 200, 20, buttonText, ip);
	}

	
	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partial) {
		super.render(ms, mouseX, mouseY, partial);
		this.drawButton(mouseX, mouseY);
	}
	
	public void drawButton(int mouseX, int mouseY){
	}
}
