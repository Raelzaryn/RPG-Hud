package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Gui;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementMiscVanilla extends HudElement{

	public HudElementMiscVanilla() {
		super(HudElementType.MISC, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.options.renderDebug;
	}
	
	@Override
	public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		if(this.settings.getBoolValue(Settings.enable_fps)) renderFPS(ms, scaledWidth, scaledHeight);
		if(this.settings.getBoolValue(Settings.enable_system_time)) renderSystemTime(ms, scaledWidth, scaledHeight);
		
	}
	
	private void renderFPS(PoseStack ms, int scaledWidth, int scaledHeight) {
		float scale = (float) this.settings.getDoubleValue(Settings.fps_scale);
		ms.scale(scale, scale, scale);
		scale = getInvertedScale(scale);
		String fps = this.mc.fpsString.split(" ")[0];
		int posX = (int) ((1 + this.settings.getPositionValue(Settings.fps_position)[0]) * scale);
		int posY = (int) ((1 + this.settings.getPositionValue(Settings.fps_position)[1]) * scale);
		Gui.drawString(ms, this.mc.font, fps, posX, posY, this.settings.getIntValue(Settings.color_fps));

		ms.scale(scale, scale, scale);
	}
	
	private void renderSystemTime(PoseStack ms, int scaledWidth, int scaledHeight) {
		float scale = (float) this.settings.getDoubleValue(Settings.system_time_scale);
		ms.scale(scale, scale, scale);
		scale = getInvertedScale(scale);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		String time = formatter.format(LocalDateTime.now());
		int posX = Math.round((1 + this.settings.getPositionValue(Settings.system_time_position)[0]) * scale);
		int posY = Math.round((scaledHeight - 1 + this.settings.getPositionValue(Settings.system_time_position)[0]) * scale)-8;
		
		if(this.settings.getBoolValue(Settings.enable_system_time_background)) {
			int width = Math.round((2*scale) + this.mc.font.width(String.valueOf(time)));
			drawRect(ms, Math.round(posX-(1*scale)), Math.round(posY - (1*scale))-1, width, Math.round(10 + (1*scale)), 0xA0000000);
		}
		
		Gui.drawString(ms, this.mc.font, time, posX, posY, this.settings.getIntValue(Settings.color_system_time));
		
		ms.scale(scale, scale, scale);
	}
    
    public float getInvertedScale(float scale) {
        return 1f / scale;
    }
    
    
}
