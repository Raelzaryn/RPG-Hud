package net.spellcraftgaming.rpghud.gui;

import static net.spellcraftgaming.rpghud.gui.hud.element.modern.HudElementHotbarModern.WIDGETS_TEX_PATH;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;

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
	
    public ISlider parent = null;

    public String suffix = "";

    public boolean drawString = true;
    
	public GuiSliderMod(EnumColor color, int x, int y, float value, float minValueIn, float maxValue, float valueStep, Button.OnPress titleIn) {
		this(color, x, y, value, minValueIn, maxValue, valueStep, null, titleIn);
	}
	
	public GuiSliderMod(EnumColor color, int x, int y, float value, float minValueIn, float maxValue, float valueStep, ISlider par, Button.OnPress titleIn) {
		super(x, y, 150, 12, Component.translatable(""), titleIn);
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
	
	/*@Override
	private int getTextureY() {
		return 0;
	}*/
    
    @Override
    public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
    	this.dragging = false;
    	return super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
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
     
    @Override
    protected void renderBg(PoseStack matrices, Minecraft client, int mouseX, int mouseY) {
    }*/
    
    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    @Override
    public void onClick(double mouseX, double mouseY)
    {
		this.sliderValue = Math.ceil(Mth.clamp(this.sliderValue * 255, 0F, 255F));
        updateSlider(mouseX, mouseY);
        this.dragging = true;
    }

    public void updateSlider(double mouseX, double mouseY)
    {
		this.sliderValue = (float) (mouseX - (this.x + 4)) / (float) (this.width - 8);

		if (this.sliderValue < 0.0F) {
			this.sliderValue = 0.0F;
		}

		if (this.sliderValue > 1.0F) {
			this.sliderValue = 1.0F;
		}
		this.value = Mth.ceil(Mth.clamp(this.sliderValue * 255, 0F, 255F));
    }

    private String getDisplayString() {
        return "#" + Integer.toHexString(getValue()).toUpperCase();
    }
    
    @Override
    public void render(PoseStack ms, int mouseX, int mouseY, float partial)
    {
        if (this.visible)
        {
        	if(this.dragging) {
        		updateSlider(mouseX, mouseY);
        	}
        	Minecraft mc = Minecraft.getInstance();
        	int color = 0 + (this.color == EnumColor.RED ? this.value << 16 : this.color == EnumColor.GREEN ? this.value << 8 : this.value);
			HudElement.drawCustomBar(ms, this.x, this.y, this.width, this.height, 100D, color, HudElement.offsetColorPercent(color, HudElement.OFFSET_PERCENT));
			
            color = 14737632;
            
            if (!this.active)
            {
                color = 10526880;
            }
            else if (this.isHovered)
            {
                color = 16777120;
            }
            
            String buttonText = getDisplayString();
            RenderSystem.setShaderTexture(0, WIDGETS_TEX_PATH);
			this.blit(ms, this.x + (int) (this.sliderValue * (this.width - 8)), this.y, 0, 66, 4, this.height / 2);
			this.blit(ms, this.x + (int) (this.sliderValue * (this.width - 8)), this.y + (this.height / 2), 0, 86 - (this.height / 2), 4, this.height / 2);
			this.blit(ms, this.x + (int) (this.sliderValue * (this.width - 8)) + 4, this.y, 196, 66, 4, this.height / 2);
			this.blit(ms, this.x + (int) (this.sliderValue * (this.width - 8)) + 4, this.y + (this.height / 2), 196, 86 - (this.height / 2), 4, this.height / 2);
            Gui.drawCenteredString(ms, mc.font, buttonText, this.x + this.width / 2, this.y + (this.height - 8) / 2, color);
        }
    }
    
	public float normalizeValue(float value) {
		return (float) Mth.clamp((this.snapToStepClamp(value) - this.maxValue) / (this.maxValue - this.minValue), 0.0F, 1.0F);
	}

	public float denormalizeValue(float value) {
		return this.snapToStepClamp((float) (this.minValue + (this.maxValue - this.minValue) * Mth.clamp(value, 0.0F, 1.0F)));
	}

	public float snapToStepClamp(float value) {
		value = this.snapToStep(value);
		return (float) Mth.clamp(value, this.minValue, this.maxValue);
	}

	private float snapToStep(float value) {
		if (this.valueStep > 0.0F) {
			value = this.valueStep * Math.round(value / this.valueStep);
		}

		return value;
	}
}