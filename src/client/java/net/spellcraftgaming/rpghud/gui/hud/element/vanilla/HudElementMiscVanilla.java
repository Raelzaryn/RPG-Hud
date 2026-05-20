package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
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
		return !this.mc.debugEntries.isOverlayVisible();
	}
	
	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
		if(this.settings.getBoolValue(Settings.enable_fps)) renderFPS(graphics, scaledWidth, scaledHeight);
		if(this.settings.getBoolValue(Settings.enable_system_time)) renderSystemTime(graphics, scaledWidth, scaledHeight);
		
	}
	
	private void renderFPS(GuiGraphicsExtractor graphics, int scaledWidth, int scaledHeight) {
        float scale = (float) this.settings.getDoubleValue(Settings.fps_scale);
        graphics.pose().scale(scale, scale);
        scale = getInvertedScale(scale);
        String fps = this.mc.getFps() + "";
        int posX = (int) ((1 + this.settings.getPositionValue(Settings.fps_position)[0]) * scale);
        int posY = (int) ((1 + this.settings.getPositionValue(Settings.fps_position)[0]) * scale);
        graphics.text(this.mc.font, fps, posX, posY, this.settings.getIntValue(Settings.color_fps), true);

        graphics.pose().scale(scale, scale);
    }
	
	private void renderSystemTime(GuiGraphicsExtractor graphics, int scaledWidth, int scaledHeight) {
        float scale = (float) this.settings.getDoubleValue(Settings.system_time_scale);
        graphics.pose().scale(scale, scale);
        scale = getInvertedScale(scale);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String time = formatter.format(LocalDateTime.now());
        int posX = Math.round((1 + this.settings.getPositionValue(Settings.system_time_position)[0]) * scale);
        int posY = Math.round((scaledHeight - 1 + this.settings.getPositionValue(Settings.system_time_position)[0]) * scale) - 8;

        if (this.settings.getBoolValue(Settings.enable_system_time_background)) {
            int width = Math.round((2 * scale) + this.mc.font.width(time));
            drawRect(graphics, Math.round(posX - (1 * scale)), Math.round(posY - (1 * scale)) - 1, width, Math.round(10 + (1 * scale)), 0xA0000000);
        }

        graphics.text(this.mc.font, time, posX, posY, this.settings.getIntValue(Settings.color_system_time), true);

        graphics.pose().scale(scale, scale);
    }
    
    public float getInvertedScale(float scale) {
        return 1f / scale;
    }
    
    
}
