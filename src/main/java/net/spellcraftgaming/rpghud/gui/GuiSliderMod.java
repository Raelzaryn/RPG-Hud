package net.spellcraftgaming.rpghud.gui;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
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

    @Nullable
    public ISlider parent = null;

    public boolean drawString = true;

    public GuiSliderMod(int buttonId, EnumColor color, int x, int y, float value, float minValueIn, float maxValue, float valueStep) {
        this(buttonId, color, x, y, value, minValueIn, maxValue, valueStep, null);
    }

    public GuiSliderMod(int buttonId, EnumColor color, int x, int y, float value, float minValueIn, float maxValue, float valueStep, @Nullable ISlider par) {
        super(buttonId, x, y, 150, 12, "");
        this.color = color;
        this.sliderValue = value / 255;
        this.value = (int) Math.ceil(value);
        this.minValue = minValueIn;
        this.maxValue = maxValue;
        this.valueStep = valueStep;
        this.y = y;

        String val;

        if(this.showDecimal) {
            val = Double.toString(this.sliderValue * (maxValue - this.minValue) + this.minValue);
            this.precision = Math.min(val.substring(val.indexOf(".") + 1).length(), 4);
        } else {
            val = Integer.toString((int) Math.round(this.sliderValue * (maxValue - this.minValue) + this.minValue));
            this.precision = 0;
        }

        if(!this.drawString)
            this.displayString = "";
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this
     * button and 2 if it IS hovering over this button.
     */
    @Override
    public int getHoverState(boolean par1) {
        return 0;
    }

    /**
     * Fired when the mouse button is released. Equivalent of
     * MouseListener.mouseReleased(MouseEvent e).
     */
    @Override
    public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
        this.dragging = false;
        return super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
    }

    public int getValueInt() {
        return (int) Math.round(this.sliderValue * (this.maxValue - this.minValue) + this.minValue);
    }

    public int getValue() {
        return (int) Math.ceil(this.value);
    }

    public void setValue(double d) {
        this.sliderValue = (d - this.minValue) / (this.maxValue - this.minValue);
    }

    public interface ISlider {
        void onChangeSliderValue(GuiSliderMod guiSliderMod);
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of
     * MouseListener.mouseDragged(MouseEvent e).
     */
    @Override
    protected void renderBg(Minecraft par1Minecraft, int par2, int par3) {
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of
     * MouseListener.mousePressed(MouseEvent e).
     */
    @Override
    public void onClick(double mouseX, double mouseY) {
        this.sliderValue = Math.ceil(MathHelper.clamp(this.sliderValue * 255, 0F, 255F));
        this.updateSlider(mouseX, mouseY);
        this.dragging = true;
    }

    public void updateSlider(double mouseX, double mouseY) {
        this.sliderValue = (float) (mouseX - (this.x + 4)) / (this.width - 8);

        if(this.sliderValue < 0.0F)
            this.sliderValue = 0.0F;

        if(this.sliderValue > 1.0F)
            this.sliderValue = 1.0F;
        this.value = MathHelper.ceil(MathHelper.clamp(this.sliderValue * 255, 0F, 255F));
    }

    private String getDisplayString() {
        return "#" + Integer.toHexString(this.getValue()).toUpperCase();
    }

    @Override
    public void render(int mouseX, int mouseY, float partial) {
        if(this.visible) {
            if(this.dragging)
                this.updateSlider(mouseX, mouseY);
            Minecraft mc = Minecraft.getInstance();
            int color = 0 + (this.color == EnumColor.RED ? this.value << 16 : this.color == EnumColor.GREEN ? this.value << 8 : this.value);
            HudElement.drawCustomBar(this.x, this.y, this.width, this.height, 100D, color, HudElement.offsetColorPercent(color, HudElement.OFFSET_PERCENT));

            color = 14737632;

            if(this.packedFGColor != 0)
                color = this.packedFGColor;
            else if(!this.enabled)
                color = 10526880;
            else if(this.hovered)
                color = 16777120;

            String buttonText = this.getDisplayString();
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            this.drawTexturedModalRect(this.x + (int) (this.sliderValue * (this.width - 8)), this.y, 0, 66, 4, this.height / 2);
            this.drawTexturedModalRect(this.x + (int) (this.sliderValue * (this.width - 8)), this.y + (this.height / 2), 0, 86 - (this.height / 2), 4,
                    this.height / 2);
            this.drawTexturedModalRect(this.x + (int) (this.sliderValue * (this.width - 8)) + 4, this.y, 196, 66, 4, this.height / 2);
            this.drawTexturedModalRect(this.x + (int) (this.sliderValue * (this.width - 8)) + 4, this.y + (this.height / 2), 196, 86 - (this.height / 2), 4,
                    this.height / 2);
            this.drawCenteredString(mc.fontRenderer, buttonText, this.x + this.width / 2, this.y + (this.height - 8) / 2, color);
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
        if(this.valueStep > 0.0F)
            value = this.valueStep * Math.round(value / this.valueStep);

        return value;
    }
}