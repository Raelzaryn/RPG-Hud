package net.spellcraftgaming.rpghud.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

public class GuiSettingsModColor extends GuiScreenTooltip {

	private EditBox colorCodeField;
	private Screen parent;
	private String colorType;
	private int colorR;
	private int colorG;
	private int colorB;
	private int color;
	private String title = "";

	public GuiSettingsModColor(Screen parent, String color, Component titleIn) {
		super(titleIn);
		this.parent = parent;
		this.colorType = color;
		setColors();
		this.title = setTitle() + " " + I18n.get("gui.rpg.editor", new Object[0]);
	}

	private String setTitle() {
		return I18n.get("name." + this.colorType, new Object[0]);
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
		this.addRenderableWidget(new GuiSliderMod(GuiSliderMod.EnumColor.RED, this.width / 2 - 75, 40, this.colorR, 0F, 255F, 1F, slider -> {
			slider.onClick(0, 0);
		}));
		this.addRenderableWidget(new GuiSliderMod(GuiSliderMod.EnumColor.GREEN, this.width / 2 - 75, 65, this.colorG, 0F, 255F, 1F, slider -> {
			slider.onClick(0, 0);
		}));
		this.addRenderableWidget(new GuiSliderMod(GuiSliderMod.EnumColor.BLUE, this.width / 2 - 75, 90, this.colorB, 0F, 255F, 1F, slider -> {
			slider.onClick(0, 0);
		}));

		this.colorCodeField = new EditBox(minecraft.font, this.width / 2 - 74, 115, 147, 20, Component.translatable(Settings.intToHexString(this.color)));
		this.colorCodeField.setValue(Settings.intToHexString(this.color));
		this.colorCodeField.setMaxLength(7);
		
		this.addRenderableWidget(colorCodeField);
		String[] colorString = new String[] {"color.red", "color.pink", "color.brown", "color.white", "color.orange", "color.green",
				"color.purple", "color.blue", "color.aqua", "color.black", "color.grey", "color.yellow"};
		
		for(int i = 0; i < 6; i++) {
			this.addRenderableWidget(new GuiButtonTooltip(10 + i,this.width / 4 * 3 - 20, 40 + (i * 20), 60, 20, Component.translatable(colorString[i]), button -> {
					actionPerformed(button);
			}));
		}

		for(int i = 0; i < 6; i++) {
			this.addRenderableWidget(new GuiButtonTooltip(16 + i, this.width / 4 * 3 + 60 - 20, 40 + (i * 20), 60, 20, Component.translatable(colorString[i+6]), button -> {
					actionPerformed(button);
			}));
		}

		this.addRenderableWidget(new GuiButtonTooltip(this.width / 2 - 100, this.height / 6 + 168, 125, 20, Component.translatable("gui.done"), button -> {
				setSettingColor();
			minecraft.setScreen(parent);
		}).setTooltip(I18n.get("tooltip.done", new Object[0])));
		this.addRenderableWidget(new GuiButtonTooltip(this.width / 2 + 24, this.height / 6 + 168, 75, 20, Component.translatable("gui.cancel"), button -> {
			minecraft.setScreen(parent);
		}).setTooltip(I18n.get("tooltip.cancel", new Object[0])));
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
				this.minecraft.setScreen(this.parent);
			} else if (button.id == 251) {
				this.minecraft.setScreen(this.parent);
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
		this.colorCodeField.setValue(Settings.intToHexString(this.color));
	}

	@Override
	public void tick() {
		super.tick();
		if (this.colorCodeField.isFocused()) {
			if(!this.colorCodeField.getValue().startsWith("#")) {
				String s = "#" + this.colorCodeField.getValue();
				if(this.colorCodeField.getValue().length() >= 7) {
					s = "#";
					for(int i = 0; i < 6; i++) {
						s += this.colorCodeField.getValue().charAt(i);
					}
				}
				this.colorCodeField.setValue(s);
			}
			
			if (this.colorCodeField.getValue().length() == 7) {
				if (this.colorCodeField.getValue().startsWith("#")) {
					if (this.colorCodeField.getValue().replace("#", "").matches("[0-9A-Fa-f]+")) {
						this.color = Integer.valueOf(this.colorCodeField.getValue().replace("#", ""), 16).intValue();
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
			this.colorCodeField.setValue(this.colorCodeField.getValue().toUpperCase());
		} else {
			this.colorCodeField.setValue(Settings.intToHexString(this.color));
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
				this.colorCodeField.setFocus(false);
		}
		return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
	       for(GuiEventListener child : this.children()) {
	            if(child instanceof GuiSliderMod) {
	                ((GuiSliderMod) child).dragging = false;
	            }
	        }
	    return super.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		Font Font = minecraft.font;
		this.renderBackground(ms);
		Gui.drawCenteredString(ms, Font, this.title, this.width / 2, 12, -1);
		Gui.drawCenteredString(ms, Font, I18n.get("color.red", new Object[0]), this.width / 2, 40 - 9, -1);
		Gui.drawCenteredString(ms, Font, I18n.get("color.green", new Object[0]), this.width / 2, 65 - 9, -1);
		Gui.drawCenteredString(ms, Font, I18n.get("color.blue", new Object[0]), this.width / 2, 90 - 9, -1);
		this.colorCodeField.render(ms,mouseX, mouseY, partialTicks);
		Gui.drawCenteredString(ms, Font, I18n.get("gui.rpg.result", new Object[0]) + ": " + Settings.intToHexString(this.color), this.width / 2, 141, -1);
		super.render(ms, mouseX, mouseY, partialTicks);
		HudElement.drawCustomBar(ms, this.width / 2 - 75, 149, 150, 16, 100D, 0, 0, this.color, HudElement.offsetColorPercent(this.color, HudElement.OFFSET_PERCENT), true);
	}
}
