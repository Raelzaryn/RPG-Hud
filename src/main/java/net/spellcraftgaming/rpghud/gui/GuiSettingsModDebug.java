package net.spellcraftgaming.rpghud.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.EnumOptionsDebugMod;
import net.spellcraftgaming.rpghud.settings.ModDebugSettings;

public class GuiSettingsModDebug extends GuiScreenTooltip {

	private GuiScreen parent;
	private HudElementType type;
	private ModDebugSettings debug;

	public GuiSettingsModDebug(GuiScreen parent, HudElementType type) {
		this.type = type;
		this.debug = ModRPGHud.instance.settingsDebug;
		this.parent = parent;
	}

	@Override
	public void initGui() {
		EnumOptionsDebugMod[] options = EnumOptionsDebugMod.getEnumOptionsOf(this.type);
		int i = 0;
		int j = options.length;
		for (int k = 0; k < j; k++) {
			System.out.println(k);
			System.out.println(options[k]);
			GuiButtonTooltip guismallbutton = new GuiButtonTooltip(k, this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), options[k], this.debug.getKeyBinding(options[k])).setTooltip();
			this.buttonList.add(guismallbutton);
			i++;
		}

		this.buttonList.add(new GuiButtonTooltip(250, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])).setTooltip("tooltip.done"));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if ((button.id < 100) && ((button instanceof GuiButtonTooltip))) {
				this.debug.setOptionValue(((GuiButtonTooltip) button).returnOptionsDebug());
				button.displayString = this.debug.getKeyBinding(((GuiButtonTooltip) button).returnOptionsDebug());
			} else if (button.id == 250) {
				this.mc.displayGuiScreen(new GuiSettingsModSub(this.parent, GuiSettingsModSub.MAIN_DEBUG));
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, this.type.getDisplayName() + " Debug Settings", this.width / 2, 12, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
