package net.spellcraftgaming.rpghud.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;

@SideOnly(Side.CLIENT)
public class GuiSliderMod extends GuiButtonTooltip {
    public enum EnumColor {
        RED,
        GREEN,
        BLUE;
    }

    public float sliderValue;
    private EnumColor color;
    public boolean dragging;
    private final float minValue;
    private final float maxValue;
    private final float valueStep;
    public int value;

    public GuiSliderMod(int buttonId, EnumColor color, int x, int y, float value, float minValueIn, float maxValue, float valueStep) {
        super(buttonId, x, y, 150, 12, "");
        this.color = color;
        this.sliderValue = value / 255;
        this.value = GameData.ceil(value);
        this.minValue = minValueIn;
        this.maxValue = maxValue;
        this.valueStep = valueStep;
        this.y = y;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this
     * button and 2 if it IS hovering over this button.
     */
    @Override
    protected int getHoverState(boolean mouseOver) {
        return 0;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of
     * MouseListener.mouseDragged(MouseEvent e).
     */
    @Override
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if(this.visible) {
            if(this.dragging) {
                this.sliderValue = (float) (mouseX - (this.x + 4)) / (float) (this.width - 8);

                if(this.sliderValue < 0.0F) {
                    this.sliderValue = 0.0F;
                }

                if(this.sliderValue > 1.0F) {
                    this.sliderValue = 1.0F;
                }

                this.displayString = this.getDisplayString();
                this.value = GameData.ceil(GameData.clamp(this.sliderValue * 255, 0F, 255F));
            }
        }
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(this.visible) {
            GameData.bindButtonTextures();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GameData.tryBlendFuncSeparate();
            GameData.blendFunc();
            int color = 0 + (this.color == EnumColor.RED ? this.value << 16 : this.color == EnumColor.GREEN ? this.value << 8 : this.value);
            HudElement.drawCustomBar(this.x, this.y, this.width, this.height, 100D, color, HudElement.offsetColorPercent(color, HudElement.OFFSET_PERCENT));
            this.mouseDragged(mc, mouseX, mouseY);
            int j = 14737632;

            if(this.packedFGColour != 0) {
                j = this.packedFGColour;
            } else if(!this.enabled) {
                j = 10526880;
            } else if(this.hovered) {
                j = 16777120;
            }
            GameData.bindButtonTextures();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.displayString = this.getDisplayString();
            this.drawTexturedModalRect(this.x + (int) (this.sliderValue * (this.width - 8)), this.y, 0, 66, 4, this.height / 2);
            this.drawTexturedModalRect(this.x + (int) (this.sliderValue * (this.width - 8)), this.y + (this.height / 2), 0, 86 - (this.height / 2), 4,
                    this.height / 2);
            this.drawTexturedModalRect(this.x + (int) (this.sliderValue * (this.width - 8)) + 4, this.y, 196, 66, 4, this.height / 2);
            this.drawTexturedModalRect(this.x + (int) (this.sliderValue * (this.width - 8)) + 4, this.y + (this.height / 2), 196, 86 - (this.height / 2), 4,
                    this.height / 2);
            this.drawCenteredString(GameData.getFontRenderer(), this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, j);

        }
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of
     * MouseListener.mousePressed(MouseEvent e).
     */
    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if(super.mousePressed(mc, mouseX, mouseY)) {
            this.sliderValue = (float) (mouseX - (this.x + 4)) / (float) (this.width - 8);

            if(this.sliderValue < 0.0F) {
                this.sliderValue = 0.0F;
            }

            if(this.sliderValue > 1.0F) {
                this.sliderValue = 1.0F;
            }

            this.displayString = this.getDisplayString();
            this.value = GameData.ceil(GameData.clamp(this.sliderValue * 255, 0F, 255F));
            this.dragging = true;
            return true;
        }
        return false;
    }

    private String getDisplayString() {
        return "#" + Integer.toHexString(getValue()).toUpperCase();
    }

    /**
     * Fired when the mouse button is released. Equivalent of
     * MouseListener.mouseReleased(MouseEvent e).
     */
    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
    }

    public float normalizeValue(float value) {
        return GameData.clamp((this.snapToStepClamp(value) - this.maxValue) / (this.maxValue - this.minValue), 0.0F, 1.0F);
    }

    public float denormalizeValue(float value) {
        return this.snapToStepClamp(this.minValue + (this.maxValue - this.minValue) * GameData.clamp(value, 0.0F, 1.0F));
    }

    public float snapToStepClamp(float value) {
        value = this.snapToStep(value);
        return GameData.clamp(value, this.minValue, this.maxValue);
    }

    private float snapToStep(float value) {
        if(this.valueStep > 0.0F) {
            value = this.valueStep * Math.round(value / this.valueStep);
        }

        return value;
    }

    public int getValue() {
        return GameData.ceil(this.value);
    }
}