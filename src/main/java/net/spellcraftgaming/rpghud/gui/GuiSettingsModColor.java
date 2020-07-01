package net.spellcraftgaming.rpghud.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

@OnlyIn(Dist.CLIENT)
public class GuiSettingsModColor extends GuiScreenTooltip {

	private TextFieldWidget colorCodeField;
	private Screen parent;
	private String colorType;
	private int colorR;
	private int colorG;
	private int colorB;
	private int color;
	private String title = "";

	public GuiSettingsModColor(Screen parent, String color, ITextComponent titleIn) {
		super(titleIn);
		this.parent = parent;
		this.colorType = color;
		setColors();
		this.title = setTitle() + " " + I18n.format("gui.rpg.editor", new Object[0]);
	}

	private String setTitle() {
		return I18n.format("name." + this.colorType, new Object[0]);
	}

	private void setColors() {
		int color = ModRPGHud.instance.settings.getIntValue(this.colorType);

		this.color = color;
		this.colorR = (color >> 16 & 255);
		this.colorG = (color >> 8 & 255);
		this.colorB = (color & 255);
	}

	private void setSettingColor() {
		ModRPGHud.instance.settings.setSetting(this.colorType, this.color);
	}

	@Override
	public void init() {
		this.addButton(new GuiSliderMod(GuiSliderMod.EnumColor.RED, this.width / 2 - 75, 40, this.colorR, 0F, 255F, 1F, slider -> {
			slider.onClick(0, 0);
		}));
		this.addButton(new GuiSliderMod(GuiSliderMod.EnumColor.GREEN, this.width / 2 - 75, 65, this.colorG, 0F, 255F, 1F, slider -> {
			slider.onClick(0, 0);
		}));
		this.addButton(new GuiSliderMod(GuiSliderMod.EnumColor.BLUE, this.width / 2 - 75, 90, this.colorB, 0F, 255F, 1F, slider -> {
			slider.onClick(0, 0);
		}));

		this.colorCodeField = new TextFieldWidget(minecraft.fontRenderer, this.width / 2 - 74, 115, 147, 20, Settings.intToHexString(this.color));
		this.colorCodeField.setText(Settings.intToHexString(this.color));
		this.colorCodeField.setMaxStringLength(7);
		
		this.children.add(colorCodeField);
		
		String[] colorString = new String[] {"color.red", "color.pink", "color.brown", "color.white", "color.orange", "color.green",
				"color.purple", "color.blue", "color.aqua", "color.black", "color.grey", "color.yellow"};
		
		for(int i = 0; i < 6; i++) {
			this.addButton(new GuiButtonTooltip(10 + i,this.width / 4 * 3 - 20, 40 + (i * 20), 60, 20, I18n.format(colorString[i]), button -> {
					actionPerformed(button);
			}));
		}

		for(int i = 0; i < 6; i++) {
			this.addButton(new GuiButtonTooltip(16 + i, this.width / 4 * 3 + 60 - 20, 40 + (i * 20), 60, 20, I18n.format(colorString[i+6]), button -> {
					actionPerformed(button);
			}));
		}

		this.addButton(new GuiButtonTooltip(this.width / 2 - 100, this.height / 6 + 168, 125, 20, I18n.format("gui.done"), button -> {
				setSettingColor();
				minecraft.displayGuiScreen(parent);
		}).setTooltip(I18n.format("tooltip.done", new Object[0])));
		this.addButton(new GuiButtonTooltip(this.width / 2 + 24, this.height / 6 + 168, 75, 20, I18n.format("gui.cancel"), button -> {
				minecraft.displayGuiScreen(parent);
		}).setTooltip(I18n.format("tooltip.cancel", new Object[0])));
	}
	
	protected void actionPerformed(Button b) {
		GuiButtonTooltip button = (GuiButtonTooltip) b;
		if (button.active) {
			if (button.id == 10) {
				setColorTo(HudElement.COLOR_RED);
			} else if (button.id == 11) {
				setColorTo(HudElement.COLOR_PINK);
			} else if (button.id == 12) {
				setColorTo(HudElement.COLOR_BROWN);
			} else if (button.id == 13) {
				setColorTo(HudElement.COLOR_WHITE);
			} else if (button.id == 14) {
				setColorTo(HudElement.COLOR_ORANGE);
			} else if (button.id == 15) {
				setColorTo(HudElement.COLOR_GREEN);
			} else if (button.id == 16) {
				setColorTo(HudElement.COLOR_PURPLE);
			} else if (button.id == 17) {
				setColorTo(HudElement.COLOR_BLUE);
			} else if (button.id == 18) {
				setColorTo(HudElement.COLOR_AQUA);
			} else if (button.id == 19) {
				setColorTo(HudElement.COLOR_BLACK);
			} else if (button.id == 20) {
				setColorTo(HudElement.COLOR_GREY);
			} else if (button.id == 21) {
				setColorTo(HudElement.COLOR_YELLOW);
			} else if (button.id == 250) {
				setSettingColor();
				this.minecraft.displayGuiScreen(this.parent);
			} else if (button.id == 251) {
				this.minecraft.displayGuiScreen(this.parent);
			}
		}
	}

