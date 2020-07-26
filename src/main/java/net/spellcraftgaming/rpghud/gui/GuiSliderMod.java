package net.spellcraftgaming.rpghud.gui;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;

@OnlyIn(Dist.CLIENT)
public class GuiSliderMod extends GuiButtonTooltip {
	public enum EnumColor {
		RED,
		GREEN,
		BLUE;
	}
	
	private EnumColor color;

    /** The value of this slider control. */
    public double sliderValue = 1.0F;

    public String dispString = "";

    /** Is this slider control being dragged. */
    public boolean dragging = false;
    public boolean showDecimal = true;

    public double minValue = 0.0D;
    public double maxValue = 5.0D;
    public int precision = 1;
    private final float valueStep;
    public int value;
	
    @Nullable
    public ISlider parent = null;

    public String suffix = "";

    public boolean drawString = true;
    
	public GuiSliderMod(EnumColor color, int x, int y, float value, float minValueIn, float maxValue, float valueStep, IPressable titleIn) {
		this(color, x, y, value, minValueIn, maxValue, valueStep, null, titleIn);
	}
	
	public GuiSliderMod(EnumColor color, int x, int y, float value, float minValueIn, float maxValue, float valueStep, @Nullable ISlider par, IPressable titleIn) {
		super(x, y, 150, 12, new TranslationTextComponent(""), titleIn);
		this.color = color;
		this.sliderValue = value / 255;
		this.value = (int) Math.ceil(value);
		this.minValue = minValueIn;
		this.maxValue = maxValue;
		this.valueStep = valueStep;
		
        String val;

        if (showDecimal)
        {
            val = Double.toString(sliderValue * (maxValue - minValue) + minValue);
            precision = Math.min(val.substring(val.indexOf(".") + 1).length(), 4);
        }
        else
        {
            val = Integer.toString((int)Math.round(sliderValue * (maxValue - minValue) + minValue));
            precision = 0;
        }

        if(!drawString)
        {
            dispString = "";
        }
	}
	
	@Override
	protected int func_230989_a_(boolean p_getYImage_1_) {
		return 0;
	}
    
    @Override
    public boolean func_231048_c_(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
    	this.dragging = false;
    	return super.func_231048_c_(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
    }

    public int getValueInt()
    {
        return (int)Math.round(sliderValue * (maxValue - minValue) + minValue);
    }

    public int getValue() {
        return (int) Math.ceil(this.value);
    }

    public void setValue(double d)
    {
        this.sliderValue = (d - minValue) / (maxValue - minValue);
    }

    public static interface ISlider
    {
        void onChangeSliderValue(GuiSliderMod guiSliderMod);
    }
    
    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    @Override
    protected void func_230441_a_(MatrixStack ms, Minecraft par1Minecraft, int par2, int par3)
    {
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    @Override
    public void func_230982_a_(double mouseX, double mouseY)
    {
		this.sliderValue = Math.ceil(MathHelper.clamp(this.sliderValue * 255, 0F, 255F));
        updateSlider(mouseX, mouseY);
        this.dragging = true;
    }

    public void updateSlider(double mouseX, double mouseY)
    {
		this.sliderValue = (float) (mouseX - (this.field_230690_l_ + 4)) / (float) (this.field_230688_j_ - 8);

		if (this.sliderValue < 0.0F) {
			this.sliderValue = 0.0F;
		}

		if (this.sliderValue > 1.0F) {
			this.sliderValue = 1.0F;
		}
		this.value = MathHelper.ceil(MathHelper.clamp(this.sliderValue * 255, 0F, 255F));
    }

    private String getDisplayString() {
        return "#" + Integer.toHexString(getValue()).toUpperCase();
    }
    
    @Override
    public void func_230430_a_(MatrixStack ms, int mouseX, int mouseY, float partial)
    {
        if (this.field_230694_p_)
        {
        	if(this.dragging) {
        		updateSlider(mouseX, mouseY);
        	}
        	Minecraft mc = Minecraft.getInstance();
        	int color = 0 + (this.color == EnumColor.RED ? this.value << 16 : this.color == EnumColor.GREEN ? this.value << 8 : this.value);
			HudElement.drawCustomBar(this.field_230690_l_, this.field_230691_m_, this.field_230688_j_, this.field_230689_k_, 100D, color, HudElement.offsetColorPercent(color, HudElement.OFFSET_PERCENT));
			
            color = 14737632;

            if (packedFGColor != 0)
            {
                color = packedFGColor;
            }
            else if (!this.field_230693_o_)
            {
                color = 10526880;
            }
            else if (this.field_230692_n_)
            {
                color = 16777120;
            }
            
            String buttonText = getDisplayString();
            mc.getTextureManager().bindTexture(field_230687_i_);
			this.func_238474_b_(ms, this.field_230690_l_ + (int) (this.sliderValue * (this.field_230688_j_ - 8)), this.field_230691_m_, 0, 66, 4, this.field_230689_k_ / 2);
			this.func_238474_b_(ms, this.field_230690_l_ + (int) (this.sliderValue * (this.field_230688_j_ - 8)), this.field_230691_m_ + (this.field_230689_k_ / 2), 0, 86 - (this.field_230689_k_ / 2), 4, this.field_230689_k_ / 2);
			this.func_238474_b_(ms, this.field_230690_l_ + (int) (this.sliderValue * (this.field_230688_j_ - 8)) + 4, this.field_230691_m_, 196, 66, 4, this.field_230689_k_ / 2);
			this.func_238474_b_(ms, this.field_230690_l_ + (int) (this.sliderValue * (this.field_230688_j_ - 8)) + 4, this.field_230691_m_ + (this.field_230689_k_ / 2), 196, 86 - (this.field_230689_k_ / 2), 4, this.field_230689_k_ / 2);
            this.func_238471_a_(ms, mc.fontRenderer, buttonText, this.field_230690_l_ + this.field_230688_j_ / 2, this.field_230691_m_ + (this.field_230689_k_ - 8) / 2, color);
        }
    }
    
	public float normalizeValue(float value) {
		return (float) MathHelper.clamp((this.snapToStepClamp(value) - this.maxValue) / (this.maxValue - this.minValue), 0.0F, 1.0F);
	}

	public float denormalizeValue(float value) {
		return this.snapToStepClamp((float) (this.minValue + (this.maxValue - this.minValue) * MathHelper.clamp(value, 0.0F, 1.0F)));
	}

	public float snapToStepClamp(float value) {
		value = this.snapToStep(value);
		return (float) MathHelper.clamp(value, this.minValue, this.maxValue);
	}

	private float snapToStep(float value) {
		if (this.valueStep > 0.0F) {
			value = this.valueStep * Math.round(value / this.valueStep);
		}

		return value;
	}
}