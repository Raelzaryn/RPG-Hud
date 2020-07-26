package net.spellcraftgaming.rpghud.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

public class TextFieldWidgetMod extends TextFieldWidget {

    /** Variable to contain the (possible) setting of this button */
    public final String enumOptions;
    /** Array that contains the tooltip of this button */
    private String[] tooltip;
    
    private ValueType type;
    public TextFieldWidgetMod(FontRenderer fontIn, ValueType type, String setting, int xIn, int yIn, int widthIn, int heightIn, String msg) {
        super(fontIn, xIn, yIn, widthIn, heightIn, msg);
        this.type = type;
        this.enumOptions = setting;
    }
    
    public TextFieldWidgetMod(FontRenderer fontIn, ValueType type, int xIn, int yIn, int widthIn, int heightIn, String msg) {
        super(fontIn, xIn, yIn, widthIn, heightIn, msg);
        this.type = type;
        this.enumOptions = null;
    }
    
    public ValueType getValueType() {
        return type;
    }
    
    public enum ValueType{
        DOUBLE,
        POSITION;
    }
    
    @Override
    public void tick() {
        super.tick();
        
    }
    
    /**
     * Sets the tooltip of this button. Should be appended at the constructor.
     * 
     * @param tooltip
     *            The String which'll be the button's tooltip. Line breaks are
     *            managed via the /n symbol combination.
     * @return the button
     */
    public TextFieldWidgetMod setTooltip(String tooltip) {
        this.tooltip = tooltip.split("/n");
        return this;
    }
    
    /**
     * Sets the tooltip to the one the setting of hits button contain.
     * 
     * @return the button
     */
    public TextFieldWidgetMod setTooltip() {
        if (this.enumOptions != null)
            return setTooltip(ModRPGHud.instance.settings.getSetting(this.enumOptions).getTooltip());
        return this;
    }
    
    /**
     * Gives the tooltip of this button
     * 
     * @return the Tooltip
     */
    public String[] getTooltip() {
        return this.tooltip;
    }

}
