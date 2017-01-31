package net.spellcraftgaming.rpghud.gui;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiScreenTooltip extends GuiScreen {

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		drawTooltip();
	}

	/**
	 * Checks if a tooltip should be rendered and if so renders it on the
	 * screen.
	 */
	private void drawTooltip() {
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreenTooltip gui = null;
		if (mc.currentScreen instanceof GuiScreenTooltip)
			gui = (GuiScreenTooltip) mc.currentScreen;
		else
			return;

		int mouseX = Mouse.getEventX() * gui.width / mc.displayWidth;
		int mouseY = gui.height - Mouse.getEventY() * gui.height / mc.displayHeight - 1;

		boolean shouldRenderTooltip = false;
		GuiButtonTooltip button = null;
		for (int x = 0; x < this.buttonList.size(); x++) {
			GuiButton b = this.buttonList.get(x);
			if (b instanceof GuiButtonTooltip)
				button = (GuiButtonTooltip) b;

			shouldRenderTooltip = mouseX >= button.xPosition && mouseY >= button.yPosition && mouseX < button.xPosition + button.width && mouseY < button.yPosition + button.height;
			if (shouldRenderTooltip)
				x = this.buttonList.size();
		}
		if (shouldRenderTooltip) {
			int posX = mouseX + 5;
			int posY = mouseY + 5;
			int totalWidth = 0;
			boolean reverseY = false;
			String[] tooltip = button.getTooltip();
			if (!(tooltip == null)) {
				int counter = 0;
				for (int id = 0; id < tooltip.length; id++) {
					int width = mc.fontRendererObj.getStringWidth(tooltip[id]);
					if (totalWidth < width)
						totalWidth = mc.fontRendererObj.getStringWidth(tooltip[id]);
					counter++;
				}
				posX -= totalWidth / 2;
				if ((posX + totalWidth + 10) > gui.width)
					posX -= (posX + totalWidth + 10) - gui.width;
				if (posX < 0)
					posX = 0;

				if ((posY + 3 + tooltip.length * 12 + 2) > gui.height)
					reverseY = true;

				if (reverseY)
					drawRect(posX, posY - 3 - tooltip.length * 12 - 2, posX + totalWidth + 10, posY, 0xA0000000);
				else
					drawRect(posX, posY, posX + totalWidth + 10, posY + 3 + tooltip.length * 12 + 2, 0xA0000000);
				for (int id = 0; id < tooltip.length; id++) {
					if (!tooltip[id].isEmpty()) {
						if (reverseY)
							gui.drawString(mc.fontRendererObj, tooltip[id], posX + 5, posY - 2 - 12 * (counter - id - 1) - 10, 0xBBBBBB);
						else
							gui.drawString(mc.fontRendererObj, tooltip[id], posX + 5, posY + 5 + 12 * id, 0xBBBBBB);
					}
				}
			}
		}
	}

}
