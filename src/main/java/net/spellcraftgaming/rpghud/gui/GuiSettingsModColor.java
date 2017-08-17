package net.spellcraftgaming.rpghud.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

public class GuiSettingsModColor extends GuiScreenTooltip {

	private GuiTextField colorCodeField;
	private GuiScreen parent;
	private String colorType;
	private int colorR;
	private int colorG;
	private int colorB;
	private int color;
	private String title = "";

	public GuiSettingsModColor(GuiScreen parent, String color) {
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
	public void initGui() {
		this.buttonList.add(new GuiSliderMod(1, GuiSliderMod.EnumColor.RED, this.width / 2 - 75, 40, this.colorR, 0F, 255F, 1F));
		this.buttonList.add(new GuiSliderMod(2, GuiSliderMod.EnumColor.GREEN, this.width / 2 - 75, 65, this.colorG, 0F, 255F, 1F));
		this.buttonList.add(new GuiSliderMod(3, GuiSliderMod.EnumColor.BLUE, this.width / 2 - 75, 90, this.colorB, 0F, 255F, 1F));

		this.colorCodeField = new GuiTextField(5, GameData.getFontRenderer(), this.width / 2 - 74, 115, 147, 20);
		this.colorCodeField.setText(Settings.intToHexString(this.color));

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
		if (button.enabled) {
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
				this.mc.displayGuiScreen(this.parent);
				ModRPGHud.instance.settings.saveSetting(this.colorType);
			} else if (button.id == 251) {
				this.mc.displayGuiScreen(this.parent);
			}
		}
	}

	public void setColorTo(int color) {
		this.color = color;
		this.colorR = (this.color >> 16 & 255);
		((GuiSliderMod) this.buttonList.get(0)).sliderValue = (float) this.colorR / 255;
		((GuiSliderMod) this.buttonList.get(0)).value = this.colorR;
		this.colorG = (this.color >> 8 & 255);
		((GuiSliderMod) this.buttonList.get(1)).sliderValue = (float) this.colorG / 255;
		((GuiSliderMod) this.buttonList.get(1)).value = this.colorG;
		this.colorB = (this.color & 255);
		((GuiSliderMod) this.buttonList.get(2)).sliderValue = (float) this.colorB / 255;
		((GuiSliderMod) this.buttonList.get(2)).value = this.colorB;
		this.colorCodeField.setText(Settings.intToHexString(this.color));
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (this.colorCodeField.isFocused()) {
			if (this.colorCodeField.getText().length() == 7) {
				if (this.colorCodeField.getText().startsWith("#")) {
					if (this.colorCodeField.getText().replace("#", "").matches("[0-9A-Fa-f]+")) {
						this.color = Integer.valueOf(this.colorCodeField.getText().replace("#", ""), 16).intValue();
						this.colorR = (this.color >> 16 & 255);
						((GuiSliderMod) this.buttonList.get(0)).sliderValue = (float) this.colorR / 255;
						((GuiSliderMod) this.buttonList.get(0)).value = this.colorR;
						this.colorG = (this.color >> 8 & 255);
						((GuiSliderMod) this.buttonList.get(1)).sliderValue = (float) this.colorG / 255;
						((GuiSliderMod) this.buttonList.get(1)).value = this.colorG;
						this.colorB = (this.color & 255);
						((GuiSliderMod) this.buttonList.get(2)).sliderValue = (float) this.colorB / 255;
						((GuiSliderMod) this.buttonList.get(2)).value = this.colorB;
					}
				}
			}
			this.colorCodeField.setText(this.colorCodeField.getText().toUpperCase());
		} else {
			this.colorCodeField.setText(Settings.intToHexString(this.color));
			this.colorR = ((GuiSliderMod) this.buttonList.get(0)).getValue();
			this.colorG = ((GuiSliderMod) this.buttonList.get(1)).getValue();
			this.colorB = ((GuiSliderMod) this.buttonList.get(2)).getValue();
			int color = (this.colorR << 16) + (this.colorG << 8) + (this.colorB);
			if (color > 0xFFFFFF)
				color = 0xFFFFFF;
			if (color < 0)
				color = 0;
			this.color = color;
		}

		this.colorCodeField.updateCursorCounter();
	}

	/**
	 * Fired when a key is typed (except F11 which toggles full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (this.colorCodeField.isFocused()) {
			this.colorCodeField.textboxKeyTyped(typedChar, keyCode);
			if (keyCode == 28)
				this.colorCodeField.setFocused(false);
		}
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		this.colorCodeField.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(GameData.getFontRenderer(), this.title, this.width / 2, 12, -1);
		this.drawCenteredString(GameData.getFontRenderer(), I18n.format("color.red", new Object[0]), this.width / 2, 40 - 9, -1);
		this.drawCenteredString(GameData.getFontRenderer(), I18n.format("color.green", new Object[0]), this.width / 2, 65 - 9, -1);
		this.drawCenteredString(GameData.getFontRenderer(), I18n.format("color.blue", new Object[0]), this.width / 2, 90 - 9, -1);
		this.colorCodeField.drawTextBox();
		this.drawCenteredString(GameData.getFontRenderer(), I18n.format("gui.rpg.result", new Object[0]) + ": " + Settings.intToHexString(this.color), this.width / 2, 141, -1);
		super.drawScreen(mouseX, mouseY, partialTicks);
		HudElement.drawCustomBar(this.width / 2 - 75, 149, 150, 16, 100D, 0, 0, this.color, HudElement.offsetColorPercent(this.color, HudElement.OFFSET_PERCENT), true);
	}
}
