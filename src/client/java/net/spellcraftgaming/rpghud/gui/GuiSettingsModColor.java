package net.spellcraftgaming.rpghud.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;
import org.lwjgl.glfw.GLFW;

@Environment(value=EnvType.CLIENT)
public class GuiSettingsModColor extends GuiScreenTooltip {

	private TextFieldWidgetMod colorCodeField;
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
		this.title = setTitle() + " " + I18n.get("gui.rpg.editor");
	}

	private String setTitle() {
		return I18n.get("name." + this.colorType);
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
		MouseButtonEvent click = new MouseButtonEvent(0.0, 0.0, new MouseButtonInfo(GLFW.GLFW_MOUSE_BUTTON_LEFT, 0));
		this.addRenderableWidget(new GuiSliderMod(GuiSliderMod.EnumColor.RED, this.width / 2 - 75, 40, this.colorR, 0F, 255F, 1F, slider -> {
			slider.onClick(click, false);
		}));
		this.addRenderableWidget(new GuiSliderMod(GuiSliderMod.EnumColor.GREEN, this.width / 2 - 75, 65, this.colorG, 0F, 255F, 1F, slider -> {
			slider.onClick(click, false);
		}));
		this.addRenderableWidget(new GuiSliderMod(GuiSliderMod.EnumColor.BLUE, this.width / 2 - 75, 90, this.colorB, 0F, 255F, 1F, slider -> {
			slider.onClick(click, false);
		}));

		this.colorCodeField = new TextFieldWidgetMod(Minecraft.getInstance().font, TextFieldWidgetMod.ValueType.HEX, this.width / 2 - 74, 115, 147, 20, Component.literal(Settings.intToHexString(this.color, false)));
		this.colorCodeField.setMaxLength(7);

		this.addRenderableWidget(this.colorCodeField);


		String[] colorString = new String[] {"color.red", "color.pink", "color.brown", "color.white", "color.orange", "color.green",
				"color.purple", "color.blue", "color.aqua", "color.black", "color.grey", "color.yellow", "color.green_frost"};
		
		for(int i = 0; i < 7; i++) {
			GuiButtonTooltip guiButtonTooltip = new GuiButtonTooltip(10 + i,this.width / 4 * 3 - 20, 40 + (i * 20), 60, 20, Component.translatable(colorString[i]), button -> {
				actionPerformed((GuiButtonTooltip) button);
			});
			this.addRenderableWidget(guiButtonTooltip);
		}

		for(int i = 0; i < 6; i++) {
			GuiButtonTooltip guiButtonTooltip = new GuiButtonTooltip(17 + i, this.width / 4 * 3 + 60 - 20, 40 + (i * 20), 60, 20, Component.translatable(colorString[i+7]), button -> {
				actionPerformed((GuiButtonTooltip) button);
			});
			this.addRenderableWidget(guiButtonTooltip);
		}

		this.addRenderableWidget(new GuiButtonTooltip(this.width / 2 - 100, this.height / 6 + 168, 125, 20, Component.translatable("gui.done"), button -> {
				setSettingColor();
				Minecraft.getInstance().setScreen(parent);
		}).setTooltip(I18n.get("tooltip.done")));
		this.addRenderableWidget(new GuiButtonTooltip(this.width / 2 + 24, this.height / 6 + 168, 75, 20, Component.translatable("gui.cancel"), button -> {
			Minecraft.getInstance().setScreen(parent);
		}).setTooltip(I18n.get("tooltip.cancel")));
	}
	
	protected void actionPerformed(GuiButtonTooltip button) {
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
			} else if (button.id == 22) {
				setColorTo(HudElement.COLOR_GREEN_FROST);
			} else if (button.id == 250) {
				setSettingColor();
				Minecraft.getInstance().setScreen(this.parent);
			} else if (button.id == 251) {
				Minecraft.getInstance().setScreen(this.parent);
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
		this.colorCodeField.setValue(Settings.intToHexString(this.color, false));
	}

	@Override
	public void tick() {
		super.tick();
		if (this.colorCodeField.isFocused()) {
			String fieldString = this.colorCodeField.getValue();
			if(!fieldString.startsWith("#")) {
				String s = "#" + fieldString;
				if(fieldString.length() >= 7) {
					s = "#";
					for(int i = 0; i < 6; i++) {
						s += fieldString.charAt(i);
					}
				}
				this.colorCodeField.setValue(s);
				fieldString = s;
			}
			
			if (fieldString.length() == 7) {
				if (fieldString.startsWith("#")) {
					if (fieldString.replace("#", "").matches("[0-9A-Fa-f]+")) {
						this.color = (0xFF << 24) | Integer.valueOf(fieldString.replace("#", ""), 16);
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
			this.colorCodeField.setValue(fieldString.toUpperCase());
		} else {
			this.colorCodeField.setValue(Settings.intToHexString(this.color, false));
			this.colorR = ((GuiSliderMod) this.children().get(0)).getValue();
			this.colorG = ((GuiSliderMod) this.children().get(1)).getValue();
			this.colorB = ((GuiSliderMod) this.children().get(2)).getValue();
			int color = (this.colorR << 16) + (this.colorG << 8) + (this.colorB);
			if (color > 0xFFFFFF)
				color = 0xFFFFFF;
			if (color < 0)
				color = 0;
            this.color = (0xFF << 24) | color;
		}
	}

	/**
	 * Fired when a key is typed (except F11 which toggles full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	public boolean keyPressed(KeyEvent event) {
		if (this.colorCodeField.isFocused()) {
			this.colorCodeField.keyPressed(event);
			if (event.key() == 28)
				this.colorCodeField.setFocused(false);
		}
		return super.keyPressed(event);
	}

	@Override
	public boolean mouseReleased(MouseButtonEvent event) {
		for(GuiEventListener child : this.children()) {
			if(child instanceof GuiSliderMod) {
				((GuiSliderMod) child).dragging = false;
			}
		}
		return super.mouseReleased(event);
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		graphics.centeredText(this.font, this.title, this.width / 2, 12, -1);
		graphics.centeredText(this.font, Component.translatable("color.red"), this.width / 2, 40 - 9, -1);
		graphics.centeredText(this.font, Component.translatable("color.green"), this.width / 2, 65 - 9, -1);
		graphics.centeredText(this.font, Component.translatable("color.blue"), this.width / 2, 90 - 9, -1);
		this.colorCodeField.extractRenderState(graphics, mouseX, mouseY, a);
		graphics.centeredText(this.font, Component.translatable("gui.rpg.result"), this.width / 2, 141, -1);

		super.extractRenderState(graphics, mouseX, mouseY, a);
		HudElement.drawCustomBar(graphics, this.width / 2 - 75, 149, 150, 16, 100D, 0, 0, this.color, HudElement.offsetColorPercent(this.color, HudElement.OFFSET_PERCENT), true);
	}
}