	public void setColorTo(int color) {
		this.color = color;
		this.colorR = (this.color >> 16 & 255);
		((GuiSliderMod) this.buttons.get(0)).sliderValue = (float) this.colorR / 255;
		((GuiSliderMod) this.buttons.get(0)).value = this.colorR;
		this.colorG = (this.color >> 8 & 255);
		((GuiSliderMod) this.buttons.get(1)).sliderValue = (float) this.colorG / 255;
		((GuiSliderMod) this.buttons.get(1)).value = this.colorG;
		this.colorB = (this.color & 255);
		((GuiSliderMod) this.buttons.get(2)).sliderValue = (float) this.colorB / 255;
		((GuiSliderMod) this.buttons.get(2)).value = this.colorB;
		this.colorCodeField.setText(Settings.intToHexString(this.color));
	}

	@Override
	public void tick() {
		super.tick();
		if (this.colorCodeField.isFocused()) {
			if(!this.colorCodeField.getText().startsWith("#")) {
				String s = "#" + this.colorCodeField.getText();
				if(this.colorCodeField.getText().length() >= 7) {
					s = "#";
					for(int i = 0; i < 6; i++) {
						s += this.colorCodeField.getText().charAt(i);
					}
				}
				this.colorCodeField.setText(s);
			}
			
			if (this.colorCodeField.getText().length() == 7) {
				if (this.colorCodeField.getText().startsWith("#")) {
					if (this.colorCodeField.getText().replace("#", "").matches("[0-9A-Fa-f]+")) {
						this.color = Integer.valueOf(this.colorCodeField.getText().replace("#", ""), 16).intValue();
						this.colorR = (this.color >> 16 & 255);
						((GuiSliderMod) this.buttons.get(0)).sliderValue = (float) this.colorR / 255;
						((GuiSliderMod) this.buttons.get(0)).value = this.colorR;
						this.colorG = (this.color >> 8 & 255);
						((GuiSliderMod) this.buttons.get(1)).sliderValue = (float) this.colorG / 255;
						((GuiSliderMod) this.buttons.get(1)).value = this.colorG;
						this.colorB = (this.color & 255);
						((GuiSliderMod) this.buttons.get(2)).sliderValue = (float) this.colorB / 255;
						((GuiSliderMod) this.buttons.get(2)).value = this.colorB;
					}
				} 
			}
			this.colorCodeField.setText(this.colorCodeField.getText().toUpperCase());
		} else {
			this.colorCodeField.setText(Settings.intToHexString(this.color));
			this.colorR = ((GuiSliderMod) this.buttons.get(0)).getValue();
			this.colorG = ((GuiSliderMod) this.buttons.get(1)).getValue();
			this.colorB = ((GuiSliderMod) this.buttons.get(2)).getValue();
			int color = (this.colorR << 16) + (this.colorG << 8) + (this.colorB);
			if (color > 0xFFFFFF)
				color = 0xFFFFFF;
			if (color < 0)
				color = 0;
			this.color = color;
		}

		this.colorCodeField.tick();
	}

	/**
	 * Fired when a key is typed (except F11 which toggles full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
		if (this.colorCodeField.isFocused()) {
			this.colorCodeField.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
			if (p_keyPressed_1_ == 28)
				this.colorCodeField.setFocused2(false);
		}
		return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		FontRenderer fontRenderer = minecraft.fontRenderer;
		this.renderBackground();
		this.drawCenteredString(fontRenderer, this.title, this.width / 2, 12, -1);
		this.drawCenteredString(fontRenderer, I18n.format("color.red", new Object[0]), this.width / 2, 40 - 9, -1);
		this.drawCenteredString(fontRenderer, I18n.format("color.green", new Object[0]), this.width / 2, 65 - 9, -1);
		this.drawCenteredString(fontRenderer, I18n.format("color.blue", new Object[0]), this.width / 2, 90 - 9, -1);
		this.colorCodeField.renderButton(mouseX, mouseY, partialTicks);
		this.drawCenteredString(fontRenderer, I18n.format("gui.rpg.result", new Object[0]) + ": " + Settings.intToHexString(this.color), this.width / 2, 141, -1);
		super.render(mouseX, mouseY, partialTicks);
		HudElement.drawCustomBar(this.width / 2 - 75, 149, 150, 16, 100D, 0, 0, this.color, HudElement.offsetColorPercent(this.color, HudElement.OFFSET_PERCENT), true);
	}
}
