package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementMiscVanilla extends HudElement{

	public HudElementMiscVanilla() {
		super(HudElementType.MISC, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return !this.mc.getDebugHud().shouldShowDebugHud();
	}
	
	@Override
	public void drawElement(DrawContext dc, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		if(this.settings.getBoolValue(Settings.enable_fps)) renderFPS(dc, scaledWidth, scaledHeight);
		if(this.settings.getBoolValue(Settings.enable_system_time)) renderSystemTime(dc, scaledWidth, scaledHeight);
		
	}
	
	private void renderFPS(DrawContext dc, int scaledWidth, int scaledHeight) {
		float scale = (float) this.settings.getDoubleValue(Settings.fps_scale);
		dc.getMatrices().scale(scale, scale, scale);
		scale = getInvertedScale(scale);
		String fps = this.mc.fpsDebugString.split(" ")[0];
		int posX = (int) ((1 + this.settings.getPositionValue(Settings.fps_position)[0]) * scale);
		int posY = (int) ((1 + this.settings.getPositionValue(Settings.fps_position)[0]) * scale);
		dc.drawText(this.mc.textRenderer, fps, posX, posY, this.settings.getIntValue(Settings.color_fps), false);

		dc.getMatrices().scale(scale, scale, scale);
	}
	
	private void renderSystemTime(DrawContext dc, int scaledWidth, int scaledHeight) {
		float scale = (float) this.settings.getDoubleValue(Settings.system_time_scale);
		dc.getMatrices().scale(scale, scale, scale);
		scale = getInvertedScale(scale);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		String time = formatter.format(LocalDateTime.now());
		int posX = (int) ((1 + this.settings.getPositionValue(Settings.system_time_position)[0]) * scale);
		int posY = (int) ((scaledHeight - 1 + this.settings.getPositionValue(Settings.system_time_position)[0]) * scale)-8;
		
		if(this.settings.getBoolValue(Settings.enable_system_time_background)) {
			int width = (int) ((2*scale) + this.mc.textRenderer.getWidth(String.valueOf(time)));
			drawRect(dc, (int) (posX-(1*scale)), (int) (posY - (1*scale)), width, (int) (10 + (1*scale)), 0xA0000000);
		}

		dc.drawText(this.mc.textRenderer, time, posX, posY, this.settings.getIntValue(Settings.color_system_time), false);
		
		dc.getMatrices().scale(scale, scale, scale);
	}
    
    public float getInvertedScale(float scale) {
        return 1f / scale;
    }
    
    
}
