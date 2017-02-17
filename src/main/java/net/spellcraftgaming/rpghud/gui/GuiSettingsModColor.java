package net.spellcraftgaming.rpghud.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.ModSettings;

public class GuiSettingsModColor extends GuiScreenTooltip{

	public enum EnumColor{
		EXPERIENCE, BREATH, HEALTH, JUMPBAR, STAMINA, POISON, HUNGER;
	}
	
	private GuiTextField colorCodeField;
	private GuiScreen parent;
	private int parentSubtype;
	private EnumColor colorType;
	private int colorR;
	private int colorG;
	private int colorB;
	private int color;
	private String title = "";
	
	public GuiSettingsModColor(GuiScreen parent, int parentSubtype, EnumColor color) {
		this.parent = parent;
		this.parentSubtype = parentSubtype;
		this.colorType = color;
		setColors();
		this.title = setTitle() + " " + I18n.format("gui.rpg.editor", new Object[0]);
	}
	
	private String setTitle() {
		switch(this.colorType) {
		case EXPERIENCE:
			return I18n.format("name.color_exp", new Object[0]);
		case BREATH:
			return I18n.format("name.color_air", new Object[0]);
		case HEALTH:
			return I18n.format("name.color_health", new Object[0]);
		case JUMPBAR:
			return I18n.format("name.color_jumpbar", new Object[0]);
		case STAMINA:
			return I18n.format("name.color_stamina", new Object[0]);
		case POISON:
			return I18n.format("name.color_poison", new Object[0]);
		case HUNGER:
			return I18n.format("name.color_hunger", new Object[0]);
		default:
			return "";
		}
	}
	
	private void setColors() {
		int color = 0;
		
		switch(this.colorType) {
		case EXPERIENCE:
			color = ModRPGHud.instance.settings.color_experience;
			break;
		case BREATH:
			color = ModRPGHud.instance.settings.color_air;
			break;
		case HEALTH:
			color = ModRPGHud.instance.settings.color_health;
			break;
		case JUMPBAR:
			color = ModRPGHud.instance.settings.color_jumpbar;
			break;
		case STAMINA:
			color = ModRPGHud.instance.settings.color_stamina;
			break;
		case POISON:
			color = ModRPGHud.instance.settings.color_poison;
			break;
		case HUNGER:
			color = ModRPGHud.instance.settings.color_hunger;
			break;
		}
		
		this.color = color;
		this.colorR = (color >> 16 & 255);
		this.colorG = (color >> 8 & 255);
		this.colorB = (color & 255);
	}
	
	private void setSettingColor() {
		switch(this.colorType) {
		case EXPERIENCE:
			ModRPGHud.instance.settings.color_experience = this.color;
			break;
		case BREATH:
			ModRPGHud.instance.settings.color_air = this.color;
			break;
		case HEALTH:
			ModRPGHud.instance.settings.color_health = this.color;
			break;
		case STAMINA:
			ModRPGHud.instance.settings.color_stamina = this.color;
			break;
		case JUMPBAR:
			ModRPGHud.instance.settings.color_jumpbar = this.color;
			break;
		case POISON:
			ModRPGHud.instance.settings.color_poison = this.color;
			break;
		case HUNGER:
			ModRPGHud.instance.settings.color_hunger = this.color;
			break;
		}
	}
	
