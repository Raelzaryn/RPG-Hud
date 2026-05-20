package net.spellcraftgaming.rpghud.gui.hud.element.simple;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.spellcraftgaming.rpghud.RPGHudUtils;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(value=EnvType.CLIENT)
public class HudElementLevelSimple extends HudElement{

	public HudElementLevelSimple() {
		super(HudElementType.LEVEL, 0, 0, 0, 0, true);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean checkConditions() {
		return RPGHudUtils.isSurvival();
	}

	@Override
	public void drawElement(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, int scaledWidth, int scaledHeight) {
        String level = String.valueOf(this.mc.player.experienceLevel);

        int width = 12;

        int posX = ((scaledWidth - width) / 2) + this.settings.getPositionValue(Settings.level_position)[0];
        int posY = scaledHeight - 32 - 8 + this.settings.getPositionValue(Settings.level_position)[1];

        if (this.settings.getStringValue(Settings.clock_time_format) == "time.24" || !this.settings.getBoolValue(Settings.render_player_face)) {
            drawRect(graphics, posX, posY, width, 8, 0xA0000000);
        } else {
            drawRect(graphics, 26 + this.settings.getPositionValue(Settings.level_position)[0], posY, width, 7, 0xA0000000);
        }
        float scale = 0.5f;
        if (this.settings.getBoolValue(Settings.debug_number_size)) scale = 0.666666666f;
        float invertedScale = 1f / scale;
        graphics.pose().scale(scale, scale);
        graphics.centeredText(this.mc.font, level, Math.round((posX + (width / 2)) * invertedScale), (int) Math.round(((posY) * invertedScale) + Math.ceil(invertedScale * 4 - 4)), 0xFF80FF20);
        graphics.pose().scale(invertedScale, invertedScale);
    }

}
