package net.spellcraftgaming.rpghud.gui;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.spellcraftgaming.rpghud.main.ModRPGHud;


public class EditBoxMod extends EditBox {

    /** Variable to contain the (possible) setting of this button */
    public final String enumOptions;
    /** Array that contains the tooltip of this button */
    private String[] tooltip;
    
    private ValueType type;
    public EditBoxMod(Font fontIn, ValueType type, String setting, int xIn, int yIn, int widthIn, int heightIn, Component msg) {
        super(fontIn, xIn, yIn, widthIn, heightIn, msg);
        this.type = type;
        this.enumOptions = setting;
    }
    
    public EditBoxMod(Font fontIn, ValueType type, int xIn, int yIn, int widthIn, int heightIn, Component msg) {
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
    public EditBoxMod setTooltip(String tooltip) {
        this.tooltip = tooltip.split("/n");
        return this;
    }
    
    /**
     * Sets the tooltip to the one the setting of hits button contain.
     * 
     * @return the button
     */
    public EditBoxMod setTooltip() {
        if (this.enumOptions != null)
            return setTooltip(ModRPGHud.instance.settings.getSetting(this.enumOptions).getTooltip());
        return this;
    }
    
    /**
     * Gives the tooltip of this button
     * 
     * @return the Tooltip
     */
    public String[] getTooltipNew() {
        return this.tooltip;
    }

}