	@Override
	public void initGui() {
		this.buttonList.add(new GuiSliderColor(1, GuiSliderColor.EnumColor.RED, this.width /2 - 75, 40, this.colorR, 0F, 255F, 1F));
		this.buttonList.add(new GuiSliderColor(2, GuiSliderColor.EnumColor.GREEN, this.width /2 - 75, 65, this.colorG, 0F, 255F, 1F));
		this.buttonList.add(new GuiSliderColor(3, GuiSliderColor.EnumColor.BLUE, this.width /2 - 75, 90, this.colorB, 0F, 255F, 1F));
		
        this.colorCodeField = new GuiTextField(5, this.fontRendererObj, this.width / 2 - 74, 115, 147, 20);
        this.colorCodeField.setText(ModSettings.intToHexString(this.color));
		
        this.buttonList.add(new GuiButtonTooltip(10, this.width / 4 * 3 - 20, 30 + 10, 60, 20, I18n.format("color.red", new Object[0])));
        this.buttonList.add(new GuiButtonTooltip(11, this.width / 4 * 3 - 20, 50 + 10, 60, 20, I18n.format("color.pink", new Object[0])));
        this.buttonList.add(new GuiButtonTooltip(12, this.width / 4 * 3 - 20, 70 + 10, 60, 20, I18n.format("color.brown", new Object[0])));
        this.buttonList.add(new GuiButtonTooltip(13, this.width / 4 * 3 - 20, 90 + 10, 60, 20, I18n.format("color.white", new Object[0])));
        this.buttonList.add(new GuiButtonTooltip(14, this.width / 4 * 3 - 20, 110 + 10, 60, 20, I18n.format("color.orange", new Object[0])));
        this.buttonList.add(new GuiButtonTooltip(15, this.width / 4 * 3 - 20, 130 + 10, 60, 20, I18n.format("color.green", new Object[0])));
        
        this.buttonList.add(new GuiButtonTooltip(16, this.width / 4 * 3 + 60 - 20, 30 + 10, 60, 20, I18n.format("color.purple", new Object[0])));
        this.buttonList.add(new GuiButtonTooltip(17, this.width / 4 * 3 + 60 - 20, 50 + 10, 60, 20, I18n.format("color.blue", new Object[0])));
        this.buttonList.add(new GuiButtonTooltip(18, this.width / 4 * 3 + 60 - 20, 70 + 10, 60, 20, I18n.format("color.aqua", new Object[0])));
        this.buttonList.add(new GuiButtonTooltip(19, this.width / 4 * 3 + 60 - 20, 90 + 10, 60, 20, I18n.format("color.black", new Object[0])));
        this.buttonList.add(new GuiButtonTooltip(20, this.width / 4 * 3 + 60 - 20, 110 + 10, 60, 20, I18n.format("color.grey", new Object[0])));
        this.buttonList.add(new GuiButtonTooltip(21, this.width / 4 * 3 + 60 - 20, 130 + 10, 60, 20, I18n.format("color.yellow", new Object[0])));
        
        this.buttonList.add(new GuiButtonTooltip(250, this.width / 2 - 100, this.height / 6 + 168, 125, 20, I18n.format("gui.done", new Object[0])).setTooltip(I18n.format("tooltip.done", new Object[0])));
        this.buttonList.add(new GuiButtonTooltip(251, this.width / 2 + 24, this.height / 6 + 168, 75, 20, I18n.format("gui.cancel", new Object[0])).setTooltip(I18n.format("tooltip.cancel", new Object[0])));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.enabled) {
			if (button.id == 10) {
				setColorTo(HudElementBarred.COLOR_RED);
			} else if (button.id == 11) {
				setColorTo(HudElementBarred.COLOR_PINK);
			} else if (button.id == 12) {
				setColorTo(HudElementBarred.COLOR_BROWN);
			} else if (button.id == 13) {
				setColorTo(HudElementBarred.COLOR_WHITE);
			} else if (button.id == 14) {
				setColorTo(HudElementBarred.COLOR_ORANGE);
			} else if (button.id == 15) {
				setColorTo(HudElementBarred.COLOR_GREEN);
			} else if (button.id == 16) {
				setColorTo(HudElementBarred.COLOR_PURPLE);
			} else if (button.id == 17) {
				setColorTo(HudElementBarred.COLOR_BLUE);
			} else if (button.id == 18) {
				setColorTo(HudElementBarred.COLOR_AQUA);
			} else if (button.id == 19) {
				setColorTo(HudElementBarred.COLOR_BLACK);
			} else if (button.id == 20) {
				setColorTo(HudElementBarred.COLOR_GREY);
			} else if (button.id == 21) {
				setColorTo(HudElementBarred.COLOR_YELLOW);
			} else if (button.id == 250) {
				setSettingColor();
				this.mc.displayGuiScreen(new GuiSettingsModSub(this.parent, this.parentSubtype));
				ModRPGHud.instance.settings.saveOptions();
			} else if (button.id == 251) {
				this.mc.displayGuiScreen(new GuiSettingsModSub(this.parent, this.parentSubtype));
			}
		}
	}
	
	public void setColorTo(int color) {
		this.color = color;
		this.colorR = (this.color >> 16 & 255);
		((GuiSliderColor)this.buttonList.get(0)).sliderValue = (float) this.colorR / 255;
		((GuiSliderColor)this.buttonList.get(0)).value = this.colorR;
		this.colorG = (this.color >> 8 & 255);
		((GuiSliderColor)this.buttonList.get(1)).sliderValue = (float) this.colorG / 255;
		((GuiSliderColor)this.buttonList.get(1)).value = this.colorG;
		this.colorB = (this.color & 255);
		((GuiSliderColor)this.buttonList.get(2)).sliderValue = (float) this.colorB / 255;
		((GuiSliderColor)this.buttonList.get(2)).value = this.colorB;
		this.colorCodeField.setText(ModSettings.intToHexString(this.color));
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		if(this.colorCodeField.isFocused()) {
            if(this.colorCodeField.getText().length() == 7) {
            	if (this.colorCodeField.getText().startsWith("#")) {
            		if(this.colorCodeField.getText().replace("#", "").matches("[0-9A-Fa-f]+")) {
            			this.color = Integer.valueOf(this.colorCodeField.getText().replace("#", ""), 16).intValue();
            			this.colorR = (this.color >> 16 & 255);
            			((GuiSliderColor)this.buttonList.get(0)).sliderValue = (float) this.colorR / 255;
            			((GuiSliderColor)this.buttonList.get(0)).value = this.colorR;
            			this.colorG = (this.color >> 8 & 255);
            			((GuiSliderColor)this.buttonList.get(1)).sliderValue = (float) this.colorG / 255;
            			((GuiSliderColor)this.buttonList.get(1)).value = this.colorG;
            			this.colorB = (this.color & 255);
            			((GuiSliderColor)this.buttonList.get(2)).sliderValue = (float) this.colorB / 255;
            			((GuiSliderColor)this.buttonList.get(2)).value = this.colorB;
            		}
            	}
            }
            this.colorCodeField.setText(this.colorCodeField.getText().toUpperCase());
		} else {
			this.colorCodeField.setText(ModSettings.intToHexString(this.color));
			this.colorR = ((GuiSliderColor)this.buttonList.get(0)).getValue();
			this.colorG = ((GuiSliderColor)this.buttonList.get(1)).getValue();
			this.colorB = ((GuiSliderColor)this.buttonList.get(2)).getValue();
			int color = (this.colorR << 16) + (this.colorG << 8) + (this.colorB);
			if(color > 0xFFFFFF) color = 0xFFFFFF;
			if(color < 0) color = 0;
			this.color = color;
		}
		
		this.colorCodeField.updateCursorCounter();
	}
	
    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    @Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (this.colorCodeField.isFocused())
        {
            this.colorCodeField.textboxKeyTyped(typedChar, keyCode);
            if(keyCode == 28) this.colorCodeField.setFocused(false);
        }
    }
    
    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    @Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        this.colorCodeField.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 12, -1);
		this.drawCenteredString(this.fontRendererObj, I18n.format("color.red", new Object[0]), this.width / 2, 40 - 9, -1);
		this.drawCenteredString(this.fontRendererObj, I18n.format("color.green", new Object[0]), this.width / 2, 65 - 9, -1);
		this.drawCenteredString(this.fontRendererObj, I18n.format("color.blue", new Object[0]), this.width / 2, 90 - 9, -1);
		this.colorCodeField.drawTextBox();
		this.drawCenteredString(this.fontRendererObj, I18n.format("gui.rpg.result", new Object[0]) + ": " + ModSettings.intToHexString(this.color), this.width / 2, 141, -1);
		super.drawScreen(mouseX, mouseY, partialTicks);
		HudElementBarred.drawCustomBar(this.width / 2 - 75, 149, 150, 16, 100D, 0, 0, this.color, HudElementBarred.offsetColorPercent(this.color, HudElementBarred.OFFSET_PERCENT), true);
	}
}
