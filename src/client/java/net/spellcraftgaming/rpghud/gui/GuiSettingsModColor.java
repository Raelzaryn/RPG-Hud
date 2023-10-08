package net.spellcraftgaming.rpghud.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class GuiSettingsModColor extends GuiScreenTooltip {

	private TextFieldWidget colorCodeField;
	private Screen parent;
	private String colorType;
	private int colorR;
	private int colorG;
	private int colorB;
	private int color;
	private String title = "";

	public GuiSettingsModColor(Screen parent, String color, Text titleIn) {
		super(titleIn);
		this.parent = parent;
		this.colorType = color;
		setColors();
		this.title = setTitle() + " " + I18n.translate("gui.rpg.editor", new Object[0]);
	}

	private String setTitle() {
		return I18n.translate("name." + this.colorType, new Object[0]);
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
		this.addDrawableChild(new GuiSliderMod(GuiSliderMod.EnumColor.RED, this.width / 2 - 75, 40, this.colorR, 0F, 255F, 1F, slider -> {
			slider.onClick(0, 0);
		}));
		this.addDrawableChild(new GuiSliderMod(GuiSliderMod.EnumColor.GREEN, this.width / 2 - 75, 65, this.colorG, 0F, 255F, 1F, slider -> {
			slider.onClick(0, 0);
		}));
		this.addDrawableChild(new GuiSliderMod(GuiSliderMod.EnumColor.BLUE, this.width / 2 - 75, 90, this.colorB, 0F, 255F, 1F, slider -> {
			slider.onClick(0, 0);
		}));

		this.colorCodeField = new TextFieldWidget(client.textRenderer, this.width / 2 - 74, 115, 147, 20, Text.translatable(Settings.intToHexString(this.color)));
		this.colorCodeField.setText(Settings.intToHexString(this.color));
		this.colorCodeField.setMaxLength(7);
		
		this.addDrawableChild(colorCodeField);
		String[] colorString = new String[] {"color.red", "color.pink", "color.brown", "color.white", "color.orange", "color.green",
				"color.purple", "color.blue", "color.aqua", "color.black", "color.grey", "color.yellow"};
		
		for(int i = 0; i < 6; i++) {
			this.addDrawableChild(new GuiButtonTooltip(10 + i,this.width / 4 * 3 - 20, 40 + (i * 20), 60, 20, Text.translatable(colorString[i]), button -> {
					actionPerformed(button);
			}));
		}

		for(int i = 0; i < 6; i++) {
			this.addDrawableChild(new GuiButtonTooltip(16 + i, this.width / 4 * 3 + 60 - 20, 40 + (i * 20), 60, 20, Text.translatable(colorString[i+6]), button -> {
					actionPerformed(button);
			}));
		}

		this.addDrawableChild(new GuiButtonTooltip(this.width / 2 - 100, this.height / 6 + 168, 125, 20, Text.translatable("gui.done"), button -> {
				setSettingColor();
				client.setScreen(parent);
		}).setTooltip(I18n.translate("tooltip.done", new Object[0])));
		this.addDrawableChild(new GuiButtonTooltip(this.width / 2 + 24, this.height / 6 + 168, 75, 20, Text.translatable("gui.cancel"), button -> {
		    client.setScreen(parent);
		}).setTooltip(I18n.translate("tooltip.cancel", new Object[0])));
	}
	
	protected void actionPerformed(ButtonWidget b) {
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
				this.client.setScreen(this.parent);
			} else if (button.id == 251) {
				this.client.setScreen(this.parent);
			}
		}
	}

	public void setColorTo(int color) {
		this.color = color;
		this.colorR = (this.color >> 16 & 255);
		((GuiSliderMod) this.children().get(0)).sliderValue = (float) this.colorR / 255;
		((GuiSliderMod) this.children().get(0)).value = this.colorR;
		this.colorG = (this.color >> 8 & 255);
		((GuiSliderMod) this.children().get(1)).sliderValue = (float) this.colorG / 255;
		((GuiSliderMod) this.children().get(1)).value = this.colorG;
		this.colorB = (this.color & 255);
		((GuiSliderMod) this.children().get(2)).sliderValue = (float) this.colorB / 255;
		((GuiSliderMod) this.children().get(2)).value = this.colorB;
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
						((GuiSliderMod) this.children().get(0)).sliderValue = (float) this.colorR / 255;
						((GuiSliderMod) this.children().get(0)).value = this.colorR;
						this.colorG = (this.color >> 8 & 255);
						((GuiSliderMod) this.children().get(1)).sliderValue = (float) this.colorG / 255;
						((GuiSliderMod) this.children().get(1)).value = this.colorG;
						this.colorB = (this.color & 255);
						((GuiSliderMod) this.children().get(2)).sliderValue = (float) this.colorB / 255;
						((GuiSliderMod) this.children().get(2)).value = this.colorB;
					}
				} 
			}
			this.colorCodeField.setText(this.colorCodeField.getText().toUpperCase());
		} else {
			this.colorCodeField.setText(Settings.intToHexString(this.color));
			this.colorR = ((GuiSliderMod) this.children().get(0)).getValue();
			this.colorG = ((GuiSliderMod) this.children().get(1)).getValue();
			this.colorB = ((GuiSliderMod) this.children().get(2)).getValue();
			int color = (this.colorR << 16) + (this.colorG << 8) + (this.colorB);
			if (color > 0xFFFFFF)
				color = 0xFFFFFF;
			if (color < 0)
				color = 0;
			this.color = color;
		}

		//this.colorCodeField.tick();
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
				this.colorCodeField.setFocused(false);
		}
		return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
	       for(Element child : this.children()) {
	            if(child instanceof GuiSliderMod) {
	                ((GuiSliderMod) child).dragging = false;
	            }
	        }
	    return super.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public void render(DrawContext dc, int mouseX, int mouseY, float partialTicks) {
		TextRenderer textRenderer = client.textRenderer;
		this.renderBackground(dc);
		dc.drawCenteredTextWithShadow(textRenderer, this.title, this.width / 2, 12, -1);
		dc.drawCenteredTextWithShadow(textRenderer, I18n.translate("color.red", new Object[0]), this.width / 2, 40 - 9, -1);
		dc.drawCenteredTextWithShadow(textRenderer, I18n.translate("color.green", new Object[0]), this.width / 2, 65 - 9, -1);
		dc.drawCenteredTextWithShadow(textRenderer, I18n.translate("color.blue", new Object[0]), this.width / 2, 90 - 9, -1);
		this.colorCodeField.render(dc ,mouseX, mouseY, partialTicks);
		dc.drawCenteredTextWithShadow(textRenderer, I18n.translate("gui.rpg.result", new Object[0]) + ": " + Settings.intToHexString(this.color), this.width / 2, 141, -1);
		super.render(dc, mouseX, mouseY, partialTicks);
		HudElement.drawCustomBar(dc, this.width / 2 - 75, 149, 150, 16, 100D, 0, 0, this.color, HudElement.offsetColorPercent(this.color, HudElement.OFFSET_PERCENT), true);
	}
}
