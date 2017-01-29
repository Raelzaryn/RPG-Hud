package net.spellcraftgaming.rpghud.gui;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.spellcraftgaming.rpghud.settings.EnumOptionsMod;

public class GuiButtonTooltip {

	public static HashMap<String, String[]> button = new HashMap<String, String[]>();
	static String[] stringsToDraw = null;
	static int buttonId;
	static GuiScreen screen;
	
	public static void drawTooltip(GuiScreen gui, GuiButton[] buttons){
			Minecraft mc = Minecraft.getMinecraft();
	        int mx = Mouse.getEventX() * gui.width / gui.mc.displayWidth;
	        int my = gui.height - Mouse.getEventY() * gui.height / gui.mc.displayHeight - 1;
	        int i = mx + 5;
	        int j = my + 5;
			String[] strings = stringsToDraw;
			int maxY = 0;
			boolean should = false;
			boolean reverse = false;
			for(int x = 0; x < buttons.length; x++){
		        boolean field_146123_n = mx >= buttons[x].xPosition && my >= buttons[x].yPosition && mx < buttons[x].xPosition + buttons[x].width && my < buttons[x].yPosition + buttons[x].height;
				if(buttons[x].id == buttonId && screen == gui && getHoverState(buttons[x], field_146123_n) == 2) should = true;
			}
			if(!(strings == null) && should){
				int counter = 0;
				for(int id = 0; id < strings.length; id++){
					int x = mc.fontRendererObj.getStringWidth(strings[id]);
					if(maxY < x) maxY = mc.fontRendererObj.getStringWidth(strings[id]);
					counter++;
				}
				if((j + 5 + 12 * counter + 10) > gui.height) reverse = true;
				if(reverse) Gui.drawRect(mx, my - 3 - strings.length * 12 - 2, mx + maxY + 10, my, 0xA0000000);
				else Gui.drawRect(i, j, i + maxY + 10, j + 3 + strings.length * 12 + 2, 0xA0000000);
				for(int id = 0; id < strings.length; id++){
					if(!strings[id].isEmpty()) {
						if(reverse) gui.drawString(mc.fontRendererObj, strings[id], i, my - 2 - 12 * (counter - id - 1) - 10, 0xBBBBBB);
						else gui.drawString(mc.fontRendererObj, strings[id], i + 5, j + 5 + 12 * id, 0xBBBBBB);
					}
				}
			}
		
	}
	
    protected static int getHoverState(GuiButton button, boolean mouseOver)
    {
        int i = 1;

        if (!button.enabled)
        {
            i = 0;
        }
        else if (mouseOver)
        {
            i = 2;
        }

        return i;
    }
    
	public static void drawTooltip(GuiScreen gui, ArrayList<GuiButton> buttons){
			Minecraft mc = Minecraft.getMinecraft();
	        int mx = Mouse.getEventX() * gui.width / gui.mc.displayWidth;
	        int my = gui.height - Mouse.getEventY() * gui.height / gui.mc.displayHeight - 1;
	        int i = mx;
	        int j = my + 8;
			String[] strings = stringsToDraw;
			int maxY = 0;
			boolean should = false;
			boolean reverse = false;
			GuiButton button;
			for(int x = 0; x < buttons.size(); x++){
				button = buttons.get(x);
		        boolean field_146123_n = mx >= button.xPosition && my >= button.yPosition && mx < button.xPosition + button.width && my < button.yPosition + button.height;
				if(button.id == buttonId && screen == gui && getHoverState(button, field_146123_n) == 2) should = true;
			}
			if(!(strings == null) && should){
				int counter = 0;
				for(int id = 0; id < strings.length; id++){
					int x = mc.fontRendererObj.getStringWidth(strings[id]);
					if(maxY < x) maxY = mc.fontRendererObj.getStringWidth(strings[id]);
					counter++;
				}
				i -= maxY/2;
				if((i + maxY + 10) > gui.width) i -= (i + maxY + 10) - gui.width;
				if(i < 0) i = 0;
				
				if((j + 5 + 12 * counter + 10) > gui.height) reverse = true;
				if(reverse) Gui.drawRect(i, my - 3 - strings.length * 12 - 2, mx + maxY + 10, my, 0xA0000000);
				else Gui.drawRect(i, j, i + maxY + 10, j + 3 + strings.length * 12 + 2, 0xA0000000);
				for(int id = 0; id < strings.length; id++){
					if(!strings[id].isEmpty()) {
						if(reverse) gui.drawString(mc.fontRendererObj, strings[id], i, my - 2 - 12 * (counter - id - 1) - 10, 0xBBBBBB);
						else gui.drawString(mc.fontRendererObj, strings[id], i + 5, j + 5 + 12 * id, 0xBBBBBB);
					}
				}
			}
		
	}
	
	public static void setTooltip(GuiScreen gui, GuiButton button){
		stringsToDraw = getToolTip(gui, button.id);
		buttonId = button.id;
		screen = gui;
	}

	private static String[] getToolTip(GuiScreen gui, int buttonId) {
		return button.get(gui.getClass().getSimpleName() + "." + buttonId);
		
	}
	
	public static void initTooltips(){
		String[] strings = new String[4];

		strings = new String[2];
		strings[0] = "Set the color of the";
		strings[1] = "experience bar";
		button.put(GuiSettingsMod.class.getSimpleName() + "." + EnumOptionsMod.COLOR_EXPERIENCE.ordinal(), strings);
		strings = new String[2];
		strings[0] = "Set the color of the";
		strings[1] = "health bar";
		button.put(GuiSettingsMod.class.getSimpleName() + "." + EnumOptionsMod.COLOR_HEALTH.ordinal(), strings);
		strings = new String[2];
		strings[0] = "Set the color of the";
		strings[1] = "jump bar";
		button.put(GuiSettingsMod.class.getSimpleName() + "." + EnumOptionsMod.COLOR_JUMPBAR.ordinal(), strings);
		strings = new String[2];
		strings[0] = "Set the color of the";
		strings[1] = "stamina/food bar";
		button.put(GuiSettingsMod.class.getSimpleName() + "." + EnumOptionsMod.COLOR_STAMINA.ordinal(), strings);
	}
}
