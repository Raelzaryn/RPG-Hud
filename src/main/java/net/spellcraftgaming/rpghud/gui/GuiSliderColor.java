package net.spellcraftgaming.rpghud.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;

@SideOnly(Side.CLIENT)
public class GuiSliderColor extends GuiButtonTooltip
{
	public enum EnumColor{
		RED, GREEN, BLUE;
	}
	
    public float sliderValue;
    private EnumColor color;
    public boolean dragging;
    private final float minValue;
    private final float maxValue;	
	private final float valueStep;
	public int value;
	
    public GuiSliderColor(int buttonId, EnumColor color, int x, int y, float value, float minValueIn, float maxValue, float valueStep)
    {
        super(buttonId, x, y, 150, 12, "");
        this.color = color;
        this.sliderValue = value / 255;
        this.value = MathHelper.ceiling_float_int(value);
        this.minValue = minValueIn;
        this.maxValue = maxValue;
        this.valueStep = valueStep;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    @Override
	protected int getHoverState(boolean mouseOver)
    {
        return 0;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    @Override
	protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            if (this.dragging)
            {
                this.sliderValue = (float)(mouseX - (this.xPosition + 4)) / (float)(this.width - 8);

                if (this.sliderValue < 0.0F)
                {
                    this.sliderValue = 0.0F;
                }

                if (this.sliderValue > 1.0F)
                {
                    this.sliderValue = 1.0F;
                }

                this.displayString = this.getDisplayString();
                this.value = MathHelper.ceiling_float_int(MathHelper.clamp_float(this.sliderValue * 255, 0F, 255F));
            }
        }
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible)
        {
            mc.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            int color = 0 + (this.color == EnumColor.RED ? this.value << 16 : this.color == EnumColor.GREEN ? this.value << 8 : this.value);
            HudElementBarred.drawCustomBar(this.xPosition, this.yPosition, this.width, this.height, 100D, color, HudElementBarred.offsetColorPercent(color, HudElementBarred.OFFSET_PERCENT));
            this.mouseDragged(mc, mouseX, mouseY);
            int j = 14737632;

            if (this.packedFGColour != 0)
            {
                j = this.packedFGColour;
            }
            else
            if (!this.enabled)
            {
                j = 10526880;
            }
            else if (this.hovered)
            {
                j = 16777120;
            }
            mc.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.displayString = this.getDisplayString();
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, this.height / 2);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)), this.yPosition + (this.height / 2), 0, 86 - (this.height / 2), 4, this.height / 2);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, this.height / 2);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)) + 4, this.yPosition + (this.height / 2), 196, 86 - (this.height / 2), 4, this.height / 2);
            this.drawCenteredString(mc.fontRendererObj, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
            
       
        }
    }
    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    @Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        if (super.mousePressed(mc, mouseX, mouseY))
        {
            this.sliderValue = (float)(mouseX - (this.xPosition + 4)) / (float)(this.width - 8);

            if (this.sliderValue < 0.0F)
            {
                this.sliderValue = 0.0F;
            }

            if (this.sliderValue > 1.0F)
            {
                this.sliderValue = 1.0F;
            }

            this.displayString = this.getDisplayString();
            this.value = MathHelper.ceiling_float_int(MathHelper.clamp_float(this.sliderValue * 255, 0F, 255F));
            this.dragging = true;
            return true;
        }
		return false;
    }

    private String getDisplayString() {
		return "#" + Integer.toHexString(getValue()).toUpperCase();
	}

	/**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    @Override
	public void mouseReleased(int mouseX, int mouseY)
    {
        this.dragging = false;
    }
    
    public float normalizeValue(float value)
    {
        return MathHelper.clamp_float((this.snapToStepClamp(value) - this.maxValue) / (this.maxValue - this.minValue), 0.0F, 1.0F);
    }

    public float denormalizeValue(float value)
    {
        return this.snapToStepClamp(this.minValue + (this.maxValue - this.minValue) * MathHelper.clamp_float(value, 0.0F, 1.0F));
    }

    public float snapToStepClamp(float value)
    {
        value = this.snapToStep(value);
        return MathHelper.clamp_float(value, this.minValue, this.maxValue);
    }

    private float snapToStep(float value)
    {
        if (this.valueStep > 0.0F)
        {
            value = this.valueStep * Math.round(value / this.valueStep);
        }

        return value;
    }
    
    public int getValue() {
    	return MathHelper.ceiling_float_int(this.value);
    }
}