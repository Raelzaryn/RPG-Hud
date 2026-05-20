package net.spellcraftgaming.rpghud.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;

@Environment(value=EnvType.CLIENT)
public class GuiSliderMod extends GuiButtonTooltip {
	public enum EnumColor {
		RED,
		GREEN,
		BLUE;
	}
	
    private static final Identifier HANDLE_TEXTURE = Identifier.withDefaultNamespace("widget/slider_handle");
    private static final Identifier HANDLE_HIGHLIGHTED_TEXTURE = Identifier.withDefaultNamespace("widget/slider_handle_highlighted");
    

    private Identifier getHandleTexture() {
        if (this.isHovered() || this.isFocused()) {
            return HANDLE_HIGHLIGHTED_TEXTURE;
        }
        return HANDLE_TEXTURE;
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
    
	public GuiSliderMod(EnumColor color, int x, int y, float value, float minValueIn, float maxValue, float valueStep, OnPress titleIn) {
		this(color, x, y, value, minValueIn, maxValue, valueStep, null, titleIn);
	}
	
	public GuiSliderMod(EnumColor color, int x, int y, float value, float minValueIn, float maxValue, float valueStep, ISlider par, OnPress titleIn) {
		super(x, y, 150, 12, Component.literal(""), titleIn);
		this.color = color;
		this.sliderValue = value / 255;
		this.value = Mth.ceil(value);
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
    public boolean mouseReleased(MouseButtonEvent event) {
        this.dragging = false;
        return super.mouseReleased(event);
    }

    public int getValueInt()
    {
        return (int)Math.round(sliderValue * (maxValue - minValue) + minValue);
    }

    public int getValue() {
        return Mth.ceil(this.value);
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
    protected void renderBackground(MatrixStack matrices, Minecraft client, int mouseX, int mouseY) {
    }*/
    
    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    @Override
    public void onClick(MouseButtonEvent event, boolean doubleClick) {
        this.sliderValue = Math.ceil(Math.clamp(this.sliderValue * 255, 0F, 255F));
        updateSlider(event.x(), event.y());
        this.dragging = true;
    }

    public void updateSlider(double mouseX, double mouseY)
    {
		this.sliderValue = (float) (mouseX - (this.getX() + 4)) / (float) (this.width - 8);

		if (this.sliderValue < 0.0F) {
			this.sliderValue = 0.0F;
		}

		if (this.sliderValue > 1.0F) {
			this.sliderValue = 1.0F;
		}
		this.value = Mth.ceil(Math.clamp(this.sliderValue * 255, 0F, 255F));
    }

    private String getDisplayString() {
        return "#" + Integer.toHexString(getValue()).toUpperCase();
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        if (this.visible) {
            if (this.dragging) {
                updateSlider(mouseX, mouseY);
            }
            int color = 0 + (this.color == EnumColor.RED ? this.value << 16 : this.color == EnumColor.GREEN ? this.value << 8 : this.value);
            HudElement.drawCustomBar(graphics, this.getX(), this.getY(), this.width, this.height, 100D, color, HudElement.offsetColorPercent(color, HudElement.OFFSET_PERCENT));

            int buttonTextColor;

            if (!this.active) {
                buttonTextColor = 10526880;
            } else if (this.isHovered()) {
                buttonTextColor = 16777120;
            } else {
                buttonTextColor = 14737632;
            }

            String buttonText = getDisplayString();
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.getHandleTexture(), this.getX() + (int) (this.sliderValue * (this.width - 8)), this.getY(), 8, this.getHeight());
            graphics.centeredText(Minecraft.getInstance().font, buttonText, this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, buttonTextColor);
        }
    }
    
	public float normalizeValue(float value) {
		return (float) Math.clamp((this.snapToStepClamp(value) - this.maxValue) / (this.maxValue - this.minValue), 0.0F, 1.0F);
	}

	public float denormalizeValue(float value) {
		return this.snapToStepClamp((float) (this.minValue + (this.maxValue - this.minValue) * Math.clamp(value, 0.0F, 1.0F)));
	}

	public float snapToStepClamp(float value) {
		value = this.snapToStep(value);
		return (float) Math.clamp(value, this.minValue, this.maxValue);
	}

	private float snapToStep(float value) {
		if (this.valueStep > 0.0F) {
			value = this.valueStep * Math.round(value / this.valueStep);
		}

		return value;
	}
}