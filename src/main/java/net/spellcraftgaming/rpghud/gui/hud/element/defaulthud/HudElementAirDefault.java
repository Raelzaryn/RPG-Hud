package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementAirDefault extends HudElement {

    public HudElementAirDefault() {
        super(HudElementType.AIR, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return GameData.shouldDrawHUD() && GameData.isPlayerUnderwater();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        int height = scaledHeight + this.settings.getPositionValue(Settings.air_position)[1];
        int width = scaledWidth / 2 - 91 + this.settings.getPositionValue(Settings.air_position)[0];
        int airAmount = GameData.getPlayerAir();
        GlStateManager.disableLighting();
        drawCustomBar(width + 21, height - 80, 141, 10, airAmount / 300.0D * 100.0D, this.settings.getIntValue(Settings.color_air),
                offsetColorPercent(this.settings.getIntValue(Settings.color_air), OFFSET_PERCENT));
    }

}
